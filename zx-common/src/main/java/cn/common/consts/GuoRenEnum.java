package cn.common.consts;

/**
 * 果仁枚举
 */
public enum GuoRenEnum {
    QS_CODE(0,"macd金叉&&布林突破&&连续3日上涨"),
    MACD_JINCHA( 1, "macd金叉"),
    THREE_UP( 2, "连续3日上涨"),
    BULIN_TUPO_SHANG( 3, "布林线突破上轨"),
    PE_MIN( 4, "市盈率最小"),
    DR_ZHANGTING( 5, "当日涨停股票"),
    BANK_MIN( 6, "银行市净率最小"),
    BULIN_TUPO_XIA( 7, "布林线突破下轨");

    private int type;
    private String prefix;

     GuoRenEnum(int type, String prefix) {
        this.type = type;
        this.prefix = prefix;
    }
    public int getType() {
        return type;
    }
    public String getPrefix() {
        return prefix;
    }

    /**
     * 通过type获取prefix
     * @param type
     * @return
     */
    public static GuoRenEnum getPrefixByType(int type){
        for(GuoRenEnum g:GuoRenEnum.values()){
            if(type==g.type){
                return g;
            }
        }
        return null;
    }
}
