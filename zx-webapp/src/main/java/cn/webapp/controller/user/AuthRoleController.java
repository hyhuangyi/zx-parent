package cn.webapp.controller.user;

import cn.biz.dto.AddRoleDTO;
import cn.biz.dto.RoleListDTO;
import cn.biz.po.AuthRole;
import cn.biz.service.IAuthRoleService;
import cn.biz.vo.MenuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation("角色菜单,新增|编辑角色的时候需要获取")
    @GetMapping("/role/menus")
    public List<MenuVO> menuList(){
        return authRoleService.getAllMenus();
    }

    @ApiOperation("添加|编辑角色 id为空添加，id不为空编辑")
    @PostMapping("/role/save")
    public boolean saveRole(@ModelAttribute @Valid AddRoleDTO dto){
        return authRoleService.saveRole(dto);
    }

}
