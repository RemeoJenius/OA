package com.jenius.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liyongjun on 17/2/26.
 */
public class SystemRequestContext {
    private static ThreadLocal<HttpServletRequest> httpRequest = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();
    public static void removeHttpSession(){
            session.remove();
    }
    public static HttpSession getSession(){
        return session.get();
    }
    public static void setSession(HttpSession _session){
        session.set(_session);
    }

}

