package com.abs.service;

import com.abs.bean.Ad;
import com.abs.dao.AdDao;
import com.abs.dto.AdDto;
import com.mchange.v2.beans.BeansUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AdServiceImpl implements  AdServiceI {
    @Value("${adImage.savePath}")
    private String adImageSavePath;
    @Autowired
    private AdDao adDao;


    @Override
    public List<Ad> search(AdDto adDto) {
        return adDao.selectByPage(adDto);
    }

    @Override
    public Ad getAdByid(long id) {
        return adDao.selectById(id);
    }

    @Override
    public int add(AdDto adDto) {

        if(adDto.getTitle()==null || adDto.getWeight()==null ||adDto.getLink()==null)
        return 3001;//资源缺少
        Ad ad= new Ad();
        BeanUtils.copyProperties(adDto,ad);
        if(adDto.getImgFile() !=null && adDto.getImgFile().getSize()>0){
            String fileName=System.currentTimeMillis()+"_"+adDto.getImgFile().getOriginalFilename();
            File file= new File(adImageSavePath,fileName);
            File fileFloder = new File(adImageSavePath);
            if(!fileFloder.exists()){
                fileFloder.mkdirs();
            }
            try {
                adDto.getImgFile().transferTo(file);
                ad.setImgFileName(fileName);
                adDao.insert(ad);
                return 1001;
            } catch (IOException e) {
                e.printStackTrace();
                return  6001;//上传失败
            }

        }
        return 6001;
    }

    @Override
    public int delete(long id) {
        return adDao.delete(id);
    }

    @Override
    public int modify(AdDto adDto) {
        return adDao.update(adDto);
    }
}
