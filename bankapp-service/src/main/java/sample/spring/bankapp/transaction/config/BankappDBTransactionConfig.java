package sample.spring.bankapp.transaction.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class BankappDBTransactionConfig {
	
	@Bean(name="txManager") 
	@Autowired
	public DataSourceTransactionManager txManager(@Qualifier ("dataSourceBankApp") DataSource datasource) {
		DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
	    return txm;
	}
	
	
	@Bean(name = "transactionTemplate")
	@Autowired
	public TransactionTemplate txTemplate(@Qualifier("txManager") DataSourceTransactionManager txManager) {
		TransactionTemplate txTemplate = new TransactionTemplate();
		txTemplate.setTransactionManager(txManager);
		txTemplate.setIsolationLevelName("ISOLATION_READ_UNCOMMITTED");
		txTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRED");
		return txTemplate;
	}
	
}
