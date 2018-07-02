package com.abs.Util;

public class CommonUtil {

    private static final String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String getCode(int number){
        String result="";
        if(number<=0)
            return  null;
        for(int i=0;i<number;i++){
            int index=(int)(Math.random()*base.length());
             result+=base.charAt(index);
        }
        return result;
    }

}
