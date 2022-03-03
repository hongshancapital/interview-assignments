package com.hongshan.homework.service.impl;

import com.hongshan.homework.persist.S2LURLMapperRepository;
import com.hongshan.homework.pojo.S2LURLMapper;
import com.hongshan.homework.service.URLConvertService;
import com.hongshan.homework.util.L2SConverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.support.ExampleMatcherAccessor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class URLConverServiceImpl implements URLConvertService {

    @Autowired
    private S2LURLMapperRepository s2LURLMapperRepository;

    @Override
    public String getShortURL(String longURL) {
        String[] shortURL = null;
        List<S2LURLMapper> list = s2LURLMapperRepository.findS2LURLMapperByLongURL(longURL);
        if(list!=null && list.size() > 0){
            return list.get(0).getShortURL();
        }
        //调用公共方法计算短地址，存储数据库，然后返回短地址
        shortURL = L2SConverUtil.convert(longURL);
        for(String url : shortURL){
            list = s2LURLMapperRepository.findS2LURLMapperByShortURL(url);
            if(list==null || list.size()==0){
                S2LURLMapper s2LURLMapper = new S2LURLMapper();
                s2LURLMapper.setShortURL(url);
                s2LURLMapper.setLongURL(longURL);
                s2LURLMapperRepository.save(s2LURLMapper);
                return url;
            }
        }
        return "ERROR1000-短地址重复，无法存储";
    }

    @Override
    public String getLongURL(String shortURL) {
        List<S2LURLMapper> list = s2LURLMapperRepository.findS2LURLMapperByShortURL(shortURL);
        if(list!=null && list.size() > 0){
            return list.get(0).getLongURL();
        }
        return "ERROR2000-短地址不存在";
    }
}
