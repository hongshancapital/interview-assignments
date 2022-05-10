package com.scdt.service;

import com.scdt.exception.BusinessException;

/**
 * LinkService
 *
 * @author weixiao
 * @date 2022-04-26 13:43
 */
public interface LinkService {

    String createShortLink(String longLink) throws BusinessException;

    String getLongLink(String shortLink) throws BusinessException;
}
