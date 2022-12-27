package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderTopping;
import com.example.demo.domain.Topping;
import com.example.demo.form.CartForm;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderToppingRepository;
import com.example.demo.repository.ToppingRepository;

/**
 * Cartへの追加、削除、表示をするServiceクラス.
 * 
 * @author kaneko
 *
 */
@Service
@Transactional
public class CartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	@Autowired
	private HttpSession session;

	/**
	 * status=0のorderIdの有無を確認→無い場合作成. カートに追加するOrderItemを登録する.
	 * カートに追加したOrderItemのToppingListを登録する.
	 * 
	 * @param form   CartForm（登録する商品の内容）
	 * @param userId ユーザーID
	 */
	public void addItem(CartForm form, Integer userId) {

		Order order = orderRepository.findByUserIdAndStatus(userId);

		if (order == null) {
			Order createOrder = new Order();
			createOrder.setUserId(userId);
			createOrder.setStatus(0);
			createOrder.setTotalPrice(0);
			orderRepository.insert(createOrder);
//			Order newOrder = orderRepository.findByUserIdAndStatus(userId);
		}
		order = orderRepository.findByUserIdAndStatus(userId);
		Integer orderId = order.getId();

		OrderItem oi = new OrderItem();
		oi.setItemId(form.getItemId());
		oi.setOrderId(orderId);
		oi.setQuantity(form.getQuantity());
		oi.setSize(form.getSize());
		orderItemRepository.insert(oi);

		OrderTopping ot = new OrderTopping();
		List<Integer> toppinglist = form.getToppingList();

		if (toppinglist != null) {
			for (Integer toppingId : toppinglist) {
				ot.setToppingId(toppingId);
				OrderItem orderItem = orderItemRepository.findMaxId();
				Integer recentId = orderItem.getId();
				ot.setOrderItemId(recentId);
				Topping topping = toppingRepository.load(toppingId);
				ot.setTopping(topping);
				orderToppingRepository.insert(ot);
			}
		}
	}

	/**
	 * カートの中身を表示する.
	 * 戻り値がList<Order>なのは履歴表示の際にもこのメソッドを使うことができ、そのようにOrderRepositoryのload()を作ったから.
	 * 
	 * @param userId ユーザーID
	 * @return Orderリスト
	 */
	public Order showCart(Integer userId) {

		Order existorder = orderRepository.findByUserIdAndStatus(userId);
		if (existorder == null) {
			return null;
		}
		Order order = orderRepository.load(existorder.getId());
		return order;
	}

	/**
	 * OrderItemを削除する. 該当するorderIdを検索し、OrderItemを削除する.
	 * 
	 * @param orderItemId OrderItemId
	 */
	public void deleteOrderItem(Integer orderItemId) {

		orderToppingRepository.delete(orderItemId);

		orderItemRepository.delete(orderItemId);
	}

	public Order createDummyOrder(Integer dummuUserId) {

		Order dummyOrderDetail = new Order();
		dummyOrderDetail.setUserId(dummuUserId);
		dummyOrderDetail.setStatus(0);
		dummyOrderDetail.setTotalPrice(0);

		orderRepository.insert(dummyOrderDetail);
		return dummyOrderDetail;
	}

	public Order searchDummyOrder(Integer dummyUserId) {
		Order order = orderRepository.findByUserIdAndStatus(dummyUserId);
		System.out.println("SearchDummyOrderメソッド内のorder = " + order);
		return order;
	}

	public Order transferItemList(Order trueOrder, List<OrderItem> dummyOrderItemList) {

//		dummyOrder.setId(trueOrder.getId());
//		dummyOrder.setUserId(trueOrder.getUserId());
//		dummyOrder.setStatus(trueOrder.getStatus());
//		dummyOrder.setTotalPrice(trueOrder.getTotalPrice());
//		List<OrderItem> dummyList = dummyOrderItemList.getOrderItemList();
//		

		// trueOrderのOrderItemListにOrderItemを加えるのではなくて、
		// dummyOrderItem(List)のorderIdをtrueOrderのOrderIdにして、更新する。
		Integer trueOrderId = trueOrder.getId();
		for (OrderItem dummyOrderItem : dummyOrderItemList) {
			dummyOrderItem.setOrderId(trueOrderId);
			orderItemRepository.update(dummyOrderItem);
		}
		return trueOrder;
	}

	public void update(Order transferdOrder) {
		orderRepository.update(transferdOrder);
	}

	public Integer findIdAtRecentOrder() {
		return orderRepository.findRecentId();
	}

	public Integer findUserIdAtRecentOrder(Integer recentId) {
		return orderRepository.findRecentUserId(recentId);
	}

	public List<OrderItem> getOrderItemListByOrderId(Integer orderId) {
		return orderItemRepository.getOrderItemListByOrderId(orderId);
	}

}
