package com.diode.interview.api;

import com.diode.interview.application.models.param.TransformLongParam;
import com.diode.interview.application.service.TransformService;
import com.diode.interview.domain.ability.Transformer;
import com.diode.interview.domain.constant.enums.TransformerEnum;
import com.diode.interview.domain.entity.MyURL;
import com.diode.interview.domain.exception.BizException;
import com.diode.interview.domain.factory.MyBeanFactory;
import com.diode.interview.domain.factory.TransformerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.mockito.Mockito.when;

/**
 * @author unlikeha@163.com
 * @date 2022/4/29
 */
@Slf4j
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TransformServiceTest {

    @InjectMocks
    private TransformService transformService;
    @Mock
    private TransformerFactory transformerFactory;

    @Test
    public void testTransformLongToShortIllegal() {
        TransformLongParam param = new TransformLongParam();
        param.setTransformerEnum(null);
        param.setExpireSecs(100);
        MockedStatic<MyBeanFactory> mockStatic = Mockito.mockStatic(MyBeanFactory.class);
        when(MyBeanFactory.createEmptyBean(MyURL.class)).thenReturn(null);
        Assertions.assertThrows(BizException.class, () -> transformService.transformLongToShort(param));
        mockStatic.close();
    }

    @Test
    public void testShortURL() {
        TransformLongParam param = new TransformLongParam();
        param.setUrl("aaaaaa");
        param.setTransformerEnum(TransformerEnum.MD5);
        when(transformerFactory.getTransformer(TransformerEnum.MD5)).thenReturn(null);
        Assertions.assertThrows(BizException.class, () -> {
            transformService.transformLongToShort(param);
        });
    }

    @Test
    public void testNull(){
        Transformer transformer = transformerFactory.getTransformer(null);
        Assert.assertTrue(Objects.isNull(transformer));
    }
}
