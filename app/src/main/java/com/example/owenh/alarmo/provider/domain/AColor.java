package com.example.owenh.alarmo.provider.domain;

/**
 * Created by owen on 2017/5/10.
 */

public class AColor {

    private String colorString;
    private String colorValue;

    public AColor(String strS,String strV){
        this.colorString = strS;
        this.colorValue = strV;
    }
    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }


}
