package com.abs.service;

import com.abs.Util.FileUploadUtil;
import com.abs.bean.Business;
import com.abs.dao.BusinessDao;
import com.abs.dto.BusinessDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuJia on 2018/7/1.
 */
@Service
public class BusinessServiceImpl implements BusinessServiceI {
    @Value("${businessImage.savePath}")
    private String savePath;
    @Value("${businessImage.url}")
    private String imgUrl;
    @Autowired
    private BusinessDao businessDao;
    @Override
    public List<Business> getAllBusinessList() {
        Business b = new Business();
        return businessDao.selectAll(b);
    }

    @Override
    public BusinessDto getBusinessById(long id) {
        Business business=businessDao.selectById(id);
        if(business==null)
            return null;
        BusinessDto businessDto= new BusinessDto();
        BeanUtils.copyProperties(business,businessDto);
        businessDto.setImg(imgUrl+business.getImgFileName());
        businessDto.setStar(this.getStarsNumber(business));
        return businessDto;
    }

    @Override
    public List<BusinessDto> getListByparam(Business business) {
        if(business.getCategory()!=null && business.getCategory()!="") {
            if (business.getCategory().equals("all")) {
                business.setCategory("");
            }
        }
        List<Business> list= businessDao.selectAll(business);
        List<BusinessDto> result= new ArrayList<BusinessDto>();
        for (Business b:list) {
            BusinessDto businessDto= new BusinessDto();
            BeanUtils.copyProperties(b,businessDto);
            businessDto.setImg(imgUrl+b.getImgFileName());
            businessDto.setStar(this.getStarsNumber(b));
            result.add(businessDto);
        }
        return result;
    }

    @Override
    public int add(BusinessDto businessDto) {
        Business business= new Business();
        BeanUtils.copyProperties(businessDto,business);
        if(business.getTitle()==null || business.getSubtitle()==null || business.getPrice()==null || business.getDistance()==null || business.getDesc()==null)
            return 3001;
        if(businessDto.getMultipartFile()!=null && businessDto.getMultipartFile().getSize()>0){
            String fileName= FileUploadUtil.upload(businessDto.getMultipartFile(),savePath);
            business.setImgFileName(fileName);
            //默认销量为0
            business.setNumber(0);
            //默认评论总数为0
            business.setCommentTotalNum(0L);
            //默认评论星星总数
            business.setStarTotalNum(0L);
            businessDao.insert(business);
            return 1001;
        }
        return 301;
    }


    @Override
    public int delete(long id) {
        return businessDao.delete(id);
    }

    @Override
    public int modify(BusinessDto businessDto) {
        Business business= new Business();
        BeanUtils.copyProperties(businessDto,business);
        if(business.getTitle()==null || business.getSubtitle()==null || business.getPrice()==null || business.getDistance()==null || business.getDesc()==null)
            return 3001;
        if(businessDto.getMultipartFile()!=null && businessDto.getMultipartFile().getSize()>0){
            String fileName= FileUploadUtil.upload(businessDto.getMultipartFile(),savePath);
            business.setImgFileName(fileName);
            businessDao.updateBusiness(business);
            return 1001;
        }
        return 301;
    }

    @Override
    public int updateNumber(long id) {
        return businessDao.updateNumber(id);
    }

    public int getStarsNumber(Business business){
        if(business.getStarTotalNum()!=null && business.getCommentTotalNum()!=null && business.getCommentTotalNum()!=0){
            return (int)(business.getStarTotalNum()/business.getCommentTotalNum());
        }
        else
            return 0;
    }


}
