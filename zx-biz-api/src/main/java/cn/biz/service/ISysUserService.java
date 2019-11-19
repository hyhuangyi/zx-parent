package cn.biz.service;
import cn.biz.dto.AddUserDTO;
import cn.biz.dto.UserListDTO;
import cn.biz.po.SysUser;
import cn.biz.vo.UserListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户相关
 **/
public interface ISysUserService {
    //新增
    boolean addUser(AddUserDTO dto);
    //编辑用户
    boolean updateUser(AddUserDTO dto);
    //user列表
    IPage<SysUser> getUserList(UserListDTO dto);
    //获取用户信息
    UserListVO getUserInfo(String id);
}
