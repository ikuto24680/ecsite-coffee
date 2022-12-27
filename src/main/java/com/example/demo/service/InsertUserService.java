package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

/**
 * ユーザー情報を登録する操作を行うサービスクラス.
 * 
 * @author hongo
 *
 */
@Service
@Transactional
public class InsertUserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザー情報を登録するメソッド.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {

		// パスワードをハッシュ化
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.insert(user);
	}

	/**
	 * メールアドレスからユーザー情報を検索するメソッド. *
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User searchByEmail(String email) {
		return userRepository.findByEmail(email);

	}
}
