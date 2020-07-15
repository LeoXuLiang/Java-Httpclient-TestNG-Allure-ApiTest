package com.qa.restclient;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.*;



public class RestClient {

    final static Logger Log = Logger.getLogger(RestClient.class);
    public static Map<String, String> cookies = new HashMap<String, String>();
    public static CookieStore cookieStore = new BasicCookieStore();


    /**
     * 不带请求头的get方法封装
     *
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
        addCookieInRequestHeaderBeforeRequest(httpget);
        CloseableHttpResponse httpResponse = httpClient.execute(httpget);
        getAndStoreCookieFromResponseHeader(httpResponse);
        Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 带header的get方法封装
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
        addCookieInRequestHeaderBeforeRequest(httpget);
        CloseableHttpResponse httpResponse = httpClient.execute(httpget);
        getAndStoreCookieFromResponseHeader(httpResponse);
        return httpResponse;
    }

    /**
     * 封装post方法
     *
     * @param url
     * @param entityString，其实就是设置请求json参数
     * @param headerMap                   带请求头
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse postJson(String url, String entityString, HashMap<String, String> headerMap) throws IOException {
        //创建一个可关闭的Httpclient对象,设置自动跟踪重定向
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setRedirectStrategy(new LaxRedirectStrategy()).build();
//        CloseableHttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        //创建一个Httppost的请求对象
        HttpPost httppost = new HttpPost(url);
        //设置payload
        httppost.setEntity(new StringEntity(entityString));

        //加载请求头到httppost对象
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());

        }
        //发送post请求
        addCookieInRequestHeaderBeforeRequest(httppost);
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);
        //通过cookieStore获取持久化cookie
        getAndStoreCookie(cookieStore);
        Log.info("开始发送post请求");
        return httpResponse;
    }


    /**
     * 封装post方法【】
     *
     * @param url
     * @param entityString，其实就是设置请求json参数
     * @param headerMap                   带请求头
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse postKeyValue(String url, Map<String, String> entityString, HashMap<String, String> headerMap) throws IOException {
        //创建一个可关闭的Httpclient对象,设置自动跟踪重定向，并且自动持久化使用cookiestore
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setRedirectStrategy(new LaxRedirectStrategy()).build();
//        CloseableHttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();

//        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        //创建一个Httppost的请求对象
        HttpPost httppost = new HttpPost(url);
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        //去除map中所有的参数名
        Set<String> keys = entityString.keySet();
        //通过循环将参数保存到list集合
        for (String name : keys) {
            String value = entityString.get(name);
            parameters.add(new BasicNameValuePair(name, value));
        }
        httppost.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));

        //加载请求头到httppost对象
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }
        //发送post请求
        addCookieInRequestHeaderBeforeRequest(httppost);
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);
        //通过cookieStore获取持久化cookie
        getAndStoreCookie(cookieStore);

        //从response set-cookie中获取cookie信息【资管产品不适用】
        //getAndStoreCookieFromResponseHeader(httpResponse);

        Log.info("开始发送post请求");
        return httpResponse;
    }

    private void addCookieInRequestHeaderBeforeRequest(HttpRequest request) {
        String jsessionIdCookie = cookies.get("JSESSIONID");
        if (jsessionIdCookie != null) {
            request.addHeader("Cookie", jsessionIdCookie);

        }
    }

    private static void getAndStoreCookieFromResponseHeader(CloseableHttpResponse httpResponse) {
        //从响应头里取出名字为"Set-Cookie"的响应头
        Header setCookieHeader = httpResponse.getFirstHeader("Set-Cookie");
        Log.info("setCookieHeader=" + setCookieHeader);

        //如果不为空
        if (setCookieHeader != null) {
            //取出此响应头的值
            String cookiePairsString = setCookieHeader.getValue();
            if (cookiePairsString != null && cookiePairsString.trim().length() > 0) {
                //以“;”来切分
                String[] cookiePairs = cookiePairsString.split(";");
                if (cookiePairs != null) {
                    for (String cookiePair : cookiePairs) {
                        //如果包含JSESSIONID，则意味着响应头里有会话ID这个数据
                        if (cookiePair.contains("JSESSIONID")) {
                            //保存到map
                            cookies.put("JSESSIONID", cookiePair);

                        }
                    }
                }
            }
        }
    }

private static void getAndStoreCookie(CookieStore cookieStore) {
    String JSESSIONID = null;
    String result = null;
    List<Cookie> cookie = cookieStore.getCookies();
    for (int i = 0; i < cookie.size(); i++) {
        if (cookie.get(i).getName().equals("JSESSIONID")) {
            JSESSIONID = cookie.get(i).getValue();
        }
    }
    if (JSESSIONID != null) {
        result = "JSESSIONID="+JSESSIONID;
    }
    Log.info(result);
    cookies.put("JSESSIONID", result);
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

    /**
     * 封装 delete请求方法
     *
     * @param url
     * @return 返回一个response对象，方便进行得到状态码和json解析
     * @throws IOException
     */
    public CloseableHttpResponse delete(String url) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdelete = new HttpDelete(url);

        //发送delete请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpdelete);
        return httpResponse;
    }

    /**
     * 获取响应状态码，常用来和TestBase中定义的状态码常量去测试断言使用
     *
     * @param response
     * @return
     */
    public int getStatusCode(CloseableHttpResponse response) {

        int statusCode = response.getStatusLine().getStatusCode();
        Log.info("解析，得到响应状态码" + statusCode);
        return statusCode;
    }

    /**
     * @param response 任何请求返回的响应对象
     * @return
     * @throws IOException
     */
    public JSONObject getResponseJson(CloseableHttpResponse response) throws IOException {
        Log.info("得到响应对象的String格式");
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject responseJson = JSON.parseObject(responseString);
        Log.info("返回响应内容的JSON格式");
        return responseJson;
    }
}
