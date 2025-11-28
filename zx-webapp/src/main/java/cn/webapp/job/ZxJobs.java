package cn.webapp.job;

import cn.common.util.date.DateUtils;
import cn.common.util.dingding.DingdingNotifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;


@Component
@Slf4j
@RequiredArgsConstructor
public class ZxJobs implements ApplicationListener<RefreshScopeRefreshedEvent> {
    private final XqDataHandle xqDataHandle;

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {

    }
    @Scheduled(cron = "${zx.cron.born}")
    public void hzxJob() {
        String builder = "**黄梓萱**" + "\n\n" + "出生日期: " + "2024-02-18 20:24" + "\n\n" + "当前日期: " + DateUtils.getStringDate(new Date(), "yyyy-MM-dd HH:mm") + "\n\n" + "出生时长: " + DateUtils.getDaysByBorn(2024, 2, 18, 20, 24) + "\n\n" +"此刻年龄: "+ DateUtils.getAgeByBorn(2024, 2, 18);
        DingdingNotifyUtil.sendDingding("黄梓萱", builder, DingdingNotifyUtil.url, DingdingNotifyUtil.secret,false);
    }

    /**
     * 记录两市成交额
     */
    @Scheduled(cron = "0 0/5 9-15 * * ?")
    public void turnoverJob() {
        String now = DateUtils.getStringDate(new Date(), "yyyy-MM-dd HH:mm");
        String hm = DateUtils.getStringDate(new Date(), "HH:mm");
        //不在交易时间或者节假日、周末不做操作
        if(XqDataHandle.ifPass(LocalTime.now())){
            return;
        }
        xqDataHandle.handleTurnover(now, hm);
    }

    /**
     * 存入每天数据
     * 每隔15分钟执行一次（9-15）
     */
    @Scheduled(cron = "0 0/30 11,15 * * ?")
    public void xqStock() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//当日日期
        if(XqDataHandle.ifPass(LocalTime.now())){
            return;
        }
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            log.error("休眠异常", e);
        }
        xqDataHandle.handXqDataAll(today);
    }
}
