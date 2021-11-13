package com.zghh.cinema_management.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zghh.cinema_management.bean.*;
import com.zghh.cinema_management.repository.*;
import com.zghh.cinema_management.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//会员用户控制器
@Controller
public class MemberController {
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private CodeRepository codeRepository;
    @Autowired
    private LoggerRepository loggerRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    //跳转至会员登录界面
    @RequestMapping("login_memberPage")
    private String login_memberPage(Model model){

        return "login_member";
    }
    //跳转至会员注册界面
    @GetMapping("regist_memberPage")
    private String regist_memberPage(Model model){
        Message msg = new Message();
        msg.setCode(100);
        msg.setMessage("欢迎加入会员家族!");
        model.addAttribute("msg",msg);
        return "regist_member";
    }
    //会员注册表单提交
    @PostMapping("regist_member")
    private String regist_member(@Nullable Members members,@Nullable MultipartFile image, Model model){
        Message msg = new Message();
        //判断注册表单提交是否为空
        if (members.getPhoneNum()!=null){
            //判断手机号码格式是否正确
            if(PhoneNumUtil.isMobile(members.getPhoneNum())){
                List<Members> membersByPhoneNum = membersRepository.findMembersByPhoneNum(members.getPhoneNum());
                if (membersByPhoneNum.isEmpty()) {
                    //判断图像是否为空
                    if (!image.isEmpty()) {//图像不为空
                        FileLoadController fileLoadController = new FileLoadController();
                        String s = fileLoadController.saveImage(image, members.getPhoneNum(), "MemberImg");
                        //判断头像上传是否成功
                        if (s != null) {//上传成功
                            members.setAvatar(s);//设置头像保存路径
                            String timeTip = TimeUtil.timeTip(new Date());//生成17位时间戳
                            String account = "m" + timeTip;//设置18位账号
                            members.setAccount(account);//设置账号
                            members.setState(1);//设置账号状态
                            members.setBalance(0.00);//设置余额
                            Members save = membersRepository.save(members);
                            log.info("会员注册:"+save.getPhoneNum()+",用户名:"+save.getNickname());
                            msg.setCode(200);
                            msg.setMessage("恭喜：" + save.getNickname() + "注册成功！你的账号为：" + save.getAccount() + "。<a href='/login_memberPage'>去登录</a>");
                        } else {
                            msg.setCode(500);
                            msg.setMessage("头像上传失败，请注意图像规格");
                        }
                    } else {
                        msg.setCode(500);
                        msg.setMessage("用户头像上传为空");
                    }
                }else {
                    msg.setCode(500);
                    msg.setMessage("此手机号已注册");
                }
            }else {
                msg.setCode(500);
                msg.setMessage("手机号码错误，请输入正确的手机号码");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("错误，注册信息为null");

        }
        model.addAttribute("msg",msg);
        return "regist_member";
    }
    //登录表单提交
    @PostMapping("login_member")
    private String login_member(@Nullable Members members, Model model, HttpSession session){
        Message msg = new Message();
        //判断登录表单是否为空
        if (members.getAccount()!=null){
            //判断是手机号登录还是账号登录
            if (PhoneNumUtil.isMobile(members.getAccount())){//手机号登录
                List<Members> membersByPhoneNum = membersRepository.findMembersByPhoneNum(members.getAccount());
                //判断此用户是否存在
                if (!membersByPhoneNum.isEmpty()){
                    //检查密码
                    Members members1 = membersByPhoneNum.get(0);
                    if (members1.getPassword().equals(members.getPassword())){
                        //检查账号状态
                        if (members1.getState().equals(1)){

                            //登陆成功
                            session.setAttribute("member",members1);
                            log.info("会员登录:"+members1.getPhoneNum()+",用户名:"+members1.getNickname());
                            msg.setCode(200);
                            msg.setMessage("登录成功");
                            model.addAttribute("msg",msg);
                            return "redirect:/";
                        }else {
                            msg.setCode(500);
                            msg.setMessage("对不起,您的账号已被封禁");
                            model.addAttribute("msg",msg);
                            return "login_member";
                        }
                    }else {
                        msg.setCode(500);
                        msg.setMessage("密码错误!");
                        model.addAttribute("msg",msg);
                        return "login_member";
                    }
                }else {
                    msg.setCode(500);
                    msg.setMessage("对不起，没有此用户");
                    model.addAttribute("msg",msg);
                    return "login_member";
                }
            }else {//使用账号登录
                //判断使用的账号格式是否正确
                if (members.getAccount().startsWith("m")&&members.getAccount().length()==18){
                    List<Members> membersByAccount = membersRepository.findMembersByAccount(members.getAccount());
                    //判断账户是否存在
                    if (!membersByAccount.isEmpty()){
                        //检查密码
                        if (membersByAccount.get(0).getPassword().equals(members.getPassword())) {
                            //检查账号状态
                            if (membersByAccount.get(0).getState().equals(1)){
                                //登陆成功
                                session.setAttribute("member",membersByAccount.get(0));
                                msg.setCode(200);
                                msg.setMessage("登录成功");
                                model.addAttribute("msg",msg);
                                return "redirect:/";
                            }else {
                                msg.setCode(500);
                                msg.setMessage("对不起,您的账号已被封禁");
                                model.addAttribute("msg",msg);
                                return "login_member";
                            }
                        }else {
                            msg.setCode(500);
                            msg.setMessage("密码错误!");
                            model.addAttribute("msg",msg);
                            return "login_member";
                        }
                    }else {
                        msg.setCode(500);
                        msg.setMessage("对不起，没有此用户");
                        model.addAttribute("msg",msg);
                        return "login_member";
                    }
                }else {
                    msg.setCode(500);
                    msg.setMessage("输入的账号格式错误");
                    model.addAttribute("msg",msg);
                    return "login_member";
                }
            }
        }else {
            msg.setCode(500);
            msg.setMessage("登录信息为null");
            model.addAttribute("msg",msg);
            return "login_member";
        }
    }
    //退出会员登录
    @RequestMapping("exit_member")
    private String exit(HttpServletRequest request){
        ClearSession.clear(request,"member");
        return "redirect:/";
    }
    //跳转至全部会员列表界面
    @GetMapping("allMemberPage")
    private String allMember(Model model){
        Message msg = new Message();
        List<Members> all = membersRepository.findAll();
        msg.setCode(100);
        msg.setMessage("一共有"+all.size()+"个会员用户");
        model.addAttribute("msg",msg);
        model.addAttribute("memberList",all);
        model.addAttribute("active",8);
        return "allMember";
    }
    //修改账号状态
    @PostMapping("updateMemberState")
    private String updateMemberState(@Nullable Integer id, Model model){
        Message msg = new Message();
        if (id!=null) {
            Optional<Members> byId = membersRepository.findById(id);
            if (byId.isPresent()){
                if (byId.get().getState().equals(0)){
                    byId.get().setState(1);
                }else if (byId.get().getState().equals(1)){
                    byId.get().setState(0);
                }
                Members saveAndFlush = membersRepository.saveAndFlush(byId.get());
                String sta=null;
                if (saveAndFlush.getState().equals(0)){
                    sta="封禁";
                }else if (saveAndFlush.getState().equals(1)){
                    sta="解封";
                }
                msg.setCode(200);
                msg.setMessage("用户:"+saveAndFlush.getNickname()+"，账号状态已被"+sta);
            }else {
                msg.setCode(500);
                msg.setMessage("修改用户不存在");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("修改id为null");
        }
        model.addAttribute("msg",msg);
        return "allMember";
    }
    //跳转至充值金额界面
    @GetMapping("RechargePage")
    private String RechargePage(Model model,HttpSession session){
        Message msg = new Message();
        Members member = (Members) session.getAttribute("member");
        msg.setCode(100);
        msg.setMessage(member.getNickname()+",你的余额为:"+member.getBalance());
        List<VerificationCode> all = codeRepository.findAll();
        int i = Message.RandomNum(0, all.size());
        model.addAttribute("codeId",all.get(i).getId());
        model.addAttribute("msg",msg);
        model.addAttribute("active",2);
        return "Recharge";
    }
    //充值表单提交
    @PostMapping("Recharge")
    private String Recharge(@Nullable VerificationCode verificationCode,Model model,HttpServletRequest request){
        Message msg = new Message();
        if (verificationCode.getId()!=null&&verificationCode.getCode()!=null){
            Optional<VerificationCode> byId = codeRepository.findById(verificationCode.getId());
            if (byId.isPresent()){
                if (byId.get().getCode().equals(verificationCode.getCode())){
                    Members member = (Members) request.getSession().getAttribute("member");
                    Optional<Members> byId1 = membersRepository.findById(member.getId());
                    //判断用户是否存在
                    if (byId1.isPresent()){
                        //根据账号和充值操作查询操作日志
                        Optional<com.zghh.cinema_management.bean.Logger> byAccountAndType = loggerRepository.findByAccountAndType(byId1.get().getAccount(), 0);
                        if (byAccountAndType.isPresent()){
                            int time = TimeUtil.minutesDifference(TimeUtil.time(new Date()), byAccountAndType.get().getTime());
                            //判断充值操作间隔是否大于12小时
                            if (time>=720){//大于12小时
                                byAccountAndType.get().setTime(TimeUtil.time(new Date()));
                                com.zghh.cinema_management.bean.Logger logger = loggerRepository.saveAndFlush(byAccountAndType.get());
                                log.info(logger.toString());
                                Double balance = byId1.get().getBalance();
                                balance += 100;
                                byId1.get().setBalance(balance);
                                Members saveAndFlush = membersRepository.saveAndFlush(byId1.get());
                                request.getSession().setAttribute("member", saveAndFlush);
                                msg.setCode(200);
                                msg.setMessage("成功充值100元");
                            }else {//小于12小时
                                msg.setCode(500);
                                msg.setMessage("充值时间需要间隔12小时，请"+(720-time)+"分钟后再来操作");
                            }
                        }else {//没有操作日志
                            //操作日志
                            com.zghh.cinema_management.bean.Logger logger = new com.zghh.cinema_management.bean.Logger();
                            logger.setAccount(member.getAccount());//设置操作者账号
                            logger.setName(member.getNickname());//设置操作者名称
                            logger.setTel(member.getPhoneNum());//设置操作者手机号
                            logger.setType(0);//设置操作类型
                            logger.setTime(TimeUtil.time(new Date()));//设置操作时间
                            com.zghh.cinema_management.bean.Logger save = loggerRepository.save(logger);//保存操作日志
                            log.info(save.toString());
                            Double balance = byId1.get().getBalance();
                            balance += 100;
                            byId1.get().setBalance(balance);
                            Members saveAndFlush = membersRepository.saveAndFlush(byId1.get());
                            request.getSession().setAttribute("member", saveAndFlush);
                            msg.setCode(200);
                            msg.setMessage("成功充值100元");
                        }
                    }else {
                        msg.setCode(500);
                        msg.setMessage("用户信息错误");
                    }
                }else {
                    msg.setCode(500);
                    msg.setMessage("验证码错误");
                }
            }else {
                msg.setCode(500);
                msg.setMessage("验证信息错误");
            }
        }else {
            msg.setCode(500);
            msg.setMessage("提交信息为null");
        }
        model.addAttribute("msg",msg);
        return "Recharge";
    }


}
