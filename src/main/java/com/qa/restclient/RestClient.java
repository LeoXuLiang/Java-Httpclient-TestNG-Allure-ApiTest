package com.qa.restclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class RestClient {
    //重构get请求
    public CloseableHttpResponse get(String url) throws IOException {
        //创建一个可关闭的httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HTTPGet的请求对象
        HttpGet httpget = new HttpGet(url);
        //执行请求，相当于jmeter上点击运行按钮，然后赋值给HTTPResponse对象接收
        CloseableHttpResponse httpResponse = httpClient.execute(httpget);

        return httpResponse;
    }
}
