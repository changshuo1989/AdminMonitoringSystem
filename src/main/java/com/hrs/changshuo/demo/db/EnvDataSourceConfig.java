package com.hrs.changshuo.demo.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.hrs.changshuo.demo.domain.admin.Environment;
import com.hrs.changshuo.demo.domain.admin.IEnvironmentDAO;

@Configuration

@EnableJpaRepositories(basePackages={"com.hrs.changshuo.demo.domain.env"},
					   entityManagerFactoryRef="envEntityManagerFactory",
					   transactionManagerRef="envTransactionManager")

public class EnvDataSourceConfig {

	
	@Bean
	JpaVendorAdapter JpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		adapter.setShowSql(true);
		return adapter;
	}
	
	@Bean(name="dataSourceMap")
	public Map<Object, Object> dataSourceMap(){
		Map<Object, Object> map=new HashMap<Object, Object>();
		
		//just for test
		//TODO: still need to figure out a way to read db in the compile time
		String name1="env1";
		DataSource ds1=DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/"+name1).
				username("root").
				password("root").
				build();
		map.put((Object)name1, (Object)ds1);
		
		
		String name2="env2";
		DataSource ds2=DataSourceBuilder.create().url("jdbc:mysql://localhost:3306/"+name2).
				username("root").
				password("root").
				build();
		map.put((Object)name2, (Object)ds2);
		
		return map;
	}
	
	@Bean(name="dataSource")
	public DynamicDataSource dynamicDataSource(){
		Map<Object, Object> dataSourceMap=dataSourceMap();
		DynamicDataSource dds=new DynamicDataSource();
		dds.setTargetDataSources(dataSourceMap);
		Iterator<Object> keys=dataSourceMap.keySet().iterator();
		Object defaultDataSource=dataSourceMap.get(keys.next());
		dds.setDefaultTargetDataSource(defaultDataSource);
		return dds;
	}
	
	@Bean(name="envEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean lefb=new LocalContainerEntityManagerFactoryBean();
		lefb.setDataSource(dynamicDataSource());
		lefb.setJpaVendorAdapter(JpaVendorAdapter());
		lefb.setPackagesToScan("com.hrs.changshuo.demo.domain.env");
		lefb.setPersistenceUnitName("envPersistenceUnit");
		lefb.afterPropertiesSet();
		return lefb.getObject();
	}
	
	@Bean(name="envTransactionManager")
	public JpaTransactionManager jpaTransactionManager(){
		JpaTransactionManager jtm=new JpaTransactionManager();
		jtm.setEntityManagerFactory(entityManagerFactory());
		return jtm;
	}

}
