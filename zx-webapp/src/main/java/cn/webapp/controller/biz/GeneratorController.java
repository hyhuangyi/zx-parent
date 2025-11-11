package cn.webapp.controller.biz;

import cn.biz.dto.TableListDTO;
import cn.biz.service.ISysService;
import cn.biz.vo.TableListVO;
import cn.common.consts.LogModuleConst;
import cn.webapp.aop.annotation.OperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "代码生成相关接口")
@RestController
@Slf4j
@RequiredArgsConstructor
public class GeneratorController {
    private final ISysService sysService;

    @ApiOperation("代码生成")
    @GetMapping("/comm/generate/code")
    public void generateCode(@ApiParam ("schema") @RequestParam String schema , @ApiParam("主键id") @RequestParam  String arr, HttpServletResponse response)throws Exception{
        sysService.generateCode(schema,arr.split(","),response);
    }

    @ApiOperation("获取schema")
    @GetMapping("/schema")
    @PreAuthorize("hasAuthority('generator:list')")
    @OperateLog(operation = "获取schema",moduleName = LogModuleConst.BIZ_MODULE)
    public List<String> schemas(){
        return sysService.getSchemas();
    }

    @ApiOperation("/数据表列表")
    @GetMapping("/generate/list")
    @PreAuthorize("hasAuthority('generator:list')")
    @OperateLog(operation = "代码生成-table list",moduleName = LogModuleConst.BIZ_MODULE)
    public IPage<TableListVO> list(@ModelAttribute TableListDTO dto){
       return sysService.getTableList(dto);
    }
}
