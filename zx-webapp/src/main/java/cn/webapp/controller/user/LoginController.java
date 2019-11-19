package cn.webapp.controller.user;

import cn.biz.po.SysUser;
import cn.biz.service.ISysUserService;
import cn.common.pojo.base.MyUserDetails;
import cn.common.pojo.base.Token;
import cn.common.pojo.servlet.ServletContextHolder;
import cn.common.util.jwt.JwtUtil;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.ValidatedRequest;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by huangYi on 2018/9/5
 **/
@Api(description = "登陆")
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ISysUserService userService;

    @ApiOperation("登陆")
    @RequestMapping(value = "/comm/login",method = RequestMethod.POST)
    @ValidatedRequest
    @OperateLog(operation = "#{#user.username}用户登入")
    public String login(@Valid@ModelAttribute(value="user") SysUser user, BindingResult result, HttpServletResponse response){
        //验证
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //通过后将authentication放入SecurityContextHolder里
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUserDetails userDetail=(MyUserDetails) authentication.getPrincipal();
        Token token=userDetail.getToken();
        ServletContextHolder.setToken(token);
        //生成token str
        String json=JSON.toJSONString(token);
        String tokenStr= JwtUtil.getToken(json);
        response.addHeader(JwtUtil.AUTHORIZATION,tokenStr);
        //存入redis
        JwtUtil.saveTokenInfo(userDetail.getToken());
        return tokenStr;
    }
}
