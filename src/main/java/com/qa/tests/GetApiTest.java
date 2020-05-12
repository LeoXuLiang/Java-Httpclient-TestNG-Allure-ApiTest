package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetApiTest extends TestBase {
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeClass
    public void setup() {
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        url = host + "/api/users";

    }

    @Test
    public void getAPITest() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        //断言状态码是不是200
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"response status code is not 200");

    }
}
