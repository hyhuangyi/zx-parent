package cn.webapp.controller.user;

import cn.biz.dto.SaveUserDTO;
import cn.biz.dto.UserListDTO;
import cn.biz.dto.UserStatusDTO;
import cn.biz.po.AuthRole;
import cn.biz.service.ISysUserService;
import cn.biz.vo.UserListVO;
import cn.common.consts.LogModuleConst;
import cn.common.exception.ZxException;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.TimeCount;
import cn.webapp.aop.annotation.ValidatedRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "用户相关")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ISysUserService userService;

    @ApiOperation("用户列表")
    @GetMapping("/list")
    @TimeCount
    @PreAuthorize("hasAuthority('user:list')")
    @OperateLog(operation = "查询用户列表",moduleName = LogModuleConst.USER_MODULE)
    public IPage<UserListVO> userList(@ModelAttribute @Valid UserListDTO dto){
        return userService.getUserList(dto);
    }

    @ApiOperation("角色下拉选")
    @GetMapping("/role/select")
    public List<AuthRole> roleSelect(){
        return userService.roleSelect();
    }

    @ApiOperation("用户详情")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('user:save')")
    @OperateLog(operation = "用户详情",moduleName = LogModuleConst.USER_MODULE)
    public UserListVO info(@PathVariable("id") String id){
      return  userService.getUserInfo(id);
    }

    @ApiOperation("新增||编辑 用户 id为空新增，id不为空编辑")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ValidatedRequest
    @PreAuthorize("hasAuthority('user:save')")
    @OperateLog(operation ="#{#user.id==null?'新增':'编辑'}用户" ,moduleName = LogModuleConst.USER_MODULE)
    public Boolean add(@Valid@ModelAttribute SaveUserDTO user, BindingResult result){
        return userService.saveUser(user);
    }

    @ApiOperation("删除用户")
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('user:del')")
    @OperateLog(operation ="删除用户" ,moduleName = LogModuleConst.USER_MODULE)
    public boolean delUser(@ApiParam("主键id") @RequestParam  @NotEmpty(message = "id不能为空") String id){
        return userService.delUser(id);
    }

    @ApiOperation("重置密码")
    @PostMapping("/reset")
    @PreAuthorize("hasAuthority('user:save')")
    @OperateLog(operation ="重置密码" ,moduleName = LogModuleConst.USER_MODULE)
    public boolean reset(@ApiParam("主键id") @RequestParam  @NotEmpty(message = "id不能为空") String id){
        return userService.reset(id);
    }

    @ApiOperation("启用 0||禁用 1")
    @PostMapping("/state")
    @ValidatedRequest
    @PreAuthorize("hasAuthority('user:save')")
    @OperateLog(operation ="#{#dto.status==1?'禁用':'启用'}用户" ,moduleName = LogModuleConst.USER_MODULE)
    public boolean reset(@Validated@ModelAttribute UserStatusDTO dto,BindingResult result){
        return userService.changeStatus(dto);
    }
    @ApiOperation("修改密码")
    @PostMapping("/change/psw")
    @OperateLog(operation ="修改密码" ,moduleName = LogModuleConst.USER_MODULE)
    public boolean change(@ApiParam("主键id") @RequestParam  @NotEmpty(message = "id不能为空") String id,
                          @ApiParam("旧密码") @RequestParam  @NotEmpty(message = "旧密码不能为空") String old,
                          @ApiParam("新密码") @RequestParam  @NotEmpty(message = "新密码不能为空") String news,
                          @ApiParam("确认密码") @RequestParam  @NotEmpty(message = "确认密码不能为空") String confirm ){
        if(!news.equals(confirm)){
            throw new ZxException("2次输入密码不一致");
        }
        return userService.change(id,old,news);
    }
}
