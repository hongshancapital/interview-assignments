package com.demo.service;

import com.demo.dto.ResponseDTO;

/**
 * @author syd
 * @description
 * @date 2022/1/12
 */
public interface DomianNamesService {
    /**
     * 长链接转换成短链接
     *
     * @param longerUrl 长域名链接
     * @return
     */
    ResponseDTO shorten(String longerUrl);

    /**
     * 端链接获取对应的长链接
     *
     * @param shortenUrl 端域名链接
     * @return
     */
    ResponseDTO longer(String shortenUrl);
}
