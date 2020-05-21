package cn.biz.service;

import cn.biz.dto.TableListDTO;
import cn.biz.mapper.SysOperateLogMapper;
import cn.biz.po.SysOperateLog;
import cn.biz.vo.TableListVO;
import cn.common.exception.ZxException;
import cn.common.util.file.AntZipUtil;
import cn.common.util.file.FileUtil;
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
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysServiceImpl implements ISysService {

    @Autowired
    private SysOperateLogMapper sysOperateLogMapper;
    @Value("${spring.datasource.druid.url}")
    private  String url;
    @Value("${spring.datasource.druid.username}")
    private String name;
    @Value("${spring.datasource.druid.password}")
    private String password;
    @Value("${spring.datasource.druid.driver-class-name}")
    private String driver;
    private String parent="com.zx";

    @Override
    public void generateCode(@ApiParam("主键id") @RequestParam  String[] arr, HttpServletResponse response)throws Exception {
        if (arr==null||arr.length==0){
            throw new ZxException("请至少选择一个");
        }
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
        dsc.setUrl(url);
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
        dto.setSchema("zx");
        List<TableListVO> list=sysOperateLogMapper.getTableList(page,dto);
        page.setRecords(list);
        return page;
    }
}
