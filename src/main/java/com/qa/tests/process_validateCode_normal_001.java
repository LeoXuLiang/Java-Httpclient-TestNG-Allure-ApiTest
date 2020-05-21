package com.qa.tests;

import com.alibaba.fastjson.JSON;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.qa.util.TestUtil.dtt;

public class process_validateCode_normal_001 extends TestBase {

    TestBase testBase;
    String host;
    String testCaseExcel;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    HashMap<String, String> postHeader = new HashMap<String, String>();

    @BeforeClass
    public void setup() {
        testBase = new TestBase();
        restClient = new RestClient();
        //配置请求头信息
        postHeader.put("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        //载入配置文件，Host内容
        host = prop.getProperty("Host");
        //载入配置文件，post接口参数
        testCaseExcel = prop.getProperty("testCase1data");
    }

    @DataProvider(name = "postData")
    public Object[][] post() throws IOException {
        return dtt(testCaseExcel, 0);
    }

    @DataProvider(name = "getData")
    public Object[][] get() throws IOException {
        return dtt(testCaseExcel, 0);
    }


    @Test
    public void validataCode(String url, String para) {
//        String param1 = JSON.toJSONString(param1);
    }
}
