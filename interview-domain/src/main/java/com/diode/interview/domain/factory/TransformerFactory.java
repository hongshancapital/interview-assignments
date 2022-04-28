package com.diode.interview.domain.factory;

import com.diode.interview.domain.ability.Transformer;
import com.diode.interview.domain.constant.enums.TransformerEnum;
import com.diode.interview.domain.tools.ListTool;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Component
public class TransformerFactory {
    @Resource
    private List<Transformer> transformerList;

    public Transformer getTransformer(TransformerEnum transformerEnum) {
        return ListTool.safeList(transformerList).stream().filter(e -> e.suit(transformerEnum.getType())).findAny().orElse(null);
    }

}
