package in.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.url}")
	private String dataSourceUrl;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.jpa.database-platform}")
	private String databasePlatform;

//	@Value("${spring.jpa.hibernate.ddl-auto}")
//	private String hibernateDdlAuto;

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(driverClassName);
	    dataSource.setUrl(dataSourceUrl);
	    return dataSource;
	}
}