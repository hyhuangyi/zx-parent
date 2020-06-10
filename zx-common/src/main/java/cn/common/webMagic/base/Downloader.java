package cn.common.webMagic.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.Random;


/**
 * 自定义 HttpClientDownloader
 */
@Component
@Slf4j
public class Downloader {

    private static RedisTemplate redisTemplate;

    @Autowired
    public  void setRedisTemplate(RedisTemplate redisTemplate) {
        Downloader.redisTemplate = redisTemplate;
    }


    public static HttpClientDownloader newIpDownloader() {
        HttpClientDownloader downloader = new HttpClientDownloader() {
            @Override
            public void setProxyProvider(ProxyProvider proxyProvider) {
                super.setProxyProvider(proxyProvider);
            }

            @Override
            protected void onError(Request request) {
                String[] ips = newIp();
                setProxyProvider(SimpleProxyProvider.from(new Proxy(ips[0], Integer.parseInt(ips[1]))));
            }
        };
        //downloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(newIp()[0], Integer.parseInt(newIp()[1])),new Proxy(newIp()[0], Integer.parseInt(newIp()[1])),new Proxy(newIp()[0], Integer.parseInt(newIp()[1]))));
        return downloader;
    }

    static String[] newIp() {
        Long size = redisTemplate.opsForList().size("ip");
        String ip = redisTemplate.opsForList().index("ip", new Random().nextInt(size.intValue())).toString();
        log.info("获取ip===========>" + ip);
        String[] ips = ip.split(":");
        return ips;
    }
}

