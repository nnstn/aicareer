package com.sitech.aicareer.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList();

        for (int i=0 ; i<30;i++){
            new Thread(()->{
                arrayList.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(arrayList);
            },String.valueOf(i)).start();
        }//java.util.ConcurrentModificationException
    }
}
