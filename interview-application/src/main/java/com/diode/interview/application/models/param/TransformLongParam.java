package com.diode.interview.application.models.param;

import com.diode.interview.domain.constant.enums.TransformerEnum;
import lombok.Data;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Data
public class TransformLongParam {
    private String url;
    private TransformerEnum transformerEnum;
    private int expireSecs;
}
