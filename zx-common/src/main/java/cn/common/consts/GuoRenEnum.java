package cn.common.consts;

/**
 * 果仁枚举
 */
public enum GuoRenEnum {
    MACD_JINCHA( 1, "macd金叉"),
    THREE_UP( 2, "连续3日上涨"),
    BULIN_TUPO( 3, "布林突破上轨"),
    PE_MIN( 4, "市盈率最小");

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
