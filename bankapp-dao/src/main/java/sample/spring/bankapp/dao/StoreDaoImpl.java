package sample.spring.bankapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import sample.spring.bankapp.domain.Shop;

@Repository(value = "storeDao")
public class StoreDaoImpl implements StoreDao {
	
	@Autowired 
    @Qualifier("jdbcStore") 
    protected NamedParameterJdbcTemplate jdbcStore;  

	@Override
	public Shop getShopByArticle(int article) {
		
		
	    
		
		
		final String sql = "select * from shop where article = :article";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("article", article);
		
		return jdbcStore.queryForObject(sql, namedParameters,
				new RowMapper<Shop>() {
					public Shop mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Shop shop = new Shop();
						shop.setArticle(rs.getInt("article"));
						shop.setDealer(rs.getString("dealer"));
						shop.setPrice(rs.getLong("price"));
						return shop;
				}
		});
	}

}
