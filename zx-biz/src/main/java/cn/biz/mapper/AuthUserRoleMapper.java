package cn.biz.mapper;

import cn.biz.po.AuthUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户和角色关系表 Mapper 接口
 * @author huangy
 * @since 2019-11-18
 */
@Repository
public interface AuthUserRoleMapper extends BaseMapper<AuthUserRole> {
    void insertBatchUserRole(List<AuthUserRole> list);
}
