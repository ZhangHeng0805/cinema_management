package com.zghh.cinema_management.controller;

import com.zghh.cinema_management.bean.Members;
import com.zghh.cinema_management.bean.Order;
import com.zghh.cinema_management.bean.RowPiece;
import com.zghh.cinema_management.bean.Screens;
import com.zghh.cinema_management.repository.MembersRepository;
import com.zghh.cinema_management.repository.OrderRepository;
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

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//订单控制器
@Controller
public class orderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RowPieceRepository rowPieceRepository;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private ScreensRepository screensRepository;
    //跳转至选择排片的页面
    @GetMapping("chooseRowPiece")
    private String chooseRowPiece(@Nullable Integer filmId, Model model){
        Message msg = new Message();
        if (filmId!=null){
            List<RowPiece> rowPieceByFilmId = rowPieceRepository.findRowPieceByFilmId(filmId);
            if (!rowPieceByFilmId.isEmpty()){
                for (RowPiece r:rowPieceByFilmId){
                    String statistical = SeatingInfoUtil.statistical(r.getSitState());
                    r.setSitState(statistical);
                    Optional<Screens> byId = screensRepository.findById(r.getScreensId());
                    if (byId.isPresent()){
                        r.setScreensName(r.getScreensName()+" ["+byId.get().getType()+"]");
                    }
                }
                model.addAttribute("rowList",rowPieceByFilmId);
                msg.setCode(200);
                msg.setMessage("一共找到该影片的"+rowPieceByFilmId.size()+"场排片放映。");
            }else {
                msg.setCode(404);
                msg.setMessage("对不起，此电影还没有安排放映!请稍等");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("错误，filmId为null");
        }
        model.addAttribute("msg",msg);
        return "chooseRowPiece";
    }
    //跳转至选座位的页面
    @GetMapping("chooseSitPage")
    private String chooseSitPage(@Nullable Integer rowPieceId, Model model){
        Message msg = new Message();
        if (rowPieceId!=null){
            Optional<RowPiece> byId = rowPieceRepository.findById(rowPieceId);
            if (byId.isPresent()){
                String statistical = SeatingInfoUtil.statistical(byId.get().getSitState());
                msg.setCode(200);
                msg.setMessage(statistical);
                model.addAttribute("rowPiece",byId.get());
            }else {
                msg.setCode(500);
                msg.setMessage("选座的排片信息没有找到！");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("选座的排片id为null");
        }
        model.addAttribute("msg",msg);
        return "chooseSit";
    }

    //选座表单提交
    @PostMapping("chooseSit")
    private String chooseSit(@Nullable Order order, Model model){
        Message msg = new Message();
        //判断选座表单是否为null
        if (order.getOrderPrice()!=null){
            List<Integer> list = SeatingInfoUtil.subSitToList(order.getSitNum());
            //判断选座信息是否正确
            if (!list.isEmpty()){
                //判断选座数量是否在4个以内
                if (list.size()<=4){
                    Optional<RowPiece> byId = rowPieceRepository.findById(order.getRowpieceId());
                    //判断排片信息是否正确
                    if (byId.isPresent()) {
                        double v = list.size() * byId.get().getFare();
                        //判断总价格是否正确
                        if (order.getOrderPrice().equals(v)){
                            Optional<Members> byId1 = membersRepository.findById(order.getAccountId());
                            //判断用户信息是否正确
                            if (byId1.isPresent()){
                                String timeTip = TimeUtil.timeTip(new Date());
                                //设置订单编号
                                order.setOrderNum("d"+timeTip+"m"+order.getAccountId()+"r"+order.getRowpieceId());
                                //设置订单时间
                                order.setOrderTime(TimeUtil.time(new Date()));
                                //设置订单状态
                                order.setOrderState(0);
                                //检查所选座位是否有人
                                boolean b = SeatingInfoUtil.checkSit(byId.get().getSitState(), list);
                                if (b) {
                                    //修改座位情况
                                    String s = SeatingInfoUtil.updateSit(byId.get().getSitState(), list, 'f');
                                    //判断修改座位情况是否成功
                                    if (s != null) {
                                        //将新的座位情况设置到排片中
                                        byId.get().setSitState(s);
                                        //修改排片中的座位情况
                                        rowPieceRepository.saveAndFlush(byId.get());
                                        //保存订单
                                        Order save = orderRepository.save(order);
                                        msg.setCode(200);
                                        msg.setMessage("订单号：" + save.getOrderNum() + " ；总金额："+save.getOrderPrice()+"元；订单状态：未完成,待支付。");
                                    } else {
                                        msg.setCode(500);
                                        msg.setMessage("错误，座位信息情况修改失败");
                                    }
                                }else {
                                   msg.setCode(500);
                                   msg.setMessage("所选座位已有人，请勿重复提交");
                                }
                            }else {
                                msg.setCode(500);
                                msg.setMessage("用户信息异常");
                            }
                        }else {
                            msg.setCode(500);
                            msg.setMessage("订单价格异常");
                        }
                    }else {
                        msg.setCode(500);
                        msg.setMessage("排片信息错误，排片信息不存在");
                    }
                }else {
                    msg.setCode(500);
                    msg.setMessage("每次最多只能选4个座位");
                }
            }else {
                msg.setCode(500);
                msg.setMessage("提交选座信息错误");
            }
//            System.out.println(order);
        }else {
            msg.setCode(500);
            msg.setMessage("请选择座位，不要提交空座位！");
        }
        model.addAttribute("msg",msg);
        return "chooseSit";
    }
    //跳转至所有订单界面
    @GetMapping("allOrderPage")
    private String allOrderPage(Model model ){
        Message msg = new Message();
        List<Order> all = orderRepository.findAll();
        for (Order o:all){
            List<Integer> list = SeatingInfoUtil.subSitToList(o.getSitNum());
            String s="";
            for (int i:list){
                s+=(i+1)+"号 ";
            }
            o.setSitNum(s);
        }
        msg.setCode(100);
        msg.setMessage("一共有"+all.size()+"个订单");
        model.addAttribute("orderList",all);
        model.addAttribute("msg",msg);
        return "allOrder";
    }
}
