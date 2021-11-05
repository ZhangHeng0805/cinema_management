package com.zghh.cinema_management.controller;



import com.google.gson.Gson;
import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.Screens;
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

@Controller
public class screensController {

    @Autowired
    ScreensRepository screensRepository;


    //查看所用影厅信息
    @RequestMapping("allScreensPage")
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
    //跳转到影厅页面
    @GetMapping("addScreensPage")
    public String toAddScreensPage(@Nullable Screens screens,Model model){

        return "addScreens";
    }
    //表单的提交
    @PostMapping("addScreens")
    public String toAddScreens(@Nullable Screens screens,Model model){
        Message msg = new Message();
        List<Screens> ByScreensName =screensRepository.findScreensByScreensName(screens.getScreensName());
        if (ByScreensName.isEmpty()){
            Gson gson = new Gson();
            List<Character> SeatingInfoList=new ArrayList<>();
            for (int i=1;i<=screens.getSeatingNum();i++){
                    SeatingInfoList.add('t');
            }
            String s = gson.toJson(SeatingInfoList);
            screens.setSeatingInfo(s);
            screensRepository.saveAndFlush(screens);
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(200);
            msg.setTitle("添加");
            msg.setMessage("添加成功");
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

}
