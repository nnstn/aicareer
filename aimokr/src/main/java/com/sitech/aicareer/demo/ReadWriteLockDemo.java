package com.sitech.aicareer.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源可以同时进行
 * 但是：
 * 如果有一个线程想去写共享资源，就不应该在有其他线程可以进行读或者写
 * 小结：
 *     读-读能共存
 *     读-写不能共存
 *     写-写不能共存
 *
 *     写操作：原子+独占
 */
class MyCache{
    private volatile Map<String,Object> cache = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key,Object obj){
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t  PUT 操作");
            try {TimeUnit.SECONDS.sleep(2L);} catch (InterruptedException e) {e.printStackTrace();}
            cache.put(key,obj);
            System.out.println(Thread.currentThread().getName()+"\t  PUT 完成");
        }finally {
            lock.writeLock().unlock();
        }
    }
    public void get(String key){
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t  GET 操作");
            try {TimeUnit.SECONDS.sleep(2L);} catch (InterruptedException e) {e.printStackTrace();}
            Object o = cache.get(key);
            System.out.println(Thread.currentThread().getName()+"\t  GET 完成 \t"+o);
        }finally {
            lock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache cache = new MyCache();
        for(int i=0;i<5;i++){
            final int temp = i;
            new Thread(()->{
                cache.put(Thread.currentThread().getName(),temp);
            },"put"+i).start();
        }
        for(int i=0;i<5;i++){
            final int temp = i;
            new Thread(()->{
                cache.get("put"+temp);
            },"get"+i).start();
        }
    }
}
