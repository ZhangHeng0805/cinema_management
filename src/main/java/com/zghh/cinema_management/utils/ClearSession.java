package com.zghh.cinema_management.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class ClearSession {
    public static void clear(HttpServletRequest request,String obj){
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            if (request.getSession().getAttribute(obj)!=null) {
                request.getSession().removeAttribute(em.nextElement().toString());
            }
        }
    }
}
