package cn.biz.vo;

import lombok.Data;
import java.util.List;

/**
 * 果仁网  智能小果 api
 */
@Data
public class GuorenVO {
    private String status;
    private DataBean data;
    @Data
    public static class DataBean {
        private String return_type;
        private String translated_query;
        private String realtime_ts;
        private SheetDataBean sheet_data;
        private SheetDefnBean sheet_defn;
        @Data
        public static class SheetDataBean {
            private int row_size;
            private int col_size;
            private List<List<Double>> meas_data;
            private List<ColBean> col;
            private List<RowBean> row;
            @Data
            public static class ColBean {
                private String fmt;
                private String type;
                private String id;
                private String name;
                private List<Double> rng;
            }
            @Data
            public static class RowBean {
                private DBean d;
                private String type;
                private String id;
                private String name;
                private List<List<String>> data;
                @Data
                public static class DBean {
                    private int disp;
                    private int id;
                }
            }
        }
        @Data
        public static class SheetDefnBean {
            private LayoutBean layout;
            @Data
            public static class LayoutBean {
                private String layout_type;
                private GridBean grid;
                @Data
                public static class GridBean {
                    private List<List<String>> column;
                    private List<String> row;
                }
            }
        }
    }
}
