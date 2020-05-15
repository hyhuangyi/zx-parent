package cn.webapp.controller.biz;

import cn.biz.service.ISysTreeDictService;
import cn.biz.vo.DictVO;
import cn.biz.vo.FundVO;
import cn.common.consts.LogModuleConst;
import cn.common.util.http.HttpRequestUtil;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.TimeCount;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Api(tags = "基金相关接口")
@RestController
@Slf4j
public class FundController {
    @Autowired
    private ISysTreeDictService sysTreeDictService;

    @TimeCount
    @ApiOperation("基金列表")
    @GetMapping("/fund/list")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "查询基金列表",moduleName = LogModuleConst.FUND_MODULE)
    public List<FundVO> fundList(){
        long start=System.currentTimeMillis();
        Map<String,List<DictVO>> map=sysTreeDictService.listDicts("fund");
        List<DictVO> list=map.get("fund");
        String  baseUrl="http://fundgz.1234567.com.cn/js/";
        List<FundVO> res=new ArrayList<>();
        for(DictVO vo:list){
           try {
               String result=  HttpRequestUtil.get(baseUrl+vo.getDdText()+".js",null,null);//获取结果
               String json=result.substring(result.indexOf("{"),result.lastIndexOf("}")+1);//获取json
               res.add(JSON.parseObject(json,FundVO.class));//转为实体
           }catch (Exception e){
               log.error("查询异常",e);
           }
        }
        Collections.sort(res);//倒序
        long end=System.currentTimeMillis();
        log.info("执行时间="+(end-start)+"毫秒");
        return res;
    }
}
