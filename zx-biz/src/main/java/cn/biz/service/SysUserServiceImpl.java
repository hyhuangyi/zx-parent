package cn.biz.service;

import cn.biz.dto.SaveUserDTO;
import cn.biz.dto.UserListDTO;
import cn.biz.dto.UserStatusDTO;
import cn.biz.mapper.AuthUserRoleMapper;
import cn.biz.mapper.SysUserMapper;
import cn.biz.po.AuthUserRole;
import cn.biz.po.SysUser;
import cn.biz.vo.MenuVO;
import cn.biz.vo.UserListVO;
import cn.biz.vo.UserRoleVO;
import cn.biz.vo.ZxToken;
import cn.common.consts.UserConst;
import cn.common.exception.ZxException;
import cn.common.pojo.base.MyUserDetails;
import cn.common.pojo.base.Token;
import cn.common.pojo.monitor.Server;
import cn.common.pojo.servlet.ServletContextHolder;
import cn.common.util.comm.RegexUtils;
import cn.common.util.jwt.JwtUtil;
import cn.common.util.string.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private AuthUserRoleMapper userRoleMapper;
    @Autowired
    private IAuthRoleService authRoleService;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public ZxToken login(SysUser user) {
        //验证
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //通过后将authentication放入SecurityContextHolder里
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUserDetails userDetail=(MyUserDetails) authentication.getPrincipal();
        Token token=userDetail.getToken();
        //生成token str
        String json= JSON.toJSONString(token);
        String tokenStr= JwtUtil.getToken(json);
        //存入redis
        JwtUtil.saveTokenInfo(token);
        token.setToken(tokenStr);
        ServletContextHolder.setToken(token);
        List<UserRoleVO> roleList=userRoleMapper.getRoleList(token.getUserId());
        if(roleList.size()==0){
            throw new ZxException("该用户未分配角色");
        }
        String ids="";
        for(UserRoleVO r:roleList){
            ids+=r.getRoleId()+",";
        }
        List<MenuVO> menusList=authRoleService.getUserMenus(ids.substring(0,ids.lastIndexOf(",")));
        ZxToken zxToken=new ZxToken();
        BeanUtils.copyProperties(token,zxToken);
        zxToken.setRoleList(roleList);
        zxToken.setMenuVOList(menusList);
        return zxToken;
    }

    /**
     * 新增||编辑用户
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(SaveUserDTO dto) {
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
            sysUser.setPassword(passwordEncoder.encode(UserConst.DEFAULT_PASSWORD));
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
        Page<SysUser> page=new Page<>(dto.getCurrent(),dto.getSize());
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

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public boolean delUser(String id) {
        if("0".equals(id)){
            throw new ZxException("超级管理员不能删除");
        }
        sysUserMapper.deleteById(id);
        userRoleMapper.delete(new QueryWrapper<AuthUserRole>().eq("user_id",id).eq("is_del",0));
        return true;
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @Override
    public boolean reset(String id) {
        SysUser sysUser=new SysUser();
        sysUser.setId(Long.valueOf(id));
        sysUser.setUpdateDate(LocalDateTime.now());
        sysUser.setPassword(passwordEncoder.encode(UserConst.DEFAULT_PASSWORD));
        sysUserMapper.updateById(sysUser);
        return true;
    }

    /**
     * 启用禁用
     * @param dto
     * @return
     */
    @Override
    public boolean changeStatus(UserStatusDTO dto) {
        if(dto.getUserId()==0){
            throw new ZxException("超级管理员不能禁用");
        }
        SysUser sysUser=new SysUser();
        sysUser.setId(dto.getUserId());
        sysUser.setStatus(dto.getStatus());
        sysUser.setUpdateDate(LocalDateTime.now());
        sysUserMapper.updateById(sysUser);
        return true;
    }

    /**
     * 修改密码
     * @param id
     * @param old
     * @param news
     * @return
     */
    @Override
    public boolean change(String id, String old, String news) {
       SysUser sysUser= sysUserMapper.selectById(id);
        if(sysUser==null){
            throw new ZxException("账号异常");
        }
        if(!passwordEncoder.matches(old,sysUser.getPassword())){
            throw new ZxException("原始密码不正确");
        }

        if(old.equals(news)){
            throw new ZxException("新密码与旧密码不能相同");
        }
        SysUser one =new SysUser();
        one.setId(Long.valueOf(id));
        one.setPassword(passwordEncoder.encode(news));
        sysUserMapper.updateById(one);
        return true;
    }
    /**
     * 监控信息
     * @return
     */
    @Override
    public Server monitorIfo(){
        Server server=new Server();
        server.getMonitorInfo();
        return server;
    }
}
