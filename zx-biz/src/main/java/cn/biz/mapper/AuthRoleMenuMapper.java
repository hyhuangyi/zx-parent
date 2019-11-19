package cn.biz.mapper;

import cn.biz.po.AuthRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色菜单表 Mapper 接口
 * @author huangy
 * @since 2019-11-18
 */
@Repository
public interface AuthRoleMenuMapper extends BaseMapper<AuthRoleMenu> {

    void insertBatchRoleMenu(List<AuthRoleMenu> list);

    @Select("select menu_id from  auth_role_menu where role_id=#{roleId}")
    List<String> selectMenusByRoleId(Long roleId);
}

