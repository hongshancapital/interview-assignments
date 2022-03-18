package com.yofei.shortlink.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MessageConverters {
    private MessageConverters() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    static {

        final ObjectMapper objectMapper = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                .registerModule(new JodaModule())
                .registerModule(new SimpleModule());
        mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
    }
}
