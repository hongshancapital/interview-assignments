package com.moonciki.interview.vo.url;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ShortUrlVo {

    private String fullUrl;
    private String urlMd5;
    private String shortUrl;

    private Integer tryCount = 0;

    public void increaseTryCount(){
        tryCount++;
    }

    public boolean shortNotBlank(){
        return StringUtils.isNotBlank(shortUrl);
    }

}
