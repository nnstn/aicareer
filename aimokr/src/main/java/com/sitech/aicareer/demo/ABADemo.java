package com.sitech.aicareer.demo;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    private static AtomicReference<Integer>  atomicReference = new AtomicReference<>(100);
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        System.out.println("=======以下是ABA问题的产生======");
        new Thread(() -> {
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"T1").start();
        new Thread(() -> {
            //T2线程暂停1秒钟保障T1线程完成ABA操作
            try {TimeUnit.SECONDS.sleep(1L);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());
        },"T2").start();

        try {TimeUnit.SECONDS.sleep(2L);} catch (InterruptedException e) {e.printStackTrace();}

        System.out.println("=======以下是ABA问题的解决======");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t线程第一次获得版本号:"+atomicStampedReference.getStamp());
            //T3线程暂停1秒钟保障T4线程获取同样版本号
            try {TimeUnit.SECONDS.sleep(3L);} catch (InterruptedException e) {e.printStackTrace();}
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t线程第二次获得版本号:"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,102,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t线程第三次获得版本号:"+atomicStampedReference.getStamp());
        },"T3").start();
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t线程第一次获得版本号:"+stamp);
            //T4线程暂停3秒钟保障T1线程完成ABA操作
            try {TimeUnit.SECONDS.sleep(5L);} catch (InterruptedException e) {e.printStackTrace();}

            boolean result1 = atomicStampedReference.compareAndSet(101,2021,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t线程是否修改成功:"+result1+"\t当前实际版本号:"+atomicStampedReference.getStamp()+"\t当前对应值:"+atomicStampedReference.getReference());
            boolean result2 = atomicStampedReference.compareAndSet(102,2021,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t线程是否修改成功:"+result2+"\t当前实际版本号:"+atomicStampedReference.getStamp()+"\t当前对应值:"+atomicStampedReference.getReference());
        },"T4").start();

    }
}
