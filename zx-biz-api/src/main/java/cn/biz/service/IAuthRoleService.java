package cn.biz.service;

import cn.biz.dto.RoleListDTO;
import cn.biz.po.AuthRole;
import cn.biz.vo.MenuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

public interface IAuthRoleService {
    //角色列表
    IPage<AuthRole> getRoleList(RoleListDTO dto);
    //所有菜单列表
    List<MenuVO> getAllMenus();
}
