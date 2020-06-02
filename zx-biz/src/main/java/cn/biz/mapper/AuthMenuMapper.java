package cn.biz.mapper;

import cn.biz.po.AuthMenu;
import cn.biz.vo.MenuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 菜单表 Mapper 接口
 * @author huangy
 * @since 2019-11-18
 */
@Repository
public interface AuthMenuMapper extends BaseMapper<AuthMenu> {
    List<MenuVO> getAllMenus();
    List<MenuVO> getUserMenus(@Param("roleIds") String roleIdS);
    Set<String> getPermissions(@Param("roleIds") String roleIdS);
}
