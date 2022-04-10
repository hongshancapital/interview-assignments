package com.work.service.impl;


import com.work.model.Result;
import com.work.model.ShortWebsite;
import com.work.service.ShortWebsiteNameService;
import com.work.util.CacheUtil;
import com.work.util.Constants;
import com.work.util.MurmurHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ShortWebsiteNameServiceImpl implements ShortWebsiteNameService {

    @Autowired
    CacheUtil cacheUtil;

    @Override
    public Result<String> convertToShortName(String longName) {
        String hashLongName = MurmurHashUtil.getHashStr(longName);
        ShortWebsite shortWebsite = new ShortWebsite();
        shortWebsite.setId(cacheUtil.getId());
        shortWebsite.setLongWebsiteName(longName);
        shortWebsite.setShortWebsiteName(Constants.SHORT_PREFIX + hashLongName);
        shortWebsite.setCreateTime(new SimpleDateFormat(Constants.CURRENT_DATE_PATTEN).format(new Date()));
        return cacheUtil.setData(shortWebsite);
    }

    @Override
    public Result<String> getLongName(String shortName) {
        ShortWebsite shortWebsite = cacheUtil.getByShortName(shortName);
        if (shortWebsite.getLongWebsiteName() != null) {
            return new Result<String>().success(shortWebsite.getLongWebsiteName());
        } else {
            return new Result<String>().error(HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND.value());
        }
    }
}
