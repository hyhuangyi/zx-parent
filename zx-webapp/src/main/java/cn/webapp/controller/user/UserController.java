package cn.webapp.controller.user;

import cn.biz.dto.SaveUserDTO;
import cn.biz.dto.UserListDTO;
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

    @ApiOperation("新增||编辑 用户 id为空新增，id不为空编辑")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ValidatedRequest
    public Boolean add(@Valid@ModelAttribute SaveUserDTO user, BindingResult result){
        return userService.saveUser(user);
    }


}
