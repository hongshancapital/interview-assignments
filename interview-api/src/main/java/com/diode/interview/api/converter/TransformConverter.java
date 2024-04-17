package com.diode.interview.api.converter;

import com.diode.interview.api.models.request.TransformLongRequest;
import com.diode.interview.api.models.request.TransformShortRequest;
import com.diode.interview.application.models.param.TransformLongParam;
import com.diode.interview.application.models.param.TransformShortParam;
import com.diode.interview.domain.constant.enums.TransformerEnum;

/**
 * @author unlikeha@163.com
 * @date 2022/4/29
 */
public class TransformConverter {
    private TransformConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static TransformLongParam convertTransformLongRequest(TransformLongRequest request){
        TransformLongParam transformLongParam = new TransformLongParam();
        transformLongParam.setUrl(request.getUrl());
        transformLongParam.setExpireSecs(request.getExpireSecs());
        TransformerEnum transformerEnum = TransformerEnum.getByType(request.getAlg());
        transformLongParam.setTransformerEnum(transformerEnum);
        return transformLongParam;
    }

    public static TransformShortParam convertTransformShortRequest(TransformShortRequest request){
        TransformShortParam transformShortParam = new TransformShortParam();
        transformShortParam.setUrl(request.getUrl());
        return transformShortParam;
    }
}
