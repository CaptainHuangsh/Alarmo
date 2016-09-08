package com.example.datatest.activity.provider;

/**
 * Created by owenh on 2016/8/29.
 */

public class TestI {

    private int id;
    private String name;
    private int price;
    private String date;
    private int page;
    public TestI(int id,String name,int price, String date, int page){

        this.id = id;
        this.name = name;
        this.date = date;
        this.page = page;
        this.price = price;

    }

    public int getId(){
        return id;
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
