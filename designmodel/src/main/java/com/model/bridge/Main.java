package com.model.bridge;

public class Main {
    public static void main(String[] args) {
        Color red = new ColorRed();
        Kuanshi shouti = new KuanshiShouti();
        GoodInerface good = new GoodInerfaceContrustuce1(red,shouti);
        good.getColor();
    }

}
