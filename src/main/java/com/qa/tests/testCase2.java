package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.qa.util.TestUtil.dtt;

public class testCase2 extends TestBase {

    TestBase testBase;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    //host根url
    String host;
    //Excel路径
    String testCaseExcel;
    //header
    HashMap<String ,String> postHeader = new HashMap<String, String>();
    @BeforeClass
    public void setUp(){
        testBase = new TestBase();
        restClient = new RestClient();
        postHeader.put("Content-Type","application/x-www-form-urlencoded");
        postHeader.put("Content-Type","application/x-www-form-urlencoded");
        //载入配置文件，接口endpoint
        host = prop.getProperty("Host");
        //载入配置文件，post接口参数
        testCaseExcel=prop.getProperty("testCase1data");
    }

    @DataProvider(name = "postData")
    public Object[][] post() throws IOException {
        return dtt(testCaseExcel,0);
    }

    @DataProvider(name = "get")
    public Object[][] get() throws IOException{
        //get类型接口
        return dtt(testCaseExcel,1);
    }

    @DataProvider(name = "delete")
    public Object[][] delete() throws IOException{
        //delete类型接口
        return dtt(testCaseExcel,2);
    }
    @Test(dataProvider = "postData")
    public void postApi(String project,String caseID,String apiSeq,String apiName,String testType,String priority,String url,String headInfo,String precondition,String methods,String dataParameters,String specialSetup,String contentType,String sign,String expectValue,String preResult,String sql,String jsonPath1,String jsonPath2,String jsonPath3,String jsonPath4,String para1,String para2,String para3,String para4,String port)
            throws Exception {
        //将传入的参数对象序列化成json对象
        String parameter = JSON.toJSONString(dataParameters);
        //发送登录请求
        closeableHttpResponse = restClient.post(host+url,parameter,postHeader);
        //从返回结果中获取状态码
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,200);
        Reporter.log("用例编号： "+  caseID);
        Reporter.log("用例标题： "+  apiName);
        Reporter.log("URL： "+  url);
        Reporter.log("请求方式： "+  methods);
        Reporter.log("请求参数： "+ parameter);
        Reporter.log("状态码："+ statusCode,true);
        Reporter.log("响应结果： "+ closeableHttpResponse.toString());
        System.out.println(restClient.getResponseJson(closeableHttpResponse));
    }

    @Test(dataProvider = "postData")
    public void postApi2(String project,String caseID,String apiSeq,String apiName,String testType,String priority,String url,String headInfo,String precondition,String methods,String dataParameters,String specialSetup,String contentType,String sign,String expectValue,String preResult,String sql,String jsonPath1,String jsonPath2,String jsonPath3,String jsonPath4,String para1,String para2,String para3,String para4,String port)
            throws Exception {
        //将传入的参数对象序列化成json对象
        String parameter = JSON.toJSONString(dataParameters);
        //发送登录请求
        closeableHttpResponse = restClient.post(host+url,parameter,postHeader);
        //从返回结果中获取状态码
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
//        ***************
        String cookie = "";

        if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                (statusCode ==HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
            // 读取新的 URL 地址
            Header locationHeader = closeableHttpResponse.getLastHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("location= " + location);
                //获取重定向的地址,并新建get
                HttpGet httpget = new HttpGet("http://10.10.96.142:8080"+location);
                //获取响应文本中的Set-Cookie
                cookie = closeableHttpResponse.getFirstHeader("Set-Cookie").getValue();
                //在httpget请求中 增加cookie字段，携带cookie发送访问请求
                httpget.addHeader(new BasicHeader("Cookie",cookie));
                closeableHttpResponse = restClient.get(host+location);
            }
        }

//        *******************


        Assert.assertEquals(statusCode,200);
        Reporter.log("用例编号： "+  caseID);
        Reporter.log("用例标题： "+  apiName);
        Reporter.log("URL： "+  url);
        Reporter.log("请求方式： "+  methods);
        Reporter.log("请求参数： "+ parameter);
        Reporter.log("状态码："+ statusCode,true);
        Reporter.log("响应结果： "+ restClient.getResponseJson(closeableHttpResponse));
        System.out.println(restClient.getResponseJson(closeableHttpResponse));
    }

    @Test(dataProvider = "get")
    public void getApi(String url) throws Exception{
        closeableHttpResponse = restClient.get(host+ url);
        int statusCode = TestUtil.getStatusCode(closeableHttpResponse);
        Assert.assertEquals(statusCode,200);
        Reporter.log("状态码："+statusCode,true);
        Reporter.log("接口地址： "+url);
    }

    @BeforeClass
    public void endTest(){
        System.out.print("测试结束");
    }

}