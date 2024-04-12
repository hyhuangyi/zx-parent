package cn.webapp.schedule;

import cn.common.util.date.DateUtils;
import cn.common.util.dingding.DingdingNotifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author： huangy
 * @since： 2024/4/12
 */
@Component
@Slf4j
@RefreshScope
public class MyDaughterJob implements ApplicationListener<RefreshScopeRefreshedEvent> {


    /*生成实例*/
    @Scheduled(cron = "${zx.cron.born}")
    public void createInstanceJob() {
        StringBuilder builder = new StringBuilder();
        builder.append("**黄梓萱**").append("\n\n").append("出生日期: ").append("2024-02-18 20:24").append("\n\n").append("当前日期: ").append(DateUtils.getStringDate(new Date(), "yyyy-MM-dd HH:mm")).append("\n\n");
        builder.append("出生时长: ").append(DateUtils.getDaysByBorn(2024, 2, 18, 20, 24));
        DingdingNotifyUtil.sendDingding("黄梓萱", builder.toString(), DingdingNotifyUtil.url, DingdingNotifyUtil.secret);
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {

    }
}
