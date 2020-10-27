package cn.webapp.schedule;

import cn.biz.mapper.StockMapper;
import cn.biz.po.Stock;
import cn.biz.service.ISysService;
import cn.biz.vo.StockVO;
import cn.common.pojo.base.PageResultDO;
import cn.common.util.date.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
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
        String hm= DateUtils.getStringDate(new Date(),"hh:mm");
        if(list.contains(hm)){
            return;
        }
        if(hm.equals("15:00")){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        StockVO stockVO=sysService.getStockInfo();
        Stock stock=new Stock();
        BeanUtils.copyProperties(stockVO,stock);
        stock.setDate(date);
        stockMapper.insert(stock);
    }
}
