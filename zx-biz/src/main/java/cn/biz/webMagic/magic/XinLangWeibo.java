package cn.biz.webMagic.magic;

import cn.biz.po.Weibo;
import cn.biz.webMagic.base.Agents;
import cn.common.util.PropertyPlaceUtil;
import cn.common.util.comm.RegexUtils;
import cn.common.util.date.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import java.util.List;

@Slf4j
@Component
public class XinLangWeibo implements PageProcessor {
    @Autowired
    private PropertyPlaceUtil propertyPlaceUtil;

    @Override
    public Site getSite() {
        return Site.me().setCharset("UTF-8").setRetryTimes(3).setSleepTime(100).setUserAgent(Agents.getRandom()).addCookie("cookie", propertyPlaceUtil.getCookie());
    }

    public void process(Page page) {
        List<Weibo> res= Lists.newArrayList();
        List<String> list = page.getHtml().xpath("//*[@id=\"pl_feedlist_index\"]/div[1]/div").all();
        for(String s:list){
            try {
                Weibo weibo=new Weibo();
                Document document= Jsoup.parse(s);
                String uid=""; //uid
                String content="";//内容
                String source="";//发布工具
                String time="";//发布时间
                String name="";//微博名
                String pics="";//图片
                String id="";//微博id
                //uid
                Elements e_id=document.select("div[class=info]").select("div");
                if(e_id.size()>=3){
                    uid=  document.select("div[class=info]").select("div").get(2).select("a").get(0).attr("href");
                    uid=uid.substring(uid.lastIndexOf("/")+1,uid.indexOf("?"));
                    name=document.select("div[class=info]").select("div").get(2).select("a").get(0).text();
                    id=document.select("div[class=card-wrap]").attr("mid");
                }else {
                    continue;
                }
                Elements e_txt=document.select("div[class=content]").select("p[class=txt]");
                if(e_txt.size()==1){
                    content=e_txt.get(0).text();
                } else {
                    content=e_txt.get(1).text();
                }
                //发布工具
                Elements e_source=document.select("p[class=from]").select("a");
                if(e_source.size()==1){
                    time=e_source.get(0).text();
                } else {
                    time=e_source.get(0).text();
                    source=e_source.get(e_source.size()-1).text();
                }
                //转发
                String forward=document.select("div[class=card-act]").select("ul").select("li").get(1).select("a").text().substring(2).trim();
                //评论
                String comment=document.select("div[class=card-act]").select("ul").select("li").get(2).select("a").text().substring(2).trim();
                //点赞
                String upvote=document.select("div[class=card-act]").select("ul").select("li").get(3).select("a").select("em").text().trim();
                String pic1=document.select("div[class=pic]").select("img").attr("src");
                String pic2=document.select("div[class=media media-piclist]").select("img").attr("src");
                if(!"".equals(pic2)){//只取一张
                    pics=pic2;
                }else {
                    pics=pic1;
                }
                weibo=weibo.setUserId(uid).setScreenName(name).setRepostsCount(forward.equals("")?"0":forward).
                        setCommentsCount(comment.equals("")?"0":comment).setAttitudesCount(upvote.equals("")?"0":upvote).
                        setText(content).setTopics(RegexUtils.getTags(content)).setSource(source).setPics(pics).setCreatedAt(DateUtils.parseWeiboDate(time)).setId(id);
                res.add(weibo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        page.putField("list", res);
        //下一页
        List<String> url=page.getHtml().xpath("//*[@id=\"pl_feedlist_index\"]/div[2]/div/a[@class='next']/@href").all();
        page.addTargetRequests(url);
    }
}

