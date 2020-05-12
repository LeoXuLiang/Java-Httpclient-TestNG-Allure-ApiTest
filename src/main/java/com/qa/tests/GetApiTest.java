package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetApiTest extends TestBase {
    TestBase testBase;
    String host;
    String url;
    RestClient restClient;

    @BeforeClass
    public void setup() {
        testBase = new TestBase();
        host = prop.getProperty("HOST");
        url = host + "/api/users";

    }

    @Test
    public void getAPITest() throws IOException {
        restClient = new RestClient();
        restClient.get(url);

    }
}
