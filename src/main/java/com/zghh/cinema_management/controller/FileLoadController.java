package com.zghh.cinema_management.controller;

import com.zghh.cinema_management.utils.CusAccessObjectUtil;
import com.zghh.cinema_management.utils.FiletypeUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/*文件加载控制器*/
@RequestMapping("fileload")
@Controller
public class FileLoadController {
    private static String baseDir = "files/";
    Logger log = LoggerFactory.getLogger(FileLoadController.class);

    //保存图片，并返回保存路径
    public String saveImage(MultipartFile img,String imgName){
        String path=null;
        if (!img.isEmpty()){
            if (img.getSize()<2080000) {
                String filename = img.getOriginalFilename();
                log.info("图片名：{}", filename);
                log.info("图片大小：{}字节", img.getSize());
                filename = filename.replace(" ", "");
                //判断文件是否为图片
                if (FiletypeUtil.getFileType(filename).equals("image")) {
                    String name = "FilmImage/星曦向荣影视网"
                            + UUID.randomUUID().toString().substring(0, 5)
                            + "_" + imgName+filename.substring(filename.lastIndexOf("."));
                    File outFile = new File(baseDir + name);
                    try {
                        FileUtils.copyToFile(img.getInputStream(), outFile);
                        path = name;
                        return path;
                    } catch (IOException e) {
                        log.error(e.getMessage());
                        return path;
                    }

                } else {
                    log.error("上传文件类型错误");
                    return path;
                }
            }else {
                log.error("上传图片大小超过2Mb");
                return path;
            }
        }else {
            return path;
        }
    }
    //删除旧图
    public void deleteImg(String path){
        String p="files/";
        File file = new File(p+path);
        if (file.exists()){
            boolean delete = file.delete();
            if (delete) {
                log.info("旧图片删除成功");
            }else {
                log.error("旧图片删除失败："+path);
            }
        }else {
            log.error("旧图片不存在："+path);
        }
    }
    @GetMapping("download/{type}/{name:.+}")
    public void show(@PathVariable("name") String name,
                       @PathVariable("type") String type, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String user_agent = CusAccessObjectUtil.getUser_Agent(request);
        String ipAddress = CusAccessObjectUtil.getIpAddress(request);
//        log.info("下载请求头："+user_agent);
//        log.info("下载IP："+ipAddress);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        File file = new File(baseDir + type+"/"+name);
        FileInputStream input = null;
        try {
            response.setHeader("Content-Length", String.valueOf(file.length()));
            input = FileUtils.openInputStream(file);
            IOUtils.copy(input, response.getOutputStream());
//            log.info("下载请求成功:"+file.getPath());
        } catch (Exception e) {
            if (e.toString().indexOf("does not exist")>1){
                response.sendError(404,"没有找到文件");
            }else {
                response.sendError(500,e.getMessage());
            }
            log.error("错误："+e.getMessage());
        }finally {
            try {
                input.close();
            }catch (Exception e){
                log.error(e.getMessage());
                response.sendError(500,e.getMessage());
            }

        }

    }

}
