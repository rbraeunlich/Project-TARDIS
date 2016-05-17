package kr.ac.kaist.se.tardis;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import kr.ac.kaist.se.tardis.users.api.UserRepository;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "kr.ac.kaist.se.tardis", 
		excludeFilters=@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={UserRepository.class}))
public class PrimaryDbConfig {

	@Autowired
	private DataSource primaryDataSource;

	@PersistenceContext(name="primary")
	@Bean
	@Primary
	public EntityManagerFactory entityManagerFactory() {
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		//FIXME extend when new entities get added
		factory.setPackagesToScan("kr.ac.kaist.se.tardis.users.copy");
		factory.setDataSource(primaryDataSource);
		factory.setPersistenceUnitName("primary");
		
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

}
