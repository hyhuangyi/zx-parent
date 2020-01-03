package cn.biz.service;

import cn.biz.mapper.AuthMenuMapper;
import cn.biz.mapper.AuthUserRoleMapper;
import cn.biz.mapper.SysUserMapper;
import cn.biz.po.SysUser;
import cn.biz.vo.UserRoleVO;
import cn.common.exception.ZxException;
import cn.common.pojo.base.MyUserDetails;
import cn.common.pojo.base.Token;
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
    @Autowired
    private AuthUserRoleMapper userRoleMapper;
    @Autowired
    private AuthMenuMapper authMenuMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("用户名："+userName);
        List<SysUser> sysUsers=sysUserMapper.getUserByName(userName);
        if (CollectionUtils.isEmpty(sysUsers)) {
            throw new ZxException("用户不存在");
        }
        SysUser sysUser=sysUsers.get(0);
        if(sysUser.getStatus()==1){
            throw new ZxException("用户被禁用");
        }
        List<String> list=userRoleMapper.getRoleCodeByUserId(sysUser.getId());
        String[] roles = list.toArray(new String[list.size()]);
        List<UserRoleVO> roleList=userRoleMapper.getRoleList(sysUser.getId());
        if(roleList==null||roleList.size()==0){
            throw new ZxException("该用户未分配角色");
        }
        String ids="";
        for(UserRoleVO r:roleList){
            ids+=r.getRoleId()+",";
        }
        String roleIds=ids.substring(0,ids.lastIndexOf(","));
        List<String> permissionList=authMenuMapper.getPermissions(roleIds);
        Token token=new Token();
        token.setRoles(roles);
        token.setPermissions(permissionList);
        token.setUserId(sysUser.getId());
        token.setEmail(sysUser.getEmail());
        token.setPhone(sysUser.getPhone());
        token.setUsername(sysUser.getUsername());
        token.setUuid(UUID.randomUUID().toString());
        MyUserDetails user =new MyUserDetails(token);
        user.setPassword(sysUser.getPassword());
        return user;
    }
}
