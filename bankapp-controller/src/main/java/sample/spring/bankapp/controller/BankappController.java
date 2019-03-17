package sample.spring.bankapp.controller;


import org.springframework.web.bind.annotation.RestController;

import sample.spring.bankapp.domain.BankAccountDetails;
import sample.spring.bankapp.domain.FixedDepositDetails;
import sample.spring.bankapp.domain.Shop;
import sample.spring.bankapp.service.BankAccountService;
import sample.spring.bankapp.service.FixedDepositService;
import sample.spring.bankapp.service.StoreService;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BankappController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private FixedDepositService fixedDepositService;
	
	@Autowired
	private StoreService storeService;
	
	private static Logger logger = LogManager.getLogger(BankappController.class);
	
	
	@RequestMapping("/index")
    public String index() {
        return "Greetings from Spring Boot!";
    }
	
	@RequestMapping("/createAccount")
	public String createBankAccount() throws Exception {
		
		
		BankAccountDetails bankAccountDetails = new BankAccountDetails();
		bankAccountDetails.setBalanceAmount(2000);
		bankAccountDetails.setLastTransactionTimestamp(new Date());

		int bankAccountId = bankAccountService.createBankAccount(bankAccountDetails);

		logger.info("Created bank account (with balance amount 2000) with id - "
				+ bankAccountId);
		
		FixedDepositDetails fdd = new FixedDepositDetails();
		
		fdd.setActive("Y");
		fdd.setFdAmount(1500);
		fdd.setBankAccountId(bankAccountId);
		fdd.setFdCreationDate(new Date());
		fdd.setTenure(12);

		int fixedDepositId = fixedDepositService.createFixedDeposit(fdd);
		logger.info("Created fixed deposit (for 1500 amount) with id - "
				+ fixedDepositId
				+ " Check FIXED_DEPOSIT_DETAILS table to verify the transaction was committed or rolledback.");
		
		
		return "Success!!";
	}
	
	@RequestMapping("/getShopPrice")
	public String getShopPrice() {
		Shop shop = storeService.getShopByArtical(2);
		
		return "price:" + shop.getPrice();
	}
	
	
}
