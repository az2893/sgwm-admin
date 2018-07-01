package com.abs.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by LiuJia on 2018/7/1.
 */
public class FileUploadUtil {

    public  static String upload(MultipartFile file, String savePath){
        if(file !=null&& file.getSize()>0){
            File fileFolder = new File(savePath);
            if(!fileFolder.exists()){
                fileFolder.mkdirs();
            }
            File savaFile =getFile(savePath,file.getOriginalFilename());
            try {
                file.transferTo(savaFile);
                return  savaFile.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "上传失败";
    }

    public static File getFile(String savePath,String originalFilename){
        String fileName=System.currentTimeMillis()+"_"+originalFilename;
        File file = new File(savePath+fileName);
        if(file.exists()){
            return getFile(savePath,originalFilename); //?
        }
        return file;

    }
}
