package com.sitech.aicareer.demo;

public class T1 {
    public volatile int count = 0;

    public void add(){
        count++;
    }

//    public static void main(String[] args) {
//        T1 t1 = new T1();
//        for(int i= 0; i<20;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j= 0;j<1000;j++){
//                        t1.add();
//                    }
//                }
//            }).start();
//        }
//
//        while (Thread.activeCount()>2){
//            Thread.yield();
//        }
//        System.out.println(Thread.activeCount());
//        System.out.println("数字："+t1.count);
//    }
}
