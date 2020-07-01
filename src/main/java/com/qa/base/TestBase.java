package com.qa.base;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    //这个类作为所有接口请求的父类，加载读取properties文件
    public Properties prop;

    public int RESPONSE_STATUS_CODE_200 = 200;
    public int RESPONSE_STATUS_CODE_201 = 201;
    public int RESPONSE_STATUS_CODE_404 = 404;
    public int RESPONSE_STATUS_CODE_500 = 500;

    //构造函数
    public TestBase(){
        try{
            prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/config/config.properties");
            prop.load(fis);
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //main函数主要为了检测user.dir目录是否正确，执行结果 E:\Java_project\MavenProject_script正是当前项目的目录
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }

}
