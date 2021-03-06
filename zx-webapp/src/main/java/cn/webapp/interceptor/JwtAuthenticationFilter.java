package cn.webapp.interceptor;

import cn.common.consts.RedisConst;
import cn.common.pojo.base.MyUserDetails;
import cn.common.pojo.base.Token;
import cn.common.pojo.base.CommonErrorCode;
import cn.common.pojo.base.ResultDO;
import cn.common.util.json.JsonResUtil;
import cn.common.util.jwt.JwtUtil;
import cn.common.pojo.servlet.ServletContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huangYi on 2018/8/26
 * OncePerRequestFilter顾名思义，他能够确保在一次请求只通过一次filter，而不需要重复执行
 * token拦截
 **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //set request和response
        ServletContextHolder.prepare(null, httpServletRequest, httpServletResponse);
        //白名单 不需要过滤直接传给下一个过滤器
        if (passWhiteUrl(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String tokenStr = httpServletRequest.getHeader(RedisConst.AUTHORIZATION);
        //tokenStr==null 不需要过滤直接传给下一个过滤器
        if (tokenStr == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        Token token = null;

        try {
            token = JwtUtil.getJwtToken(tokenStr);
        } catch (Exception e) {
            JsonResUtil.renderJson(new ResultDO(CommonErrorCode.TOKEN_EXPIRE));
            return;
        }
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("checking authentication für user " +token.getUsername());
            MyUserDetails user = JwtUtil.getUserDetail(token);
            if (user != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                ServletContextHolder.setToken(token);
                //刷新token
                String newToken=JwtUtil.refreshToken(token);
                httpServletResponse.setHeader(RedisConst.AUTHORIZATION, newToken);
            } else {
                //判断此token是否被T下线的
                if (JwtUtil.isKickOut(token)) {//是,提示账号在其他设备登录
                    JsonResUtil.renderJson(new ResultDO(CommonErrorCode.USER_KICKOUT));
                } else {//否,token失效
                    JsonResUtil.renderJson(new ResultDO(CommonErrorCode.TOKEN_EXPIRE));
                }
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 白名单过滤
     *
     * @param request
     */
    private boolean passWhiteUrl(HttpServletRequest request) {
        String uri = request.getServletPath();
        if (uri.startsWith("/comm") || uri.startsWith("/druid")||uri.startsWith("/actuator")) {
            return true;
        }
        return false;
    }
}
