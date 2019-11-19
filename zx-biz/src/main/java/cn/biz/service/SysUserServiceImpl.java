package cn.biz.service;

import cn.biz.mapper.SysUserMapper;
import cn.biz.po.SysUser;
import cn.common.exception.ZxException;
import cn.common.util.comm.RegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by huangYi on 2018/9/6
 **/
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    /**
     * 注册
     * @param sysUser
     * @return
     */
    @Override
    public boolean register(SysUser sysUser) {
        if(StringUtils.isEmpty(sysUser.getEmail())){
            throw new ZxException("邮箱不能为空");
        }
        if(StringUtils.isEmpty(sysUser.getPhone())){
            throw new ZxException("手机号码不能为空");
        }
        if(!RegexUtils.checkEmail(sysUser.getEmail())){
            throw new ZxException("邮箱格式不正确！");
        }
        if(!RegexUtils.checkMobile(sysUser.getPhone())){
            throw new ZxException("手机格式不正确");
        }
        List<SysUser> list1=sysUserMapper.getUserByEmail(sysUser.getEmail());
        List<SysUser> list2=sysUserMapper.getUserByPhone(sysUser.getPhone());
        List<SysUser> list3=sysUserMapper.getUserByName(sysUser.getUsername());
        if(list1.size()>=1){
            throw new ZxException("邮箱已被注册");
        }
        if(list2.size()>=1){
            throw new ZxException("手机号码已被注册");
        }
        if(list3.size()>=1){
            throw new ZxException("用户名已被注册");
        }
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setCreateDate(LocalDateTime.now());
        sysUser.setUpdateDate(LocalDateTime.now());
        sysUserMapper.insert(sysUser);
        return true;
    }




}
