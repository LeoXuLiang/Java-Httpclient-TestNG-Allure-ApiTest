package com.qa.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class TestInterface {

    public static void main(String[] args) {
        String url = "http://xxx.xx.xx.xx//omnibilling/billing/charge";//测试环境URL地址
        String filePath = "/home/songmingliang/桌面/测试用例.xlsx";
        for(int i = 1; i<10; i++) {
            String jsonData = readJsonExcel(filePath,i);
            if(jsonData == null) {
                continue;
            }
            String response =  sendInfo(url, jsonData);
            Double responseCash = analyzeJsonData(response);
            writeBillToExcel(filePath,i,responseCash);
        }


    }
    /*
     * 接口测试用
     * 1.连接系统接口,发送接收json数据
     * 2.准备数据 excel准备测试数据,接收数据写入excel中
     * 3.解析响应的json
     */

    //连接系统接口
    //发送json格式的数据  POST方法
    public static String sendInfo(String url, String data) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);//Post方法连接
        StringEntity myEntity = new StringEntity(data, ContentType.APPLICATION_JSON);//构造请求数据,请求数据结构
        post.setEntity(myEntity);//设置请求体
        String responseContent = null; //响应数据
        CloseableHttpResponse response = null;//

        try {
            response = client.execute(post);
            //对响应内容进行判断
            if(response.getStatusLine().getStatusCode() == 200 ) {
                HttpEntity entity = response.getEntity();
                responseContent  =  EntityUtils.toString(entity, "utf-8");//设置响应字体
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (response != null)
                    response.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (client != null)
                        client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseContent;
    }

    //响应解析后获得的费用写到excel用例中
    public static void writeBillToExcel(String filePath, int rowNum,Double responsCash) {
        File file = new File(filePath);
        InputStream is = null;
        Workbook wb = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(file);
            wb = new XSSFWorkbook(is);
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);
        Row row  = sheet.getRow(rowNum);
        Cell cell = row.createCell(12);
        cell.setCellValue(responsCash);

        try {
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //响应是以json格式返回 进行解析 获取计费 汇率
    public static double analyzeJsonData(String responseJsonStr) {
        JSONObject responseJson = JSONObject.parseObject(responseJsonStr);
        JSONObject dataJson = (JSONObject) responseJson.get("data");

        Object billingAmount = dataJson.get("billingAmount");//费用
        Object exchangeRate  = dataJson.get("exchangeRate"); //汇率

        Double billingAmount_d  = Double.valueOf(billingAmount.toString());//负数
        Double exchangeRate_d = Double.valueOf(exchangeRate.toString());

        Double realCharge = billingAmount_d * exchangeRate_d;
        //最后  去负数取绝对值   确定精度
        BigDecimal b= new BigDecimal(Math.abs(realCharge));
        BigDecimal localCash =b.setScale(3, BigDecimal.ROUND_HALF_UP);
        return localCash.doubleValue();
    }

    //获得具体某行某列单元格值
    public static String cellValue(Sheet sheet,int rowNum,int columnNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(columnNum);
        return cell.getStringCellValue();
    }

    //从excel文件中读取请求json,默认excel版本是高版本xlsx
    public static String readJsonExcel(String filePath, int rowNum) {
        File file = new File(filePath);
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(file);
            wb = new XSSFWorkbook(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //默认是第一个Sheet页
        Sheet sheet = wb.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();  //sheet页里获取全部行数

        if( rowNum > lastRowNum ) {
            return null;
        }
        //第一行默认是用例表头
        Row row = sheet.getRow(rowNum);
        //用例固定E列是测试数据  json格式的
        Cell cell = row.getCell(10);
        try {
           // wb.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cell.getStringCellValue();
    }
}
