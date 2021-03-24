package com.wjup.shorturl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 链接持久化实体对象
 * @classname: UrlEntity
 * @author niuxing@huaxiapawn
 * @date 2021/3/21 14:25
*/
@Data
public class UrlEntity implements Serializable {

    /**
     * 业务主键uuid
     */
    private String uuid;
    /**
     * 生成的短链接字符串
     */
    private String shortUrlId;
    /**
     * 需要转化的长连接
     */
    private String longUrl;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date lastView;
    /**
     * 密码(用于短链接加密)
     */
    private String viewPwd;
}
