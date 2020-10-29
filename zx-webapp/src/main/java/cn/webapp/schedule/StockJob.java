package cn.webapp.schedule;

import cn.biz.mapper.StockMapper;
import cn.biz.po.Stock;
import cn.biz.service.ISysService;
import cn.biz.vo.FundVO;
import cn.biz.vo.StockVO;
import cn.common.util.date.DateUtils;
import cn.common.util.mail.MailAddress;
import cn.common.util.mail.MailMessageObject;
import cn.common.util.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class StockJob {
    @Autowired
    private ISysService sysService;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private MailUtil mailUtil;

    private static List<String> list = new ArrayList<>();

    static {
        list.add("09:00");
        list.add("09:15");
        list.add("11:45");
        list.add("15:15");
        list.add("15:30");
        list.add("15:45");
    }

    /**
     * 记录两市成交额
     */
    @Scheduled(cron = "0 0/30 9,10,11,13,14,15 * * ?")
    public void stockJob() {

        String date = DateUtils.getStringDate(new Date(), "yyyy-MM-dd HH:mm");
        String hm = DateUtils.getStringDate(new Date(), "HH:mm");
        //不在交易时间或者节假日、周末不做操作
        if (list.contains(hm) || !"0".equals(DateUtils.isHoliday(DateFormatUtils.format(new Date(), "yyyyMMdd")))) {
            return;
        }
        try {
            if ("09:30".equals(hm) || "15:00".equals(hm)) {
                Thread.sleep(90000);
            } else {
                Thread.sleep(30000);//休息30秒
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StockVO stockVO = sysService.getStockInfo();
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockVO, stock);
        stock.setDate(date);
        stockMapper.insert(stock);
        log.info("========时间：" + date + ";两市成交额：" + stock.getTurnOver() + ";上证：" + stock.getShangz() + ";深证：" + stock.getShenz() + ";创业板：" + stock.getChuangy() + "========");
    }

    /**
     *
     */
    @Scheduled(cron = "0 0/30 9,10,11,13,14,15 * * ?")
    public void fundJob() {
        List<String> up = new ArrayList<>();
        List<String> down = new ArrayList<>();
        List<FundVO> list = sysService.fundList(34L);//zx
        String context = "";
        for (FundVO vo : list) {
            if (vo.getGszzl() > 1) {
                up.add(vo.getName() + ":" + vo.getGszzl());
            } else if (vo.getGszzl() < -1) {
                down.add(vo.getName() + ":" + vo.getGszzl());
            }
        }
        MailMessageObject mailMessageObject = new MailMessageObject();
        if (up.size() != 0) {
            context += "上涨超过1%的有：" + up.toString() + ";";
        }
        if (down.size() != 0) {
            context += "下跌超过1%的有：" + up.toString();
        }
        if (!"".equals(context)) {
            mailMessageObject.setContext(context);
            mailMessageObject.setSubject("涨跌助手");
            MailAddress address = new MailAddress("zx", mailUtil.getUsername());
            List<MailAddress> listAddress = new ArrayList<>();
            listAddress.add(address);
            mailUtil.setFrom(address);
            mailMessageObject.setCc(listAddress);
            mailMessageObject.setTo(listAddress);
            mailUtil.send(mailMessageObject);
        }
    }
}
