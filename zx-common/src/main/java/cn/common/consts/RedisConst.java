package cn.common.consts;

//redis相关常量
public class RedisConst {
    //被踢key
    public final static String KIT_OUT_KEY="kitOut:";
    //登陆成功key
    public final static String LOGIN_SUCCESS="token:pc:loginSuccess:";
    //token key
    public final static String TOKEN_KEY="token:pc:";
    //过期时间 2小时 以毫秒计算
    public static final long EXPIRATION_TIME = 7200 * 1000L;
    //签名key
    public static final String JWT_SIGNING_KEY = "springBoot-zx";
    //token heard头
    public static final String AUTHORIZATION = "authorization";
    //csdn magic
    public static final String CSDN_KEY = "csdnMagic";
}
