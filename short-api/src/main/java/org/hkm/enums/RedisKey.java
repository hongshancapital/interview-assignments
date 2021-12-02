package org.hkm.enums;

import java.text.MessageFormat;

public enum RedisKey {

    Short_Url_Prefix("s_url:{0}")
    ;

    private String pattern;

    RedisKey(String pattern){
        this.pattern = pattern;
    }

    public String key(Object... args) {
        return MessageFormat.format(this.pattern, args);
    }


}
