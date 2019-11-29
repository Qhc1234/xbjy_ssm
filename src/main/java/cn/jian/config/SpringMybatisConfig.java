package cn.jian.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "cn.jian.mapper")//扫描mapper
@ComponentScan({"cn.jian.inteceptor","cn.jian.service"})
@EnableTransactionManagement
public class SpringMybatisConfig {
    //数据源
    @Bean
    public DruidDataSource getDataSource(){
        Properties properties = new Properties();
        try {
            properties.load(SpringMybatisConfig.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.configFromPropety(properties);

        return druidDataSource;
    }

    //sqlSessionFactor创建
    @Bean
    public SqlSessionFactoryBean getSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //tkmapper 需要使用  tkmapper提供的configuration
        //默认使用驼峰命名转换
        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
//        configuration.setLogImpl(Log4j2Impl.class);//设置日志类
        configuration.setMapUnderscoreToCamelCase(true);//设置驼峰命名转换
        sessionFactoryBean.setConfiguration(configuration);

        //设置分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(new Properties());//使用默认配置
        sessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});//设置使用插件
        return sessionFactoryBean;

    }

    @Bean/*事务管理器*/
    public DataSourceTransactionManager getTransactionManager(DruidDataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
