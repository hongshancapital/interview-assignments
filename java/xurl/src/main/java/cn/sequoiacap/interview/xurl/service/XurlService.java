package cn.sequoiacap.interview.xurl.service;

import cn.sequoiacap.interview.xurl.util.BaseConvertor;
import cn.sequoiacap.interview.xurl.util.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class XurlService {
  private static final Map<String, String> SHORT_TO_LONG = new ConcurrentHashMap<>();
  private static final Map<String, String> LONG_TO_SHORT = new ConcurrentHashMap<>();

  public static class GenerateError extends Exception {
    public GenerateError(String msg) {
      super(msg);
    }
  }

  private long getIdWithRetry() throws GenerateError {
    // 获取id时最可能出现的是同一毫秒超出限制，所以这里失败后休眠1ms后再试
    long id;
    try {
      id = IDGenerator.generate();
    } catch (IDGenerator.OutOfIndexError e) {
      try {
        Thread.sleep(1);
        id = IDGenerator.generate();
      } catch (InterruptedException | IDGenerator.OutOfIndexError ex) {
        throw new GenerateError("failed to generate id");
      }
    }
    return id;
  }

  /**
   * 读取原始url
   *
   * @param shortCode 链接短码
   * @return 原始url
   */
  public String getOriUrl(String shortCode) {
    return SHORT_TO_LONG.get(shortCode);
  }

  /**
   * 获取原始链接的短码表示
   *
   * @param oriUrl 原始链接
   * @return 表示原始链接的短码，最长8位
   * @throws GenerateError 生成短码异常
   */
  public String getShortCode(String oriUrl) throws GenerateError {
    String shortCode = LONG_TO_SHORT.get(oriUrl);
    if (shortCode == null) {
      long id = getIdWithRetry();
      shortCode = BaseConvertor.to62(id);
      SHORT_TO_LONG.put(shortCode, oriUrl);
      LONG_TO_SHORT.put(oriUrl, shortCode);
    }
    return shortCode;
  }
}
