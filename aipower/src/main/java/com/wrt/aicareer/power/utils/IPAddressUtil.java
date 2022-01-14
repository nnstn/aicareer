package com.wrt.aicareer.power.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/***
 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
 */
@Slf4j
public class IPAddressUtil {
	
	private static final String x_Real_IP = "X-Real-IP";
	private static final String x_forwarded_for = "X-Forwarded-For" ;
	private static final String Proxy_Client_IP = "Proxy-Client-IP" ;
	private static final String WL_Proxy_Client_IP = "WL-Proxy-Client-IP" ;
	private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP" ;
	private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR" ;
	private static final String unknown = "unknown" ;
	private static final String defaultIp = "127.0.0.1";
	
	public static String getClientIp(HttpServletRequest request) {
		String ip = null;
        String ips = request.getHeader(x_forwarded_for);           
        if(ips !=null && ips.length()>0){
        	int index = ips.indexOf(",");
        	if(index ==-1){
        		ip=ips;
        	}else{
        		ip= ips.substring(0,index);
        	}
        }else{
        	ip = ips;
        }

        if (checkIP(ip)) {
        	ip = request.getHeader(x_Real_IP);
        }  
        if (checkIP(ip)) {
            ip = request.getHeader(Proxy_Client_IP);
        }
        if (checkIP(ip)) {
            ip = request.getHeader(WL_Proxy_Client_IP);
        }
        if (checkIP(ip)) {
            ip = request.getHeader(HTTP_CLIENT_IP);
        }
        if (checkIP(ip)) {
            ip = request.getHeader(HTTP_X_FORWARDED_FOR);
        }
        if (checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        if (checkIP(ip)) {
     	   ip = defaultIp;
        }
        
        return ip;
    }

    private static boolean checkIP(String ip){
	    return (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip));
    }

    public static String getHostIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("IP地址获取失败" + e.toString());
        }
        return "";
    }

}
