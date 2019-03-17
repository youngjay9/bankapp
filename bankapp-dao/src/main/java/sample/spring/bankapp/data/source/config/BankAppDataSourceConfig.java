package sample.spring.bankapp.data.source.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@Configuration
public class BankAppDataSourceConfig{
	
	/**
	 * 取得 spring_bank_app_db 的 properties
	 * @return DataSourceProperties
	 */
	@Primary
    @Bean(name = "primaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }
	
	/**
	 * 所有的 data source config 中，必需設定一個 data source 為 primary,不然 spring boot 起動時會報錯！ 
	 * @return DataSource
	 */
	@Primary
    @Bean(name = "dataSourceBankApp")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
    }
	
	@Bean(name="jdbcBankApp")
	public JdbcTemplate bankAppJdbcTemplate(
			@Qualifier("dataSourceBankApp")DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	
	@Bean(name="nameAndParameterJdbcBankApp")
	public NamedParameterJdbcTemplate storeNameParameterJdbcTemplate(
			@Qualifier("dataSourceBankApp")DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	
}
