package cn.biz.webMagic.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.CharsetUtils;
import us.codecraft.webmagic.utils.HttpClientUtils;
import us.codecraft.webmagic.utils.HttpConstant;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;


/**
 * 自定义 HttpClientDownloader
 */
@Component
@Slf4j
public class ProxyDownloader {

    private static RedisTemplate redisTemplate;

    @Autowired
    public  void setRedisTemplate(RedisTemplate redisTemplate) {
        ProxyDownloader.redisTemplate = redisTemplate;
    }

    public static HttpClientDownloader newIpDownloader() {
        HttpClientDownloader downloader = new HttpClientDownloader() {
            private boolean responseHeader = true;

            /**
             * 重写handleResponse 除200以外都要重试和换代理ip
             * @param request
             * @param charset
             * @param httpResponse
             * @param task
             * @return
             * @throws IOException
             */
            @Override
            protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
                Page page = new Page();
                if (httpResponse.getStatusLine().getStatusCode() != HttpConstant.StatusCode.CODE_200) {
                    log.error("handleResponse>返回码=>"+httpResponse.getStatusLine().getStatusCode());
                    String[] ips = newIp();
                    setProxyProvider(SimpleProxyProvider.from(new Proxy(ips[0], Integer.parseInt(ips[1]))));
                    page.setDownloadSuccess(false);
                } else {
                    byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
                    String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
                    page.setBytes(bytes);
                    if (!request.isBinaryContent()){
                        if (charset == null) {
                            charset =this.getHtmlCharset(contentType, bytes);
                        }
                        page.setCharset(charset);
                        page.setRawText(new String(bytes, charset));
                    }
                    page.setUrl(new PlainText(request.getUrl()));
                    page.setRequest(request);
                    page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
                    page.setDownloadSuccess(true);
                    if (this.responseHeader) {
                        page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
                    }
                }
                return page;
            }
            /**处理错误 换代理ip*/
            @Override
            protected void onError(Request request) {
                log.error("ProxyDownloader=>onError");
                String[] ips = newIp();
                setProxyProvider(SimpleProxyProvider.from(new Proxy(ips[0], Integer.parseInt(ips[1]))));
            }
            private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
                String charset = CharsetUtils.detectCharset(contentType, contentBytes);
                if (charset == null) {
                    charset = Charset.defaultCharset().name();
                }
                return charset;
            }
        };
       // downloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("183.167.217.152",63000)));
        return downloader;
    }
    /**从redis代理池里随机取ip*/
    static String[] newIp() {
        try {
            Long size = redisTemplate.opsForList().size("ip");
            String ip = redisTemplate.opsForList().index("ip", new Random().nextInt(size.intValue())).toString();
            log.info("获取ip===========>" + ip);
            String[] ips = ip.split(":");
            return ips;
        } catch (Exception e) {
            log.error("ProxyDownloader=>newIp");
            return new String[]{"183.167.217.152","63000"};
        }
    }
}

