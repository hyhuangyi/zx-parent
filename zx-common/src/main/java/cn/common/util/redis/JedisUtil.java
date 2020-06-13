package cn.common.util.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import javax.annotation.PostConstruct;

/**
 * jedis工具类
 */
@Component
public class JedisUtil {
    @Value("${spring.redis.host}")
    private  String host;
    @Value("${spring.redis.port}")
    private  Integer port;
    @Value("${spring.redis.password}")
    private  String password;
    @Value("${spring.redis.timeout}")
    private  Integer timeout;
    @Value("${spring.redis.database}")
    private  Integer database;

    private static JedisPool pool=null ;

    @PostConstruct
    public void init(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxTotal(100);
        //最大空闲连接数
        poolConfig.setMaxIdle(30);
        //最小空闲连接数
        poolConfig.setMinIdle(10);
        pool = new JedisPool(poolConfig,host,port,timeout,password,database);
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }
    public static void release(Jedis jedis){
        if(null != jedis){
            jedis.close();
        }
    }
}