package com.zoujing.shortlink.model;

import com.zoujing.shortlink.exception.ShortLinkException;
import com.zoujing.shortlink.exception.ShortLinkResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@Slf4j
public final class ApiTemplate {

    /**
     * api执行模板方法
     */
    public static void execute(CommonResult result, ApiCallBack callBack) {
        StopWatch watch = new StopWatch();
        watch.start();
        // todo 设置全链路traceId
        try {
            if (callBack != null) {
                // 入口日志
                callBack.log();

                // 参数校验
                callBack.check();

                // 执行业务逻辑
                callBack.process();
            }
            result.setSuccess(true);
            result.setResultCode(ShortLinkResultEnum.SUCCESS);
            result.setResultDesc(ShortLinkResultEnum.SUCCESS.getDesc());
        } catch (ShortLinkException e) {
            if (e.getResultCode() == null) {
                // 默认系统异常
                e.setResultCode(ShortLinkResultEnum.SYSTEM_ERROR);
            }
            String message = e.getMessage();
            log.warn("api service ShortLinkException:{},errMsg = {}", e.getResultCode().getCode(), message);
            result.setResultCode(e.getResultCode());
            result.setResultDesc(e.getResultCode().getDesc());
        } catch (Throwable t) {
            log.error("api service exception.", t);
            result.setResultCode(ShortLinkResultEnum.SYSTEM_ERROR);
            result.setResultDesc(ShortLinkResultEnum.SYSTEM_ERROR.getDesc());
        } finally {
            watch.stop();
            log.info("Api 执行耗时：{} ms", watch.getTotalTimeMillis());
            // todo 清理线程上下文
        }
    }
}
