package cn.biz.service;

import cn.biz.dto.RoleListDTO;
import cn.biz.mapper.AuthMenuMapper;
import cn.biz.mapper.AuthRoleMapper;
import cn.biz.po.AuthMenu;
import cn.biz.po.AuthRole;
import cn.biz.vo.MenuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthRoleServiceImpl implements IAuthRoleService {
    @Autowired
    private AuthRoleMapper roleMapper;
    @Autowired
    private AuthMenuMapper menuMapper;

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
        List<MenuVO> list=menuMapper.getAllMenus();
        List<MenuVO> menus=new ArrayList<>();
        for(MenuVO m:list){
            if("0".equals(m.getParentId())){
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
            if (dto.getParentId().equals(vo.getId())) {
                children.add(dto);
            }
        }
        if (!CollectionUtils.isEmpty(children)) {
            for (MenuVO child : children) {
                getChild(child, list);
            }
        }
    }
}
