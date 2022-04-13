package test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短链接映射表
 * </p>
 *
 * @author baomidou generator
 * @since 2022-03-05
 */
@TableName("short_link_config")
public class ShortLinkConfigPO extends Model<ShortLinkConfigPO> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 短链接域名
     */
    private String shortLink;

    /**
     * 长链接域名
     */
    private String longLink;

    /**
     * 长链接域名hash
     */
    private String longLinkHash;

    public Long getId() {
        return id;
    }

    public String getShortLink() {
        return shortLink;
    }

    public String getLongLink() {
        return longLink;
    }

    public String getLongLinkHash() {
        return longLinkHash;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public void setLongLinkHash(String longLinkHash) {
        this.longLinkHash = longLinkHash;
    }
}
