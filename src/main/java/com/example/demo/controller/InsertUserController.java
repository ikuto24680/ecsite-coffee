package com.example.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.form.InsertUserForm;
import com.example.demo.service.InsertUserService;

/**
 * ユーザー登録画面の処理を行うコントローラークラス.
 * 
 * @author hongo
 *
 */
@Controller
@RequestMapping("/insertUser")
public class InsertUserController {
	@Autowired
	InsertUserService insertUserService;

	/**
	 * ユーザー情報登録画面に遷移する.
	 * 
	 * @param form ユーザー情報を登録する際のフォーム.
	 * @return ユーザー情報登録画面
	 */
	@GetMapping("/toInsert")
	public String toInsert(InsertUserForm form) {
		return "register_user";
	}

	/**
	 * ユーザー情報の登録を行う.
	 * 
	 * @param form ユーザー情報を格納するフォーム
	 * @return ログイン画面
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertUserForm form, BindingResult result) {
		// 入力したメールアドレスが既に登録されていた場合、エラーメッセージを返す
		if (insertUserService.searchByEmail(form.getEmail()) != null) {
			result.rejectValue("email", null, "　そのメールアドレスはすでに使われています");
		}

		// 入力したパスワードと確認用パスワードが一致しない場合、エラーメッセージを返す
		if (!form.getPassword().equals(form.getConfimationPassword())) {
			result.rejectValue("confimationPassword", null, "");

		}

		if (result.hasErrors()) {
			return toInsert(form);
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setName(form.getLastName() + form.getFirstName());
		insertUserService.insert(user);

		return "redirect:/insertUser/login";
	}

	/**
	 * ログイン画面に遷移する.
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String tologin() {
		return "login";
	}

}
