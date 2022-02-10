package com.example.util;
import com.alibaba.fastjson.JSON;
import java.util.Collection;
import java.util.Map;
/**
 * 描述:
 *
 * @author eric
 * @create 2021-07-21 2:59 下午
 */
public class LogUtils {
    public LogUtils() {
    }

    public static String getLogContent(String messageFormat, Map<String, String> values, Object[] args) {
        if (messageFormat != null && messageFormat.length() != 0) {
            StringBuffer messageBuffer = new StringBuffer();
            StringBuffer variableBuffer = new StringBuffer();
            int indexof$ = -1;
            int indexofBraceStart = -1;
            int indexOfVariable = 0;

            for(int index = 0; index < messageFormat.length(); ++index) {
                char content = messageFormat.charAt(index);
                if (content == '$') {
                    indexof$ = index;
                } else if (content == '{') {
                    indexofBraceStart = index;
                } else if (content == '}') {
                    if (indexof$ > 0 && indexofBraceStart - indexof$ == 1) {
                        messageBuffer.append(getPrintVariable(values, variableBuffer.toString()));
                        variableBuffer.setLength(0);
                        indexof$ = -1;
                        indexofBraceStart = -1;
                    } else {
                        messageBuffer.append(getPrintVariable(args, indexOfVariable));
                        ++indexOfVariable;
                    }
                } else if (indexof$ > 0) {
                    variableBuffer.append(content);
                } else {
                    messageBuffer.append(content);
                }
            }

            return messageBuffer.toString();
        } else {
            return "";
        }
    }

    public static Object getPrintVariable(Object[] args, int index) {
        return args != null && index <= args.length - 1 ? getPrintObject(args[index]) : "";
    }

    public static Object getPrintVariable(Map<String, String> maps, String key) {
        return maps != null && maps.size() != 0 ? maps.get(key) : "";
    }

    public static Object getPrintObject(Object object) {
        if (object == null) {
            return null;
        } else {
            return needTrans2Json(object.getClass()) ? JSON.toJSONString(object) : object;
        }
    }

    public static boolean needTrans2Json(Class claz) {
        if (claz.isPrimitive()) {
            return false;
        } else {
            return !claz.getName().startsWith("java") || claz.isAssignableFrom(Collection.class);
        }
    }
}

