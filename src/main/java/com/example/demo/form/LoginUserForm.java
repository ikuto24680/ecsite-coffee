package com.example.demo.form;

/**
 * ログイン時に入力した情報を格納するフォーム.
 * 
 * @author hongo
 *
 */
public class LoginUserForm {
	/** メールアドレス */
	private String email;
	/** パスワード */
	private String password;

	/** GetterとSetter */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/** toString */
	@Override
	public String toString() {
		return "LoginUserForm [email=" + email + ", password=" + password + "]";
	}
}
