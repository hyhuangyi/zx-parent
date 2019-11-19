package cn.webapp.controller.user;

import cn.biz.dto.AddUserDTO;
import cn.biz.dto.UserListDTO;
import cn.biz.group.ZxSecond;
import cn.biz.po.SysUser;
import cn.biz.service.ISysUserService;
import cn.biz.vo.UserListVO;
import cn.webapp.aop.annotation.ValidatedRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(description = "用户相关")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ISysUserService userService;

    @ApiOperation("用户列表")
    @GetMapping("/list")
    public IPage<SysUser> roleList(@ModelAttribute @Valid UserListDTO dto){
        return userService.getUserList(dto);
    }

    @ApiOperation("用户详情")
    @GetMapping("/info/{id}")
    public UserListVO info(@PathVariable("id") String id){
      return  userService.getUserInfo(id);
    }

    @ApiOperation("新增用户")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ValidatedRequest
    public Boolean add(@Valid@ModelAttribute AddUserDTO user, BindingResult result){
        return userService.addUser(user);
    }

    @ApiOperation("编辑用户")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ValidatedRequest
    public Boolean update(@Validated({ZxSecond.class})@ModelAttribute AddUserDTO user, BindingResult result){
        return userService.updateUser(user);
    }
}
