package com.zghh.cinema_management.controller;

import com.zghh.cinema_management.bean.Administrator;
import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.repository.FilmRepository;
import com.zghh.cinema_management.utils.Message;
import com.zghh.cinema_management.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*影片控制器*/
@Controller
public class FilmController {
    @Autowired
    private FilmRepository filmRepository;
    /*跳转至影片列表页面*/
    @RequestMapping("/")
    private String toFilmListPage(Model model){
        List<Film> all = filmRepository.findAll();
        model.addAttribute("films",all);
        return "index";
    }
    /*添加影片页面跳转*/
    @RequestMapping("addFilmPage")
    private String toAddFilmPage(@Nullable Film film, MultipartFile image, Model model){
        Message msg = new Message();
        //判断表单数据是否为空
        if (film.getFilmName()!=null){
            //根据片名查询影片
            List<Film> filmByFilmName = filmRepository.findFilmByFilmName(film.getFilmName());
            if (!image.isEmpty()) {
                //判断数据库中是否存在同名的影片
                if (!filmByFilmName.isEmpty()) {
                    //判断同名电影的导演是否一致
                    if (filmByFilmName.get(0).getDirector().equals(film.getDirector())) {
                        msg.setTime(TimeUtil.time(new Date()));
                        msg.setCode(500);
                        msg.setTitle("film为重复");
                        msg.setMessage("该影片已存在，请不要重复添加！");
                    } else {
                        FileLoadController fileLoadController = new FileLoadController();
                        String s = fileLoadController.saveImage(image, film.getFilmName());
                        if (s!=null) {
                            //设置封面
                            film.setCover(s);
                            Film save = filmRepository.save(film);
                            msg.setTime(TimeUtil.time(new Date()));
                            msg.setCode(200);
                            msg.setTitle("film添加成功");
                            msg.setMessage("影片《" + save.getFilmName() + "》,添加成功！");
                        }else {
                            msg.setTime(TimeUtil.time(new Date()));
                            msg.setCode(500);
                            msg.setTitle("上传图片错误");
                            msg.setMessage("影片封面上传错误,添加失败！");
                        }
                    }
                } else {
                    FileLoadController fileLoadController = new FileLoadController();
                    String s = fileLoadController.saveImage(image, film.getFilmName());
                    if (s!=null) {
                        film.setCover(s);
                        Film save = filmRepository.save(film);
                        msg.setTime(TimeUtil.time(new Date()));
                        msg.setCode(200);
                        msg.setTitle("film添加成功");
                        msg.setMessage("影片《" + save.getFilmName() + "》,添加成功！");
                    }else {
                        msg.setTime(TimeUtil.time(new Date()));
                        msg.setCode(500);
                        msg.setTitle("上传图片错误");
                        msg.setMessage("影片封面上传错误,添加失败！");
                    }
                }
            }else {
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(100);
                msg.setTitle("image为null");
                msg.setMessage("图片上传为空！");
            }
        }else {
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(100);
            msg.setTitle("film为null");
            msg.setMessage("请添加影片！");
        }
        model.addAttribute("msg",msg);
        model.addAttribute("active","2");
        return "addFilm";
    }
    //跳转至全部影片管理界面
    @RequestMapping("allFilmPage")
    private String allFilm(Model model, HttpServletRequest request){
//        Message message = new Message();
//        Administrator admin = (Administrator) request.getSession().getAttribute("admin");
        List<Film> all = filmRepository.findAll();
        model.addAttribute("filmList",all);
        model.addAttribute("active","1");
        return "allFilm";
    }

    //跳转至修改电影的界面
    @GetMapping("updateFilmPage")
    private String updateFilmPage(@Nullable Integer id,Model model){
        Message msg = new Message();
        if (id!=null) {
            Optional<Film> byId = filmRepository.findById(id);
            //判断修改影片的id是否存在
            if (byId.isPresent()) {
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(100);
                msg.setTitle("跳转至修改电影的界面");
                msg.setMessage("请修改影片《" + byId.get().getFilmName() + "》");
                model.addAttribute("updateFilm",byId.get());
            } else {
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(500);
                msg.setTitle("修改电影的id错误");
                msg.setMessage("修改电影的id错误！");
            }
        }else {
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(500);
            msg.setTitle("修改电影的id为null");
            msg.setMessage("错误，修改影片的id为空");
        }
        model.addAttribute("msg",msg);
        return "updateFilm";
    }
    //修改影片表单提交
    @PostMapping("updateFilm")
    private String updateFilm(@Nullable Film film, MultipartFile image, Model model){
        Message msg = new Message();
        if (film.getId()!=null){
            //旧的电影信息
            Optional<Film> byId = filmRepository.findById(film.getId());
            //判断修改影片的id是否存在
            if (byId.isPresent()) {
                //判断是否上传封面
                if (!image.isEmpty()) {
                    FileLoadController fileLoadController = new FileLoadController();
                    //保存新的图片
                    String s = fileLoadController.saveImage(image, film.getFilmName());
                    if (s!=null){
                        //将新的图片路径存入新的电影信息中
                        film.setCover(s);
                        //删除旧的图片
                        fileLoadController.deleteImg(byId.get().getCover());
                        //修改影片
                        Film film1 = filmRepository.saveAndFlush(film);
                        msg.setTime(TimeUtil.time(new Date()));
                        msg.setCode(200);
                        msg.setTitle("修改电影成功");
                        msg.setMessage("成功，修改电影《"+film1.getFilmName()+"》成功！");
                    }else {
                        msg.setTime(TimeUtil.time(new Date()));
                        msg.setCode(500);
                        msg.setTitle("上传图片错误");
                        msg.setMessage("影片封面上传错误,修改失败！");
                    }
                } else {
                    film.setCover(byId.get().getCover());
                    //修改影片
                    Film film1 = filmRepository.saveAndFlush(film);
                    msg.setTime(TimeUtil.time(new Date()));
                    msg.setCode(200);
                    msg.setTitle("修改电影成功");
                    msg.setMessage("成功，修改电影《"+film1.getFilmName()+"》成功！");
                }
            }else {
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(500);
                msg.setTitle("修改电影的id错误");
                msg.setMessage("失败，修改电影的id错误！");
            }
        }else {
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(500);
            msg.setTitle("修改电影的数据为空");
            msg.setMessage("失败，修改影片的数据为空");
        }
        model.addAttribute("msg",msg);
        return "updateFilm";
    }
    @PostMapping("deleteFilmById")
    private String deleteFilmById(@Nullable Integer id,Model model){
        Message msg = new Message();
        if (id!=null){
            Optional<Film> byId = filmRepository.findById(id);
            //判断该id的影片是否存在
            if (byId.isPresent()){
                filmRepository.deleteById(id);
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(200);
                msg.setTitle("删除电影成功");
                msg.setMessage("成功，删除影片《"+byId.get().getFilmName()+"》成功!");
            }else {
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(500);
                msg.setTitle("删除电影的id为错误");
                msg.setMessage("失败，删除影片的id为错误，没有找到该影片!");
            }
        }else {
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(500);
            msg.setTitle("删除电影的id为空");
            msg.setMessage("失败，删除影片的id为空!");
        }
        model.addAttribute("msg",msg);
        return "updateFilm";
    }
}
