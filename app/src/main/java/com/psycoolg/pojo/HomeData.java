package com.psycoolg.pojo;

public class HomeData {
    private int position;
    private String name;
    private int drawerImage;

    public HomeData(int position, String name, int drawerImage) {
        this.position = position;
        this.name = name;
        this.drawerImage = drawerImage;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawerImage() {
        return drawerImage;
    }

    public void setDrawerImage(int drawerImage) {
        this.drawerImage = drawerImage;
    }
}
