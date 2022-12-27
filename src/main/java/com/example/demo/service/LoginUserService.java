package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

/**
 * ログインの処理を行うサービスクラス.
 * 
 * @author hongo
 *
 */
@Service
public class LoginUserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ログインの機能を持つメソッド.
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return user メールアドレスとパスワードの検索結果
	 */
	public User login(String email, String password) {
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			Boolean passwordCheck=passwordEncoder.matches(password, user.getPassword());
			if(passwordCheck==false) {
				user=null;
			}
		}
		return user;
	}

}
