package cn.webapp.controller.user;

import cn.biz.dto.SaveRoleDTO;
import cn.biz.dto.RoleListDTO;
import cn.biz.po.AuthRole;
import cn.biz.service.IAuthRoleService;
import cn.biz.vo.MenuVO;
import cn.biz.vo.RoleVO;
import cn.common.consts.LogModuleConst;
import cn.webapp.aop.annotation.OperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "角色权限相关api")
@RestController
@Validated
@RequestMapping("/role")
public class AuthRoleController {
    @Autowired
    private IAuthRoleService authRoleService;
    @ApiOperation("角色列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('role:list')")
    @OperateLog(operation = "查询角色列表",moduleName = LogModuleConst.ROLE_MODULE)
    public IPage<AuthRole> roleList(@ModelAttribute @Valid RoleListDTO dto){
        return authRoleService.getRoleList(dto);
    }

    @ApiOperation("角色菜单,新增|编辑角色的时候需要获取")
    @GetMapping("/menus")
    @PreAuthorize("hasAnyAuthority('role:save')")
    @OperateLog(operation = "全部菜单",moduleName = LogModuleConst.ROLE_MODULE)
    public List<MenuVO> menuList(){
        return authRoleService.getAllMenus();
    }

    @ApiOperation("添加||编辑角色 id为空添加，id不为空编辑")
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('role:save')")
    @OperateLog(operation ="#{#dto.id==null?'新增':'编辑'}角色" ,moduleName = LogModuleConst.ROLE_MODULE)
    public boolean saveRole(@ModelAttribute @Valid SaveRoleDTO dto){
        return authRoleService.saveRole(dto);
    }

    @ApiOperation("查询角色")
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('role:save')")
    @OperateLog(operation = "查询角色信息",moduleName = LogModuleConst.ROLE_MODULE)
    public RoleVO selectRole(@ApiParam("主键id") @RequestParam @NotEmpty(message = "id不能为空") String id){
        return authRoleService.selectRole(id);
    }

    @ApiOperation("启用||禁用角色")
    @PostMapping("/state")
    @PreAuthorize("hasAuthority('role:save')")
    @OperateLog(operation ="#{#type==0?'启用':'禁用'}角色" ,moduleName = LogModuleConst.ROLE_MODULE)
    public boolean updateRole(@ApiParam("主键id") @RequestParam  @NotEmpty(message = "id不能为空") String id,
                           @ApiParam("type") @RequestParam  @NotNull(message = "0启用 1禁用") int type){
        return authRoleService.updateRole(id,type);
    }

    @ApiOperation("删除角色")
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('role:del')")
    @OperateLog(operation = "删除角色",moduleName = LogModuleConst.ROLE_MODULE)
    public boolean delRole(@ApiParam("主键id") @RequestParam  @NotEmpty(message = "id不能为空") String id ){
        return authRoleService.delRole(id);
    }
}
