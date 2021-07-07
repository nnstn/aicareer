package com.wrt.aicareer.web.holder;

import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.constants.ConstantRbac;
import com.wrt.aicareer.power.utils.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程内变量
 */
public class RequestHolder {

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    private static final ThreadLocal<AtomicLong> totalCountHolder = new ThreadLocal<AtomicLong>();

    public static void add(AtomicLong totalCount) {
        totalCountHolder.set(totalCount);
    }
    public static AtomicLong getTotalCount() {
        return totalCountHolder.get();
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }
    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }
    public static void remove() {
        requestHolder.remove();
    }
    public static String getRemoteIp() {
        return IPAddressUtil.getClientIp(requestHolder.get());
    }

    public static RbacUser getUser(){
       return (RbacUser)requestHolder.get().getSession().getAttribute(ConstantRbac.SESSION_USER);
    }
}
