package cn.leirq.demoshorturl.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 短url实体模型
 * @author Ricky
 */
@Setter
@Getter
public class ShortUrlModel {

    /**
     * 生成的短url
     */
    private String shortUrl;

    /**
     * 原始长url
     */
    private String longUrl;

    /**
     * 随机后缀， 默认是空，避免不同url的hash相同
     */
    private String randomSuffix;

}
