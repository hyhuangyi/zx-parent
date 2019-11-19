package cn.webapp.controller.user;

import cn.biz.group.ZxFirst;
import cn.biz.po.CdCity;
import cn.common.pojo.servlet.ServletContextHolder;
import cn.webapp.aop.annotation.Decrypt;
import cn.webapp.aop.annotation.Encrypt;
import cn.webapp.aop.annotation.TimeCount;
import cn.webapp.aop.annotation.ValidatedRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

    @Decrypt
    @ApiOperation("对传过来的加密参数解密")
    @PostMapping("/comm/decryption")
    @ResponseBody
    public CdCity Decryption(@RequestBody CdCity city){
        return city;
    }

    @Encrypt
    @ApiOperation("对返回值进行加密")
    @GetMapping("/comm/encryption")
    @ResponseBody
    public CdCity encryption(){
        CdCity city = new CdCity();
        city.setCode("10000");
        city.setFullName("杭州");
        return city;
    }
}
