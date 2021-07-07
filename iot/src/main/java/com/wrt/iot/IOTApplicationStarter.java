package com.wrt.iot;

import com.wrt.iot.service.VideoService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : wangjn_bj
 * @date : 2021/6/14 15:47
 */
@SpringBootApplication
public class IOTApplicationStarter {
    public static void main(String[] args) {
        //SpringApplication.run(IOTApplicationStarter.class,args);
        VideoService.prepare();
        VideoService.getCamera("rtsp://admin:hik12345+@192.168.1.87:554/openUrl/tjeaMU0");
        System.out.println("使用java截取一张图片");
    }
}
