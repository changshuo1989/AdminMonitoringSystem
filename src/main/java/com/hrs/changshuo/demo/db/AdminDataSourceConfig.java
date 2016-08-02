package com.hrs.changshuo.demo.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.hrs.changshuo.demo.domain.admin.IEnvironmentDAO;

@Configuration
@Primary
@EnableJpaRepositories(basePackages={"com.hrs.changshuo.demo.domain.admin"},
					   entityManagerFactoryRef="adminEntityManagerFactory",
					   transactionManagerRef="adminTransactionManager")
public class AdminDataSourceConfig {
	
	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;
	
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	@Primary
	public DataSource dataSource(){
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="adminEntityManager")
	@Primary
	public EntityManager entityManager(){
		return entityManagerFactory().createEntityManager();
	}
	
	@Bean(name="adminEntityManagerFactory")
	@Primary
	public EntityManagerFactory entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean lefb=new LocalContainerEntityManagerFactoryBean();
		lefb.setDataSource(dataSource());
		lefb.setJpaVendorAdapter(jpaVendorAdapter);
		lefb.setPackagesToScan("com.hrs.changshuo.demo.domain.admin");
		lefb.setPersistenceUnitName("adminPersistenceUnit");
		lefb.afterPropertiesSet();
		return lefb.getObject();
	}
	
	@Bean(name="adminTransactionManager")
	@Primary
	public PlatformTransactionManager transactionManager(){
		return new JpaTransactionManager(entityManagerFactory());
	}	
}
