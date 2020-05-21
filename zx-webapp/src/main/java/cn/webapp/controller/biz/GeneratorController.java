package cn.webapp.controller.biz;

import cn.biz.dto.TableListDTO;
import cn.biz.service.ISysService;
import cn.biz.vo.TableListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "代码生成相关接口")
@RestController
@Slf4j
public class GeneratorController {
    @Autowired
    private ISysService sysService;

    @ApiOperation("代码生成")
    @GetMapping("/comm/generate/code")
    public void generateCode(String arr, HttpServletResponse response)throws Exception{
        sysService.generateCode(arr.split(","),response);
    }

    @ApiOperation("/数据表列表")
    @GetMapping("/generate/list")
    public IPage<TableListVO> list(@ModelAttribute TableListDTO dto){
       return sysService.getTableList(dto);
    }
}
