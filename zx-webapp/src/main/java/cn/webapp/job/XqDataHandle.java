package cn.webapp.job;

import cn.biz.mapper.StockMapper;
import cn.biz.mapper.XqDataMapper;
import cn.biz.po.Stock;
import cn.biz.po.XqData;
import cn.biz.vo.StockVO;
import cn.biz.vo.XueqiuVO;
import cn.common.util.date.DateUtils;
import cn.common.util.dingding.DingdingNotifyUtil;
import cn.common.util.http.HttpRequestUtil;
import cn.common.util.math.XMathUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.biz.service.SysServiceImpl.XUE_QIU_REALTIME_STOCK;
import static cn.biz.service.SysServiceImpl.XUE_QIU_V2;


@Component
@RequiredArgsConstructor
@Slf4j
public class XqDataHandle {
    private final XqDataMapper xqDataMapper;
    private final StockMapper stockMapper;

    /**
     * Checks if the given time is within trading hours (9:30-11:30 and 13:00-15:00), inclusive
     *
     * @param time the time to check
     * @return true if time is within trading hours, false otherwise
     */
    public static boolean isWithinTradingHours(LocalTime time) {
        LocalTime morningStart = LocalTime.of(9, 30);
        LocalTime morningEnd = LocalTime.of(11, 30);
        LocalTime afternoonStart = LocalTime.of(13, 0);
        LocalTime afternoonEnd = LocalTime.of(15, 0,59);
        return (!time.isBefore(morningStart) && !time.isAfter(morningEnd)) ||
                (!time.isBefore(afternoonStart) && !time.isAfter(afternoonEnd));
    }

    public static Boolean ifPass(LocalTime localTime) {
        boolean isTradingTime = XqDataHandle.isWithinTradingHours(localTime);
        boolean isWorkday = "0".equals(DateUtils.isHoliday(DateFormatUtils.format(new Date(), "yyyy-MM-dd")));
        //不在交易时间 9:30-11:30 13:00-15:00 直接pass
        //不是交易日 直接pass
        return !isTradingTime || !isWorkday;
    }

    public void handleTurnover(String date, String hm) {
        try {
            if ("09:30".equals(hm) || "15:00".equals(hm)) {
                Thread.sleep(90000);
            } else {
                Thread.sleep(30000);//休息30秒
            }
            StockVO stockVO = getStockInfoV2();
            Stock stock = new Stock();
            BeanUtils.copyProperties(stockVO, stock);
            stock.setDate(date);
            stockMapper.insert(stock);
            log.info("========时间：" + date + ";两市成交额：" + stock.getTurnOver() + ";上证：" + stock.getShangz() + ";深证：" + stock.getShenz() + ";创业板：" + stock.getChuangy() + "========");
        } catch (Exception e) {
            log.error("获取成交额异常", e);
            DingdingNotifyUtil.sendDingding("handleTurnover异常", "handleTurnover异常,error=>" + e.getMessage(), DingdingNotifyUtil.url, DingdingNotifyUtil.secret, true);
        }

    }

    public void handXqDataAll(String today) {
        try {
            Thread.sleep(30 * 1000);
            List<XqData> add = new ArrayList<>();//入库的数据
            for (String order : Arrays.asList("asc", "desc")) {
                for (int i = 1; i <= 30; i++) {
                    fetchXqData(add, today, i, order);
                }
            }
            if (!add.isEmpty()) {
                xqDataMapper.delete(new QueryWrapper<XqData>().eq("date", today));
                xqDataMapper.insertBatch(add);
            }
        } catch (Exception e) {
            DingdingNotifyUtil.sendDingding("handXqDataAll异常", "handXqDataAll取数异常,error=>" + e.getMessage(), DingdingNotifyUtil.url, DingdingNotifyUtil.secret, true);
        }
    }


    public StockVO getStockInfoV2() {
        StockVO res = new StockVO();
        Map<String, String> head = new HashMap<>();
        head.put("Host", "xueqiu.com");
        head.put("Accept", "application/json");
        head.put("User-Agent", "Xueqiu iPhone 15.8");
        Map<String, String> req = new HashMap<>();
        req.put("symbol", "SH000001,SZ399001,SZ399006");
        String json = HttpRequestUtil.get(XUE_QIU_REALTIME_STOCK, req, head);
        JSONArray array = JSON.parseObject(json).getJSONArray("data");
        BigDecimal amount = BigDecimal.ZERO;
        for (Object l : array) {
            JSONObject jsonObject = JSON.parseObject(l.toString());
            String symbol = jsonObject.getString("symbol");
            double percent = jsonObject.getDoubleValue("percent");
            double current = jsonObject.getDoubleValue("current");
            if (symbol.contains("000001")) {
                res.setShangz(percent);
            } else if (symbol.contains("399001")) {
                res.setShenz(percent);
            } else {
                res.setChuangy(percent);
            }
            System.out.println(symbol + "=》current:" + current + " percent:" + percent);
            if (symbol.contains("000001") || symbol.contains("399001")) {
                amount = amount.add(jsonObject.getBigDecimal("amount"));
            }
        }
        System.out.println("amount=>" + XMathUtil.divide(amount, BigDecimal.valueOf(100000000), 2));
        res.setTurnOver(XMathUtil.divide(amount, BigDecimal.valueOf(100000000), 2).doubleValue());
        return res;
    }

    private void fetchXqData(List<XqData> add, String today, Integer i, String order) {
        Map<String, String> head = new HashMap<>();
        head.put("Host", "xueqiu.com");
        head.put("Accept", "application/json");
        head.put("User-Agent", "Xueqiu iPhone 11.8");
        String url = MessageFormat.format(XUE_QIU_V2, i, order);
        String json = HttpRequestUtil.get(url, null, head);

        XueqiuVO vo = JSONObject.parseObject(json, XueqiuVO.class);
        List<XueqiuVO.DataBean.ListBean> list = vo.getData().getList();
        for (XueqiuVO.DataBean.ListBean bean : list) {
            XqData data = new XqData().setDate(today).setCreateTime(LocalDateTime.now()).setAmount(XMathUtil.divide(bean.getAmount(), "100000000")).setAmplitude(bean.getAmplitude()).setCurrent(bean.getCurrent()).setCurrentYearPercent(bean.getCurrent_year_percent()).setMarketCapital(XMathUtil.divide(bean.getMarket_capital(), "100000000")).setName(bean.getName()).setPercent(bean.getPercent()).setSymbol(bean.getSymbol()).setTurnoverRate(bean.getTurnover_rate()).setVolumeRatio(bean.getVolume_ratio());
            if (!add.stream().map(XqData::getSymbol).collect(Collectors.toList()).contains(bean.getSymbol())) {
                add.add(data);
            }
        }
    }
}
