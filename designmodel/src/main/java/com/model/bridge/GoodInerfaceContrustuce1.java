package com.model.bridge;

public class GoodInerfaceContrustuce1 implements GoodInerface {
    private Color color;
    private Kuanshi kuanshi;

    public GoodInerfaceContrustuce1(Color color, Kuanshi kuanshi) {
        this.color = color;
        this.kuanshi = kuanshi;
    }

    public void getColor(){
        color.getColor();
    }
    public void getKuanshi(){
        kuanshi.getKuanshi();
    }
}
