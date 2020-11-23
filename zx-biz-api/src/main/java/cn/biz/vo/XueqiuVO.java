package cn.biz.vo;

import lombok.Data;
import java.util.List;

/**
 * 雪球 api
 */
@Data
public class XueqiuVO {
    private DataBean data;
    private String error_code;
    private String error_description;
    @Data
    public static class DataBean {
        private String count;
        private List<ListBean> list;
        @Data
        public static class ListBean {
            private String symbol;
            private Object net_profit_cagr;
            private double ps;
            private String type;
            private double percent;
            private boolean has_follow;
            private double tick_size;
            private double pb_ttm;
            private String float_shares;
            private double current;
            private double amplitude;
            private Object pcf;
            private double current_year_percent;
            private String float_market_capital;
            private String market_capital;
            private String dividend_yield;
            private String lot_size;
            private double roe_ttm;
            private double total_percent;
            private String percent5m;
            private double income_cagr;
            private String amount;
            private double chg;
            private long issue_date_ts;
            private String main_net_inflows;
            private String volume;
            private double volume_ratio;
            private double pb;
            private String followers;
            private double turnover_rate;
            private double first_percent;
            private String name;
            private Object pe_ttm;
            private String total_shares;
        }
    }
}
