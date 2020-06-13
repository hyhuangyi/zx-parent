package cn.webapp.controller.zx;

import cn.common.util.redis.RedisLockUtil;
import cn.common.util.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huangYi on 2019/6/29.
 **/
@Api(tags = "分布式锁测试")
@Controller
public class RedisLockController {
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("分布式锁测试一")
    @GetMapping(value = "/comm/redisTest1")
    @ResponseBody
    public void test1() throws Exception {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                boolean lock = RedisLockUtil.lock("yi",8000);
                if (lock) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "获得锁");
                    } finally {
                        RedisLockUtil.releaseLock("yi");
                        System.out.println(Thread.currentThread().getName() + "释放锁成功");
                    }
                } else {
                    System.err.println(Thread.currentThread().getName() + "未获得锁");
                }
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            }).start();

        }
        countDownLatch.await();
        System.out.println("count=" + countDownLatch.getCount() + ",主线程等待结束!");
    }


    @ApiOperation("分布式锁测试二")
    @GetMapping(value = "/comm/redisTest2")
    @ResponseBody
    public void test2() throws Exception {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                boolean lock = RedisLockUtil.tryLock(new Jedis(), "zx", Thread.currentThread().getName(), 100000);
                if (lock) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "加锁成功");
                    } finally {
                        RedisLockUtil.relaceLock(new Jedis(), "zx", Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getName() + "释放锁成功");
                    }
                } else {
                    System.err.println(Thread.currentThread().getName() + "未获得锁");
                }
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            }).start();
        }
        countDownLatch.await();
        System.out.println("count=" + countDownLatch.getCount() + ",主线程等待结束!");
    }

    @ApiOperation("分布式锁测试三")
    @GetMapping(value = "/comm/redisTest3")
    @ResponseBody
    public void test3() throws Exception {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                boolean lock = RedisLockUtil.tryLock("hy");
                if (lock) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "获得锁");
                    } finally {
                        RedisLockUtil.unLock("hy");
                        System.out.println(Thread.currentThread().getName() + "释放锁成功");
                    }
                } else {
                    System.err.println(Thread.currentThread().getName() + "未获得锁");
                }
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            }).start();

        }
        countDownLatch.await();
        System.out.println("count=" + countDownLatch.getCount() + ",主线程等待结束!");
    }


    // 这里必须是Long
    RedisScript<Long> script = RedisScript.of("local num=redis.call('get',KEYS[1])\r\n" +
            "if tonumber(num)>0 then\r\n" +
            "   return redis.call('decr',KEYS[1])\r\n" +
            "else\r\n" +
            "   return -1\r\n" +
            "end", Long.class);

    @ApiOperation("超卖测试")
    @PostMapping("/comm/stock")
    @ResponseBody
    public void redisStock(Long val){
        //设置库存
        RedisUtil.set("stock",val);
        for(int i=0;i<val+5;i++){
            new Thread(()->{
                long remaind = 0;
                if ((remaind=(Long)redisTemplate.execute(script, Arrays.asList("stock"), new Object[] {})) >= 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + "扣除成功" + "剩余：" + remaind);
                    //处理后续逻辑
                }else {
                    System.out.println(Thread.currentThread().getName() + ":" + "扣除失败" + "剩余：" + remaind);
                    //直接返回
                }
            }).start();
        }
    }
}
