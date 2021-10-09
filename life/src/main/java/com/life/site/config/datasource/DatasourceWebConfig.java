package com.life.site.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


 @Configuration
 @EnableTransactionManagement
 @MapperScan(basePackages = {"com.life.site"} , sqlSessionFactoryRef = "webSqlSessionFactory")
 @ConfigurationProperties(prefix = "spring.datasource")
 public class DatasourceWebConfig extends HikariConfig{
    @Autowired
    private ApplicationContext applicationContext;

    
    @Bean(name ="webDatasource")
    public DataSource datasource() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(this));
    }

    @Bean
    public PlatformTransactionManager stdwebTxManager(@Qualifier("webDatasource") DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }
 
    @Bean(name = "webSqlSessionFactory")
    public SqlSessionFactory webSqlSessionFactory(@Qualifier("webDatasource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource);
        
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/web/**/*.xml"));
        
        Resource myBatisConfig = applicationContext.getResource("classpath:mybatis/mybatis-config.xml");
        sqlSessionFactoryBean.setConfigLocation(myBatisConfig);

        return sqlSessionFactoryBean.getObject();
    }
    
    
    @Bean(destroyMethod = "clearCache")
    public SqlSession webSqlSession(@Qualifier("webSqlSessionFactory")  SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    

}