package com.example.datatest.activity.provider;

/**
 * Created by owenh on 2016/8/29.
 */

public class TestI {

    private String name;
    private int price;
    private String date;
    private int page;
    public TestI(String name,int price, String date, int page){

        this.name = name;
        this.date = date;
        this.page = page;
        this.price = price;

    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public String getDate(){
        return date;
    }

    public int getPage(){
        return page;
    }


}
