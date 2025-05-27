package cn.webapp.schedule;

import cn.biz.mapper.StockMapper;
import cn.biz.mapper.XqDataMapper;
import cn.biz.po.Stock;
import cn.biz.po.XqData;
import cn.biz.service.ISysService;
import cn.biz.vo.StockVO;
import cn.biz.vo.XueqiuVO;
import cn.common.pojo.servlet.ServletContextHolder;
import cn.common.util.date.DateUtils;
import cn.common.util.http.HttpRequestUtil;
import cn.common.util.ip.IpUtil;
import cn.common.util.math.XMathUtil;
import cn.common.util.redis.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.biz.service.SysServiceImpl.XUE_QIU_REALTIME_STOCK;

@Component
@Slf4j
public class StockJob {
    @Autowired
    private ISysService sysService;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private XqDataMapper xqDataMapper;


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
    @Scheduled(cron = "0 0/15 9,10,11,13,14,15 * * ?")
    public void stockJob() {
        String date = DateUtils.getStringDate(new Date(), "yyyy-MM-dd HH:mm");
        String hm = DateUtils.getStringDate(new Date(), "HH:mm");
        //不在交易时间或者节假日、周末不做操作
        if (list.contains(hm) || !"0".equals(DateUtils.isHoliday(DateFormatUtils.format(new Date(), "yyyy-MM-dd")))) {
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
        StockVO stockVO = sysService.getStockInfoV2();
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockVO, stock);
        stock.setDate(date);
        stockMapper.insert(stock);
        log.info("========时间：" + date + ";两市成交额：" + stock.getTurnOver() + ";上证：" + stock.getShangz() + ";深证：" + stock.getShenz() + ";创业板：" + stock.getChuangy() + "========");
    }


    /**
     * 存入每天数据
     * 每隔15分钟执行一次（9-15）
     */
    @Scheduled(cron = "0 0/15 9,10,11,13,14,15 * * ?")
    public void xqStock() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//当日日期
        String hm = DateUtils.getStringDate(new Date(), "HH:mm");
        if (list.contains(hm) || !"0".equals(DateUtils.isHoliday(today))) {//排除不在交易时间或者节假日、周末
            return;
        }
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handXqData(today);
    }

    private  static String codes = "SZ000858,SH600869";

    public void handXqData(String today) {
        List<XqData> add = new ArrayList<>();//入库的数据
        Map<String, String> req = new HashMap<>();
        if(RedisUtil.hasKey("zx_run_stocks")){
            codes=RedisUtil.get("zx_run_stocks").toString();
        }
        req.put("symbol", codes);
        String result = HttpRequestUtil.get(XUE_QIU_REALTIME_STOCK, req, null);
        String arr = JSONObject.parseObject(result).get("data").toString();
        //指定数据
        List<XueqiuVO.DataBean.ListBean> list = JSONArray.parseArray(arr, XueqiuVO.DataBean.ListBean.class);
        List<XqData> xqData = stockMapper.selectStockInfos();
        Map<String, String> xqDataMap = xqData.stream().collect(Collectors.toMap(XqData::getSymbol, XqData::getName, (v1, v2) -> v1));
        //全部数据
        //List<XueqiuVO.DataBean.ListBean> list = sysService.getXueqiuList(null, null);
        for (XueqiuVO.DataBean.ListBean bean : list) {
            XqData data = new XqData()
                    .setDate(today)
                    .setCreateTime(LocalDateTime.now())
                    .setAmount(XMathUtil.divide(bean.getAmount(), "100000000"))
                    .setAmplitude(bean.getAmplitude())
                    .setCurrent(bean.getCurrent())
                    .setCurrentYearPercent(bean.getCurrent_year_percent())
                    .setMarketCapital(XMathUtil.divide(bean.getMarket_capital(), "100000000"))
                    .setName(bean.getName())
                    .setPercent(bean.getPercent())
                    .setSymbol(bean.getSymbol())
                    .setTurnoverRate(bean.getTurnover_rate())
                    .setVolumeRatio(bean.getVolume_ratio());
            if (xqDataMap.containsKey(bean.getSymbol().substring(2))) {
                data.setName(xqDataMap.get(bean.getSymbol().substring(2)));
            }
            add.add(data);
        }
        xqDataMapper.delete(new QueryWrapper<XqData>().eq("date", today));
        xqDataMapper.insertBatch(add);
    }
}
