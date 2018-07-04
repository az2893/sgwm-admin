package com.abs.constant;

public enum ApiCodeEnum {
    //第一位为1正确 为2是错误
    LOGIN_SUCCESS(1010,"登录成功"),
    LOGIN_FAIL(2010,"登录失败"),
    USERNAME_TRUE(1020,"用户名正确"),
    USERNAME_ERROR(2020,"用户名错误"),
    CODE_SEND_SUCCESS(1030,"发送成功"),
    CODE_SEND_FAIL(2030,"发送失败"),
    CLICKQUICK_FAIL(2040,"点击太快了，休息一下吧"),
    NOT_LOGIN(2050,"请先登录"),
    BUY_SUCCESS(1060,"购买成功"),
    BUY_FAIL(2060,"购买失败"),
    COMMENT_SUCCESS(1070,"评论成功"),
    COMMENT_FAIL(2070,"评论失败");


    private int code;
    private String msg;

    ApiCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
