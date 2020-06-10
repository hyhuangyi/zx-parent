package cn.common.pojo.webMagic;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

@Slf4j
public class CSDN implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public Site getSite() {
        return site;
    }

    public void process(Page page) {
        // 文章页
        if (page.getUrl().regex("https://blog.csdn.net/qq_37209293/article/details/[0-9]{7}").match()) {
            page.putField("Title", page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1/text()").toString());
            page.putField("Content",
                    page.getHtml().xpath("//*[@id=\"content_views\"]/p/text()").all().toString());
        } else {// 列表页
            List<String> list = page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[2]/div/p/a/@href").all();
            page.addTargetRequests(list);
        }
    }
}