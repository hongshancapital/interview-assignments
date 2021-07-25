package com.xwt.config.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.util.List;

/**
 * 序列化
 *
 * @author xiwentao
 */
public class JsonSerializerRewrite extends JsonSerializer {

    private ObjectMapper objectMapper;

    public JsonSerializerRewrite(ObjectMapper objectMapper, List<JacksonModuleRegistrar> modules) {
        super(modules);
        this.objectMapper = objectMapper.copy();
        for (JacksonModuleRegistrar each : modules) {
            each.maybeRegisterModule(this.objectMapper);
        }
    }

    @Override
    public Json toJson(Object toSerialize) {
        try {
            return new Json(this.objectMapper.writeValueAsString(toSerialize));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not write JSON", e);
        }
    }

}
