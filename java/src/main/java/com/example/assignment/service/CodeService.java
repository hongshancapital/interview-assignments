package com.example.assignment.service;

import com.example.assignment.Exception.ShortCodeUseOutException;

public interface CodeService {

    /**
     * 短域名生成接口
     * @return 获取短域名
     * @throws ShortCodeUseOutException 短域名耗尽异常
     */
    String generateCode() throws ShortCodeUseOutException;
}
