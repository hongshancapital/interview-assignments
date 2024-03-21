package cn.sequoiacap.links.service;

import cn.sequoiacap.links.entities.Link;

/**
 * @author : Liushide
 * @date :2022/4/6 10:36
 * @description : 链接服务接口
 */
public interface LinkService {

    /**
     * 保存链接
     * @param link 链接信息
     * @return
     */
    void addLink(Link link);

    /**
     * 恢复URL
     * @param shortCode
     * @return
     */
    Link getLinkByCode(String shortCode);

}
