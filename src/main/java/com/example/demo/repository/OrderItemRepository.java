package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.OrderItem;

/**
 * OrderItemのRepository.
 * 
 * @author kaneko
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER2 = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		return orderItem;
	};

	private final static RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER3 = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setSize(rs.getString("size").charAt(0));
		return orderItem;
	};

	/**
	 * OrderItemを１件登録する.
	 * 
	 * @param oi Orderitemオブジェクト
	 */
	public void insert(OrderItem oi) {

		String sql = "INSERT INTO order_items(item_id,order_id,quantity,size) VALUES (:itemId, :orderId, :quantity, :size) ;";

		SqlParameterSource param = new BeanPropertySqlParameterSource(oi);

		template.update(sql, param);
	}

	/**
	 * トッピングも含め、OrderItemを１件削除する.
	 * 
	 * @param id orderItemId
	 */
	public void delete(Integer id) {

		String sql = "DELETE FROM order_items WHERE id = :id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		template.update(sql, param);
	}

	/**
	 * 登録した最新のOrderItemを１件取り出す.
	 * 
	 * @return OrderItem
	 */
	public OrderItem findMaxId() {

		String sql = "SELECT max(id) id FROM order_items;";

		List<OrderItem> oiList = template.query(sql, ORDER_ITEM_ROW_MAPPER2);

		return oiList.get(0);
	}

	/**
	 * 指定のOrderIdに該当するOrderItemのリストを返す.
	 * 
	 * @param orderId 指定のOrderId
	 * @return OrderItemList
	 */
	public List<OrderItem> getOrderItemListByOrderId(Integer orderId) {

		String sql = "SELECT id,item_id,order_id,quantity,size FROM order_items WHERE order_id = :orderId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);

		List<OrderItem> orderItemList = template.query(sql, param, ORDER_ITEM_ROW_MAPPER3);
		return orderItemList;
	}

	/**
	 * OrderItemを更新する.
	 * 
	 * @param orderItem
	 */
	public void update(OrderItem orderItem) {

		String sql = "UPDATE order_items SET item_id = :itemId,order_id = :orderId, quantity = :quantity, size = :size WHERE id = :id ;";

		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		template.update(sql, param);
	}
}
