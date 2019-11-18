package cn.biz.service;

import cn.biz.mapper.SysUserMapper;
import cn.biz.po.SysUser;
import cn.common.pojo.base.MyUserDetails;
import cn.common.pojo.base.Token;
import cn.common.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.UUID;

/**
 * Created by huangYi on 2018/8/31
 **/
@Service
@Slf4j
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("用户名："+userName);
        List<SysUser> sysUsers=sysUserMapper.getUserByName(userName);
        if (CollectionUtils.isEmpty(sysUsers)) {
            throw new UserException("用户不存在");
        }
        SysUser sysUser=sysUsers.get(0);
        Token token=new Token();
        token.setEmail(sysUser.getEmail());
        token.setPhone(sysUser.getPhone());
        token.setUsername(sysUser.getUsername());
        token.setUuid(UUID.randomUUID().toString());
        MyUserDetails user =new MyUserDetails(token);
        user.setPassword(sysUser.getPassword());
        return user;
    }
}
