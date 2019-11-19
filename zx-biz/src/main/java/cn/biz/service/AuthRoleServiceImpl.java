package cn.biz.service;

import cn.biz.dto.AddRoleDTO;
import cn.biz.dto.RoleListDTO;
import cn.biz.mapper.AuthMenuMapper;
import cn.biz.mapper.AuthRoleMapper;
import cn.biz.mapper.AuthRoleMenuMapper;
import cn.biz.po.AuthRole;
import cn.biz.po.AuthRoleMenu;
import cn.biz.vo.MenuVO;
import cn.common.exception.ZxException;
import cn.common.util.comm.PinYinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AuthRoleServiceImpl implements IAuthRoleService {
    @Autowired
    private AuthRoleMapper roleMapper;
    @Autowired
    private AuthMenuMapper menuMapper;
    @Autowired
    private AuthRoleMenuMapper roleMenuMapper;

    /**
     *  角色列表
     * @param dto
     * @return
     */
    @Override
    public IPage<AuthRole> getRoleList(RoleListDTO dto) {
        Page<AuthRole> page=new Page(dto.getCurrent(),dto.getSize());
        page.setRecords(roleMapper.getRoleList(dto,page));
        return page;
    }

    /**
     * 所有菜单列表
     * @return
     */
    @Override
    public List<MenuVO> getAllMenus() {
        //获取所有菜单列
        List<MenuVO> list=menuMapper.getAllMenus();
        List<MenuVO> menus=new ArrayList<>();
        //找出父菜单 递归处理子集
        for(MenuVO m:list){
            if(0L==m.getParentId()){
                getChild(m,list);
                menus.add(m);
            }
        }
        return menus;
    }
    /*对子集处理*/
    private void getChild(MenuVO vo, List<MenuVO> list) {
        List<MenuVO> children = vo.getSubMenu();
        for (MenuVO dto : list) {//获取子集
            if (dto.getParentId().longValue()==vo.getId().longValue()) {
                children.add(dto);
            }
        }
        if (!CollectionUtils.isEmpty(children)) {
            for (MenuVO child : children) {
                getChild(child, list);
            }
        }
    }

    /**
     * 新增||编辑角色
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(AddRoleDTO dto) {
        String str=dto.getMenuList();
        if(StringUtils.isEmpty(str)){
            throw new ZxException("菜单权限集合不能为空");
        }
        List<AuthRoleMenu> addList= Lists.newArrayList();
        AuthRole authRole=new AuthRole();
        authRole.setRoleName(dto.getRoleName());
        authRole.setRoleCode("ROLE_"+PinYinUtil.getPinYinInitialLetter(dto.getRoleName()).toUpperCase());
        authRole.setUpdateTime(LocalDateTime.now());
        if(StringUtils.isBlank(dto.getId())){
           Integer count= roleMapper.selectCount(new QueryWrapper<AuthRole>().eq("role_name",dto.getRoleName()).eq("is_del",0));
           if(count!=0){
               throw new ZxException("角色已存在！");
           }
           authRole.setCreateTime(LocalDateTime.now());
           roleMapper.insert(authRole);
        }else{
            AuthRole one= roleMapper.selectById(dto.getId());
            if(one==null){
                throw new ZxException("该角色不存在");
            }
            authRole.setId(one.getId());
            if(!one.getRoleName().equals(dto.getRoleName())){
                Integer count= roleMapper.selectCount(new QueryWrapper<AuthRole>().eq("role_name",dto.getRoleName()).eq("is_del",0));
                if(count!=0){
                    throw new ZxException("角色已存在！");
                }
                roleMapper.updateById(authRole);
            }
            roleMenuMapper.delete(new QueryWrapper<AuthRoleMenu>().eq("role_id",dto.getId()));
        }
        //角色权限操作
        Long roleId=authRole.getId();
        String[] ids= str.split(",");
        for(String s:ids){
            addList.add(new AuthRoleMenu(roleId,Long.valueOf(s)));
        }
        roleMenuMapper.insertBatchRoleMenu(addList);
        return true;
    }
}
