package org.example.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HttpClient工具类
 * 
 * @author wanglicheng
 * @version 2019年7月30日
 */
public abstract class HttpClientUtil {

	/**
	 * 发送post请求
	 *
	 * @param requestUrl
	 * @param jsonStr
	 * @return
	 */
	public static String doJsonPost(String requestUrl, String jsonStr) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost post = new HttpPost(requestUrl);
		post.addHeader(HTTP.CONTENT_TYPE, "application/json");
		post.setEntity(new StringEntity(jsonStr, "UTF-8"));
		HttpResponse response = client.execute(post);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		String temp = null;
		StringBuffer sb = new StringBuffer("");
		while ((temp = reader.readLine()) != null) {
			sb.append(temp);
		}
		reader.close();
		return sb.toString();
	}

    public static String httpDelete(String url) throws Exception {
    	HttpClient httpClient = HttpClients.createDefault();
    	HttpDelete httpDelete = new HttpDelete(url);

    	HttpResponse httpResponse = httpClient.execute(httpDelete);

        InputStream is = null;
        BufferedReader br = null;
        StringBuilder sBuilder = null;

        //连接成功
        if(200 == httpResponse.getStatusLine().getStatusCode()){
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            br = new BufferedReader(new InputStreamReader(is));
            String tempStr;
            sBuilder = new StringBuilder();
            while ((tempStr = br.readLine()) != null) {
                sBuilder.append(tempStr);
            }
            br.close();
            is.close();
        }

        return sBuilder==null? "":sBuilder.toString();
    }

}
