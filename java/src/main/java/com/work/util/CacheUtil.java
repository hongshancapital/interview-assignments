package com.work.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.work.model.Result;
import com.work.model.ShortWebsite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class CacheUtil {
    private static BloomFilter<String> bloomFilter;

    private static ArrayList<ShortWebsite> dataList;


    private static Integer expectedInsertions = 1000000;

    private static double fpp = 0.01;

    @PostConstruct
    public void init() {
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), expectedInsertions, fpp);
        dataList = new ArrayList<>();
    }

    public ShortWebsite getByLongName(String longName) {
        ShortWebsite result = new ShortWebsite();
        for (ShortWebsite shortWebsite : dataList) {
            if (longName.equals(shortWebsite.getLongWebsiteName())) {
                result = shortWebsite;
            }
        }
        return result;
    }

    public ShortWebsite getByShortName(String shortName) {
        ShortWebsite result = new ShortWebsite();
        for (ShortWebsite shortWebsite : dataList) {
            if (shortName.equals(shortWebsite.getShortWebsiteName())) {
                result = shortWebsite;
            }
        }
        return result;
    }

    public Integer getId() {
        return dataList.size() + 1;
    }

    public Result<String> setData(ShortWebsite data) {
        return setShortWebsite(data);
    }

    private Result<String> setShortWebsite(ShortWebsite data) {
        //布隆过滤器中未存储的短域名服务,长域名服务
        if (!bloomFilter.mightContain(data.getShortWebsiteName()) && !bloomFilter.mightContain(data.getLongWebsiteName())) {
            //如果布隆过滤器中没有此次存入的长域名，短域名,则为新增
            dataList.add(data);
            bloomFilter.put(data.getShortWebsiteName());
            bloomFilter.put(data.getLongWebsiteName());
            return new Result<String>().success(data.getShortWebsiteName());
        }
        if (!bloomFilter.mightContain(data.getShortWebsiteName()) && bloomFilter.mightContain(data.getLongWebsiteName())) {
            //如果布隆过滤器中已经存在此次存入的长域名,证明需要更新
            ShortWebsite oldData = getByLongName(data.getLongWebsiteName());
            dataList.remove(oldData);
            bloomFilter.put(data.getShortWebsiteName());
            dataList.add(data);
            return new Result<String>().success(data.getShortWebsiteName());
        }
        if (bloomFilter.mightContain(data.getShortWebsiteName()) && !bloomFilter.mightContain(data.getLongWebsiteName())) {
            //如果布隆过滤器中已经存在此次存入的短域名,这是hash冲突，需要重新生成新的hashCode
            String longName = data.getLongWebsiteName();
            String salt = UUID.randomUUID().toString();
            String hashStr = MurmurHashUtil.getHashStr(longName + salt);
            data.setShortWebsiteName(Constants.SHORT_PREFIX + hashStr);
            dataList.add(data);
            return new Result<String>().success(data.getShortWebsiteName());
        }
        //如果布隆过滤器都有，则无需更新返回已经存在数据
        return new Result<String>().success(data.getShortWebsiteName());
    }
}
