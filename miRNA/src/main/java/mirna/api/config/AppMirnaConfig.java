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

import com.jcraft.jsch.JSchException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import mirna.ssh.CTestDriver;


@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef = "mirnaEntityManagerFactory", 
        transactionManagerRef = "mirnaTransactionManager",
		basePackages = { "mirna.api.repo" })
@ComponentScan(basePackages = "mirna", excludeFilters = {
		//@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION) })
public class AppMirnaConfig extends RepositoryRestMvcConfiguration {

	@Bean(name = "mirnaDataSource")
	public DataSource dataSource() {
		
		/*System.out.println("USING SSH!!!");
		String strSshUser = "root";              // SSH loging username
		String strSshPassword = "lnkdkhaos";     // SSH login password
		String strSshHost = "150.214.214.5";     // hostname or ip or SSH server
		int nSshPort = 22;                       // remote SSH host port number
		String strRemoteHost = "192.168.44.23";  // hostname or ip of your database server
		int nLocalPort = 3366;                   // local port number use to bind SSH tunnel
		int nRemotePort = 3306;                  // remote port number of your database
		
		try {
			CTestDriver.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
		} catch (JSchException e) {
			e.printStackTrace();
		}*/
		
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql://192.168.44.23:3306");
		//dataSource.setUrl("jdbc:mysql://localhost:3366");
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
