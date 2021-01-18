package com.wb.http_server;

import com.wb.http_server.network.HTTPServer;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class BootStrap {
  public static void main(String []args){
      HTTPServer httpServer = new HTTPServer();
      httpServer.start(83);
  }
}
