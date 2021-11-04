package com.zghh.cinema_management.controller;

import com.google.gson.Gson;
import com.zghh.cinema_management.bean.*;
import com.zghh.cinema_management.repository.*;
import com.zghh.cinema_management.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private ScreensRepository screensRepository;
    @Autowired
    private RowPieceRepository rowPieceRepository;
    @Autowired
    private OrderRepository orderRepository;


//    @ResponseBody
    @RequestMapping("test")
    public String hello(Model model){
        Administrator administrator = administrator();//管理员对象
        Film film = film();//电影对象
        Members members = members();//会员对象
        Screens screens = screens();//影厅对象
        RowPiece rowPiece = rowPiece();//排片对象
        Order order = order();

//        Administrator save = administratorRepository.saveAndFlush(administrator);//管理员添加
//        Film save = filmRepository.saveAndFlush(film);//电影添加
//        Members saveAndFlush = membersRepository.saveAndFlush(members);//会员添加
//        Screens saveAndFlush = screensRepository.saveAndFlush(screens);//影厅添加
//        RowPiece saveAndFlush = rowPieceRepository.saveAndFlush(rowPiece);//排片添加
//        Order saveAndFlush = orderRepository.saveAndFlush(order);//订单添加

//        System.out.println(saveAndFlush);
        List<Film> filmList = filmRepository.findAll();
//        System.out.println(save);
        model.addAttribute("films",filmList);
        return "index";
    }
    //编造订单数据
    private Order order(){
        Order order = new Order();
        order.setAccountId(1);
        String timeTip = TimeUtil.timeTip(new Date());
//        System.out.println(timeTip);
        order.setOrderNum(timeTip);
        order.setOrderPrice(36.6);
        order.setOrderState(1);
        order.setOrderTime(TimeUtil.time(new Date()));
        order.setRowpieceId(1);
        order.setSitNum(12);
        return order;
    }

    //编造排片数据
    private RowPiece rowPiece(){
        RowPiece rowPiece = new RowPiece();
        rowPiece.setFilmId(1);
        rowPiece.setScreensId(1);
        rowPiece.setPlayingTime("2021-11-11");
        rowPiece.setFare(36.6);
        Gson gson = new Gson();
        List<Character> SeatingInfoList=new ArrayList<>();
        for (int i=1;i<=100;i++){
            if (i==12||i==8) {
                SeatingInfoList.add('f');
            }else {
                SeatingInfoList.add('t');
            }
        }
        String s = gson.toJson(SeatingInfoList);
        rowPiece.setSitState(s);
        return rowPiece;
    }
    //编造影厅信息
    private Screens screens(){
        Screens screens = new Screens();
        screens.setScreensName("光明顶");
        screens.setSeatingNum(100);
        screens.setType("3D");
        screens.setState(1);
        Gson gson = new Gson();
        List<Character> SeatingInfoList=new ArrayList<>();
        for (int i=1;i<=100;i++){
            if (i==12||i==8) {
                SeatingInfoList.add('f');
            }else {
                SeatingInfoList.add('t');
            }
        }
        String s = gson.toJson(SeatingInfoList);
//        System.out.println(s.length());
        screens.setSeatingInfo(s);
        return screens;
    }

    private List<Character> jsonToList(String json){
        List<Character> l=new ArrayList<>();
        if (json.startsWith("[")&&json.endsWith("]")){
            String[] split = json.split("\",\"");
            System.out.println(split);
            for (String s:split){
                if (s.length()==1){
                    char[] chars = s.toCharArray();
                    l.add(chars[0]);
                }else {
                    String s1 = s.replace("[\"", "").replace("\"]", "");
                    char[] chars = s1.toCharArray();
                    System.out.println(chars);
                    l.add(chars[0]);
                }
            }
            return l;
        }else {
            return l;
        }
    }

    //编造会员信息
    private Members members(){
        Members members = new Members();
        members.setAccount("zhangheng");
        members.setAvatar("/static/images/chat_t.png");
        members.setNickname("星曦向荣");
        members.setGender(1);
        members.setBirthday("2000-8-5");
        members.setPassword("10120812");
        members.setPhoneNum("13733430842");
        members.setEmail("zhangheng.0805@qq.com");
        members.setBalance(999.99);
        members.setState(1);
        return members;

    }
    //编造电影数据
    private Film film(){
        Film film = new Film();
        film.setCover("/files/FilmImage/你好李焕英.jpg");
        film.setDirector("贾玲");
        film.setFilmName("你好李焕英");
        film.setFilmType("剧情，搞笑，奇幻");
        film.setCountry("中国大陆");
        film.setMainActor("贾玲，张小斐，沈腾，陈赫，刘佳");
        film.setFilmIntroduced("该片根据2016年的同名小品及贾玲亲身经历改编，讲述了刚考上大学的女孩贾晓玲经历了一次人生的大起大落后情绪失控，随后意外穿越回到了二十世纪八十年代，与20年前正值青春的母亲李焕英相遇的故事");
        film.setMovieRatings(8.2);
        film.setState(0);
        film.setReleaseTime("2021-02-12");
        return film;
    }
    //编造管理员数据
    private Administrator administrator(){
        Administrator administrator = new Administrator();
        administrator.setAccount("zhangheng");
        administrator.setName("张恒");
        administrator.setPhoneNum("13733430842");
        administrator.setPassword("10120812");
        administrator.setState(1);
        return administrator;
    }

}
