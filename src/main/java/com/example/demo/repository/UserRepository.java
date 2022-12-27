package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

/**
 * usersテーブルを操作するリポジトリクラス.
 * 
 * @author hongo
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * UserのLOW_MAPPERを定数として定義.
	 */
	private static final RowMapper<User> USER_LOW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));

		return user;
	};

	/**
	 * ユーザー情報を登録する.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		String sql = "INSERT INTO users (name,email,password,zipcode,address,telephone) VALUES(:name,:email,:password,:zipcode,:address,:telephone)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	/**
	 * メールアドレスからユーザー情報を検索する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM users WHERE email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);

		List<User> userList = template.query(sql, param, USER_LOW_MAPPER);

		if (userList.size() == 0) { // 検索結果が０だった場合「null」を返す
			return null;
		}

		return userList.get(0);
	}

	/**
	 * メールアドレスとパスワードを探すメソッド
	 * @param email　メールアドレス
	 * @param password　パスワード
	 * @return　メールアドレスとパスワードを探してきた検索結果
	 */
	public User findByEmailAndPassword(String email, String password) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM users WHERE email=:email AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);

		List<User> userList = template.query(sql, param, USER_LOW_MAPPER);

		if (userList.size() == 0) { // 検索結果が０だった場合「null」を返す
			return null;
		}
		return userList.get(0);
	}
}
