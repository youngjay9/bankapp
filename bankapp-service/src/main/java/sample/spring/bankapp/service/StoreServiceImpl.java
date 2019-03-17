package sample.spring.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sample.spring.bankapp.annotations.Action;
import sample.spring.bankapp.dao.StoreDao;
import sample.spring.bankapp.domain.Shop;

@Service(value = "storeService")
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	@Qualifier(value = "storeDao")
	private StoreDao storeDao;
	
	
	
	@Override
	@Action(name="StoreService AOP Test!!")
	public Shop getShopByArtical(int article) {
		
		return storeDao.getShopByArticle(article);
	}

}
