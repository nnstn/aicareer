package com.sitech.aicareer.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * 运营数据库数据源
 * @author wangjn_bj
 */
@Configuration
@MapperScan(basePackages = DataSourcePromoteConfig.PACKAGE, sqlSessionFactoryRef = "promoteSqlSessionFactory")
public class DataSourcePromoteConfig {

    /**
     * 配置多数据源 关键就在这里 这里配置了不同的数据源扫描不同mapper
     */
    static final String PACKAGE = "com.sitech.aipaas.mapperpromote";
    static final String MAPPER_LOCATION = "classpath:mybatis/promote/*.xml";

    /**
     * 连接数据库信息 这个其实更好的是用配置中心完成
     */
    @Value("${promote.datasource.url}")
    private String url;

    @Value("${promote.datasource.username}")
    private String username;

    @Value("${promote.datasource.password}")
    private String password;

    @Value("${promote.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean("promoteDataSource")
    public DataSource promoteDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name = "promoteTransactionManager")
    public DataSourceTransactionManager promoteTransactionManager() {
        return new DataSourceTransactionManager(promoteDataSource());
    }

    @Bean(name = "promoteSqlSessionFactory")
    public SqlSessionFactory promoteSqlSessionFactory(@Qualifier("promoteDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(DataSourceMasterConfig.MYBATIS_LOCATION));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DataSourcePromoteConfig.MAPPER_LOCATION));

        return sessionFactory.getObject();
    }
}
