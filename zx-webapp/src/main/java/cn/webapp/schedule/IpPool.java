package cn.webapp.schedule;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 *IP代理池
 */
@Slf4j
//@Component
public class IpPool {

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "*/20 * * * * ?")
    void update() {
        List<String> range = redisTemplate.opsForList().range("ip", 0, -1);
        for (String ip : range) {
            if (ifUseless(ip)) {
                log.error(ip + "  从redis移除");
                redisTemplate.opsForList().remove("ip", 0, ip);
            }
        }
    }

    @Scheduled(cron = "*/15 * * * * ?")
    void ips() {
        String string = null;
        try {
            Document document = Jsoup.connect("https://www.xicidaili.com/nn").timeout(3000).get();
            Elements tags = document.select("#ip_list > tbody > tr");
            for (Element element : tags) {
                //取得ip地址节点
                Elements tdChilds = element.select("tr > td:nth-child(2)");
                //取得端口号节点
                Elements tcpd = element.select("tr > td:nth-child(3)");
                if (StringUtils.isNotBlank(tdChilds.text()) && StringUtils.isNotBlank(tcpd.text())) {
                    string = tdChilds.text() + ":" + tcpd.text();
                    if (!ifUseless(string)) {
                        List<String> range = redisTemplate.opsForList().range("ip", 0, -1);
                        if (!range.contains(string)) {
                           log.info(string + "  存进redis");
                            if (redisTemplate.opsForList().size("ip") > 100)
                                redisTemplate.opsForList().rightPopAndLeftPush("ip", string);
                            else redisTemplate.opsForList().leftPush("ip", string);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无效的ip 返回true 有效的ip返回false
     * @param ip
     * @return
     */
    boolean ifUseless(String ip) {
        String[] split = ip.split(":");
        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
            InetSocketAddress addr = new InetSocketAddress(split[0], Integer.parseInt(split[1]));
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
            InputStream in = null;
            try {
                URLConnection conn = url.openConnection(proxy);
                conn.setConnectTimeout(2000);
                in = conn.getInputStream();
            } catch (Exception e) {
                return true;
            }
            String s = IOUtils.toString(in);
            if (s.indexOf("baidu") > 0) {
                log.info(proxy+"有效");
                return false;
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }
}


