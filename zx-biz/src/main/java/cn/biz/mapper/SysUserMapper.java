package cn.biz.mapper;

import cn.biz.dto.UserListDTO;
import cn.biz.po.SysUser;
import cn.biz.vo.UserListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    @Select("SELECT count(1) FROM `sys_user` where phone=#{phone} or username=#{username}")
    Integer selectUserCount(@Param("username") String username,@Param("phone") String phone);
    List<UserListVO> getUserList(@Param("dto") UserListDTO dto, Page page);
    UserListVO getUserInfo(String id);
}
