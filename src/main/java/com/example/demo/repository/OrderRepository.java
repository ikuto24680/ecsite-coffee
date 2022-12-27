package com.example.demo.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderTopping;
import com.example.demo.domain.Topping;

/**
 * 注文情報を操作するリポジトリ.
 * 
 * @author inagakisaia
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	public static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new LinkedList<Order>();
		List<OrderItem> ordeItemList = null;
		List<OrderTopping> orderToppingList = null;

		long beforeOrderId = 0;
		long beforeOrderIitemId = 0;
		long beforeOrderTopping = 0;

		while (rs.next()) {

			int nowOrderId = rs.getInt("o_id");

			if (nowOrderId != beforeOrderId) {
				Order order = new Order();
				order.setId(nowOrderId);
				order.setUserId(rs.getInt("o_user_id"));
				order.setStatus(rs.getInt("o_status"));
				order.setTotalPrice(rs.getInt("o_total_price"));
				order.setOrderDate(rs.getDate("o_order_date"));
				order.setDestinationName(rs.getString("o_destination_name"));
				order.setDestinationEmail(rs.getString("o_destination_email"));
				order.setDestinationZipcode(rs.getString("o_destination_zipcode"));
				order.setDestinationAddress(rs.getString("o_destination_address"));
				order.setDestinationTel(rs.getString("o_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("o_delivery_time"));
				order.setPaymentMethod(rs.getInt("o_payment_method"));

				ordeItemList = new ArrayList<OrderItem>();
				order.setOrderItemList(ordeItemList);

				orderList.add(order);
			}

			int nowOrderItemId = rs.getInt("oi_id");
			if (nowOrderItemId != 0 && nowOrderItemId != beforeOrderIitemId) {

				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("oi_item_id"));
				orderItem.setOrderId(rs.getInt("oi_order_id"));
				orderItem.setQuantity(rs.getInt("oi_quantity"));
				orderItem.setSize(rs.getString("oi_size").charAt(0));

				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));

				orderItem.setItem(item);

				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				ordeItemList.add(orderItem);

			}

			int nowOrderTopping = rs.getInt("ot_id");
			if (nowOrderTopping != 0 && nowOrderTopping != beforeOrderTopping) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setToppingId(rs.getInt("ot_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("ot_order_item_id"));

				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			beforeOrderTopping = nowOrderTopping;
			beforeOrderIitemId = nowOrderItemId;
			beforeOrderId = nowOrderId;
		}
		return orderList;
	};

	public static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {

		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));

		return order;
	};

	public static final RowMapper<Order> ORDER_ROW_MAPPER2 = (rs, i) -> {

		Order order = new Order();
		order.setId(rs.getInt("id"));

		return order;
	};

	public static final RowMapper<Order> ORDER_ROW_MAPPER3 = (rs, i) -> {

		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));

		return order;
	};

	/**
	 * userId と status=0 を条件にOrderオブジェクトを1件取得する
	 * 
	 * @param userId ユーザーID
	 */
	public Order findByUserIdAndStatus(Integer userId) {

		String sql = "SELECT id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method FROM orders WHERE user_id = :userId AND status = 0; ";

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		List<Order> order = template.query(sql, param, ORDER_ROW_MAPPER);

		System.out.println("findByUserIdAndStatusメソッド内の order =" + order);
		try {
			order.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

		return order.get(0);
	}

	/**
	 * orderIdを条件にOrderリストを返す.
	 * 
	 * @param orderId オーダーID
	 * @return Orderリスト（履歴検索も考慮して、複数検索ができるようにしてます。）
	 */
	public Order load(Integer orderId) {

		String sql = "SELECT o.id o_id, o.user_id o_user_id, o.status o_status, o.total_price o_total_price, o.order_date o_order_date, o.destination_name o_destination_name, o.destination_email o_destination_email, o.destination_zipcode o_destination_zipcode, o.destination_address o_destination_address, o.destination_tel o_destination_tel, o.delivery_time o_delivery_time, o.payment_method o_payment_method, "
				+ "oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id,oi.quantity oi_quantity, oi.size oi_size, "
				+ "ot.id ot_id, ot.topping_id ot_topping_id,ot.order_item_id ot_order_item_id, "
				+ "i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, "
				+ "t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l "
				+ "FROM Orders o LEFT OUTER  JOIN order_items oi ON o.id = oi.order_id "
				+ " LEFT OUTER  JOIN order_toppings ot ON oi.id = ot.order_item_id "
				+ " LEFT OUTER JOIN items i ON i.id = oi.item_id "
				+ " LEFT OUTER JOIN toppings t ON t.id = ot.topping_id " + "WHERE o.id = :orderId ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);

		List<Order> orderList = null;
		orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList.get(0);
	}

	/**
	 * 該当ユーザーのStatus=0のOrderを１件登録する.
	 * 
	 * @param order Order
	 */
	public void insert(Order order) {

		String sql = "INSERT INTO orders(user_id,status,total_price) VALUES(:userId, 0, :totalPrice);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		template.update(sql, param);
	}

	/**
	 * 注文情報を更新する.
	 * 
	 * @param order 注文情報
	 */
	public void update(Order order) {
		String sql = "UPDATE orders SET order_date=:orderDate, destination_name=:destinationName,"
				+ " destination_email=:destinationEmail,destination_zipcode=:destinationZipcode,"
				+ "destination_address=:destinationAddress,destination_tel=:destinationTel,"
				+ "delivery_time=:deliveryTime,payment_method=:paymentMethod,status=:status WHERE id=:id;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(sql, param);
	}
	
	/**
	 * ユーザーIDから配送済み情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> findByUserIdAndStatusFour(Integer userId) {
		String sql = "SELECT o.id o_id, o.user_id o_user_id, o.status o_status, o.total_price o_total_price, o.order_date o_order_date, o.destination_name o_destination_name, o.destination_email o_destination_email, o.destination_zipcode o_destination_zipcode, o.destination_address o_destination_address, o.destination_tel o_destination_tel, o.delivery_time o_delivery_time, o.payment_method o_payment_method, "
				+ "oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id,oi.quantity oi_quantity, oi.size oi_size, "
				+ "ot.id ot_id, ot.topping_id ot_topping_id,ot.order_item_id ot_order_item_id, "
				+ "i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, "
				+ "t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l "
				+ "FROM Orders o LEFT OUTER  JOIN order_items oi ON o.id = oi.order_id "
				+ " LEFT OUTER  JOIN order_toppings ot ON oi.id = ot.order_item_id "
				+ " LEFT OUTER JOIN items i ON i.id = oi.item_id "
				+ " LEFT OUTER JOIN toppings t ON t.id = ot.topping_id " + "WHERE o.user_id = :userId AND o.status=4 ORDER BY o.id DESC ;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		List<Order> orderList = null;
		orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}
	/**
	 * ユーザーIDから発送済み情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> findByUserIdAndStatusThree(Integer userId) {
		String sql = "SELECT o.id o_id, o.user_id o_user_id, o.status o_status, o.total_price o_total_price, o.order_date o_order_date, o.destination_name o_destination_name, o.destination_email o_destination_email, o.destination_zipcode o_destination_zipcode, o.destination_address o_destination_address, o.destination_tel o_destination_tel, o.delivery_time o_delivery_time, o.payment_method o_payment_method, "
				+ "oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id,oi.quantity oi_quantity, oi.size oi_size, "
				+ "ot.id ot_id, ot.topping_id ot_topping_id,ot.order_item_id ot_order_item_id, "
				+ "i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, "
				+ "t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l "
				+ "FROM Orders o LEFT OUTER  JOIN order_items oi ON o.id = oi.order_id "
				+ " LEFT OUTER  JOIN order_toppings ot ON oi.id = ot.order_item_id "
				+ " LEFT OUTER JOIN items i ON i.id = oi.item_id "
				+ " LEFT OUTER JOIN toppings t ON t.id = ot.topping_id " + "WHERE o.user_id = :userId AND o.status=3 ORDER BY o.id DESC ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		
		List<Order> orderList = null;
		orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}
	/**
	 * ユーザーIDから入金済み情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> findByUserIdAndStatusTow(Integer userId) {
		String sql = "SELECT o.id o_id, o.user_id o_user_id, o.status o_status, o.total_price o_total_price, o.order_date o_order_date, o.destination_name o_destination_name, o.destination_email o_destination_email, o.destination_zipcode o_destination_zipcode, o.destination_address o_destination_address, o.destination_tel o_destination_tel, o.delivery_time o_delivery_time, o.payment_method o_payment_method, "
				+ "oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id,oi.quantity oi_quantity, oi.size oi_size, "
				+ "ot.id ot_id, ot.topping_id ot_topping_id,ot.order_item_id ot_order_item_id, "
				+ "i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, "
				+ "t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l "
				+ "FROM Orders o LEFT OUTER  JOIN order_items oi ON o.id = oi.order_id "
				+ " LEFT OUTER  JOIN order_toppings ot ON oi.id = ot.order_item_id "
				+ " LEFT OUTER JOIN items i ON i.id = oi.item_id "
				+ " LEFT OUTER JOIN toppings t ON t.id = ot.topping_id " + "WHERE o.user_id = :userId AND o.status=2 ORDER BY o.id DESC ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		
		List<Order> orderList = null;
		orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}
	/**
	 * ユーザーIDから未入金情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> findByUserIdAndStatusOne(Integer userId) {
		String sql = "SELECT o.id o_id, o.user_id o_user_id, o.status o_status, o.total_price o_total_price, o.order_date o_order_date, o.destination_name o_destination_name, o.destination_email o_destination_email, o.destination_zipcode o_destination_zipcode, o.destination_address o_destination_address, o.destination_tel o_destination_tel, o.delivery_time o_delivery_time, o.payment_method o_payment_method, "
				+ "oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id,oi.quantity oi_quantity, oi.size oi_size, "
				+ "ot.id ot_id, ot.topping_id ot_topping_id,ot.order_item_id ot_order_item_id, "
				+ "i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, "
				+ "t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l "
				+ "FROM Orders o LEFT OUTER  JOIN order_items oi ON o.id = oi.order_id "
				+ " LEFT OUTER  JOIN order_toppings ot ON oi.id = ot.order_item_id "
				+ " LEFT OUTER JOIN items i ON i.id = oi.item_id "
				+ " LEFT OUTER JOIN toppings t ON t.id = ot.topping_id " + "WHERE o.user_id = :userId AND o.status=1 ORDER BY o.id DESC ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		
		List<Order> orderList = null;
		orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}

	/**
	 * 登録した最新のOrderのidを返します,
	 * 
	 * @return 最新のId
	 */
	public Integer findRecentId() {

		String sql = "SELECT max(id) id FROM orders ;";

		List<Order> orderList = template.query(sql, ORDER_ROW_MAPPER2);
		Integer recentId = orderList.get(0).getId();
		return recentId;
	}

	/**
	 * 指定のidを持つOrderのuserIdを返します.
	 * 
	 * @param recentId id
	 * @return userId
	 */
	public Integer findRecentUserId(Integer recentId) {

		String sql = "SELECT id,user_id FROM orders WHERE id = :recentId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("recentId", recentId);

		List<Order> orderList = template.query(sql, param, ORDER_ROW_MAPPER3);
		Integer recentUserId = orderList.get(0).getUserId();
		return recentUserId;
	}

}
