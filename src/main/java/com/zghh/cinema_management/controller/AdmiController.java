package com.zghh.cinema_management.controller;

import com.zghh.cinema_management.bean.Administrator;
import com.zghh.cinema_management.repository.AdministratorRepository;
import com.zghh.cinema_management.utils.ClearSession;
import com.zghh.cinema_management.utils.Message;
import com.zghh.cinema_management.utils.PhoneNumUtil;
import com.zghh.cinema_management.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class AdmiController {
    @Autowired
    private AdministratorRepository administratorRepository;


    //管理员登录页面
    @RequestMapping("login_admi")
    private String login(@Nullable Administrator administrator, Model model, HttpSession session){
        Message msg = new Message();
        //判断表单数据是否为空
        if (administrator.getPhoneNum()!=null){
            //判断是否使用手机号登录
            if (administrator.getPhoneNum().length()==11){
                List<Administrator> administratorByPhoneNum = administratorRepository.findAdministratorByPhoneNum(administrator.getPhoneNum());
                //判断改手机号的用户是否存在
                if (!administratorByPhoneNum.isEmpty()){
                    //判断使用手机号登录的密码是否正确
                    if (administratorByPhoneNum.get(0).getPassword().equals(administrator.getPassword())){
                        //判断账号状态（0：禁用 1：正常）
                        if (administratorByPhoneNum.get(0).getState().equals(1)) {
                            msg.setTime(TimeUtil.time(new Date()));
                            msg.setCode(200);
                            msg.setTitle("登录成功");
                            msg.setMessage("登录成功！");
                            session.setAttribute("admin", administratorByPhoneNum.get(0));
                            model.addAttribute("msg", msg);
                            return "redirect:allFilmPage";
                        }else {
                            msg.setTime(TimeUtil.time(new Date()));
                            msg.setCode(500);
                            msg.setTitle("账号禁用");
                            msg.setMessage("对不起！你的账号已被禁用。");
                            model.addAttribute("msg",msg);
                            return "login_admi";
                        }
                    }else {
                        msg.setTime(TimeUtil.time(new Date()));
                        msg.setCode(500);
                        msg.setTitle("登录失败");
                        msg.setMessage("密码错误！");
                        model.addAttribute("msg",msg);
                        return "login_admi";
                    }
                }else {
                    msg.setTime(TimeUtil.time(new Date()));
                    msg.setCode(500);
                    msg.setTitle("手机号错误");
                    msg.setMessage("用户不存在！");
                    model.addAttribute("msg",msg);
                    return "login_admi";
                }
                //判断是否使用账号登录
            }else if (administrator.getPhoneNum().length()==17){
                List<Administrator> administratorByAccount = administratorRepository.findAdministratorByAccount(administrator.getPhoneNum());
                //判断该账号的用户是否存在
                if (!administratorByAccount.isEmpty()){
                    //判断使用账号登录的密码是否正确
                    if (administratorByAccount.get(0).getPassword().equals(administrator.getPassword())){
                        //判断账号状态（0：禁用 1：正常）
                        if (administratorByAccount.get(0).getState().equals(1)) {
                            msg.setTime(TimeUtil.time(new Date()));
                            msg.setCode(200);
                            msg.setTitle("登录成功");
                            msg.setMessage("登录成功！");
                            session.setAttribute("admin", administratorByAccount.get(0));
                            model.addAttribute("msg", msg);
                            return "redirect:allFilmPage";
                        }else {
                            msg.setTime(TimeUtil.time(new Date()));
                            msg.setCode(500);
                            msg.setTitle("账号禁用");
                            msg.setMessage("对不起！你的账号已被禁用。");
                            model.addAttribute("msg",msg);
                            return "login_admi";
                        }
                    }else {
                        msg.setTime(TimeUtil.time(new Date()));
                        msg.setCode(500);
                        msg.setTitle("登录失败");
                        msg.setMessage("密码错误！");
                        model.addAttribute("msg",msg);
                        return "login_admi";
                    }
                }else {
                    msg.setTime(TimeUtil.time(new Date()));
                    msg.setCode(500);
                    msg.setTitle("账号错误");
                    msg.setMessage("用户不存在！");
                    model.addAttribute("msg",msg);
                    return "login_admi";
                }
            }else {
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(500);
                msg.setTitle("账号格式错误");
                msg.setMessage("请输入正确的手机号或账号进行登录！");
                model.addAttribute("msg",msg);
                return "login_admi";
            }
        }else {
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(100);
            msg.setTitle("登录为空");
            msg.setMessage("请登录！");
            model.addAttribute("msg",msg);
            return "login_admi";
        }
    }
    //管理员注册页面
    @RequestMapping("regist_admiPage")
    private String regist(@Nullable Administrator administrator,Model model){
        Message msg = new Message();
        //判断表单数据是否为空
        if (administrator.getPhoneNum()!=null){
            if (PhoneNumUtil.isMobile(administrator.getPhoneNum())) {
                List<Administrator> administratorByPhoneNum = administratorRepository.findAdministratorByPhoneNum(administrator.getPhoneNum());
                if (administratorByPhoneNum.isEmpty()) {
                    String s = TimeUtil.timeTip(new Date());//17位账号
                    administrator.setAccount(s);
                    administrator.setState(1);
                    Administrator save = administratorRepository.save(administrator);
                    msg.setTime(TimeUtil.time(new Date()));
                    msg.setCode(200);
                    msg.setTitle("注册成功");
                    msg.setMessage("注册成功！你的账号为：" + save.getAccount());
                } else {
                    msg.setTime(TimeUtil.time(new Date()));
                    msg.setCode(500);
                    msg.setTitle("电话号已注册");
                    msg.setMessage("该电话号码已被注册！");
                }
            }else {
                msg.setTime(TimeUtil.time(new Date()));
                msg.setCode(500);
                msg.setTitle("电话号格式错误");
                msg.setMessage("请输入正确的11位电话号码！");
            }
        }else {
            msg.setTime(TimeUtil.time(new Date()));
            msg.setCode(100);
            msg.setTitle("注册为空");
            msg.setMessage("请注册！");
        }
        model.addAttribute("msg",msg);
        return "regist_admi";
    }
    @RequestMapping("exit")
    private String exit(HttpServletRequest request){
        ClearSession.clear(request);
        return "login_admi";
    }
}
