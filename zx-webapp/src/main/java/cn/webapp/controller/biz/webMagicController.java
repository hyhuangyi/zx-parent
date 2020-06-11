package cn.webapp.controller.biz;

import cn.biz.service.ISysService;
import cn.common.consts.RedisConst;
import cn.common.exception.ZxException;
import cn.common.util.redis.RedisUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Api(tags = "webMagic相关接口")
@RestController
@Slf4j
public class webMagicController {

    @Autowired
    private ISysService sysService;

    @GetMapping("/comm/start/csdn")
    @ApiOperation("启动csdn访问")
    public Boolean startCsdn(@RequestParam("page") String page, @RequestParam(value = "minute", defaultValue = "5") Integer minute) {
        if (!RedisUtil.hasKey(RedisConst.CSDN_KEY + page)) {
            RedisUtil.set(RedisConst.CSDN_KEY + page, 0);
            sysService.handleCsdn(page, minute);
            return true;
        }
        throw new ZxException("已在执行");
    }

    @GetMapping("/comm/stop/csdn")
    @ApiOperation("关闭csdn")
    public Boolean stopCsdn(@RequestParam("page") String page) {
        if (RedisUtil.hasKey(RedisConst.CSDN_KEY + page)) {
            RedisUtil.del(RedisConst.CSDN_KEY + page);
            return true;
        }
        throw new ZxException("key不存在");
    }

    @GetMapping("/comm/runStatus")
    @ApiOperation("获取运行状态")
    public Map<String,Object> getStatus(){
       Object o1= RedisUtil.get(RedisConst.CSDN_KEY+1);
       Object o2= RedisUtil.get(RedisConst.CSDN_KEY+2);
       Map<String,Object> map= Maps.newHashMap();
       map.put("v1",o1);
       map.put("v2",o2);
       return map;
    }
}
