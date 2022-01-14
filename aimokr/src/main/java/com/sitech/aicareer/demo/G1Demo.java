package com.sitech.aicareer.demo;

import java.util.Random;

/**
 * G1 垃圾收集器
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseG1GC
 */
public class G1Demo {
    public static void main(String[] args) {
        String str ="G1垃圾收集器";
        while (true){
            str += str+ new Random().nextInt(7777777);
            str.intern();
        }
    }
}
