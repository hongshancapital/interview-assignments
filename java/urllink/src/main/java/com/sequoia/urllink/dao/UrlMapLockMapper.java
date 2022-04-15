package com.sequoia.urllink.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sequoia.urllink.bean.UrlMapLock;
import org.springframework.stereotype.Repository;

/**
 * @author liuhai
 * @date 2022.4.15
 */
@Repository
public interface UrlMapLockMapper extends BaseMapper<UrlMapLock> {
    long selectIdx();

    void addIdx(long val);
}
