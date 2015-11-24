package mirna.api.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef = "mirnaEntityManagerFactory", 
        transactionManagerRef = "mirnaTransactionManager",
		basePackages = { "mirna.api.repo" })
@ComponentScan(basePackages = "mirna", excludeFilters = {
		@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION) })
public class AppMirnaConfig extends RepositoryRestMvcConfiguration {

	@Bean(name = "mirnaDataSource")
	public DataSource dataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql://192.168.44.23:3306");
	    dataSource.setDatabaseName("mirna");
	    dataSource.setUser("mirna");
	    dataSource.setPassword("mirna");
	    return dataSource;
	}

	@Bean(name = "mirnaJpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setDatabase(Database.MYSQL);
		return adapter;
	}

	@Bean(name = "mirnaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws ClassNotFoundException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("mirna.api.model");
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		return factoryBean;
	}

	@Bean(name = "mirnaTransactionManager")
	public JpaTransactionManager transactionManager()
			throws ClassNotFoundException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory()
				.getObject());

		return transactionManager;
	}

}
