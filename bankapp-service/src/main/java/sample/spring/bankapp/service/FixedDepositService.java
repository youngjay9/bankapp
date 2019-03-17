package sample.spring.bankapp.service;

import sample.spring.bankapp.domain.FixedDepositDetails;

public interface FixedDepositService {
	
	int createFixedDeposit(FixedDepositDetails fdd) throws Exception;
	
	FixedDepositDetails getFixedDeposit(int fixedDepositId);

}
