package com.xcx.security.tool;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.xcx.security.model.BaseModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;

/**
 * @author 邢晨旭
 * @date 2023/2/1
 */
public class AutoGenerator {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create("", "", "")
                .globalConfig(builder -> {
                    builder.author("邢晨旭") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xcx.security") // 设置父包名
                            .entity("vo.rbac")
                            .service("service.rbac")
                            .serviceImpl("service.rbac.impl")
                            .mapper("mapper.rbac")
                            .controller("controller.rbac")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper/rbac")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
//                            .addInclude("team,team_member") // 设置需要生成的表名
                            .entityBuilder()
                            .superClass(BaseModel.class)
                            .disableSerialVersionUID()
                            .enableChainModel()
                            .enableLombok()
                            .enableRemoveIsPrefix()
                            .enableTableFieldAnnotation()
                            .addSuperEntityColumns("delete_status", "create_account", "create_time", "update_account", "update_time")
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                            .idType(IdType.AUTO)
//                        .formatFileName("%sBo")
                            .build()

                            .controllerBuilder()
                            .enableRestStyle()
                            .formatFileName("%sController")
                            .build()

                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .build()

                            .mapperBuilder()
                            .mapperAnnotation(Mapper.class)
                            .enableBaseColumnList()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")
                            .build();
                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
