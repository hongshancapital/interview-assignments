package cn.sequoiacap.links.dao;

import cn.sequoiacap.links.entities.Link;

/**
 * @author : Liushide
 * @date :2022/4/6 09:23
 * @description : 链接Dao接口
 */
public interface LinkDao {

    /**
     * 保存链接
     * @param link 链接信息
     * @return
     */
    public void addLink(Link link);

    /**
     * 恢复URL
     * @param shortCode 短码
     * @return
     */
    Link getLinkByCode(String shortCode);

}
