package com.sitech.aicareer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : wangjn_bj
 * @date : 2021/1/19 14:30
 */
@SpringBootApplication
@EnableTransactionManagement

public class OfficeStarter {

    public static void main(String[] args) {
        SpringApplication.run(OfficeStarter.class,args);
    }

}
