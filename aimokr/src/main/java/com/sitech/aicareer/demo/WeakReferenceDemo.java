package com.sitech.aicareer.demo;

import java.lang.ref.WeakReference;

public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o = new Object();
        WeakReference weak = new WeakReference(o);
        System.out.println(o);
        System.out.println(weak.get());
        o = null;
        System.gc();
        System.out.println(weak.get());
    }
}
