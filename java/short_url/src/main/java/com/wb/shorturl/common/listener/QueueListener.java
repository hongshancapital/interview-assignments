package com.wb.shorturl.common.listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wb.shorturl.common.task.StorageTask;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 队列监听器，初始化启动所有监听任务
 *
 * @author bing.wang
 * @date 2021/1/8
 */

@Component
public class QueueListener  {
 
  @Autowired
  private StorageTask storageTask;
 
  /**
   * 初始化时启动监听请求队列
   */
  @PostConstruct
  public void init() {
    storageTask.start();
  }
 
  /**
   * 销毁容器时停止监听任务
   */
  @PreDestroy
  public void destory() {
    storageTask.setRunning(false);
  }


}