package cn.common.util.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class HttpRequestUtil {
    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * json格式post请求
     * @param url
     * @param jsonString
     * @param headerMap
     * @return
     */
    public static String post(String url, String jsonString, Map<String, String> headerMap) {
        CloseableHttpResponse response = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            Header[] headers = getHeaders(headerMap);
            if (headers.length != 0){
                httpPost.setHeaders(headers);
            }
            httpPost.setEntity(new StringEntity(jsonString, ContentType.create("application/json","utf-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 返回http请求头列表
     *
     * @param headerMap
     * @return
     */
    private static Header[] getHeaders(Map<String, String> headerMap) {
        if (headerMap.isEmpty())
            return new Header[0];
        Header[] headers = new Header[headerMap.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
        }
        return headers;
    }

    /**
     * 表单上传文件
     */
    public static String uploadFile(File postFile, String postUrl, Map postParam,Map headerMap){
        CloseableHttpResponse response=null;
        String result = "";
        try{
            //把一个普通参数和文件上传给下面这个地址    是一个servlet
            HttpPost httpPost = new HttpPost(postUrl);
            //把文件转换成流对象FileBody
            FileBody fundFileBin = new FileBody(postFile);
            //设置传输参数
            MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
            multipartEntity.addPart("fileinfo", fundFileBin);//相当于<input type="file" name="media"/>
            //设计文件以外的参数
            Set<String> keySet = postParam.keySet();
            for (String key : keySet) {
                if(postParam.get(key)!=null){
                    multipartEntity.addPart(key, new StringBody(postParam.get(key).toString(), ContentType.create("text/plain", "UTF-8")));
                }
            }
            HttpEntity reqEntity =  multipartEntity.build();
            httpPost.setEntity(reqEntity);
            Header[] headers = getHeaders(headerMap);
            if (headers.length != 0){
                httpPost.setHeaders(headers);
            }
            //发起请求   并返回请求的响应
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally{
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

