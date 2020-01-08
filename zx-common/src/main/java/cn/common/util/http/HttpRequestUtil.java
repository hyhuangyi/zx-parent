package cn.common.util.http;

import cn.common.exception.ZxException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
     * get请求
     * @param url
     * @param headerMap
     * @return
     */
    public static String get(String url, Map<String, String> headerMap) {
        CloseableHttpResponse response = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpGet.setHeader("Accept", "application/json");
            Header[] headers = getHeaders(headerMap);
            if (headers.length != 0){
                httpGet.setHeaders(headers);
            }
            response = httpClient.execute(httpGet);
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
     * post表单形式
     * @param url
     * @param reqParams
     * @param headerMap
     * @return
     */
    public static String formPost(String url, Map<String, String> reqParams, Map<String, String> headerMap)  {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000) //连接超时时间
                    .setConnectionRequestTimeout(30000)//从线程池中获取线程超时时间
                    .setSocketTimeout(30000)//设置数据超时时间
                    .build();
            httpPost.setConfig(requestConfig);
            setPostParams(httpPost, reqParams);
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            Header[] headers = getHeaders(headerMap);
            if (headers.length != 0){
                httpPost.setHeaders(headers);
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (IOException e) {
            throw new ZxException("请求服务端报错:"+e);
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

    private static void setPostParams(HttpPost httpost,Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

    public static void download(InputStream in, HttpServletResponse response) throws Exception{
        OutputStream outputStream= response.getOutputStream();
        try {
            response.setContentType("application/octet-stream;charset=UTF-8;");//设置返回的文件类型
            response.addHeader("Content-disposition", "attachment;");// 设置头部信息
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ( (bytesRead=in.read(buffer))!= -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            if(in!=null){
                in.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }
}

