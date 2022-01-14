package com.sitech.aicareer.demo;

import java.util.concurrent.TimeUnit;

class MythreadRunnnable implements Runnable{

    private String lockA;
    private String lockB;

    public MythreadRunnnable(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 拿到了 锁:"+lockA+",进程下一步我需要:"+lockB);
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println("我拿到了 锁:"+lockA+",进程下一步我需要:"+lockB);

            }
        }
    }
}


public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA ="LockA";
        String lockB ="LockB";
        new Thread(new MythreadRunnnable(lockA,lockB),"ThreadAAA").start();
        new Thread(new MythreadRunnnable(lockB,lockA),"ThreadBBB").start();
    }
}
