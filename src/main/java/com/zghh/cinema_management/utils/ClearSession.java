package com.zghh.cinema_management.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class ClearSession {
    public static void clear(HttpServletRequest request){
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
    }
}
