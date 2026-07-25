package com.icbc.gjljfl.area.service;

import com.alibaba.fastjson.JSONObject;
import com.icbc.gjljfl.common.ResponseEntity;
public interface AreaService {
    //短域名存储接口：接受长域名信息，返回短域名信息
    ResponseEntity saveUrl(String url);
    //短域名读取接口：接受短域名信息，返回长域名信息
    ResponseEntity readUrl(String url);

}
