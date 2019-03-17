package sample.spring.bankapp.dao;

import sample.spring.bankapp.domain.FixedDepositDetails;

public interface FixedDepositDao {
	
	int createFixedDeposit(FixedDepositDetails fdd);
	
	FixedDepositDetails getFixedDeposit(int fixedDepositId);
}
