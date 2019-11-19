package cn.biz.service;

import cn.biz.dto.RoleListDTO;
import cn.biz.po.AuthRole;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface IAuthRoleService {
    //角色列表
    IPage<AuthRole> getRoleList(RoleListDTO dto);
}
