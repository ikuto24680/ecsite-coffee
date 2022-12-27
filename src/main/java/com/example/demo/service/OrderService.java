package com.example.demo.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Order;
import com.example.demo.form.OrderForm;
import com.example.demo.repository.OrderRepository;

/**
 * 注文情報を操作するサービス.
 * 
 * @author inagakisaia
 *
 */
@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 注文をします.
	 * 
	 * @param orderForm 注文情報を受け取るフォーム.
	 */
	public void order(OrderForm orderForm) {
		Order order=orderRepository.load(Integer.parseInt(orderForm.getId()));

		BeanUtils.copyProperties(orderForm, order);
		order.setId(Integer.parseInt(orderForm.getId()));
		order.setPaymentMethod(Integer.parseInt(orderForm.getPaymentMethod()));
		
		LocalDate nowDate=LocalDate.now();
		order.setOrderDate(Date.valueOf(nowDate));
		
		//配達日時、時間を合わせてTimestamp型に変換
		LocalDate localDate = orderForm.getDeliveryDate().toLocalDate();
		LocalDateTime localDateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(),
				localDate.getDayOfMonth(), orderForm.getDeliveryTime(), 0, 0, 0);
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timestamp);
    
		//決済方法によってステータスを変更
		if(orderForm.getPaymentMethod().equals("1")) {
			order.setStatus(1);
		}else if(orderForm.getPaymentMethod().equals("2")) {
			order.setStatus(2);
		}
		
		orderRepository.update(order);
	}

}
