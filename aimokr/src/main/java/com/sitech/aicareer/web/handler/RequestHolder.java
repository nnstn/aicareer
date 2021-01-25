package com.sitech.aicareer.web.handler;


import com.sitech.aicareer.pojo.User;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

    private static final ThreadLocal<User> userHolder = new ThreadLocal<User>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void add(User sysUser) {
        userHolder.set(sysUser);
    }
    public static User getCurrentUser() {
        return userHolder.get();
    }


    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
