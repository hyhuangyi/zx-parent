package cn.biz.service;

import cn.biz.dto.SaveRoleDTO;
import cn.biz.dto.RoleListDTO;
import cn.biz.po.AuthRole;
import cn.biz.vo.MenuVO;
import cn.biz.vo.RoleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

public interface IAuthRoleService {
    //角色列表
    IPage<AuthRole> getRoleList(RoleListDTO dto);
    //所有菜单列表
    List<MenuVO> getAllMenus();
    //新增||编辑角色
    boolean saveRole(SaveRoleDTO dto);
    //查询角色信息
    RoleVO selectRole(String roleId);
    //启用禁用角色
    boolean updateRole(String roleId,int type);
    //删除角色
    boolean delRole(String roleId);
    //获取用户菜单
    List<MenuVO> getUserMenus(String roleIds);
}
