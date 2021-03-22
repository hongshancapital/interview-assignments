package xiejin.java.interview.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 长短域名映射表
 * </p>
 *
 * @author xiejin
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Domain implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 原域名
     */
    private String originalDomain;

    /**
     * 短域名
     */
    private String shortDomain;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 状态 1正常 0冻结 -1删除
     */
    private Integer state;

    private String creator;

    private Date createTime;

    private String updator;

    private Date updateTime;


}
