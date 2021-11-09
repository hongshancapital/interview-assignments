package com.nbasoccer.shorturl.utils;

import java.util.regex.Pattern;

public class UrlRegUtils {

    public static Boolean isValidUrl(String url){
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }

}
