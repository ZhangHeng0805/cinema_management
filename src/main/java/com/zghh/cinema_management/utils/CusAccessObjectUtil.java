package com.zghh.cinema_management.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义访问对象工具类
 *
 * 获取对象的IP地址等信息
 * @author X-rapido
 *
 */
public class CusAccessObjectUtil {

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
public static void printAdress(HttpServletRequest request){
    String uri = request.getRequestURI();//返回请求行中的资源名称
    String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
    String ip = request.getRemoteAddr();//返回发出请求的IP地址
    String params = request.getQueryString();//返回请求行中的参数部分
    String host=request.getRemoteHost();//返回发出请求的客户机的主机名
    int port =request.getRemotePort();//返回发出请求的客户机的端口号。
    System.out.println("IP地址:"+ip);
    System.out.println("完整url:"+url);
    System.out.println("资源名称:"+uri);
    System.out.println("参数部分:"+params);
    System.out.println("主机名:"+host);
    System.out.println("端口号:"+port);
}
public static String getUser_Agent(HttpServletRequest request){
    String ua = request.getHeader("User-Agent");
    return ua;
}
}
