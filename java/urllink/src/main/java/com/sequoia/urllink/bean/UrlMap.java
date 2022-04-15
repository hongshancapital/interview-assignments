package com.sequoia.urllink.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * URL映射表
 * </p>
 * @author liuhai
 * @date 2022.4.15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UrlMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 短链URL
     */
    private String shortUrl;

    /**
     * 长链URL
     */
    private String longUrl;

    /**
     * 短链摘要
     */
    private String shortUrlDigest;

    /**
     * 长链摘要
     */
    private String longUrlDigest;

    /**
     * 压缩码
     */
    private String shortCode;

    /**
     * 描述
     */
    private String description;

    /**
     * URL状态,1:正常,2:已失效
     */
    private Integer urlStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime editTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新者
     */
    private String editor;

    /**
     * 软删除标识
     */
    private Integer deleted;

    /**
     * 版本号
     */
    private Long version;
    /**
     * 附加字串
     */
    private String attach;
    /**
     * 访问次数
     */
    private Long visitCount;


    public enum Status {
        NORMAL(1, "正常"),
        INVALID(2, "失效");

        private int code;
        private String name;

        private Status(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
