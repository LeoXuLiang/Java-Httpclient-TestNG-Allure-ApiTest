import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class postRequest {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient yuantong = HttpClients.createDefault();
        HttpPost bondPost = new HttpPost("");
        //添加头域信息
        bondPost.addHeader("11","");
        bondPost.addHeader("11","");
        //包装请求体
        StringEntity bondContent = new StringEntity("");
        //设置请求头域
        bondContent.setContentType("");
        bondContent.setContentEncoding("gbk");
        //设置需要传递的请求体就是bondContent
        bondPost.setEntity(bondContent);
        //获取返回，并且拆出返回体的实际内容
        HttpResponse bondRes = yuantong.execute(bondPost);
        HttpEntity bondEn = bondRes.getEntity();
        String bondStr = EntityUtils.toString(bondEn);



    }
}
