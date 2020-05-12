package com.qa.restclient;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

public class RestClient {
    //1. Get 请求方法
    public void get(String url) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpGET请求对象
        HttpGet httpget = new HttpGet(url);
        //执行请求，相当于jmeter上点击执行按钮，然后复制给HTTPResponse对象接收
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        //拿到Http响应状态码，例如和200，404，500去比较
        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("response status code -->" + responseStatusCode);
        //把响应内容存储在字符串对象
        String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        //创建JSON对象，把上面字符串序列化成JSON对象
        JSONObject responseeJson = JSON.parseObject(responseString);
        System.out.println("respon json from API->" + responseeJson);
        //获取响应头信息，返回是一个数组
        Header[] headerArry = httpResponse.getAllHeaders();
        //创建一个hashmap对象，通过jmeter可以看到请求响应头信息都是key和value的形式，所有我们用hashmap
        HashMap<String, String> hm = new HashMap<String, String>();
        //增强for循环遍历headerArray数组，依次把元素添加到hashmap集合
        for (Header header:headerArry){
            hm.put(header.getName(), header.getValue());
        }
        //打印hashmap
        System.out.println("response headers-->" + hm);
    }
}
