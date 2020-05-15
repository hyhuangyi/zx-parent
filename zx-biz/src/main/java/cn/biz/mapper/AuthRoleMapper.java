package cn.biz.mapper;

import cn.biz.dto.RoleListDTO;
import cn.biz.po.AuthRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 角色表 Mapper 接口
 * @author huangy
 * @since 2019-11-18
 */
@Repository
public interface AuthRoleMapper extends BaseMapper<AuthRole> {
    List<AuthRole> getRoleList(@Param("dto")RoleListDTO dto, Page page);
    @Select("select *from auth_role where 1=1")
    List<AuthRole> roleSelect();
}
