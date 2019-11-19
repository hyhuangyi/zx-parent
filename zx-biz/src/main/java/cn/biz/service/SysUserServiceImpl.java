package cn.biz.service;

import cn.biz.dto.AddUserDTO;
import cn.biz.dto.UserListDTO;
import cn.biz.mapper.AuthUserRoleMapper;
import cn.biz.mapper.SysUserMapper;
import cn.biz.po.AuthUserRole;
import cn.biz.po.SysUser;
import cn.biz.vo.UserListVO;
import cn.common.exception.ZxException;
import cn.common.util.comm.RegexUtils;
import cn.common.util.string.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by huangYi on 2018/9/6
 **/
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private AuthUserRoleMapper userRoleMapper;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    /**
     * 新增||编辑用户
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(AddUserDTO dto) {
        if(dto.getRoles().size()==0){
            throw new ZxException("角色不能为空");
        }
        if(!RegexUtils.checkEmail(dto.getEmail())){
            throw new ZxException("邮箱格式不正确！");
        }
        if(!RegexUtils.checkMobile(dto.getPhone())){
            throw new ZxException("手机格式不正确");
        }
        SysUser sysUser=new SysUser();
        sysUser.setPhone(dto.getPhone());
        sysUser.setEmail(dto.getEmail());
        sysUser.setUsername(dto.getUsername());
        sysUser.setUpdateDate(LocalDateTime.now());

        if(StringUtils.isBlank(dto.getId())){//新增
            sysUser.setPassword(passwordEncoder.encode("123456"));
            sysUser.setCreateDate(LocalDateTime.now());
            if(sysUserMapper.selectUserCount(dto.getUsername(),dto.getPhone())>0){
                throw new ZxException("该用户已存在");
            }
            sysUserMapper.insert(sysUser);
        }else {
            SysUser one= sysUserMapper.selectById(dto.getId());
            if(one==null){
                throw new ZxException("用户不存在");
            }
            if(!dto.getUsername().equals(one.getUsername())){
                throw new ZxException("账号不能修改");
            }
            if(!dto.getPhone().equals(one.getPhone())){
                if(sysUserMapper.selectCount(new QueryWrapper<SysUser>().eq("phone",dto.getPhone()))!=0){
                    throw new ZxException("手机号已被注册");
                }
            }
            sysUser.setId(one.getId());
            sysUserMapper.updateById(sysUser);
            userRoleMapper.delete(new QueryWrapper<AuthUserRole>().eq("user_id",dto.getId()));
        }
        List<AuthUserRole> addList= Lists.newArrayList();
        for(String str:dto.getRoles()){
            addList.add(new AuthUserRole(sysUser.getId(),Long.valueOf(str)));
        }
        userRoleMapper.insertBatchUserRole(addList);
        return true;
    }

    /**
     * 用户列表
     * @param dto
     * @return
     */
    @Override
    public IPage<SysUser> getUserList(UserListDTO dto) {
        Page<SysUser> page=new Page(dto.getCurrent(),dto.getSize());
        page.setRecords(sysUserMapper.getUserList(dto,page));
        return page;
    }

    /**
     * 获取角色信息
     * @param id
     * @return
     */
    @Override
    public UserListVO getUserInfo(String id) {
      return sysUserMapper.getUserInfo(id);
    }
}
