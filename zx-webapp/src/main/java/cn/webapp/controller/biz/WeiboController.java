package cn.webapp.controller.biz;

import cn.biz.dto.WeiboDTO;
import cn.biz.po.Weibo;
import cn.biz.service.ISysService;
import cn.common.consts.LogModuleConst;
import cn.webapp.aop.annotation.OperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "weibo相关接口")
@RestController
@Slf4j
@RequiredArgsConstructor
public class WeiboController {
    private final ISysService sysService;

    @ApiOperation("微博列表")
    @GetMapping("/weibo/list")
    @OperateLog(operation = "微博列表查询", moduleName = LogModuleConst.BIZ_MODULE)
    public IPage<Weibo> list(@ModelAttribute WeiboDTO dto) {
        return sysService.getWeiboSearchList(dto);
    }

    @ApiOperation("按关键词爬取微博话题列表")
    @GetMapping("/comm/getWbData")
    public void getData(@RequestParam("key") String key) {
        sysService.handleWeibo(key);
    }

    @ApiOperation("清空数据")
    @GetMapping("/weibo/clean")
    public Boolean cleanData() {
        return sysService.cleanWeibo();
    }
}
