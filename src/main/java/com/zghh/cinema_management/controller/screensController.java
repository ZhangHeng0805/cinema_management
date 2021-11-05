package com.zghh.cinema_management.controller;


import com.zghh.cinema_management.bean.Screens;
import com.zghh.cinema_management.repository.ScreensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class screensController {

    @Autowired
    ScreensRepository screensRepository;


    //查看所用影厅信息
    @RequestMapping("allScreensPage")
    public String toScreensPage(Model model){

        List<Screens> screensRepositoryAll = screensRepository.findAll();
        model.addAttribute("screensList",screensRepositoryAll);
        return "allScreens";
    }

    //添加影厅
    @RequestMapping("addScreensPage")
    public String toAddScreens(){
        return "addScreens";
    }

}
