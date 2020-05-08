package cn.webapp.controller.biz;

import cn.biz.vo.FundVO;
import cn.common.util.http.HttpRequestUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Api(tags = "基金相关接口")
@RestController
@Slf4j
public class FundController {
    @GetMapping("/comm/fund/list")
    public List<FundVO> fundList(){
        long start=System.currentTimeMillis();
        String[] foundCode={"001178","001593","320007","001618","519181","162703","008851","008400","005224","519674","001371","160224","501058","501015"};
        String  baseUrl="http://fundgz.1234567.com.cn/js/";
        List<FundVO> res=new ArrayList<>();
        for(String code:foundCode){
           try {
               String result=  HttpRequestUtil.get(baseUrl+code+".js",null,null);//获取结果
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
