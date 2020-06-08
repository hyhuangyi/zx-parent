package cn.biz.service;

import cn.biz.dto.FundDTO;
import cn.biz.dto.TableListDTO;
import cn.biz.dto.WeiboDTO;
import cn.biz.mapper.FundMapper;
import cn.biz.mapper.SysOperateLogMapper;
import cn.biz.mapper.WeiboMapper;
import cn.biz.po.Fund;
import cn.biz.po.Weibo;
import cn.biz.vo.DictVO;
import cn.biz.vo.FundVO;
import cn.biz.vo.TableListVO;
import cn.common.exception.ZxException;
import cn.common.util.algorithm.ListUtil;
import cn.common.util.comm.RegexUtils;
import cn.common.util.file.AntZipUtil;
import cn.common.util.file.FileUtil;
import cn.common.util.http.HttpRequestUtil;
import cn.common.util.http.RestTemplateUtil;
import cn.common.util.math.BigDecimalUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class SysServiceImpl implements ISysService {

    @Autowired
    private SysOperateLogMapper sysOperateLogMapper;
    @Autowired
    private WeiboMapper weiboMapper;
    @Autowired
    private ISysTreeDictService sysTreeDictService;
    @Autowired
    private FundMapper fundMapper;
    @Value("${spring.datasource.druid.url}")
    private  String url;
    @Value("${spring.datasource.druid.username}")
    private String name;
    @Value("${spring.datasource.druid.password}")
    private String password;
    @Value("${spring.datasource.druid.driver-class-name}")
    private String driver;
    private String parent="com.zx";

    /**基金详情**/
    public static final String FUND_DETAIL = "http://fund.eastmoney.com/pingzhongdata/";
    /**所有基金**/
    public static final String FUND_ALL = "http://fund.eastmoney.com/js/fundcode_search.js";
    /**基金估值**/
    public static final String FUND_GZ = "http://fundgz.1234567.com.cn/js/";
    /**输出基金费率0的结果地址**/
    public static final String ZERO_FUND_PATH="/home/zeroFund.txt";
    /**输出基金费率0的实时排名结果地址**/
    public static final String ZERO_FUND_RANK_PATH="/home/zeroFundRank.txt";

    @Override
    public void generateCode(String schema ,String[] arr, HttpServletResponse response)throws Exception {
        if (arr==null||arr.length==0){
            throw new ZxException("请至少选择一个");
        }
        String replace= url.substring(url.indexOf("6/")+2, url.indexOf("?"));//截取当前schema
        String realUrl=url.replace(replace,schema);//替换schmea
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath="/home/download/"; //输出到指定目录
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zx");
        gc.setOpen(false);//是否打开输出目录
        gc.setIdType(IdType.AUTO);//主键策略
        gc.setSwagger2(true); //实体属性 Swagger2注解
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(realUrl);
        //dsc.setSchemaName("public");
        dsc.setDriverName(driver);
        dsc.setUsername(name);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                if(pc.getModuleName()!=null){
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }else {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/src/main/resources/mapper/"
                            + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //公共父类
        //strategy.setSuperEntityClass("com.zx.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("com.zx.common.BaseController");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setInclude(arr);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
        try {
//            response.setContentType("application/octet-stream;charset=UTF-8;");
//            response.addHeader("Content-Disposition", "attachment;");
            OutputStream outputStream = response.getOutputStream();
            AntZipUtil.compress(outputStream,projectPath+ File.separator+"src");
        }finally {
            FileUtil.deleteDirectory(projectPath);
        }
    }

    @Override
    public IPage<TableListVO> getTableList(TableListDTO dto) {
        Page<TableListVO> page=new Page<>(dto.getCurrent(),dto.getSize());
        List<TableListVO> list=sysOperateLogMapper.getTableList(page,dto);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<String> getSchemas() {
        return sysOperateLogMapper.getSchemas();
    }

    @Override
    public IPage<Weibo> getWeiboSearchList(WeiboDTO dto) {
        Page<Weibo> page=new Page<>(dto.getCurrent(),dto.getSize());
        List<Weibo> list=weiboMapper.getWeiboList(page,dto);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<FundVO> fundList() {
        long start = System.currentTimeMillis();
        Map<String, List<DictVO>> map = sysTreeDictService.listDicts("fund");
        List<DictVO> list = map.get("fund");
        List<FundVO> res = new ArrayList<>();
        for (DictVO vo : list) {
            try {
                String result = HttpRequestUtil.get(FUND_GZ + vo.getDdText() + ".js", null, null);//获取结果
                String json = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);//获取json
                FundVO fundVO = JSON.parseObject(json, FundVO.class);
                fundVO.setId(vo.getDdId());
                fundVO.setRemark(Double.valueOf(vo.getRemark()));
                fundVO.setLy(BigDecimalUtils.mulFool(2, vo.getRemark(), fundVO.getGszzl() / 100));
                res.add(fundVO);//转为实体
            } catch (Exception e) {
                log.error("查询异常", e);
            }
        }
        Collections.sort(res);//倒序
        long end = System.currentTimeMillis();
        log.info("执行时间=" + (end - start) + "毫秒");
        return res;
    }

    @Override
    public Boolean updateAllFund() {
        List<Fund> list=new ArrayList<>();
        long start = System.currentTimeMillis();
        String result = HttpRequestUtil.get(FUND_ALL, null, null);//获取结果
        String json=result.substring(result.indexOf("["),result.lastIndexOf(";"));//json  基金列表
        List<List> l=  JSON.parseArray(json,List.class);
        l.forEach(data->{
            Fund fund=new Fund();
            fund.setCode(data.get(0).toString());
            fund.setShortPy(data.get(1).toString());
            fund.setName(data.get(2).toString());
            fund.setType(data.get(3).toString());
            fund.setFullPy(data.get(4).toString());
            list.add(fund);
        });
        List<List<Fund>> tempList= ListUtil.AssignBatchList(list, 5000);
        for(List<Fund> res:tempList){
            fundMapper.batchInsertOrUpdate(res);
        }
        long end = System.currentTimeMillis();
        log.info("执行时间=" + (end - start) + "毫秒");
        return true;
    }

    @Override
    public IPage<Fund> getAllFund(FundDTO dto) {
        Page<Fund> page=new Page<>(dto.getCurrent(),dto.getSize());
        List<Fund> list=fundMapper.getAllFund(page,dto);
        list.forEach(l->{
            try {
                String result = HttpRequestUtil.get(FUND_DETAIL + l.getCode() + ".js", null, null);//获取结果
                String rate = result.substring(result.indexOf("fund_Rate") + 11, result.indexOf("fund_Rate") + 15);
                if(RegexUtils.checkDecimals(rate)){
                    l.setBuyRate(rate);
                }else {
                    l.setBuyRate("/");
                }
            } catch (Exception e) {
                l.setBuyRate("/");
                e.printStackTrace();
            }
        });
        page.setRecords(list);
        return page;
    }

    @Override
    public List<String> getFundType() {
        return fundMapper.getFundType();
    }

    @Override
    public List<Fund> getZeroRateFund(int num) throws Exception{
        if(num<=0){
            num=50;
        }
        log.info("开始执行");
        long start=System.currentTimeMillis();
        //多线程插入会有并发问题 需要加锁
        List<Fund> res=Collections.synchronizedList(new ArrayList<>());
        List<Fund> funds=fundMapper.getFundForZero();
        //大概5000多条，切分成100份，多线程去执行
        List<List<Fund>> all= ListUtil.averageAssign(funds,num);
        CountDownLatch countDownLatch=new CountDownLatch(num);
        for(List<Fund> fl:all){
            new Thread(()->{
                handleZero(Collections.synchronizedList(fl),res);
                countDownLatch.countDown();
            }).start();
        }
        //主线程等所有线程完成工作才继续执行后面的代码，当计算器减到0阻塞结束
        countDownLatch.await();
        long end=System.currentTimeMillis();
        FileUtil.writeFile(ZERO_FUND_PATH,JSON.toJSONString(res));
        log.info("所有线程执行结束，耗时："+(end-start)/(60*1000)+"分钟。一共"+res.size()+"条。");
        return res;
    }

    /**处理多线程任务*/
    public  void  handleZero(List<Fund> fl,List<Fund> res) {
        for (Fund f : fl) {
            try {
                String result = HttpRequestUtil.get(FUND_DETAIL + f.getCode() + ".js", null, null);//获取结果
                //log.info(result + "---" + Thread.currentThread().getName() + "========" + f.getCode());
                String rate = result.substring(result.indexOf("fund_Rate") + 11, result.indexOf("fund_Rate") + 15);
                log.info(rate);
                if ("0.00".equals(rate)) {
                    res.add(f);
                }
            } catch (Exception e) {
                log.error(Thread.currentThread().getName() + ":" + f.getCode(), e);
            }
        }
    }

    /**处理多线程任务*/
    public  void  handleZeroRank(List<Fund> fl,List<FundVO> res) {
        for (Fund f : fl) {
            try {
                String result = HttpRequestUtil.get(FUND_GZ + f.getCode() + ".js", null, null);//获取结果
                String json = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);//获取json
                FundVO fundVO = JSON.parseObject(json, FundVO.class);
                res.add(fundVO);//转为实体
            } catch (Exception e) {
                log.error(Thread.currentThread().getName()+"----"+f.getCode()+"异常");
            }
        }
    }
    @Override
    public List<FundVO> getZeroRateFundRank(int num)throws Exception{
        if(num<=0){
            num=50;
        }
        long start=System.currentTimeMillis();
        String data= FileUtil.readFile(ZERO_FUND_PATH);
        List<Fund> funds= JSONArray.parseArray(data,Fund.class);
        List<FundVO> res=Collections.synchronizedList(new ArrayList<>());
        //切分
        List<List<Fund>> all= ListUtil.averageAssign(funds,num);
        CountDownLatch countDownLatch=new CountDownLatch(num);
        for(List<Fund> fl:all){
            new Thread(()->{
                handleZeroRank(Collections.synchronizedList(fl),res);
                countDownLatch.countDown();
            }).start();
        }
        //主线程等所有线程完成工作才继续执行后面的代码，当计算器减到0阻塞结束
        countDownLatch.await();
        long end=System.currentTimeMillis();
        Collections.sort(res);//倒序
        FileUtil.writeFile(ZERO_FUND_RANK_PATH,JSON.toJSONString(res));
        log.info("所有线程执行结束，耗时："+(end-start)+"毫秒。一共"+res.size()+"条。");
        return res;
    }

}
