package cn.webapp.controller.user;

import cn.biz.po.SysUser;
import cn.biz.service.ISysUserService;
import cn.common.exception.ZxException;
import cn.common.pojo.base.Token;
import cn.common.pojo.monitor.Server;
import cn.common.pojo.servlet.ServletContextHolder;
import cn.common.util.jwt.JwtUtil;
import cn.common.util.redis.RedisUtil;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.TimeCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
/**
 * Created by huangYi on 2018/9/5
 **/
@Api(description = "登陆/出||系统监控")
@RestController
public class LoginController {

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("登陆")
    @RequestMapping(value = "/comm/login",method = RequestMethod.POST)
    @OperateLog(operation = "#{#user.username}用户登入")
    @TimeCount
    public Token login(@ModelAttribute SysUser user){
        if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
            throw new ZxException("用户名或密码不能为空");
        }
        return sysUserService.login(user);
    }

    @ApiOperation("退出")
    @GetMapping("/quit")
    public Boolean logout(){
        Token token=ServletContextHolder.getToken();
        String key= JwtUtil.tokenKey(token);
        RedisUtil.del(key);//删除token key
        String loginSuccess=JwtUtil.loginSuccessKey(token);
        RedisUtil.del(loginSuccess);//删除 登录成功的key
        ServletContextHolder.setToken(null);
        SecurityContextHolder.clearContext();
        return true;
    }

    @TimeCount
    @ApiOperation("监控")
    @GetMapping("/comm/monitor")
    public Server monitor(){
      return  sysUserService.monitorIfo();
    }

}
