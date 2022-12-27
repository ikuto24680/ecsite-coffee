package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderRepository;

/**
 * 注文履歴を操作するサービス.
 * 
 * @author inagakisaia
 *
 */
@Service
public class OrderHistoryService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * ユーザーIDから配送済み情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> showHistoryOfStatusFour(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusFour(userId);
		return orderList;
	}

	/**
	 * ユーザーIDから発送済み情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> showHistoryOfStatusThree(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusThree(userId);
		return orderList;
	}

	/**
	 * ユーザーIDから入金済み情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> showHistoryOfStatusTwo(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusTow(userId);
		return orderList;
	}

	/**
	 * ユーザーIDから未入金情報を検索します.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> showHistoryOfStatusOne(Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusOne(userId);
		return orderList;
	}

}
