package com.sitech.aicareer.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS()");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendEmail()");

    }

    ///////////////////////////////////
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    private void get() {
        /**加锁几次要解锁几次**/
        lock.lock();
        lock.lock();

        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }
    private void set() {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        }finally {
            lock.unlock();
        }
    }
}
public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            phone.sendSMS();
        },"t1").start();
        new Thread(()->{
            phone.sendSMS();
        },"t2").start();

        try {TimeUnit.SECONDS.sleep(2L);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println();
        System.out.println();
        new Thread(phone,"t3").start();
        new Thread(phone,"t4").start();
    }
}
