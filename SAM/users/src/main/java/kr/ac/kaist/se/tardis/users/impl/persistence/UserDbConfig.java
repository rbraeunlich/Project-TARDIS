package kr.ac.kaist.se.tardis.users.impl.persistence;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import kr.ac.kaist.se.tardis.users.api.UserWithoutPasswordRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "kr.ac.kaist.se.tardis.users", 
		excludeFilters=@Filter(type=FilterType.ASSIGNABLE_TYPE,classes=UserWithoutPasswordRepository.class),
		entityManagerFactoryRef="userEntityManager",
		transactionManagerRef="userTransactionManager")
public class UserDbConfig {

	@Autowired
	@Qualifier("userDS")
	private DataSource userDataSource;

	@Bean(name="userEntityManager")
	public EntityManagerFactory entityManagerFactory() {
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(UserImpl.class.getPackage().getName());
		factory.setDataSource(userDataSource);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean(name="userTransactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

}
