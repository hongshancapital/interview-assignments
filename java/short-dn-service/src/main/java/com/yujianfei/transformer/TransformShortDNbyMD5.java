package com.yujianfei.transformer;

import com.yujianfei.entity.LongDN;
import com.yujianfei.entity.ShortDN;
import com.yujianfei.utils.MD5Util;
import org.springframework.stereotype.Service;

@Service
public class TransformShortDNbyMD5 implements ITransformShortDN {

    @Override
    public ShortDN transform(LongDN longDN) {
        return new ShortDN(MD5Util.ShortText(longDN.getUrl()));
    }

}
