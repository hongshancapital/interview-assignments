package com.example.sequoiahomework.rest;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;
import com.example.sequoiahomework.common.response.DataResult;
import com.example.sequoiahomework.vo.url.ChangeUrlVo;

/**
 * @author Irvin
 * @description api请求类
 * @date 2021/10/10 13:36
 */
@BaseRequest(
        baseURL = "http://127.0.0.1:18000/sequoia",
        contentType = "application/json"
)
public interface MyRequest {

    /**
     * 请求长链接转短链接
     *
     * @param changeUrlVo 数据对象
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.String>
     * @author Irvin
     * @date 2021/10/10
     */
    @Post(
            url = "/change/url/lts",
            dataType = "json"
    )
    DataResult<String> longToShort(@Body ChangeUrlVo changeUrlVo);

    /**
     * 请求短链接转长链接
     *
     * @param changeUrlVo 数据对象
     * @return com.example.sequoiahomework.common.response.DataResult<java.lang.String>
     * @author Irvin
     * @date 2021/10/10
     */
    @Post(
            url = "/change/url/stl",
            dataType = "json"
    )
    DataResult<String> shortToLong(@Body ChangeUrlVo changeUrlVo);
}
