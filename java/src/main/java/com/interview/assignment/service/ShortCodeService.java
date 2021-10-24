package com.interview.assignment.service;

import com.interview.assignment.request.ShortCodeGenerateRequest;
import com.interview.assignment.request.ShortCodeQueryRequest;
import com.interview.assignment.response.ShortCodeGenerateResponse;
import com.interview.assignment.response.ShortCodeQueryResponse;

public interface ShortCodeService {
  /**
   * 根据长链接生成短码
   *
   * @param request 长链接相关信息
   * @return 生成的短码
   */
  ShortCodeGenerateResponse generate(ShortCodeGenerateRequest request);

  /**
   * 根据
   *
   * @param request
   * @return
   */
  ShortCodeQueryResponse detail(ShortCodeQueryRequest request);
}
