package cn.biz.service;

import cn.biz.dto.RoleListDTO;
import cn.biz.mapper.AuthRoleMapper;
import cn.biz.po.AuthRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthRoleServiceImpl implements IAuthRoleService {
    @Autowired
    private AuthRoleMapper roleMapper;
    //角色列表
    @Override
    public IPage<AuthRole> getRoleList(RoleListDTO dto) {
        Page<AuthRole> page=new Page(dto.getCurrent(),dto.getSize());
        page.setRecords(roleMapper.getRoleList(dto,page));
        return page;
    }
}
