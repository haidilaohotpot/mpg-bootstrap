package com.wonder4work.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class GeneratorUtils {
    // 全局配置
    private final static String OUTPUT_DIR = "D:/git_sources/generator/src/main/java";// 生成文件的输出目录
    // 数据源配置
    private final static String DATABASE_IP = "127.0.0.1";// 数据库id
    private final static String DATABASE_NAME = "foodie-shop-dev";// 数据库名称
    // 包配置
    private final static String PARENT = "com.wonder4work";// 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        /*
         * 配置路径
         */
        String projectPath = System.getProperty("user.dir"); // 获取项目路径
        String objPath = projectPath + "/src/main/java"; // 获取java目录
        String parentPackage = "com.wonder4work"; // 配置包路径

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(objPath); // 配置路径
        gc.setOpen(false); // 是否打开生成的文件夹
        gc.setAuthor("wonder4work"); // author
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        gc.setMapperName("%sMapper"); // 设置mapper接口后缀
        gc.setServiceName("%sService"); // 设置Service接口后缀
        gc.setServiceImplName("%sServiceImpl"); // 设置Service实现类后缀
        gc.setControllerName("%sController"); // 设置controller类后缀
        gc.setXmlName("%sMapper"); // 设置sql映射文件后缀
        gc.setFileOverride(true); // 是否覆盖同名文件，默认是false
        gc.setActiveRecord(false); // 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false); // XML 二级缓存
        gc.setBaseResultMap(true); // XML ResultMap
        gc.setBaseColumnList(false); // XML columList
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://"+DATABASE_IP+":3306/"+DATABASE_NAME+"?characterEncoding=UTF-8&useSSL=false&useUnicode=true&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");// JDK8
        dsc.setUsername("root");
        dsc.setPassword("7777777");
        mpg.setDataSource(dsc);

        /*
         * 策略配置
         */
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表字段映射到实体的命名策略: 下划线转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setNaming(NamingStrategy.underline_to_camel) // 表名生成策略
                .setRestControllerStyle(true); // 设置controller自动加RestController注解
//        strategy.setInclude(new String[] {"t_role"}); //修改替换成你需要的表名，多个表名传数组,如果注释掉就生成库中所有的表
//        strategy.setTablePrefix(new String[] { "t_" }); // 此处可以修改为您的表前缀
        strategy.setEntityLombokModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage).setController("controller");
        packageConfig .setService("service"); //服务接口
        packageConfig.setServiceImpl("service.impl"); //服务实现
        packageConfig .setMapper("mapper"); //dao层
//        packageConfig.setXml("mapper"); //dao层对应的xml文件
        packageConfig .setEntity("pojo"); // pojo对象

        mpg.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };


        // 自定义xml的存放路径
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper.xml文件存放的路径
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "-mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置生成的资源模板
        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setController(null);    //不生成controller
//        templateConfig.setService(null);    //不生成service
//        templateConfig.setServiceImpl(null);//不生成service实现
        templateConfig.setXml(null);        // 关闭默认 xml 生成，调整生成 至 根目录

        mpg.setTemplate(templateConfig);

        // 执行生成
        mpg.execute();
    }
}
