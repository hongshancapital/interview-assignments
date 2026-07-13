package com.example.service.impl;

import com.example.service.ShortAddressService;
import com.example.annotation.Sl4jLogger;
import com.example.service.business.MD5BaseShortUrlService;
import com.example.bean.result.ResultRpc;
import com.example.bean.result.ResultRpcEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 描述:
 * 短地址服务
 *
 * @author eric
 * @create 2021-07-21 3:30 下午
 */
@Slf4j
@Service("shortAddressService")
public class ShortAddressServiceImpl implements ShortAddressService {

    @Autowired
    MD5BaseShortUrlService md5BaseShortUrlService;

    @Override
    @Sl4jLogger
    public ResultRpc<String> genShortAddress(String longAddress) {
        String shortAddress = "";
        try {
            shortAddress = md5BaseShortUrlService.convertShort(longAddress);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultRpc.getErrorResult(ResultRpcEnum.RPC_SYSTEM_ERROR_FAIL.getCode(), e.getMessage());
        }
        if(StringUtils.isEmpty(shortAddress)) {
            return ResultRpc.getErrorResult(ResultRpcEnum.RPC_SYSTEM_ERROR_FAIL.getCode(), ResultRpcEnum.RPC_SYSTEM_ERROR_FAIL.getDesc());
        } else {
            return ResultRpc.success(shortAddress);
        }
    }

    @Override
    @Sl4jLogger
    public ResultRpc<String> getLongAddressByShortAddress(String shortAddress) {
        String longAddress = "";
        try {
            longAddress = md5BaseShortUrlService.lookupLong(shortAddress);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultRpc.getErrorResult(ResultRpcEnum.RPC_SYSTEM_ERROR_FAIL.getCode(), e.getMessage());
        }
        if(StringUtils.isEmpty(longAddress)) {
            return ResultRpc.getErrorResult(ResultRpcEnum.RPC_SYSTEM_ERROR_FAIL.getCode(), ResultRpcEnum.RPC_SYSTEM_ERROR_FAIL.getDesc());
        } else {
            return ResultRpc.success(longAddress);
        }
    }
}
