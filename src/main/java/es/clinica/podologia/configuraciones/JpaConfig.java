package es.clinica.podologia.configuraciones;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Ignacio Rafael
 *
 */
public class JpaConfig {

	@Autowired
	Environment environment;

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getProperty("user"));
		dataSource.setPassword(environment.getProperty("password"));
		Properties properties1 = new Properties();
		properties1.setProperty("spring.jpa.generate-ddl", environment.getProperty("spring.jpa.generate-ddl"));
		dataSource.setConnectionProperties(properties1);
		Properties properties2 = new Properties();
		properties2.setProperty("spring.jpa.hibernate.ddl-auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
		dataSource.setConnectionProperties(properties2);
		return dataSource;
	}

}
