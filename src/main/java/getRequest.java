
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class getRequest {
    public static void main(String[] args) throws IOException {
        //创建一个httpclient的客户端用于发包操作
        CloseableHttpClient shunfeng = HttpClients.createDefault();
        //填写快递单
        //根据http方法，创建请求方式。并且填写url地址
        HttpGet ipGet = new HttpGet("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=123.123.123.1&co=&resource_id=6006&t=1587310645567&ie=utf8&oe=gbk&format=json&tn=baidu");
        HttpResponse ipRes = shunfeng.execute(ipGet);
        System.out.println("返回体是"+ipRes);
        //拆开返回体，得到实体内容
        HttpEntity ipEN = ipRes.getEntity();
        System.out.println("实体内容是" + ipEN);
        //将实体转换成为可以看懂的字符串
        String ipStr = EntityUtils.toString(ipEN);
        System.out.println("字符串内容是" + ipStr);

        //判断结果中是否包含北京
        if (ipStr.contains("北京")) {
            System.out.println("测试通过");
        } else {
            System.out.println("测试失败");
        }


        //jsonPath进行解析

        String t = JsonPath.read(ipStr, "$.t").toString();
        System.out.println("t的值是"+t);
        String location = JsonPath.read(ipStr, "$.data[0].location").toString();
        System.out.println("location的值是" + location);

        if(location.equals("北京市北京市 联通")) {
            System.out.println("测试通过");
        } else {
            System.out.println("测试失败");
        }
    }
}
