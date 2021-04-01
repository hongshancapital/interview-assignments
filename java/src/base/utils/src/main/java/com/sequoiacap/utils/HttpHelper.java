package com.sequoiacap.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpHelper
{
	private static Log log = LogFactory.getLog(HttpHelper.class);

	static private class miTM
		implements TrustManager, X509TrustManager
	{
		public X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}

		public boolean isServerTrusted(X509Certificate[] certs)
		{
			return true;
		}
		
		public boolean isClientTrusted(X509Certificate[] certs)
		{
			return true;
		}
		
		public void checkServerTrusted(X509Certificate[] certs, String authType)
			throws CertificateException
		{
			return;
		}
		
		public void checkClientTrusted(X509Certificate[] certs, String authType)
			throws CertificateException
		{
			return;
		}
	}
	
	static {
		try
		{
			SSLContext sc = SSLContext.getInstance("SSL");

			sc.init(null, new TrustManager[] { new miTM() }, null);

			HttpsURLConnection.setDefaultSSLSocketFactory(
				sc.getSocketFactory());
			
			HttpsURLConnection.setDefaultHostnameVerifier(
				new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session)
					{
						return true;
					}
				});
		} catch(Exception e)
		{ }
	}
	
    /**
     * 发送get请求
     *
     * @param url  请求地址
     * @param list 请求参数
     *
     * @return 请求结果
     *
     * @throws IOException
     */
    public static String sendGet(String url, List<HTTPParam> list)
    		throws IOException {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        HttpURLConnection connection = null; //创建的http连接
        BufferedReader bufferedReader = null; //接受连接受的参数
        //如果存在参数，我们才需要拼接参数 类似于 localhost/index.html?a=a&b=b
        
        if (list != null && list.size() > 0) 
        {
            for (int i = 0; i < list.size(); i++) 
            {
                buffer.append(list.get(i).getKey()).append("=")
                	.append(URLEncoder.encode(list.get(i).getValue(), "utf-8"));
                //如果不是最后一个参数，不需要添加&
                if ((i + 1) < list.size()) 
                {
                    buffer.append("&");
                }
            }
            url = url + "?" + buffer.toString();
        }

        log.info(String.format("send get %s", url));
        
        //创建URL
        httpUrl = new URL(url);
        //建立连接
        connection =(HttpURLConnection) httpUrl.openConnection();

        connection.setUseCaches(false);
    	connection.setConnectTimeout(3000);
    	connection.setReadTimeout(3000);

        connection.setRequestProperty("accept", 
        	"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("user-agent", 
        	"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.connect();

        //接受连接返回参数
        bufferedReader = new BufferedReader(
    		new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) 
        {
            result.append(line);
        }
        bufferedReader.close();

        log.info(String.format(
        	"response get %s, %s", url, result.toString()));
        
        return result.toString();
    }
 
    /**
     * 发送Post请求
     *
     * @param url  请求地址
     * @param list 请求参数
     *
     * @return 请求结果
     *
     * @throws IOException
     */
    public static String sendPost(String url, List<HTTPParam> list) 
    		throws IOException {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        URLConnection connection = null; //创建的http连接
        PrintWriter printWriter = null;
        BufferedReader bufferedReader; //接受连接受的参数
        //创建URL
        httpUrl = new URL(url);
        //建立连接
        connection = httpUrl.openConnection();

        connection.setRequestProperty("accept", 
        	"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", 
        	"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.setRequestProperty("content-type",
        	"application/x-www-form-urlencoded");
        
        connection.setUseCaches(false);
    	connection.setConnectTimeout(3000);
    	connection.setReadTimeout(3000);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        printWriter = new PrintWriter(connection.getOutputStream());
        if (list != null && list.size() > 0) 
        {
            for (int i = 0; i < list.size(); i++) 
            {
                buffer.append(list.get(i).getKey()).append("=")
                	.append(URLEncoder.encode(list.get(i).getValue(), "utf-8"));
                //如果不是最后一个参数，不需要添加&
                if ((i + 1) < list.size()) 
                {
                    buffer.append("&");
                }
            }
        }
        
        log.info(String.format(
        	"send post %s, body: %s", url, buffer.toString()));
        
        printWriter.print(buffer.toString());
        printWriter.flush();
        connection.connect();
        //接受连接返回参数
        bufferedReader = new BufferedReader(
        		new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) 
        {
            result.append(line);
        }
        bufferedReader.close();
        
        log.info(String.format(
        	"response post %s, body: %s", url, result.toString()));
        
        return result.toString();
    }
    
    /**
     * 发送get请求
     *
     * @param url  请求地址
     * @param list 请求参数
     *
     * @return 请求结果
     *
     * @throws IOException
     */
    public static String sendGet(String url, Map<String, String> map)
    		throws IOException {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        URLConnection connection = null; //创建的http连接
        BufferedReader bufferedReader = null; //接受连接受的参数
        //如果存在参数，我们才需要拼接参数 类似于 localhost/index.html?a=a&b=b
        
        if (map != null && !map.isEmpty())
        {
        	for (Map.Entry<String, String> entry : map.entrySet())
        	{
        		buffer.append(entry.getKey()).append("=")
        			.append(URLEncoder.encode(entry.getValue(), "utf-8"))
        			.append("&");
        	}
        	//拼完后，去掉最后一个&
        	buffer.deleteCharAt(buffer.length() - 1);
        	url = url + "?" + buffer.toString();
        }
        
        log.info("get: " + url);
        
        //创建URL
        httpUrl = new URL(url);
        //建立连接
        connection = httpUrl.openConnection();
        connection.setRequestProperty("accept", 
        	"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", 
        	"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.connect();
        //接受连接返回参数
        bufferedReader = new BufferedReader(
        		new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) 
        {
            result.append(line);
        }
        bufferedReader.close();
        
        log.info(String.format(
        	"response get %s, body: %s", url, result.toString()));
        
        return result.toString();
    }
 
    /**
     * 发送Post请求
     *
     * @param url  请求地址
     * @param list 请求参数
     *
     * @return 请求结果
     *
     * @throws IOException
     */
    public static String sendPost(
    	String url, Map<String, String> map) 
		throws IOException 
    {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        URLConnection connection = null; //创建的http连接
        PrintWriter printWriter = null;
        BufferedReader bufferedReader; //接受连接受的参数
        //创建URL
        httpUrl = new URL(url);
        //建立连接
        connection = httpUrl.openConnection();
        connection.setRequestProperty("accept", 
        	"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", 
        	"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.setRequestProperty("content-type",
        	"application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        printWriter = new PrintWriter(connection.getOutputStream());
        if (map != null && !map.isEmpty())
        {
        	for (Map.Entry<String, String> entry : map.entrySet())
        	{
        		buffer.append(entry.getKey()).append("=")
        			.append(URLEncoder.encode(entry.getValue(), "utf-8"))
        			.append("&");
        	}
        	//拼完后，去掉最后一个&
        	buffer.deleteCharAt(buffer.length() - 1);
        	
        }
        
        log.info(String.format(
        	"send post %s, body: %s", url, buffer.toString()));
       
        printWriter.print(buffer.toString());
        printWriter.flush();
        connection.connect();

        //接受连接返回参数
        bufferedReader = new BufferedReader(
        		new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) 
        {
            result.append(line);
        }
        bufferedReader.close();
    
        log.info(String.format(
        	"response post %s, body: %s", url, result.toString()));
        
        return result.toString();
    }

    public static String encodeForm(Map<String, Object> params)
    {
    	return encodeForm(params, "&");
    }
    
    public static String encodeForm(Map<String, Object> params, String ch)
    {
    	StringBuffer buffer = new StringBuffer();
    	
    	for(Map.Entry<String, Object> entry: params.entrySet())
    	{
    		try
    		{
	    		buffer.append(entry.getKey()).append("=")
	    			.append(URLEncoder.encode(
	    				String.valueOf(entry.getValue()), "utf-8"))
	    			.append(ch);
    		} catch(Exception e)
    		{ }
    	}
    	
    	//拼完后，去掉最后一个字符
    	buffer.setLength(buffer.length() - ch.length());
    	
    	return buffer.toString();
    }
}
