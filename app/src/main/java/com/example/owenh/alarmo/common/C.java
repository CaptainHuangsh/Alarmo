package com.example.owenh.alarmo.common;

import java.util.HashMap;

/**
 * Created by owen on 2017/5/10.
 */

public class C {
    public static HashMap<String, String> colorMap = new HashMap<String
            , String>() {
        {
            colorMap.put("#0000FF", "蓝色");
            colorMap.put("#008000", "绿色");
            colorMap.put("#008B8B","暗青色");
            colorMap.put("#00BFFF","深天蓝色" );
            colorMap.put("#00CED1", "暗宝石绿");
            colorMap.put("#00FF00","酸橙色" );
            colorMap.put("#00FFFF", "浅绿色");
            colorMap.put("#4B0082", "靛青色");
            colorMap.put("#F5FFFA", "薄荷色");
            colorMap.put("#F8F8FF", "幽灵白");
            colorMap.put("#FF0000", "红色");
            colorMap.put("#FFD700", "金色");
            colorMap.put("#FFFF00", "黄色");
            colorMap.put("", "请选择要显示的颜色");
        }
    };

}
