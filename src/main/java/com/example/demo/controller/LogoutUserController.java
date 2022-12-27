package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログアウトを行うコントローラークラス.
 * 
 * @author hongo
 *
 */
@Controller
@RequestMapping("/logoutUser")
public class LogoutUserController {

	@Autowired
	private HttpSession session;

	/**
	 * ログアウトの処理を行うメソッド.
	 * 
	 * @return 商品一覧画面
	 */
	@GetMapping(value = "/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/loginUser/toLogin";
	}

}
