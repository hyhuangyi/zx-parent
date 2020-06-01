package cn.webapp.controller.biz;

import cn.biz.dto.WeiboDTO;
import cn.biz.po.Weibo;
import cn.biz.service.ISysService;
import cn.common.consts.LogModuleConst;
import cn.webapp.aop.annotation.OperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "微博相关接口")
@RestController
@Slf4j
public class WeiboController {
    @Autowired
    private ISysService sysService;

    @ApiOperation("微博列表")
    @GetMapping("/weibo/list")
    @OperateLog(operation = "微博列表查询",moduleName = LogModuleConst.BIZ_MODULE)
    public IPage<Weibo> list(@ModelAttribute WeiboDTO dto){
        return sysService.getWeiboSearchList(dto);
    }
}
