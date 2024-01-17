package cn.biz.service;

import cn.biz.dto.*;
import cn.biz.mapper.*;
import cn.biz.po.Fund;
import cn.biz.po.FundOwn;
import cn.biz.po.Weibo;
import cn.biz.po.XqData;
import cn.biz.vo.*;
import cn.biz.webMagic.base.ProxyDownloader;
import cn.biz.webMagic.pipline.WeiboPipLine;
import cn.biz.webMagic.magic.WeiboTopics;
import cn.common.consts.RedisConst;
import cn.common.exception.ZxException;
import cn.biz.webMagic.pipline.CSDNPipeline;
import cn.biz.webMagic.magic.CSDN;
import cn.common.pojo.base.Token;
import cn.common.pojo.servlet.ServletContextHolder;
import cn.common.util.algorithm.ListUtil;
import cn.common.util.file.AntZipUtil;
import cn.common.util.file.FileUtil;
import cn.common.util.http.HttpRequestUtil;
import cn.common.util.ip.IpUtil;
import cn.common.util.math.BigDecimalUtils;
import cn.common.util.math.NumberUtil;
import cn.common.util.math.XMathUtil;
import cn.common.util.redis.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.beust.jcommander.internal.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class SysServiceImpl implements ISysService {

    @Autowired
    private SysOperateLogMapper sysOperateLogMapper;
    @Autowired
    private WeiboMapper weiboMapper;
    @Autowired
    private FundMapper fundMapper;
    @Autowired
    private FundOwnMapper fundOwnMapper;
    @Autowired
    private CSDNPipeline csdnPipeline;
    @Autowired
    private WeiboPipLine weiboPipLine;
    @Autowired
    private WeiboTopics weiboTopics;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private XqDataMapper xqDataMapper;

    @Value("${spring.datasource.druid.url}")
    private String url;
    @Value("${spring.datasource.druid.username}")
    private String name;
    @Value("${spring.datasource.druid.password}")
    private String password;
    @Value("${spring.datasource.druid.driver-class-name}")
    private String driver;
    private String parent = "com.zx";

    /**
     * 基金详情
     **/
    public static final String FUND_DETAIL = "http://fund.eastmoney.com/pingzhongdata/";
    /**
     * 所有基金
     **/
    public static final String FUND_ALL = "http://fund.eastmoney.com/js/fundcode_search.js";
    /**
     * 基金估值
     **/
    public static final String FUND_GZ = "http://fundgz.1234567.com.cn/js/";
    /**
     * 小熊api 大盘信息
     */
    public static final String XX_STOCK = "https://api.doctorxiong.club/v1/stock/board";

    /**
     * 果仁网api
     */
    public static final String GUO_REN = "https://guorn.com/language/query?query=";

    /**
     * 雪球api
     */
    public static final String XUE_QIU = "https://xueqiu.com/service/v5/stock/screener/quote/list?page=1&size=5000&order=asc&order_by=current_year_percent&market=CN&type=sh_sz";

    /**
     * 雪球个股实时数据
     */
    public static final String XUE_QIU_REALTIME_STOCK = "https://stock.xueqiu.com/v5/stock/realtime/quotec.json";
    /**
     * 输出基金费率0的结果地址
     **/
    public static final String ZERO_FUND_PATH = "/home/zx/fund/zeroFund.txt";
    /**
     * 输出基金费率0的实时排名结果地址
     **/
    public static final String ZERO_FUND_RANK_PATH = "/home/zx/fund/zeroFundRank.txt";
    /**
     * 横坐标
     */
    private static final List<String> HZB = new ArrayList<>();

    //初始化
    static {
        HZB.add("09:30");
        HZB.add("09:45");
        HZB.add("10:00");
        HZB.add("10:15");
        HZB.add("10:30");
        HZB.add("10:45");
        HZB.add("11:00");
        HZB.add("11:15");
        HZB.add("11:30");
        HZB.add("13:00");
        HZB.add("13:15");
        HZB.add("13:30");
        HZB.add("13:45");
        HZB.add("14:00");
        HZB.add("14:15");
        HZB.add("14:30");
        HZB.add("14:45");
        HZB.add("15:00");
    }

    /*生成代码*/
    @Override
    public void generateCode(String schema, String[] arr, HttpServletResponse response) throws Exception {
        if (arr == null || arr.length == 0) {
            throw new ZxException("请至少选择一个");
        }
        String replace = url.substring(url.indexOf("6/") + 2, url.indexOf("?"));//截取当前schema
        String realUrl = url.replace(replace, schema);//替换schmea
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = "/home/download/"; //输出到指定目录
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

                if (pc.getModuleName() != null) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/src/main/resources/mapper/" + pc.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                } else {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
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
            AntZipUtil.compress(outputStream, projectPath + File.separator + "src");
        } finally {
            FileUtil.deleteDirectory(projectPath);
        }
    }

    /*表列表*/
    @Override
    public IPage<TableListVO> getTableList(TableListDTO dto) {
        Page<TableListVO> page = new Page<>(dto.getCurrent(), dto.getSize());
        List<TableListVO> list = sysOperateLogMapper.getTableList(page, dto);
        page.setRecords(list);
        return page;
    }

    /*获取schemas*/
    @Override
    public List<String> getSchemas() {
        return sysOperateLogMapper.getSchemas();
    }

    /*微博查询*/
    @Override
    public IPage<Weibo> getWeiboSearchList(WeiboDTO dto) {
        Page<Weibo> page = new Page<>(dto.getCurrent(), dto.getSize());
        List<Weibo> list = weiboMapper.getWeiboList(page, dto);
        page.setRecords(list);
        return page;
    }

    /*处理csdn异步调用的方法*/
    @Override
    @Async("myTaskAsyncPool")
    public void handleCsdn(String page, Integer minute) {
        while (RedisUtil.hasKey(RedisConst.CSDN_KEY + page)) {
            try {//休眠60秒
                Thread.sleep(minute * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (RedisUtil.hasKey(RedisConst.CSDN_KEY + page)) {
                RedisUtil.incr(RedisConst.CSDN_KEY + page, 1);
            } else {
                break;
            }
            log.info("第" + page + "页准备执行第" + RedisUtil.get(RedisConst.CSDN_KEY + page) + "次，执行周期为" + minute + "分钟/次");
            Spider.create(new CSDN()).addUrl("https://blog.csdn.net/qq_37209293/article/list/" + page).addPipeline(csdnPipeline).thread(1).runAsync();
        }
    }

    /*爬取微博*/
    @Override
    @Async("myTaskAsyncPool")
    public void handleWeibo(String key) {
        String baseUrl = "https://s.weibo.com/weibo?q=%23" + key + "%23";
        Spider.create(weiboTopics).addUrl(baseUrl).addPipeline(weiboPipLine).setDownloader(ProxyDownloader.newIpDownloader()).thread(1).runAsync();
    }

    /*清空微博*/
    @Override
    public Boolean cleanWeibo() {
        weiboMapper.cleanWeiboData();
        return true;
    }

    /*我的基金列表*/
    @Override
    public List<FundVO> fundList(long userId) {
        long start = System.currentTimeMillis();
        List<FundOwn> list = fundOwnMapper.selectList(new QueryWrapper<FundOwn>().eq("user_id", userId));
        List<FundVO> res = new ArrayList<>();
        for (FundOwn vo : list) {
            try {
                String result = HttpRequestUtil.get(FUND_GZ + vo.getCode() + ".js", null, null);//获取结果
                String json = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);//获取json
                FundVO fundVO = JSON.parseObject(json, FundVO.class);
                fundVO.setId(vo.getId());
                fundVO.setRemark(vo.getRemark());//备注
                fundVO.setHoldNum(Double.parseDouble(vo.getHoldNum()));//持有份额
                fundVO.setHoldMoney(BigDecimalUtils.mulFool(2, fundVO.getDwjz(), fundVO.getHoldNum()).doubleValue());//持有金额
                fundVO.setLy(BigDecimalUtils.mulFool(2, fundVO.getHoldMoney(), fundVO.getGszzl() / 100));//估值利润
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

    /*修改持有金额*/
    @Override
    public Boolean updateHoldMoney(Long id, String holdNum) {
        FundOwn fundOwn = new FundOwn();
        fundOwn.setId(id);
        fundOwn.setHoldNum(holdNum);
        fundOwnMapper.updateById(fundOwn);
        return true;
    }

    /*修改备注*/
    @Override
    public Boolean updateRemark(Long id, String remark) {
        FundOwn fundOwn = new FundOwn();
        fundOwn.setId(id);
        fundOwn.setRemark(remark);
        fundOwnMapper.updateById(fundOwn);
        return true;
    }

    /*更新基金列表*/
    @Override
    public Boolean updateAllFund() {
        List<Fund> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        String result = HttpRequestUtil.get(FUND_ALL, null, null);//获取结果
        String json = result.substring(result.indexOf("["), result.lastIndexOf(";"));//json  基金列表
        List<List> l = JSON.parseArray(json, List.class);
        l.forEach(data -> {
            Fund fund = new Fund();
            fund.setCode(data.get(0).toString());
            fund.setShortPy(data.get(1).toString());
            fund.setName(data.get(2).toString());
            fund.setType(data.get(3).toString());
            fund.setFullPy(data.get(4).toString());
            list.add(fund);
        });
        List<List<Fund>> tempList = ListUtil.AssignBatchList(list, 5000);
        for (List<Fund> res : tempList) {
            fundMapper.batchInsertOrUpdate(res);
        }
        long end = System.currentTimeMillis();
        log.info("执行时间=" + (end - start) + "毫秒");
        return true;
    }

    /*分页查询所有基金列表*/
    @Override
    public IPage<Fund> getAllFund(FundDTO dto) {
        Page<Fund> page = new Page<>(dto.getCurrent(), dto.getSize());
        List<Fund> list = fundMapper.getAllFund(page, dto);
        /**
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
         **/
        page.setRecords(list);
        return page;
    }

    /*基金下拉选*/
    @Override
    @Cacheable(value = "fund", key = "'select_all'", unless = "#result == null")
    public List<String> getFundSelect() {
        List<String> res = new ArrayList<>();
        List<Fund> list = fundMapper.getFundForZero();
        list.forEach(l -> {
            res.add(l.getCode() + "-" + l.getName());
        });
        return res;
    }

    /*新增基金*/
    @Override
    public boolean addFund(AddFundDTO dto) {
        String fund = dto.getFund();
        String[] arr = fund.split("-");
        if (arr.length != 2) {
            throw new ZxException("基金传值不符合要求");
        }
        Token token = ServletContextHolder.getToken();
        FundOwn fundOwn = new FundOwn();
        Integer count = fundOwnMapper.selectCount(new QueryWrapper<FundOwn>().eq("user_id", token.getUserId()).eq("code", arr[0]));
        if (count != 0) {
            throw new ZxException("当前基金已存在列表中，请不要重复加入！");
        }
        fundOwn.setRemark(dto.getRemark());
        fundOwn.setHoldNum(dto.getHoldNum());
        fundOwn.setCode(arr[0]);
        fundOwn.setName(arr[1]);
        fundOwn.setUserId(token.getUserId());
        fundOwn.setCreateTime(LocalDateTime.now());
        fundOwn.setUpdateTime(LocalDateTime.now());
        fundOwnMapper.insert(fundOwn);
        return true;
    }

    /*删除基金*/
    @Override
    public boolean delFund(Long id) {
        fundOwnMapper.deleteById(id);
        return true;
    }

    /*基金类型*/
    @Override
    public List<String> getFundType() {
        return fundMapper.getFundType();
    }

    /*获取费率为0的基金*/
    @Override
    public List<Fund> getZeroRateFund(int num) throws Exception {
        if (num <= 0) {
            num = 50;
        }
        log.info("开始执行");
        long start = System.currentTimeMillis();
        //多线程插入会有并发问题 需要加锁
        List<Fund> res = Collections.synchronizedList(new ArrayList<>());
        List<Fund> funds = fundMapper.getFundForZero();
        //大概5000多条，切分成100份，多线程去执行
        List<List<Fund>> all = ListUtil.averageAssign(funds, num);
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (List<Fund> fl : all) {
            new Thread(() -> {
                handleZero(Collections.synchronizedList(fl), res);
                countDownLatch.countDown();
            }).start();
        }
        //主线程等所有线程完成工作才继续执行后面的代码，当计算器减到0阻塞结束
        countDownLatch.await();
        long end = System.currentTimeMillis();
        FileUtil.writeFile(ZERO_FUND_PATH, JSON.toJSONString(res));
        log.info("所有线程执行结束，耗时：" + (end - start) / (60 * 1000) + "分钟。一共" + res.size() + "条。");
        return res;
    }

    /**
     * 处理多线程任务
     */
    public void handleZero(List<Fund> fl, List<Fund> res) {
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

    /**
     * 处理多线程任务
     */
    public void handleZeroRank(List<Fund> fl, List<FundVO> res) {
        for (Fund f : fl) {
            try {
                String result = HttpRequestUtil.get(FUND_GZ + f.getCode() + ".js", null, null);//获取结果
                String json = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);//获取json
                FundVO fundVO = JSON.parseObject(json, FundVO.class);
                res.add(fundVO);//转为实体
            } catch (Exception e) {
                log.error(Thread.currentThread().getName() + "----" + f.getCode() + "异常");
            }
        }
    }

    /*费率为0的基金当日排行*/
    @Override
    public List<FundVO> getZeroRateFundRank(int num) throws Exception {
        if (num <= 0) {
            num = 50;
        }
        long start = System.currentTimeMillis();
        String data = FileUtil.readFile(ZERO_FUND_PATH);
        List<Fund> funds = JSONArray.parseArray(data, Fund.class);
        List<FundVO> res = Collections.synchronizedList(new ArrayList<>());
        //切分
        List<List<Fund>> all = ListUtil.averageAssign(funds, num);
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (List<Fund> fl : all) {
            new Thread(() -> {
                handleZeroRank(Collections.synchronizedList(fl), res);
                countDownLatch.countDown();
            }).start();
        }
        //主线程等所有线程完成工作才继续执行后面的代码，当计算器减到0阻塞结束
        countDownLatch.await();
        long end = System.currentTimeMillis();
        Collections.sort(res);//倒序
        FileUtil.writeFile(ZERO_FUND_RANK_PATH, JSON.toJSONString(res));
        log.info("所有线程执行结束，耗时：" + (end - start) + "毫秒。一共" + res.size() + "条。");
        return res;
    }

    /**
     * 获取大盘信息
     *
     * @return
     */
    @Override
    public StockVO getStockInfo() {
        String res = HttpRequestUtil.get(XX_STOCK, null, null);
        JSONObject obj = JSONObject.parseObject(res);
        StockVO vo = new StockVO();
        double cje = 0.0;
        if ("200".equals(obj.get("code").toString())) {
            JSONArray array = JSONArray.parseArray(obj.get("data").toString());
            for (Object o : array) {
                JSONObject j = JSONObject.parseObject(o.toString());
                if (j.get("code").equals("sh000001") || j.get("code").equals("sz399001")) {
                    cje = NumberUtil.add(cje, j.get("turnover").toString());
                }
                if (j.get("code").equals("sh000001")) {//上证
                    vo.setShangz(Double.valueOf(j.get("changePercent").toString()));
                }
                if (j.get("code").equals("sz399001")) {//深证
                    vo.setShenz(Double.valueOf(j.get("changePercent").toString()));
                }
                if (j.get("code").equals("sz399006")) {//创业板
                    vo.setChuangy(Double.valueOf(j.get("changePercent").toString()));
                }
            }
            vo.setTurnOver(NumberUtil.div(cje, 10000));
        }
        return vo;
    }

    @Override
    public StockVO getStockInfoV2() {
        StockVO res = new StockVO();
        Map<String, String> head = new HashMap<>();
        head.put("Host", "xueqiu.com");
        head.put("Accept", "application/json");
        head.put("User-Agent", "Xueqiu iPhone 15.8");
        Map<String, String> req = new HashMap<>();
        req.put("symbol", "SH000001,SZ399001,SZ399006");
        String json = HttpRequestUtil.get(XUE_QIU_REALTIME_STOCK, req, head);
        JSONArray array = JSON.parseObject(json).getJSONArray("data");
        BigDecimal amount = BigDecimal.ZERO;
        for (Object l : array) {
            JSONObject jsonObject = JSON.parseObject(l.toString());
            String symbol = jsonObject.getString("symbol");
            double percent = jsonObject.getDoubleValue("percent");
            double current = jsonObject.getDoubleValue("current");
            if (symbol.contains("000001")) {
                res.setShangz(percent);
            } else if (symbol.contains("399001")) {
                res.setShenz(percent);
            } else {
                res.setChuangy(percent);
            }
            System.out.println(symbol + "=》current:" + current + " percent:" + percent);
            if (symbol.contains("000001") || symbol.contains("399001")) {
                amount = amount.add(jsonObject.getBigDecimal("amount"));
            }
        }
        System.out.println("amount=>" + XMathUtil.divide(amount, BigDecimal.valueOf(100000000), 2));
        res.setTurnOver(XMathUtil.divide(amount, BigDecimal.valueOf(100000000), 2).doubleValue());
        return res;
    }

    /**
     * 获取大盘chart数据
     *
     * @param type
     * @return
     */
    @Override
    public Map getStockChartData(String type) {
        List series = new ArrayList();
        //展示最近7天
        List<String> date = stockMapper.getDate();
        if (date.size() == 0) {
            return null;
        }
        //数据
        List<Map> data = stockMapper.getData();
        for (int i = 0; i < date.size(); i++) {
            Map m = new HashMap();
            List<Double> turnOver = new ArrayList<>();
            for (int j = 0; j < HZB.size(); j++) {
                boolean isSetNull = true;
                for (int k = 0; k < data.size(); k++) {
                    if (date.get(i).equals(data.get(k).get("date")) && HZB.get(j).equals(data.get(k).get("hour"))) {
                        turnOver.add((double) data.get(k).get("turnOver"));
                        isSetNull = false;
                        break;
                    }
                }
                if (isSetNull) {
                    turnOver.add(null);
                }
            }
            m.put("data", turnOver);
            m.put("name", date.get(i));
            m.put("type", type);
            series.add(m);
        }
        //结果集
        Map result = Maps.newHashMap();
        result.put("xAxis", HZB);
        result.put("series", series);
        result.put("legend", date);
        return result;
    }

    /**
     * 获取年内涨幅少的股票
     *
     * @param percent     当日涨幅
     * @param yearPercent 年内涨幅
     * @return
     */
    @Override
    public List<XueqiuVO.DataBean.ListBean> getXueqiuList(Double percent, Integer yearPercent) {
        Map<String, String> head = new HashMap();
        head.put("Host", "xueqiu.com");
        head.put("Accept", "application/json");
        head.put("User-Agent", "Xueqiu iPhone 11.8");
        String json = HttpRequestUtil.get(XUE_QIU, null, head);
        XueqiuVO vo = JSONObject.parseObject(json, XueqiuVO.class);
        List<XueqiuVO.DataBean.ListBean> list = vo.getData().getList();
        List<XueqiuVO.DataBean.ListBean> res = new ArrayList();
        for (XueqiuVO.DataBean.ListBean l : list) {
            if (percent == null || yearPercent == null) {
                res.add(l);
            } else if (l.getCurrent_year_percent() <= yearPercent && l.getPercent() >= percent) {
                res.add(l);
            }
        }
        return res;
    }

    /**
     * 获取雪球历史数据
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<XqData> getXqHistoryList(XqHistoryDTO dto) {
        Page<XqData> page = new Page<>(dto.getCurrent(), dto.getSize());
        List<XqData> list = xqDataMapper.getXqHistoryList(page, dto);
        page.setRecords(list);
        return page;
    }

    /**
     * 获取个股实时信息
     *
     * @param codes
     * @return
     */
    @Override
    public Map<String, String> getRealTimeInfo(String codes, int type) {
        log.info("访问ip={}", IpUtil.getIpAddress(ServletContextHolder.getRequest()));
        Map req = new HashMap<>();
        Map res = new HashMap<>();
        String[] codeArr = codes.split(",");
        List temp = new ArrayList();
        for (String str : codeArr) {
            if (str.startsWith("S")) {
                temp.add(str);
            } else if (str.startsWith("6")) {
                temp.add("SH" + str);
            } else {
                temp.add("SZ" + str);
            }
        }
        req.put("symbol", StringUtils.join(temp, ","));
        String result = HttpRequestUtil.get(XUE_QIU_REALTIME_STOCK, req, null);
        String arr = JSONObject.parseObject(result).get("data").toString();
        List<XueqiuVO.DataBean.ListBean> list = JSONArray.parseArray(arr, XueqiuVO.DataBean.ListBean.class);
        list.forEach(l -> {
            if (type == 0) {
                res.put(l.getSymbol(), l.getPercent());
            } else {
                res.put(l.getSymbol(), "涨幅：" + l.getPercent() + "%  换手率：" + l.getTurnover_rate() + "%  成交额：" + XMathUtil.divide(l.getAmount(), "100000000") + "亿");
            }
        });
        return res;
    }
}
