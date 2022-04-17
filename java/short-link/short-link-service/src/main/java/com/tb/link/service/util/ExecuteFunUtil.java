package com.tb.link.service.util;

import com.alibaba.fastjson.JSON;
import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.enums.ShortLinkErrorCodeEnum;
import com.tb.link.infrastructure.exception.TbRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author andy.lhc
 * @date 2022/4/16 20:18
 */
@Slf4j
public class ExecuteFunUtil {

    /**
     * 封一個函數,統一打日志
     *
     * @param request
     * @param opName
     * @param fn
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R extends Serializable> Result<R> execute(T request, String opName, Supplier<Result<R>> fn) {
        long t1 = System.currentTimeMillis();
        Result<R> result = null;
        try {
            result = fn.get();
            return result;
        } catch (TbRuntimeException se) {
            log.error("ExecuteFunUtil|{}|with|StdRuntimeException|{}|{}|{}"
                    , opName
                    , JSON.toJSONString(request)
                    , se.getErrorCode()
                    , se.getErrorMsg()
                    , se);
            return Result.fail(se.getErrorCode(), se.getErrorMsg());
        } catch (Throwable e) {
            log.error("ExecuteFunUtil|{}|with|StdRuntimeException|{}|{}|{}"
                    , opName
                    , JSON.toJSONString(request)
                    , ShortLinkErrorCodeEnum.SYSTEM_ERROR.getErrorCode()
                    , ShortLinkErrorCodeEnum.SYSTEM_ERROR.getErrorMsg()
                    , e);
            return Result.fail(ShortLinkErrorCodeEnum.SYSTEM_ERROR);
        } finally {
            long rt = System.currentTimeMillis() - t1;
            String success = Objects.nonNull(result) && result.isSuccess() ? "Y" : "N";
            // 埋点日志，配置监控使用
            log.info("ServiceLog|execute|{}|{}|{}|{}|{}|{}|{}"
                    , opName
                    , rt
                    , success
                    , Objects.nonNull(result) ? result.getErrorCode() : ""
                    , Objects.nonNull(result) ? result.getErrorMsg() : ""
                    , JSON.toJSONString(request)
                    , JSON.toJSONString(result)
            );
        }

    }


}
