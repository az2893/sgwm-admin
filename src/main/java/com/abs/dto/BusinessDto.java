package com.abs.dto;

import com.abs.bean.Business;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by LiuJia on 2018/7/1.
 */
public class BusinessDto extends Business {
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    private MultipartFile multipartFile;
}
