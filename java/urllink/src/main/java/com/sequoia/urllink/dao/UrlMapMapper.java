package com.sequoia.urllink.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sequoia.urllink.bean.UrlMap;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * URL映射表 Mapper 接口
 * </p>
 * @author liuhai
 * @date 2022.4.15
 */
@Repository
public interface UrlMapMapper extends BaseMapper<UrlMap> {
    void incrementVisit(String shortCode);
}
