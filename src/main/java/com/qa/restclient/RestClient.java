package com.qa.restclient;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class RestClient {

    //重构后Get方法：
    public CloseableHttpResponse get(String url) throws ClientProtocolException,IOException{
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient=HttpClients.createDefault();
        //创建一个HttpGet的请求对象
        HttpGet httpget=new HttpGet(url);
        //执行请求,相当于jmeter上点击运行按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse=httpclient.execute(httpget);

        return httpResponse;
    }

    //2. Get 请求方式（带请求头信息head）
    public CloseableHttpResponse get(String url, HashMap<String, String> headmap) throws IOException {
        //创建一个可关闭的Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个Httpget的请求对象
        HttpGet httpget = new HttpGet(url);

        //加载请求头到httpget对象
        for (Map.Entry<String, String> entry : headmap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }
        //执行请求，相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse = httpClient.execute(httpget);
        return httpResponse;

    }

    //3. POST方法
    public CloseableHttpResponse post(String url, String entitystring, HashMap<String, String> headermap) throws IOException {
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HTTPPost的请求对象
        HttpPost httppost = new HttpPost(url);
        //设置请求参数，post请求的body部分；setEntity(),()中是一个HTTPEntity；get请求没有body部分，请求参数都在header中所以没用setEntity()这个方法
        httppost.setEntity(new StringEntity(entitystring));
        //加载请求头到httppost对象，post请求的头部分，如果提交格式是json还是xml等
        for (Map.Entry<String, String> entry : headermap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }
        //发送post请求
        CloseableHttpResponse httpResponse = httpClient.execute(httppost);
        return httpResponse;
    }

    //4.Put方法
    public CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);
        httpput.setEntity(new StringEntity(entityString));

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpput.addHeader(entry.getKey(), entry.getValue());
        }
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpput);
        return httpResponse;
    }

    //5.Delete方法
    public CloseableHttpResponse delete(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdelete = new HttpDelete(url);
        //发送delete请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpdelete);
        return httpResponse;
    }
}
