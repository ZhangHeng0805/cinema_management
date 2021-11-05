package com.zghh.cinema_management.controller;

import com.zghh.cinema_management.bean.RowPiece;
import com.zghh.cinema_management.repository.FilmRepository;
import com.zghh.cinema_management.repository.RowPieceRepository;
import com.zghh.cinema_management.repository.ScreensRepository;
import com.zghh.cinema_management.utils.SeatingInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


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

}
