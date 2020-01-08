package cn.common.util.http;

import cn.common.exception.ZxException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    public static String get(String url, Map<String,String> req,Map<String, String> headerMap) {
        CloseableHttpResponse response = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url+getQueryString(req));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpGet.setHeader("Accept", "*/*");
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
            httpPost.setHeader("Accept", "*/*");
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


    /**
     * 表单上传文件
     */
    public static String uploadFile(MultipartFile file, String postUrl, Map postParam, Map headerMap){
        CloseableHttpResponse response = null;
        String result = "";
        try{
            //把一个普通参数和文件上传给下面这个地址    是一个servlet
            HttpPost httpPost = new HttpPost(postUrl);

            //设置传输参数
            MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
            multipartEntity.addBinaryBody("file",file.getInputStream(), ContentType.MULTIPART_FORM_DATA,"report");
            //设计文件以外的参数
            if (postParam!=null){
                Set<String> keySet = postParam.keySet();
                for (String key : keySet) {
                    if(postParam.get(key)!=null){
                        multipartEntity.addPart(key, new StringBody(postParam.get(key).toString(), ContentType.create("text/plain", "UTF-8")));
                    }
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

                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 下载
     * @param url
     * @param headerMap
     * @return
     */
    public static void downloadFile(String url,Map<String,String> req, Map<String, String> headerMap,HttpServletResponse response) {
        CloseableHttpResponse closeableHttpResponse = null;
        ServletOutputStream out = null;
        try {
            HttpGet httpGet = new HttpGet(url+getQueryString(req));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpGet.setHeader("Accept", "*/*");
            Header[] headers = getHeaders(headerMap);
            if (headers.length != 0){
                httpGet.setHeaders(headers);
            }
            closeableHttpResponse = httpClient.execute(httpGet);
            Header encode = closeableHttpResponse.getFirstHeader("Content-Type");  //请求头，要返回content-type，以便前台知道如何处理
            response.setHeader(encode.getName(), encode.getValue());
            HttpEntity entity = closeableHttpResponse.getEntity();  //取出返回体
            out = response.getOutputStream();
            entity.writeTo(out);  //将返回体通过响应流写到前台
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if(closeableHttpResponse!=null){
                    closeableHttpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回http请求头列表
     *
     * @param headerMap
     * @return
     */
    private static Header[] getHeaders(Map<String, String> headerMap) {
        if (MapUtils.isEmpty(headerMap))
            return new Header[0];
        Header[] headers = new Header[headerMap.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
        }
        return headers;
    }

    /**
     * 拼接参数
     * @param paramMap
     * @return
     */
    public static String getQueryString(Map<String, String> paramMap) {
        String queryString = "";
        if (MapUtils.isNotEmpty(paramMap)) {
            boolean first = true;
            for (String key : paramMap.keySet()) {
                String value = paramMap.get(key);
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                if (!first) {
                    queryString += "&";
                } else {
                    queryString += "?";
                    first = false;
                }
                queryString += key + "=";
                try {
                    queryString += URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return queryString;
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
}

