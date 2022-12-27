package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

/**
 * 商品情報を操作するリポジトリ.
 * 
 * @author inagakisaia
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {

		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));

		return item;

	};

	/**
	 * IDから商品情報を一件検索します.
	 * 
	 * @param id ID
	 * @return 検索された商品情報
	 */
	public Item load(int id) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

	/**
	 * 名前で商品情報をあいまい検索します.
	 * 
	 * @param name 商品名
	 * @return 名前が曖昧一致している商品一覧
	 */
	public List<Item> findByItemName(String name) {

		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE name ILIKE :name ORDER BY price_m DESC;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品情報を全件検索します.
	 * 
	 * @return 検索された商品情報全件
	 */
	public List<Item> findAll() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m DESC;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);

		return itemList;
	}

}
