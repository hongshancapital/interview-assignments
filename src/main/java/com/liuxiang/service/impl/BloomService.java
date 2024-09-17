package com.liuxiang.service.impl;

import com.liuxiang.service.IShortIdExist;
import com.liuxiang.util.BloomSingleton;
import org.springframework.stereotype.Service;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Service
public class BloomService implements IShortIdExist {

    @Override
    public boolean isExist(String shortUrl) {
        return BloomSingleton.getInstance().isExist(shortUrl);
    }

}
