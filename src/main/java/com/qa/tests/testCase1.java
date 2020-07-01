package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.qa.util.TestUtil.dtt;

public class testCase1 extends TestBase {

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
        System.out.println(closeableHttpResponse.toString());
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
