package cn.sequoiacap.interview.xurl.util;

import org.springframework.context.ApplicationContext;

public class SpringUtil {
  private static ApplicationContext applicationContext;

  public static <T> T getBean(Class<T> clazz) {
    return applicationContext.getBean(clazz);
  }

  public static void setApplicationContext(ApplicationContext context) {
    applicationContext = context;
  }

  public static boolean isSet() {
    return applicationContext != null;
  }
}
