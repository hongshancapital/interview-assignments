package com.hongshan.shorturl.domain;

import com.hongshan.shorturl.domain.dto.ShortUrlGenerateDTO;
import com.hongshan.shorturl.domain.entity.ShortUrlEntity;
import com.hongshan.shorturl.domain.model.ResultVO;
import com.hongshan.shorturl.domain.model.ShortUrlModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author: huachengqiang
 * @date: 2022/3/20
 * @description:
 * @version: 1.0
 */
public class ShortUrlGenDtoTests {

    @Test
    public void notValidUrlCheck() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        String[] urls = {
                "",
                "hhhhsi",
                "http:dekkk",
                "https:/dlllsoda"
        };
        for (int i = 0; i < urls.length; i++) {
            ShortUrlGenerateDTO dto = new ShortUrlGenerateDTO();
            dto.setOriginUrl(urls[i]);
            Assert.assertTrue(urls[i] + "合法链接", !CollectionUtils.isEmpty(validator.validate(dto)));
        }
    }

    @Test
    public void validUrlCheck() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        String[] urls = {
                "https://zhuanlan.zhihu.com/p/94000190",
                "https://www.baidu.com/s?wd=public%20class%20ExpireTimeValidator%20implements%20ConstraintValidator%3CExpireTimeValidator.ExpireTimeConstr&rsv_spt=1&rsv_iqid=0xebd266e0000054b2&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=0&rsv_dl=tb&oq=jacoco%25E8%25A6%2586%25E7%259B%2596%25E7%258E%2587&rsv_btype=t&inputT=961&rsv_t=46eepBiciBTfFg6gvBjsZ%2FSd5zgl629fmD%2BUl%2BBJGN7r6IFHhqE8bKmTC1T7rnqiLyZT&rsv_pq=88913da100069333&rsv_sug3=81&rsv_sug1=80&rsv_sug7=001&rsv_sug4=1181&rsv_sug=9",
                "http://www.xx.com/lldao.js",
                "https://xx.cn/i90Olsp"
        };
        for (int i = 0; i < urls.length; i++) {
            ShortUrlGenerateDTO dto = new ShortUrlGenerateDTO();
            dto.setOriginUrl(urls[i]);
            Assert.assertTrue(urls[i] + "非法链接", CollectionUtils.isEmpty(validator.validate(dto)));
        }
    }

    @Test
    public void validExpireCheck() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Long[] expireAtTimes = {
                null, 10099292992992L, Long.MAX_VALUE
        };
        for (int i = 0; i < expireAtTimes.length; i++) {
            ShortUrlGenerateDTO dto = new ShortUrlGenerateDTO();
            dto.setExpireAt(expireAtTimes[i]);
            dto.setOriginUrl("https://xx.cn/i90Olsp");
            Set<ConstraintViolation<ShortUrlGenerateDTO>> validate = validator.validate(dto);
            Assert.assertTrue(expireAtTimes[i] + "非法过期时间", CollectionUtils.isEmpty(validate));
        }
    }

    @Test
    public void invalidExpireCheck() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Long[] expireAtTimes = {
                1L, 10L, 1000_100L, 0x123L, 0x10231 - 12L
        };
        for (int i = 0; i < expireAtTimes.length; i++) {
            ShortUrlGenerateDTO dto = new ShortUrlGenerateDTO();
            dto.setExpireAt(expireAtTimes[i]);
            dto.setOriginUrl("https://xx.cn/i90Olsp");
            Set<ConstraintViolation<ShortUrlGenerateDTO>> validate = validator.validate(dto);
            Assert.assertTrue(expireAtTimes[i] + "非法过期时间", !CollectionUtils.isEmpty(validate));
        }
    }

    @Test
    public void dtoCheck() {
        String originUrl = "https://zhuanlan.zhihu.com/p/94000190";
        Long expireAt = Long.MAX_VALUE;
        ShortUrlGenerateDTO dto1 = new ShortUrlGenerateDTO();
        dto1.setOriginUrl(originUrl);
        dto1.setExpireAt(expireAt);
        ShortUrlGenerateDTO dto2 = new ShortUrlGenerateDTO();
        dto2.setOriginUrl(originUrl);
        dto2.setExpireAt(expireAt);
        Assert.assertEquals(dto1.getOriginUrl(), dto2.getOriginUrl());
        Assert.assertEquals(dto1.getExpireAt(), dto2.getExpireAt());
        Assert.assertEquals(dto1.hashCode(), dto2.hashCode());
        Assert.assertEquals(dto1.toString(), dto2.toString());
        Assert.assertEquals(dto1, dto2);
    }

    @Test
    public void modeCheck() {
        String originUrl = "https://zhuanlan.zhihu.com/p/94000190";
        Long expireAt = Long.MAX_VALUE;
        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setOriginUrl(originUrl);
        entity.setExpireAt(expireAt);
        entity.setShortKey("oksdIS09u");
        ShortUrlModel model1 = new ShortUrlModel(entity);
        ShortUrlModel model2 = new ShortUrlModel(entity);
        Assert.assertEquals(model1.getOriginUrl(), model2.getOriginUrl());
        Assert.assertEquals(model1.getExpireAt(), model2.getExpireAt());
        Assert.assertEquals(model1.hashCode(), model2.hashCode());
        Assert.assertEquals(model1.toString(), model2.toString());
        Assert.assertEquals(model1, model2);
    }

    @Test
    public void entityCheck() {
        String originUrl = "https://zhuanlan.zhihu.com/p/94000190";
        Long expireAt = Long.MAX_VALUE;
        ShortUrlEntity entity1 = new ShortUrlEntity();
        ShortUrlEntity entity2 = new ShortUrlEntity();
        entity1.setOriginUrl(originUrl);
        entity1.setExpireAt(expireAt);
        entity1.setShortKey("oksdIS09u");
        entity2.setOriginUrl(originUrl);
        entity2.setExpireAt(expireAt);
        entity2.setShortKey("oksdIS09u");
        Assert.assertEquals(entity2.getOriginUrl(), entity2.getOriginUrl());
        Assert.assertEquals(entity2.getExpireAt(), entity2.getExpireAt());
        Assert.assertEquals(entity2.hashCode(), entity2.hashCode());
        Assert.assertEquals(entity2.toString(), entity2.toString());
        Assert.assertTrue(entity1.equals(entity2));
    }

    @Test
    public void resultCheck() {
        String originUrl = "https://zhuanlan.zhihu.com/p/94000190";
        Long expireAt = Long.MAX_VALUE;
        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setOriginUrl(originUrl);
        entity.setExpireAt(expireAt);
        entity.setShortKey("oksdIS09u");
        ShortUrlModel model1 = new ShortUrlModel(entity);
        ShortUrlModel model2 = new ShortUrlModel(entity);
        Assert.assertEquals(model1.getOriginUrl(), model2.getOriginUrl());
        ResultVO.error(-1, "asd");
        Assert.assertEquals(model1.getExpireAt(), model2.getExpireAt());
        Assert.assertEquals(model1.hashCode(), model2.hashCode());
        Assert.assertEquals(model1.toString(), model2.toString());
        Assert.assertEquals(model1, model2);
        ResultVO<ShortUrlModel> resultVO1 = ResultVO.success(model1);
        ResultVO<ShortUrlModel> resultVO2 = ResultVO.success(model2);
        Assert.assertEquals(resultVO1.hashCode(), resultVO2.hashCode());
        Assert.assertEquals(resultVO1.toString(), resultVO2.toString());
        Assert.assertEquals(resultVO1, resultVO2);
    }
}
