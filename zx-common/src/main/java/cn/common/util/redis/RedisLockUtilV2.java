package cn.common.util.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 为确保分布式锁可用，确保四个条件：
 * 1)互斥性。（在任意时刻，只有一个客户端能持有锁）
 * 2)不会发生死锁。(设置过期时间 防止系统崩溃没有释放锁 形成死锁)
 * 3)解铃还须系铃人。(加锁和解锁必须是同一个客户端，客户端自己不能释放掉别人加的锁 值设置成uuid)
 * 4)加锁和解锁必须具有原子性。(加锁 用了setIfAbsent(原子性)  释放锁用了lua 脚本(原子性))
 */
@Component
@Slf4j
public class RedisLockUtilV2 {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public RedisLockUtilV2(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取锁并设置过期时间
     *
     * @param key     redis key
     * @param value   redis 值 建议uuid  每个客户端加锁的唯一标识
     * @param timeout 过期时间 单位秒
     * @return
     */
    public Boolean tryLock(String key, String value, Long timeout) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }


    /**
     * 解锁
     * @param key key
     * @param value key对应的值
     */
    public void unLock(String key,String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        // 使用redis执行lua脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        // 第一个要是script 脚本 ，第二个需要判断的key，第三个是value值。
        redisTemplate.execute(redisScript, Collections.singletonList(key), value);
    }
}