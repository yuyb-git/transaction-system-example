package com.example.transaction.utils;

import com.example.transaction.utils.key.SnowFlake;

public class IdUtils {

    /**
     * 雪花算法获取唯一id
     */
    public static long getId(){
        return  SnowFlake.nId();
    }

}
