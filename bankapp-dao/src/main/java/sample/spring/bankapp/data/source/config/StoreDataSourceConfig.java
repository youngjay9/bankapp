package sample.spring.bankapp.data.source.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class StoreDataSourceConfig {
	
	
	/**
	 * 取得 store_db 的 properties
	 * @return
	 */
    @Bean(name = "secondaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "dataSourceStore")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return secondaryDataSourceProperties().initializeDataSourceBuilder().build();
    }
	
	
	@Bean(name="jdbcStore")
	public NamedParameterJdbcTemplate storeJdbcTemplate(
			@Qualifier("dataSourceStore")DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	

}
