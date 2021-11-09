package com.zghh.cinema_management.controller;

import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.RowPiece;
import com.zghh.cinema_management.bean.Screens;
import com.zghh.cinema_management.repository.FilmRepository;
import com.zghh.cinema_management.repository.RowPieceRepository;
import com.zghh.cinema_management.repository.ScreensRepository;
import com.zghh.cinema_management.utils.Message;
import com.zghh.cinema_management.utils.SeatingInfoUtil;
import com.zghh.cinema_management.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;


/*排片管理控制器*/
@Controller
public class rowPieceController {
    @Autowired
    private RowPieceRepository rowPieceRepository;
    @Autowired
    private ScreensRepository screensRepository;
    @Autowired
    private FilmRepository filmRepository;

    /*跳转至排片列表页面*/
    @GetMapping("allRowPiecePage")
    private String allRowPiecePage(Model model){
        List<RowPiece> all = rowPieceRepository.findAll();
        for (RowPiece r:all){
            //统计座位情况（剩余座位和已选座位）
            String statistical = SeatingInfoUtil.statistical(r.getSitState());
            //将统计的座位情况重新设置
            r.setSitState(statistical);
        }
        model.addAttribute("active",5);
        model.addAttribute("rowPieceList",all);
        return "allRowPiece";
    }
    //跳转至添加排片的页面
    @RequestMapping("addRowPiecePage")
    private String addRowPiecePage(Model model){
        Message msg = new Message();
        List<Film> all1 = filmRepository.findAll();
        List<Screens> all2 = screensRepository.findAll();
        if (all1.isEmpty()){
            msg.setCode(404);
            msg.setMessage("没有可以排片的影片，请先添加电影！");
        }else if (all2.isEmpty()){
            msg.setCode(404);
            msg.setMessage("没有可以排片的影厅，请先添加影厅！");
        }else {
            Iterator<Film> iterator1 = all1.iterator();
            Iterator<Screens> iterator2 = all2.iterator();
            while (iterator1.hasNext()){
                if (iterator1.next().getState().equals(1)){
                }else {
                    iterator1.remove();
                }
            }
            while (iterator2.hasNext()){
                if (iterator2.next().getState().equals(1)){
                }else {
                    iterator2.remove();
                }
            }
            if (all1.isEmpty()){
                msg.setCode(404);
                msg.setMessage("没有[正在热映]的影片,请设置电影的状态！");
            }else if (all2.isEmpty()){
                msg.setCode(404);
                msg.setMessage("没有[已启用]的影厅,请设置影厅的状态！");
            }else {
                model.addAttribute("films",all1);
                model.addAttribute("screens",all2);
            }
        }
        model.addAttribute("msg",msg);
        model.addAttribute("active",6);
        return "addRowPiece";
    }

    //添加排片信息的表单提交
    @PostMapping("addRowPiece")
    private String addRowPiece(@Nullable RowPiece rowPiece,Model model){
        Message msg = new Message();
        //判断表单提交数据是否为null
        if (rowPiece.getFilmId()!=null&&rowPiece.getScreensId()!=null) {//提交不为空
            Optional<Film> byId = filmRepository.findById(rowPiece.getFilmId());
            Optional<Screens> byId1 = screensRepository.findById(rowPiece.getScreensId());
            //判断排片电影是否存在
            if (byId.isPresent()) {//排片影片存在
                //判断排片影厅是否存在
                if (byId1.isPresent()) {//排片影厅存在
                    //设置排片的电影名称
                    rowPiece.setFilmName(byId.get().getFilmName());
                    //设置排片的影厅名称
                    rowPiece.setScreensName(byId1.get().getScreensName());
                    //初始化座位状态
                    String s = SeatingInfoUtil.initSeating(byId1.get().getSeatingNum());
                    //设置排片的座位情况
                    rowPiece.setSitState(s);
                    List<RowPiece> rowPieceByScreensId = rowPieceRepository.findRowPieceByScreensId(rowPiece.getScreensId());
                    //判断是否有相同影厅的排片
                    if (!rowPieceByScreensId.isEmpty()) {//有相同影厅的排片
                        //检查同一影厅的排片的时间间隔
                        boolean b_s = true;//是否间隔3小时
                        for (RowPiece r : rowPieceByScreensId) {
                            int i = TimeUtil.minutesDifference(r.getPlayingTime(), rowPiece.getPlayingTime());
                            if (i <= 180) {
                                b_s = false;
                                break;
                            }
                        }
                        if (b_s) {//同一影厅的排片的时间间隔大于3小时
                            RowPiece save = rowPieceRepository.save(rowPiece);
                            msg.setCode(200);
                            msg.setMessage("排片成功！《" + save.getFilmName() + "》于" + save.getPlayingTime() + "在[" + save.getScreensName() + "]放映");
                        } else {//同一影厅的排片的时间间隔小于3小时
                            msg.setCode(500);
                            msg.setMessage("影厅播放时间冲突，同一影厅排片的时间间隔应大于3小时");
                           }
                    }else {//没有同一影厅的排片
                        RowPiece save = rowPieceRepository.save(rowPiece);
                        msg.setCode(200);
                        msg.setMessage("排片成功！《" + save.getFilmName() + "》于" + save.getPlayingTime() + "在[" + save.getScreensName() + "]放映");
                    }
                } else {
                    msg.setCode(500);
                    msg.setMessage("排片失败，没有找到影厅");
                }

            } else {
                msg.setCode(500);
                msg.setMessage("排片失败，没有找到影片");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("排片失败，请选择电影和影厅");
        }
        model.addAttribute("msg",msg);
        return "addRowPiece";
    }
    //跳转至修改排片的界面
    @GetMapping("updateRowPiecePage")
    private String updateRowPiecePage(@Nullable Integer id,Model model){
        Message msg = new Message();
        if (id!=null){
            Optional<RowPiece> byId = rowPieceRepository.findById(id);
            if (byId.isPresent()){
                model.addAttribute("RowPiece",byId.get());
                msg.setCode(100);
                msg.setMessage("请修改《"+byId.get().getFilmName()+"》影片于"+byId.get().getPlayingTime()+"在["+byId.get().getScreensName()+"]影厅的排片");
            }else {
                msg.setCode(500);
                msg.setMessage("错误，没有找到要修改的排片信息");
            }
        }else {
           msg.setCode(500);
           msg.setMessage("错误，修改排片的id为为空");
        }
        model.addAttribute("msg",msg);
        return "updateRowPiece";
    }
    //检查同一影厅的排片时间间隔
    private boolean checkScreens(Integer screensId,RowPiece rowPiece){
        //同一影厅的排片的时间间隔是否大于3小时
        boolean b=true;//大于3小时
        List<RowPiece> rowPieceByScreensId = rowPieceRepository.findRowPieceByScreensId(screensId);
        if (!rowPieceByScreensId.isEmpty()) {//有相同影厅的排片
            //移除相同排片
            boolean remove = rowPieceByScreensId.remove(rowPiece);
            for (RowPiece r : rowPieceByScreensId) {
                if (r.getId()!=rowPiece.getId()) {//不是同一排片
                    int i = TimeUtil.minutesDifference(r.getPlayingTime(), rowPiece.getPlayingTime());
                    if (i <= 180) {
                        b = false;
                        break;
                    }
                }
            }
        }
        return b;
    }
    //修改排片的表单提交
    @PostMapping("updateRowPiece")
    private String updateRowPiece(@Nullable RowPiece rowPiece,Model model){
        Message msg = new Message();
        if (rowPiece.getId()!=null){
            Optional<RowPiece> byId = rowPieceRepository.findById(rowPiece.getId());
            //查询验证修改的排片是否存在
            if (byId.isPresent()){//排片信息存在
                //检查同一影厅的排片时间间隔
                boolean b = checkScreens(rowPiece.getScreensId(), rowPiece);
                if (b) {//同一影厅的排片时间间隔大于3小时
                    //设置座位信息
                    rowPiece.setSitState(byId.get().getSitState());
                    RowPiece saveAndFlush = rowPieceRepository.saveAndFlush(rowPiece);
                    msg.setCode(200);
                    msg.setMessage(saveAndFlush.getPlayingTime() + "播放《" + saveAndFlush.getFilmName() + "》的排片信息修改成功");
                }else {//同一影厅的排片时间间隔小于3小时
                    msg.setCode(500);
                    msg.setMessage("影厅播放时间冲突，同一影厅排片的时间间隔应大于3小时");
                }
            }else {
                msg.setCode(500);
                msg.setMessage("修改失败，排片信息错误!");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("修改失败，排片数据提交为空");
        }
        model.addAttribute("msg",msg);
        return "updateRowPiece";
    }
    //删除排片信息
    @PostMapping("deleteRowPiece")
    private String deleteRowPiece(@Nullable Integer id, Model model){
        Message msg = new Message();
        //判断表单提交数据是否为null
        if (id!=null){
            Optional<RowPiece> byId = rowPieceRepository.findById(id);
            //判断排片是否存在
            if (byId.isPresent()){
                rowPieceRepository.deleteById(id);
                msg.setCode(200);
                msg.setMessage("排片信息删除成功!");
            }else {
                msg.setCode(500);
                msg.setMessage("删除的影片不存在！");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("删除排片id为null");
        }
        model.addAttribute("msg",msg);
        return "updateRowPiece";
    }


}
