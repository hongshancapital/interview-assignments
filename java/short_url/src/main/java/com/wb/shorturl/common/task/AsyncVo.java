package com.wb.shorturl.common.task;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * 存储异步处理信息
 *
 * @param <I> 接口输入参数
 * @param <O> 接口返回参数
 * @author bing.wang
 * @date 2020/1/8
 */
public class AsyncVo<I, O> {

    /**
     * 请求参数
     */
    private I params;



    public I getParams() {
        return params;
    }

    public void setParams(I params) {
        this.params = params;
    }


}