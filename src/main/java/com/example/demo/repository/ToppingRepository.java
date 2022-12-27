package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Topping;

/**
 * トッピング情報を操作するリポジトリ.
 * 
 * @author inagakisaia
 *
 */
@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = new BeanPropertyRowMapper<>(Topping.class);

	/**
	 * トッピング情報を全件検索します.
	 * 
	 * @return 検索されたトッピング情報
	 */
	public List<Topping> findAll() {
		String sql = "SELECT id,name,price_m,price_l FROM toppings;";
		List<Topping> toppingList = template.query(sql, TOPPING_ROW_MAPPER);
		return toppingList;
	}
	
//	public Topping findMaxId() {
////		String sql = "SELECT id,name,price_m,price_l FROM toppings WHERE id = :id ;";
//		String sql = "SELECT max(id) FROM toppings ;";
//		
////		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
//		
//		List<Topping> toppinglist = template.query(sql, TOPPING_ROW_MAPPER);
//		
//		return toppinglist.get(0);
//	}

	public Topping load(Integer toiingId) {
		
		String sql = "SELECT id,name,price_m,price_l FROM toppings WHERE id = :id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",toiingId);
		
		Topping topping = template.queryForObject(sql, param, TOPPING_ROW_MAPPER);
		return topping;
	}
}
