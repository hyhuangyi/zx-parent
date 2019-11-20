package cn.biz.service;
import cn.biz.dto.SaveUserDTO;
import cn.biz.dto.UserListDTO;
import cn.biz.dto.UserStatusDTO;
import cn.biz.po.SysUser;
import cn.biz.vo.UserListVO;
import cn.biz.vo.ZxToken;
import com.baomidou.mybatisplus.core.metadata.IPage;


/**
 * 用户相关
 **/
public interface ISysUserService {
    ZxToken login(SysUser user);
    //新增||编辑
    boolean saveUser(SaveUserDTO dto);
    //user列表
    IPage<SysUser> getUserList(UserListDTO dto);
    //获取用户信息
    UserListVO getUserInfo(String id);
    //删除用户
    boolean delUser(String id);
    //重置密码
    boolean reset(String id);
    //启用禁用
    boolean changeStatus(UserStatusDTO dto);
    //修改密码
    boolean change(String id,String old,String news);
}
