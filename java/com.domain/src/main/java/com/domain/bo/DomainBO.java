package com.domain.bo;

import lombok.Data;

/**
 * 短域名服务
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Data
public class DomainBO  extends  BaseBO implements java.io.Serializable {

    private String shortUrl; //短地址

    private String longUrl;  //长域名

    private String addressCode; //短地址代码


    public String toString(){
        return longUrl+","+shortUrl+","+addressCode;
    }
}
