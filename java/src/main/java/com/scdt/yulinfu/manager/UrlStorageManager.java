package com.scdt.yulinfu.manager;

import com.scdt.yulinfu.dao.UrlStorageDao;
import com.scdt.yulinfu.doamin.UrlStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Component
public class UrlStorageManager {

    @Autowired
    private UrlStorageDao urlStorageDao;

    /**
     * 保存映射关系
     * @param storage
     * @return
     */
    public int save(UrlStorage storage) {
        return urlStorageDao.insertSelective(storage);
    }

    /**
     * 根据短链接获取长链接
     * @param shortLink
     * @return
     */
    public String getLongLink(String shortLink) {
        if (StringUtil.isEmpty(shortLink)){
            return null;
        }
        Condition condition = new Condition(UrlStorage.class);
        condition.and().andEqualTo("shortLink", shortLink);
        return Optional.ofNullable(urlStorageDao.selectByCondition(condition))
                .orElse(new ArrayList<>())
                .stream()
                .findFirst()
                .orElse(new UrlStorage())
                .getLongUrl();
    }

}
