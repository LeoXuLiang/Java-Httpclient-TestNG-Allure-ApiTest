package com.qa.restclient;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;



public class RestClient {

    final static Logger Log = Logger.getLogger(RestClient.class);


    /**
     * 不带请求头的get方法封装
     * @param url
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse get(String url) throws IOException {

        //创建一个可关闭的Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个Httpget的请求对象
        HttpGet httpget = new HttpGet(url);
        //执行请求，相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        Log.info("开始发送get请求...");
        CloseableHttpResponse httpResponse = httpClient.execute(httpget);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 带请求头的get方法封装
     *
     * @param url
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws IOException {
        //创建一个可关闭的Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个Httpget的请求对象
        HttpGet httpget = new HttpGet(url);
        //加载请求头到Httpget对象
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }
        //执行请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpget);
        return httpResponse;
    }

    /**
     * 封装post方法
     *
     * @param url
     * @param entityString，其实就是设置请求json参数
     * @param headerMap 带请求头
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws IOException {
        //创建一个可关闭的Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个Httppost的请求对象
        HttpPost httppost = new HttpPost(url);
        //设置payload
        httppost.setEntity(new StringEntity(entityString));

        //加载请求头到httppost对象
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());

        }
        //发送post请求
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);
        Log.info("开始发送post请求");
        return httpResponse;
    }

    /**
     * 封装put请求方法，参数和post方法一样
     *
     * @param url
     * @param entityString，这个主要是设置payload，一般来说就是json串
     * @param headerMap,带请求的头信息，格式是键值对，所以这里使用hashmap
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);
        httpput.setEntity(new StringEntity(entityString));

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpput.addHeader(entry.getKey(), entry.getValue());
        }
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpput);
        Log.info("开始发送Put请求");
        return httpResponse;

    }
}
