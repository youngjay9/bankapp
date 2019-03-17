package sample.spring.bankapp.dao;

import sample.spring.bankapp.domain.Shop;

public interface StoreDao {
	public Shop getShopByArticle(int article); 
}
