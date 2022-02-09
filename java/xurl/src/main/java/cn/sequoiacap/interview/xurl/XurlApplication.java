package cn.sequoiacap.interview.xurl;

import cn.sequoiacap.interview.xurl.util.SpringUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class XurlApplication implements CommandLineRunner {

  private final ApplicationContext appContext;

  public XurlApplication(ApplicationContext appContext) {
    this.appContext = appContext;
  }

  public static void main(String[] args) {
    SpringApplication.run(XurlApplication.class, args);
  }

  @Override
  public void run(String... args) {
    SpringUtil.setApplicationContext(appContext);
  }
}
