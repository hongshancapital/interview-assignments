package com.sequoia.urllink.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Administrator
 */
public class HttpUtil {
  public final static int connectTimeout = 10000;
  public final static int readTimeout = 10000;
  public final static int readTimeout2 = 1000000000;
  private static Logger logger = Logger.getLogger(HttpUtil.class);

  /**
   * 使用Get方式获取数据
   *
   * @param url URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
   * @param url url
   * @return String
   * @throws Exception
   */
  public static String sendGet(String url) throws Exception {
    StringBuilder result = new StringBuilder();
    BufferedReader in = null;
    HttpURLConnection connection = null;
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      connection = (HttpURLConnection) realUrl.openConnection();
      // 设置通用的请求属性
      connection.setConnectTimeout(connectTimeout);// 连接超时 单位毫秒
      connection.setReadTimeout(readTimeout);// 读取超时 单位毫秒
      connection.setRequestProperty("accept", "*/*");
      connection.setRequestProperty("connection", "Keep-Alive");
      connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // 建立实际的连接
      connection.connect();
      // 定义 BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      throw new Exception("发送GET请求出现异常：" + e.getMessage(), e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
        if (connection != null) {
          connection.disconnect();
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }
    return result.toString();
  }

  /**
   * 使用Get方式获取数据
   *
   * @param url   URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
   * @param heads heads
   * @return String
   * @throws Exception
   */
  public static String sendGetTimeOutAndHeads(String url, Map<String, String> heads) throws Exception {
    return sendGetWithHeads(url, heads, readTimeout2);
  }

  /**
   * 使用Get方式获取数据
   *
   * @param url     URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
   * @param heads   heads
   * @param timeOut timeOut
   * @return String
   * @throws Exception
   */
  public static String sendGetWithHeads(String url, Map<String, String> heads, int timeOut) throws Exception {
    StringBuilder result = new StringBuilder();
    BufferedReader in = null;
    HttpURLConnection connection = null;
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      connection = (HttpURLConnection) realUrl.openConnection();
      // 设置通用的请求属性
      connection.setConnectTimeout(connectTimeout);// 连接超时 单位毫秒
      connection.setReadTimeout(timeOut);// 读取超时 单位毫秒
      connection.setRequestProperty("accept", "*/*");
      connection.setRequestProperty("connection", "Keep-Alive");
      connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      // 添加头信息
      if (MapUtils.isNotEmpty(heads)) {
        for (String key : heads.keySet()) {
          connection.setRequestProperty(key, heads.get(key));
        }
      }

      // 建立实际的连接
      connection.connect();
      // 定义 BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      throw new Exception("发送GET请求出现异常：" + e.getMessage(), e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
        if (connection != null) {
          connection.disconnect();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return result.toString();
  }

  public static String sendPost(String url, String param, String contentType) throws Exception {
    PrintWriter out = null;
    BufferedReader in = null;
    StringBuilder result = new StringBuilder();
    HttpURLConnection conn = null;
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      conn = (HttpURLConnection) realUrl.openConnection();
      // 设置通用的请求属性
      conn.setRequestMethod("POST");// 提交模式
      conn.setConnectTimeout(connectTimeout);// 连接超时 单位毫秒
      conn.setReadTimeout(readTimeout);// 读取超时 单位毫秒
      if (contentType != null && !contentType.isEmpty()) {
        conn.setRequestProperty("Content-Type", contentType);
      }
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      throw new Exception("发送 POST请求出现异常：" + e.getMessage(), e);
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
        if (conn != null) {
          conn.disconnect();
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }
    return result.toString();
  }

  /**
   * POST请求，Map形式数据
   *
   * @param url   请求地址
   * @param param 请求数据
   * @throws UnsupportedEncodingException
   */
  public static String sendPost(String url, Map<String, String> param) throws Exception {
    return sendPost(url, genUrlencoded(param), null);
  }

  public static String sendPost2(String url, String param, String contentType) throws Exception {
    StringBuilder result = new StringBuilder();
    PrintWriter out = null;
    BufferedReader in = null;
    HttpURLConnection conn = null;
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      conn = (HttpURLConnection) realUrl.openConnection();
      // 设置通用的请求属性
      conn.setRequestMethod("POST");// 提交模式
      conn.setReadTimeout(readTimeout2);// 读取超时 单位毫秒
      conn.setConnectTimeout(connectTimeout);// 连接超时 单位毫秒
      if (contentType != null && !contentType.isEmpty()) {
        conn.setRequestProperty("Content-Type", contentType);
      }
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      throw new Exception("发送 POST请求出现异常：" + e.getMessage(), e);
    } finally {
      try {
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
        if (conn != null) {
          conn.disconnect();
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }
    return result.toString();
  }

  /**
   * POST请求，json字符串形式数据
   *
   * @param url   请求地址
   * @param param 请求的json数据
   * @throws Exception
   */
  public static String sendPostJson(String url, String param) throws Exception {
    return sendPost(url, param, "application/json;charset=UTF-8");
  }

  public static String sendPostJsonTimeOutAndHeads(String url, String param, Map<String, String> heads)
      throws Exception {
    return sendPostWithHeads(url, param, "application/json;charset=UTF-8", heads, readTimeout2);
  }

  public static String sendPostJsonWithoutTimeOut(String url, String param) throws Exception {
    return sendPost2(url, param, "application/json;charset=UTF-8");
  }

  public static String sendPostWithHeads(String url, String param, String contentType, Map<String, String> heads,
                                         int timeOut) throws Exception {
    PrintWriter out = null;
    BufferedReader in = null;
    StringBuilder result = new StringBuilder();
    HttpURLConnection conn = null;
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      conn = (HttpURLConnection) realUrl.openConnection();
      // 设置通用的请求属性
      conn.setRequestMethod("POST");// 提交模式
      conn.setConnectTimeout(connectTimeout);// 连接超时 单位毫秒
      conn.setReadTimeout(timeOut);// 读取超时 单位毫秒
      if (contentType != null && !contentType.isEmpty()) {
        conn.setRequestProperty("Content-Type", contentType);
      }
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      // 添加头信息
      if (MapUtils.isNotEmpty(heads)) {
        for (String key : heads.keySet()) {
          conn.setRequestProperty(key, heads.get(key));
        }
      }

      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      throw new Exception("发送 POST请求出现异常：" + e.getMessage(), e);
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
        if (conn != null) {
          conn.disconnect();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return result.toString();
  }

  /**
   * POST请求，Map形式数据 :application/x-www-form-urlencoded
   *
   * @param url   请求地址
   * @param param 请求数据
   * @return data
   * @throws UnsupportedEncodingException
   */
  public static String sendPostWithUrlencoded(String url, Map<String, String> param) throws Exception {
    return sendPost(url, genUrlencoded(param), "application/x-www-form-urlencoded");
  }

  private static String genUrlencoded(Map<String, String> param) throws Exception {
    StringBuilder httpUrl = new StringBuilder();
    if (param != null && !param.isEmpty()) {
      for (Map.Entry<String, String> entry : param.entrySet()) {
        httpUrl.append(entry.getKey()).append("=");
        httpUrl.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        httpUrl.append("&");
      }
      httpUrl.deleteCharAt(httpUrl.length() - 1);
    }
    return httpUrl.toString();
  }
}
