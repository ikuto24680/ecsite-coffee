package com.example.demo.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.form.OrderForm;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;

/**
 * 注文情報を操作するコントローラ.
 * 
 * @author inagakisaia
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 注文画面を表示します.
	 * 
	 * @return 注文画面
	 */
	@GetMapping("/toOrder")
	public String toOrder(OrderForm orderForm, Model model) {

		User user = null;
		user=(User)(session.getAttribute("user"));
		Integer userId = 0;
		try {
		userId = user.getId();
		}catch(Exception e) {
			session.setAttribute("throughOrderConfirmation", true);
			return "redirect:/loginUser/toLogin";
		}

		Order order = cartService.showCart(userId);
		model.addAttribute("order",order);
		
		//htmlのヘッダーのカードにアイテム数を表示させるためにセットしてます
		int orderItemCount =  order.getOrderItemList().size();
		session.setAttribute("orderItemCount", orderItemCount);
		
		return "order_confirm";
	}

	/**
	 * 注文完了画面を表示します.
	 * 
	 * @return 注文完了画面
	 */
	@GetMapping("/toFinished")
	public String toFinished() {
		//注文完了画面表示時にヘッダーの上のカートのアイコンの個数をリセットしてます
		session.removeAttribute("orderItemCount");
		return "order_finished";
	}

	/**
	 * お届け先情報が自動入力された注文画面を表示します.
	 * 
	 * @param orderForm 注文情報を受け取るフォーム
	 * @return 注文画面
	 */
	@PostMapping("/autoEntry")
	public String autoEntry(OrderForm orderForm, Model model) {

		model.addAttribute("autoEntry", "autoEntry");

		System.out.println("autoEntry");
		return toOrder(orderForm, model);
	}

	/**
	 * 注文をします.
	 * 
	 * @param orderForm 注文情報を受け取るフォーム
	 * @return 注文完了画面にリダイレクト
	 */
	@PostMapping("/")
	public String order(@Validated OrderForm orderForm, BindingResult result, Model model) {
		if (orderForm.getDeliveryDate() == null) {
			model.addAttribute("deliveryDateError", "日付を入力してください");
			return toOrder(orderForm, model);
		}
		LocalDateTime nowDateTime=LocalDateTime.now();
		nowDateTime=nowDateTime.plusHours(3);
		LocalDateTime orderDateTime=orderForm.getDeliveryDate().toLocalDate().atStartOfDay();
		orderDateTime.plusHours(orderForm.getDeliveryTime());
		if(nowDateTime.isAfter(orderDateTime)) {
			model.addAttribute("deliveryDateError", "今から3時間後の日時をご入力ください");
			return toOrder(orderForm, model);
		}

		if (result.hasErrors()) {
			return toOrder(orderForm, model);
		}

		orderService.order(orderForm);
		
		return "redirect:/order/toFinished";
	}
}
