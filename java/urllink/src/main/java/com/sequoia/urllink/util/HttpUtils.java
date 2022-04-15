package com.sequoia.urllink.util;

import com.sequoia.urllink.base.model.ResultMessage;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author liuhai
 * @date 2022.4.15
 */
public class HttpUtils {
  private static RestTemplate restTemplate = new RestTemplate();

  /**
   * @param url     url
   * @param headers headers
   * @param param   param
   * @return
   * @throws Exception
   */
  public static ResultMessage sendPostJsonWithHeads(String url, HttpHeaders headers, Object param) throws Exception {
    if (Objects.isNull(headers)) {
      headers = new HttpHeaders();
    }
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Object> requestEntity = new HttpEntity<>(param, headers);
    ResponseEntity<ResultMessage> resp =
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResultMessage.class);
    return resp.getBody();
  }

  /**
   * post请求， ContentType：application/x-www-form-urlencoded
   *
   * @param url   url
   * @param param param
   * @return String
   * @throws Exception Exception
   */
  public static String sendPostWithUrlencoded(String url, Map<String, String> param) throws Exception {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    StringBuilder paramStr = new StringBuilder();
    for (String key : param.keySet()) {
      if (paramStr.length() > 0) {
        paramStr.append("&");
      }
      paramStr.append(key).append("=").append(URLEncoder.encode(param.get(key), StandardCharsets.UTF_8.displayName()));
    }
    HttpEntity<String> requestEntity = new HttpEntity<>(paramStr.toString(), requestHeaders);
    ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    return resp.getBody();
  }
}
