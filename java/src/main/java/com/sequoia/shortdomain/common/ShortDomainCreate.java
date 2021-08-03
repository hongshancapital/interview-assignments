package com.sequoia.shortdomain.common;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class ShortDomainCreate {

    /**
     * id=123&email=123@quanshi.com&mobile=12312312
     *
     * @param uri
     * @return
     */
    public static Map<String, String> tranUri2Map(String uri){
        Map<String, String> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(uri)) {
            Iterable<String> kvIt = Splitter.on("&").split(uri);
            for (String string : kvIt) {
                List<String> it = Splitter.on("=").splitToList(string);
                map.put(it.get(0), it.get(1));
            }
        }

        return map;
    }

    public static String shortenCodeUrl(String longUrl, int urlLength) {
        if (urlLength < 4 ) {
            urlLength = 8;// defalut length
        }
        StringBuilder sbBuilder = new StringBuilder(urlLength + 2);
        String md5Hex = "";
        int nLen = 0;
        while (nLen < urlLength) {
            // 这个方法是先 md5 再 base64编码 参见
            // https://github.com/ndxt/centit-commons/blob/master/centit-utils/src/main/java/com/centit/support/security/Md5Encoder.java
            md5Hex = Md5Encoder.encodeBase64(md5Hex + longUrl);
            for(int i=0;i<md5Hex.length();i++){
                char c = md5Hex.charAt(i);
                if(c != '/' && c != '+'){
                    sbBuilder.append(c);
                    nLen ++;
                }
                if(nLen == urlLength){
                    break;
                }
            }
        }
        return sbBuilder.toString();
    }
}
