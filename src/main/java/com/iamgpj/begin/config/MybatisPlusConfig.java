package com.iamgpj.begin.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/19 12:57
 */
@Configuration
@MapperScan("com.iamgpj.begin.module.*.*.dao")
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/*Mapper.xml"));
        factoryBean.setTypeAliasesPackage("com.iamgpj.begin.module.*.*.entity");
        factoryBean.setTypeEnumsPackage("com.iamgpj.begin.module.*.*.enums");

        MybatisConfiguration configuration = new MybatisConfiguration();

        //configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);

        if (this.databaseIdProvider != null) {
            factoryBean.setDatabaseIdProvider(databaseIdProvider);
        }
        factoryBean.setConfiguration(configuration);
        factoryBean.setPlugins(new Interceptor[]{
                paginationInterceptor(),
                performanceInterceptor()
        });
        factoryBean.setGlobalConfig(globalConfiguration());
        return factoryBean.getObject();
    }

    @Bean
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration();
        //  主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
        conf.setIdType(0);
        // 字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
        conf.setFieldStrategy(2);
        // 驼峰下划线转换
        conf.setDbColumnUnderline(true);
        // 刷新mapper 调试神器
        conf.setRefresh(true);
        // 逻辑删除配置
        /*conf.setLogicDeleteValue("true");
        conf.setLogicNotDeleteValue("false");*/
        // 自定义sql注入器
        conf.setSqlInjector(new LogicSqlInjector());
        // 自定义填充策略接口实现
        //conf.setMetaObjectHandler();
        return conf;
    }
}
