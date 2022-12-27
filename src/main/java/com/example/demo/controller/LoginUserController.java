package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;
import com.example.demo.form.LoginUserForm;
import com.example.demo.service.CartService;
import com.example.demo.service.LoginUserService;

/**
 * ログイン画面の処理を行うコントローラークラス.
 * 
 * @author hongo
 *
 */
@Controller
@RequestMapping("/loginUser")
public class LoginUserController {
	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private CartService cartService;

	@Autowired
	private HttpSession session;

	/**
	 * ログイン画面を出力します.
	 * 
	 * @param form ログインユーザーフォーム
	 * @return ログイン画面
	 */
	@RequestMapping("/toLogin")
	public String toLogin(LoginUserForm form) {

		return "login";
	}

	/**
	 * ログインを行うメソッド.
	 * 
	 * @param form   ユーザーログイン情報フォーム
	 * @param result エラー情報を格納するオブジェクト
	 * @return ログイン前に表示していたページ
	 */
	@PostMapping("/login")
	public String login(Model model, LoginUserForm form, BindingResult result) {

		User user = loginUserService.login(form.getEmail(), form.getPassword());

		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレス、またはパスワードが間違っています。");
			return toLogin(form);
		}
		session.setAttribute("user", user);
		String userName = user.getName();
		session.setAttribute("userName", userName);
		
		Boolean isToOrderHistory = (Boolean)session.getAttribute("toOrderHistory");
		if(isToOrderHistory != null && isToOrderHistory.booleanValue()) {
			return "redirect:/orderHistory/";
		}
		
		
			Boolean isThroughOrderConfirmation = (Boolean) session.getAttribute("throughOrderConfirmation");
			if(isThroughOrderConfirmation != null && isThroughOrderConfirmation.booleanValue()) {
			Integer recentId = cartService.findIdAtRecentOrder();
			Integer recentUserId = cartService.findUserIdAtRecentOrder(recentId);
			Order dummyOrder = cartService.searchDummyOrder(recentUserId);
			Integer dummyOrderId = dummyOrder.getId();
			List<OrderItem> dummyOrderItemList = cartService.getOrderItemListByOrderId(dummyOrderId);
			
			Order trueOrder = cartService.searchDummyOrder(user.getId());
			Order transferdOrder = cartService.transferItemList(trueOrder,dummyOrderItemList);
			cartService.update(transferdOrder);
			session.removeAttribute("throughOrderConfirmation");
			return "redirect:/order/toOrder";
			}else {
			return "redirect:/";
		}

	}

}
