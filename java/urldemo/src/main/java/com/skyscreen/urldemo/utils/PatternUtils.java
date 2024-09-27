package com.skyscreen.urldemo.utils;

import java.util.regex.Pattern;

public class PatternUtils {


    public static boolean checkIfValidUrl(String url){
        if (Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\\\\\/])+$")
                .matcher(url).find()) {
            return true;
        }else {
            return false;
        }
    }
}
