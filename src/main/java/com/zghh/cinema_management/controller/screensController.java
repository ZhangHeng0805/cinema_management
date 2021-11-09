package com.zghh.cinema_management.controller;



import com.google.gson.Gson;
import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.RowPiece;
import com.zghh.cinema_management.bean.Screens;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class screensController {

    @Autowired
    ScreensRepository screensRepository;
    @Autowired
    private RowPieceRepository rowPieceRepository;


    //跳转至影厅列表界面
    @GetMapping("allScreensPage")
    public String toScreensPage(Model model){

        List<Screens> screensRepositoryAll = screensRepository.findAll();
        for (Screens screens:screensRepositoryAll){
            //统计座位情况（剩余座位和已选座位）
            String statistical = SeatingInfoUtil.statistical(screens.getSeatingInfo());
            //将统计的座位情况重新设置
            screens.setSeatingInfo(statistical);
        }
        model.addAttribute("active",3);
        model.addAttribute("screensList",screensRepositoryAll);
        return "allScreens";
    }

    //添加影厅
    //跳转到添加影厅页面
    @GetMapping("addScreensPage")
    public String toAddScreensPage(Model model){
        Message msg = new Message();
        msg.setCode(100);
        msg.setMessage("请添加影厅信息！");
        model.addAttribute("active",4);
        model.addAttribute("msg",msg);
        return "addScreens";
    }
    //添加影厅的表单的提交
    @PostMapping("addScreens")
    public String toAddScreens(@Nullable Screens screens,Model model){
        Message msg = new Message();
        List<Screens> ByScreensName =screensRepository.findScreensByScreensName(screens.getScreensName());
        if (ByScreensName.isEmpty()){
            String s = SeatingInfoUtil.initSeating(screens.getSeatingNum());
            screens.setSeatingInfo(s);
            Screens save = screensRepository.save(screens);
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(200);
            msg.setTitle("添加");
            msg.setMessage("影厅<"+save.getScreensName()+">添加成功");
        }
        else {
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(500);
            msg.setTitle("名字重复");
            msg.setMessage("影厅名称重复！");

        }

        model.addAttribute("msg",msg);
        model.addAttribute("active",4);
        return "addScreens";
    }
    //跳转至修改影厅的界面
    @GetMapping("updateScreensPage")
    private String updateScreensPage(@Nullable Integer id, Model model){
        Message msg = new Message();
        if (id!=null){
            Optional<Screens> byId = screensRepository.findById(id);
            if (byId.isPresent()){
                model.addAttribute("screens",byId.get());
                msg.setCode(100);
                msg.setMessage("请修改影厅["+byId.get().getScreensName()+"]");
            }else {
                msg.setCode(500);
                msg.setMessage("修改的影厅信息不存在");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("修改影厅的id为空");
        }
        model.addAttribute("msg",msg);
        return "updateScreens";
    }
    //检查影厅名称是否同名
    private boolean checkScreensName(String ScreensName,Screens screens){
        boolean b=false;
        List<Screens> screensByScreensName = screensRepository.findScreensByScreensName(ScreensName);
        if (!screensByScreensName.isEmpty()){
            for (Screens s:screensByScreensName){
                if (!s.getId().equals(screens.getId())) {
                    if (s.getScreensName().equals(screens.getScreensName())) {
                        b = true;
                        break;
                    }
                }
            }
        }
        return b;
    }
    @PostMapping("updateScreens")
    private String updateScreens(@Nullable Screens screens, Model model){
        Message msg = new Message();
        //判断表单提交数据是否为空
        if (screens.getId()!=null){
            Optional<Screens> byId = screensRepository.findById(screens.getId());
            //判断修改的影厅信息是否存在
            if (byId.isPresent()){
                boolean b = checkScreensName(screens.getScreensName(), screens);
                //检查影厅是否同名
                if (!b){//没有同名的影厅
                    //设置影厅座位情况
                    screens.setSeatingInfo(byId.get().getSeatingInfo());
                    Screens saveAndFlush = screensRepository.saveAndFlush(screens);
                    msg.setCode(200);
                    msg.setMessage("影厅修改成功：["+saveAndFlush.getScreensName()+"]");
                }else {
                    msg.setCode(500);
                    msg.setMessage("影厅名称重复，影厅名称已存在");
                }
            }else {
                msg.setCode(500);
                msg.setMessage("没有找到该影厅的信息,修改影厅信息错误!");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("修改影厅表单提交为null");
        }
        model.addAttribute("msg",msg);
        return "updateScreens";
    }
    @PostMapping("deleteScreens")
    private String deleteScreens(@Nullable Integer id, Model model){
        Message msg = new Message();
        //判断删除id是否为空
        if (id!=null){
            Optional<Screens> byId = screensRepository.findById(id);
            //判断删除的影厅是否存在
            if (byId.isPresent()){
                List<RowPiece> rowPieceByScreensId = rowPieceRepository.findRowPieceByScreensId(id);
                if (rowPieceByScreensId.isEmpty()) {
                    screensRepository.deleteById(id);
                    msg.setCode(200);
                    msg.setMessage("影厅[" + byId.get().getScreensName() + "]删除成功！");
                }else {
                    msg.setCode(500);
                    msg.setMessage("该影厅已排片，无法删除！");
                }
            }else {
                msg.setCode(500);
                msg.setMessage("删除的影厅不存在");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("删除影厅id为null");
        }
        model.addAttribute("msg",msg);
        return "updateScreens";
    }

}
