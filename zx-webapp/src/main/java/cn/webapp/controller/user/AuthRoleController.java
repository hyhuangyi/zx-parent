package cn.webapp.controller.user;

import cn.biz.dto.RoleListDTO;
import cn.biz.po.AuthRole;
import cn.biz.service.IAuthRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Api(description = "角色权限相关api")
@RestController
@Validated
public class AuthRoleController {
    @Autowired
    private IAuthRoleService authRoleService;
    @ApiOperation("角色列表")
    @GetMapping("/role/list")
    public IPage<AuthRole> roleList(@ModelAttribute @Valid RoleListDTO dto){
        return authRoleService.getRoleList(dto);
    }
}
