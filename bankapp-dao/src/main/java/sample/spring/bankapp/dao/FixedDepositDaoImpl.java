package sample.spring.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sample.spring.bankapp.domain.FixedDepositDetails;

@Repository(value = "fixedDepositDao")
public class FixedDepositDaoImpl implements FixedDepositDao {
	
	@Autowired
	@Qualifier("jdbcBankApp")
	private JdbcTemplate jdbcBankApp;

	@Autowired
	@Qualifier("nameAndParameterJdbcBankApp")
	private NamedParameterJdbcTemplate nameAndParameterJdbcBankApp;

	public int createFixedDeposit(final FixedDepositDetails fdd) {
		final String sql = "insert into fixed_deposit_details(account_id, fd_creation_date, amount, tenure, active) "
				+ "values(?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcBankApp.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						new String[] { "fixed_deposit_id" });
				ps.setInt(1, fdd.getBankAccountId());
				ps.setDate(2, new java.sql.Date(fdd.getFdCreationDate()
						.getTime()));
				ps.setInt(3, fdd.getFdAmount());
				ps.setInt(4, fdd.getTenure());
				ps.setString(5, fdd.getActive());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public FixedDepositDetails getFixedDeposit(final int fixedDepositId) {
		final String sql = "select * from fixed_deposit_details where fixed_deposit_id = :fixedDepositId";
		SqlParameterSource namedParameters = new MapSqlParameterSource(
				"fixedDepositId", fixedDepositId);
		return nameAndParameterJdbcBankApp.queryForObject(sql, namedParameters,
				new RowMapper<FixedDepositDetails>() {
					public FixedDepositDetails mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						FixedDepositDetails fdd = new FixedDepositDetails();
						fdd.setActive(rs.getString("active"));
						fdd.setBankAccountId(rs.getInt("account_id"));
						fdd.setFdAmount(rs.getInt("amount"));
						fdd.setFdCreationDate(rs.getDate("fd_creation_date"));
						fdd.setFixedDepositId(rs.getInt("fixed_deposit_id"));
						fdd.setTenure(rs.getInt("tenure"));
						return fdd;
					}
				});
	}

}
