package cn.webapp.schedule;

import cn.biz.mapper.StockMapper;
import cn.biz.po.Stock;
import cn.biz.service.ISysService;
import cn.biz.vo.StockVO;
import cn.common.util.date.DateUtils;
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

    private static List<String> list=new ArrayList<>();
    static {
        list.add("09:00");
        list.add("09:15");
        list.add("11:45");
        list.add("15:15");
        list.add("15:30");
        list.add("15:45");
    }
    @Scheduled(cron="0 0/15 9,10,11,13,14,15 * * ?")
    public void cronJob(){

        String date= DateUtils.getStringDate(new Date(),"yyyy-MM-dd HH:mm");
        String hm= DateUtils.getStringDate(new Date(),"HH:mm");
        if(list.contains(hm)||!"0".equals(DateUtils.isHoliday(DateFormatUtils.format(new Date(),"yyyyMMdd")))){
            return;
        }
        try {
            Thread.sleep(30000);//休息30秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StockVO stockVO=sysService.getStockInfo();
        Stock stock=new Stock();
        BeanUtils.copyProperties(stockVO,stock);
        stock.setDate(date);
        stockMapper.insert(stock);
        log.info("========时间："+date+";两市成交额："+stock.getTurnOver()+";上证："+stock.getShangz()+";深证："+stock.getShenz()+";创业板："+stock.getChuangy()+"========");
    }
}
