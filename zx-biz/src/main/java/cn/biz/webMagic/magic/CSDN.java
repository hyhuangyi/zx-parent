package cn.biz.webMagic.magic;

import cn.biz.webMagic.base.Agents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import java.util.List;

@Slf4j
@Component
public class CSDN implements PageProcessor {
    private Site site = Site.me().setCharset("UTF-8").setRetryTimes(6).setCycleRetryTimes(10).setSleepTime(1000).setUserAgent(Agents.getRandom());
    @Override
    public Site getSite() {
        return site;
    }

    public void process(Page page) {
        // 文章页
        if (page.getUrl().regex("https://blog.csdn.net/qq_37209293/article/details/[0-9]{7}").match()) {
            page.putField("title", page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1/text()").toString());
            //page.putField("content",page.getHtml().xpath("//*[@id=\"content_views\"]/p/text()").all().toString());
        } else {// 列表页
            List<String> list = page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[2]/div/p/a/@href").all();
            page.addTargetRequests(list);
            page.setSkip(true); //设置skip之后，这个页面的结果不会被Pipeline处理
        }
    }
}