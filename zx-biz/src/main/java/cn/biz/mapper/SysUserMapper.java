package cn.biz.mapper;

import cn.biz.po.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户 Mapper 接口
 * @author huangy
 * @since 2019-11-18
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<SysUser> getUserByName(String userName);

    List<SysUser> getUserByPhone(String phone);

    List<SysUser> getUserByEmail(String email);
}
