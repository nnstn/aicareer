package com.wrt.aicareer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 19:46
 */
@SpringBootApplication
@EnableTransactionManagement
public class AiRbacStarter {

    public static void main(String[] args) {
        SpringApplication.run(AiRbacStarter.class,args);
    }
}
