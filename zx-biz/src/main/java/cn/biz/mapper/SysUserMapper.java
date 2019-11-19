package cn.biz.mapper;

import cn.biz.dto.UserListDTO;
import cn.biz.po.SysUser;
import cn.biz.vo.UserListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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
    List<SysUser> getUserList(@Param("dto") UserListDTO dto, Page page);
    UserListVO getUserInfo(String id);
}
