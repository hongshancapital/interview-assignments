package com.youming.sequoia.sdn.apipublic.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.youming.sequoia.sdn.apipublic.vo.response.ResponseResultVO;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class TestJsonTransformUtils {

    @Test
    public void test() {
        JsonTransformUtils jsonTransformUtils = new JsonTransformUtils();
        JsonTransformUtils.toJson(new ResponseResultVO());
        JsonTransformUtils.toObject("{\"key\":\"value\"}", ResponseResultVO.class);
        JsonTransformUtils.toObject("{\"key\":\"value\"}", new TypeReference<HashMap>() {
        });
        JsonTransformUtils.toObject("{\"key\":\"value\"}", ResponseResultVO.class, new SimpleDateFormat());
        JsonTransformUtils.toJsonNode("{\"key\":\"value\"}");
    }
}
