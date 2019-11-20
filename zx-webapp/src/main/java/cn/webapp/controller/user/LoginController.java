package cn.webapp.controller.user;

import cn.biz.po.SysUser;
import cn.biz.service.ISysUserService;
import cn.common.pojo.base.Token;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.TimeCount;
import cn.webapp.aop.annotation.ValidatedRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
/**
 * Created by huangYi on 2018/9/5
 **/
@Api(description = "登陆")
@RestController
public class LoginController {

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("登陆")
    @RequestMapping(value = "/comm/login",method = RequestMethod.POST)
    @ValidatedRequest
    @OperateLog(operation = "#{#user.username}用户登入")
    @TimeCount
    public Token login(@Valid@ModelAttribute(value="user") SysUser user, BindingResult result){
        return sysUserService.login(user);
    }
}
