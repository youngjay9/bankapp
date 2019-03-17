package sample.spring.bankapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import sample.spring.bankapp.domain.BankAccountDetails;

@Repository(value = "bankAccountDao")
public class BankAccountDaoImpl implements BankAccountDao {
	
	private static Logger logger = LogManager.getLogger(BankAccountDaoImpl.class);
	
	
	private SimpleJdbcInsert insertBankAccountDetail;

	@Autowired
	@Qualifier("jdbcBankApp")
	private JdbcTemplate jdbcBankApp;

	@Autowired
	private void setDataSource(@Qualifier("dataSourceBankApp")DataSource dataSource) {
		this.insertBankAccountDetail = new SimpleJdbcInsert(dataSource)
				.withTableName("bank_account_details")
				.usingGeneratedKeyColumns("account_id");
	}

	
	public void subtractFromAccount(int bankAccountId, int amount) {
		
		int balanceAmount = 0;
		
		String sql = "select * from bank_account_details where account_id = " + bankAccountId;
		
		logger.info("sql==>" + sql);
		
		
		try {
			List<Map<String,Object>> list = jdbcBankApp.queryForList(sql);
			
			Map<String, Object> row = list.get(0);
			
			Float tmpBalanceAmount = (Float)row.get("BALANCE_AMOUNT");
			
			
			balanceAmount = tmpBalanceAmount.intValue();
			
			logger.info("balanceAmount==>" + tmpBalanceAmount.intValue());
			
			logger.info("amount==>"+amount);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		if (balanceAmount < amount) {
			
			logger.info("throw Insufficient exception!!");
			
			throw new RuntimeException(
					"Insufficient balance amount in bank account");
		}
		
		jdbcBankApp
				.update("update bank_account_details set balance_amount = ? where account_id = ?",
						amount, bankAccountId);
	}



	public int createBankAccount(final BankAccountDetails bankAccountDetails) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("balance_amount", bankAccountDetails.getBalanceAmount());
		parameters.put("last_transaction_ts", new java.sql.Date(
				bankAccountDetails.getLastTransactionTimestamp().getTime()));
		Number key = insertBankAccountDetail.executeAndReturnKey(parameters);
		return key.intValue();
	}
}
