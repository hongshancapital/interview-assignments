package com.yujianfei.transformer;

import com.yujianfei.entity.LongDN;
import com.yujianfei.entity.ShortDN;

/**
 * 短域名计算策略接口
 * @author Yujianfei
 */
public interface ITransformShortDN {

    public ShortDN transform(LongDN longDN);

}

