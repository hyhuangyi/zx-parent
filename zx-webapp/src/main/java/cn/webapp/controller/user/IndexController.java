package cn.webapp.controller.user;

import cn.biz.group.ZxFirst;
import cn.biz.po.CdCity;
import cn.common.pojo.servlet.ServletContextHolder;
import cn.webapp.aop.annotation.TimeCount;
import cn.webapp.aop.annotation.ValidatedRequest;
import cn.webapp.domain.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangYi on 2018/8/13
 **/
@Api(description = "首页")
@Validated
@Controller
public class IndexController {

    @ApiOperation("服务监控")
    @GetMapping("/comm/monitor")
    public String monitor(Model model)throws Exception{
        Server server=new Server();
        server.copyTo();
        model.addAttribute("/comm/server", server);
        return "server";
    }
    @ApiOperation("爱情树")
    @RequestMapping(value = "/comm/aqs",method = RequestMethod.GET)
    public String aqs(){
        return "aqs";
    }

    @ApiOperation("跨域测试")
    @ResponseBody
    @RequestMapping(value = "/comm/zx",method = RequestMethod.GET)
    public String zx(HttpServletRequest request)  {
        System.out.println(request.getHeader("authorization"));
       return "http://xll-test.oss-cn-shanghai.aliyuncs.com/template/1571975546369560.png";
    }

    /**
     * 权限必须要有前缀ROLE_
     */
    @PreAuthorize("hasRole('ROLE_admin')")
    @ApiOperation("@Valid@ModelAttribute注解测试(分组) ")
    @GetMapping("/valid")
    @ResponseBody
    @ValidatedRequest
    @TimeCount
    public Map exceptionTest(@Validated({ZxFirst.class})@ModelAttribute CdCity city, BindingResult result) {
        Map map=new HashMap();
        map.put("code",city.getCode());
        map.put("ass", SecurityContextHolder.getContext().getAuthentication());
        map.put("token",ServletContextHolder.getToken());
        return map;
    }
}
