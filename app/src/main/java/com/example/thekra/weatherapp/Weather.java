package com.example.thekra.weatherapp;



public class Weather {
    private String name;
    private String desc;
    private String image;

    public Weather(String name, String desc, String image) {
        this.name = name;
        this.desc = desc;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }


    public String getImage() {
        return image;
    }


}
