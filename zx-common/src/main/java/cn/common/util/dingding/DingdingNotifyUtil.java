package cn.common.util.dingding;


import cn.common.util.dingding.msg.At;
import cn.common.util.dingding.msg.MarkDownContent;
import cn.common.util.dingding.msg.MarkDownMsg;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

@Slf4j
public class DingdingNotifyUtil {

    private static String url="https://oapi.dingtalk.com/robot/send?access_token=31d756dc11a9fa23d850879416cb1e7c097a2def004f62d81b274a8970ae8638";
    private static String secret="SECf495fcaaf44bfae5bb95eee177adf9545d3ac4ae670920bc607d46514ff6d274";
    /**
     * 钉钉通知
     */
    public static void sendDingding(String title, String content, String dingdingUrl, String secret) {
        HttpResponse response = null;
        HttpClient httpclient = HttpClients.createDefault();
        MarkDownContent markdowncontent = new MarkDownContent();
        markdowncontent.setText(content);
        markdowncontent.setTitle(title);
        At at = new At();
        MarkDownMsg markDownMsg = new MarkDownMsg();
        markDownMsg.setAt(at);
        markDownMsg.setIsAtAll(false);
        markDownMsg.setMsgtype("markdown");
        markDownMsg.setIsAtAll(false);
        markDownMsg.setMarkdown(markdowncontent);
        Gson gson = new Gson();
        String textMsg = gson.toJson(markDownMsg);
        StringEntity se = new StringEntity(textMsg, "utf-8");
        try {
            Long timestamp = System.currentTimeMillis();
            String postUrl = dingdingUrl + "&timestamp=" + timestamp + "&sign=" + sign(timestamp, secret);
            HttpPost httppost = new HttpPost(postUrl);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            httppost.setEntity(se);
            response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            } else {
                log.error("钉钉发送信息报错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("钉钉发送信息报错", e);
        }
    }

    /**
     * 钉钉加签
     *
     * @param timestamp
     * @param secret
     * @return
     * @throws Exception
     */
    public static String sign(Long timestamp, String secret) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
    }

    public static void main(String[] args) {
        sendDingding("测试", "test", url,secret);
    }
}
