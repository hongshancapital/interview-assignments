package com.sequoia.urllink.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sequoia.urllink.base.model.ResultMessage;
import com.sequoia.urllink.bean.GenCodeParam;
import com.sequoia.urllink.bean.UrlMap;

import java.util.List;

/**
 * <p>
 * URL映射表 服务类
 * </p>
 * @author liuhai
 * @date 2022.4.15
 */
public interface IUrlMapService extends IService<UrlMap> {

    /**
     * 生成短链
     *
     * @param longUrl 长链
     * @param attach  附加字串，目前是传手机号
     * @return
     */
    String genCode(String longUrl, String attach);

    /**
     * 生成短链
     * 需要注意的是，返回的shorturl要与 attach 映射的顺序一致
     *
     * @return
     */
    List<String> genCodes(GenCodeParam param);

    /**
     * 根据短链code获取url
     *
     * @param shortCode
     * @return
     */
    String getLongUrl(String shortCode);

    /**
     * 每年新表
     *
     * @return
     */
    ResultMessage createTable();
}
