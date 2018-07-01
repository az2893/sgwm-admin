package com.abs.dto;

import com.abs.bean.Ad;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.web.multipart.MultipartFile;


public class AdDto extends Ad {
    private String img;
    
    private MultipartFile imgFile;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }
    
    
}
