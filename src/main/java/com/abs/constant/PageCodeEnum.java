package com.abs.constant;

/**
 * Created by LiuJia on 2018/6/30.
 */
public enum PageCodeEnum {
    ADD_SUCCESS(1001,"添加成功"),
    ADD_FAIL_RECOURCE(3001,"上传失败,资源不足"),
    ADD_FILE_FAIL(6001,"上传文件失败"),
    ;
    public static final String KEY = "pageCode";
    PageCodeEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private Integer code;
    private String msg;

}
