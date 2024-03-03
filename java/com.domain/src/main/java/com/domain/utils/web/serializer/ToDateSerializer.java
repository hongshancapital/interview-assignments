package com.domain.utils.web.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.domain.utils.DateUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 自定义日期 序列化器
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class ToDateSerializer implements ObjectSerializer {
    public static final ToDateSerializer instance = new ToDateSerializer();

    public ToDateSerializer() {
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
        } else {
            String strVal ="";
            if(object instanceof Date){
                strVal= DateUtils.getDateToString((Date)object,DateUtils.DEFAUL_DATE_FORMATE);
            }else  if(object instanceof Timestamp){
                strVal= DateUtils.getTimestampToString((Timestamp)object,DateUtils.DEFAUL_DATE_FORMATE);
            }
            out.writeString(strVal);
        }
    }
}
