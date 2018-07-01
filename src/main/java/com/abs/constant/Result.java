package com.abs.constant;

/**
 * Created by LiuJia on 2018/6/30.
 */
public class Result {
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    int code;
    String msg;
}
