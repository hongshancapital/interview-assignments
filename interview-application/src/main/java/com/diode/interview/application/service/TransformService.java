package com.diode.interview.application.service;

import com.diode.interview.application.models.param.TransformLongParam;
import com.diode.interview.application.models.param.TransformShortParam;
import com.diode.interview.domain.exception.BizException;
import com.diode.interview.domain.ability.Transformer;
import com.diode.interview.domain.constant.enums.TransformerEnum;
import com.diode.interview.domain.entity.MyURL;
import com.diode.interview.domain.factory.MyBeanFactory;
import com.diode.interview.domain.factory.TransformerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Slf4j
@Service
public class TransformService {
    @Resource
    private TransformerFactory transformerFactory;

    public String transformLongToShort(TransformLongParam param) {
        log.debug("transformLongToShort param:{}", param);
        MyURL myURL = MyBeanFactory.createEmptyBean(MyURL.class);
        if (Objects.isNull(myURL)) {
            throw new BizException("生成链接领域对象失败！");
        }
        myURL.setUrl(param.getUrl());
        myURL.setExpireSecs(param.getExpireSecs());
        TransformerEnum transformerEnum = param.getTransformerEnum();
        //根据对应的转换器进行链接转换
        Transformer transformer = transformerFactory.getTransformer(transformerEnum);
        if (Objects.isNull(transformer)) {
            throw new BizException("算法对应的转换器不存在！");
        }
        String url = myURL.longToShort(transformer);
        if (Objects.isNull(url)) {
            throw new BizException("长链接转换时发生错误！");
        }
        return url;
    }

    public String transformShortToLong(TransformShortParam param) {
        log.debug("transformShortToLong param:{}", param);
        MyURL myURL = MyBeanFactory.createEmptyBean(MyURL.class);
        if (Objects.isNull(myURL)) {
            throw new BizException("生成链接领域对象失败！");
        }
        myURL.setUrl(param.getUrl());
        String url = myURL.shortToLong();
        if (Objects.isNull(url)) {
            throw new BizException("未查到对应长连接，或许短链接已过期！");
        }
        return url;
    }
}
