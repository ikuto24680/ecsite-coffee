package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ユーザー情報を登録する際のフォームクラス.
 * 
 * @author hongo
 *
 */
public class InsertUserForm {
	/** 名 */
	@NotBlank(message="名を入力してください")
	private String firstName;

	/** 姓 */
	@NotBlank(message="姓を入力してください")
	private String lastName;

	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message="メールアドレスの形式が不正です")
	private String email;

	/** パスワード */
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[.?/-])[a-zA-Z0-9.?/-]{8,16}$", message="パスワードはアルファベットの大文字・小文字・記号（.?/-）を含む  ８文字以上１６文字以内で設定してください")
	private String password;

	/** 確認用パスワード */
	@NotBlank(message="パスワードと確認用パスワードが不一致です")
	private String confimationPassword;

	/** 郵便番号 */
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$",message="郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;

	/** 住所 */
	@NotBlank(message="住所を入力してください")
	private String address;

	/** 電話番号 */
	@Pattern(regexp="^\\d{2,4}-\\d{2,4}-\\d{4}$",message="電話番号は-を含んで正しい入力をしてください")
	private String telephone;

	/** GetterとSetter */

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public String getConfimationPassword() {
		return confimationPassword;
	}

	public void setConfimationPassword(String confimationPassword) {
		this.confimationPassword = confimationPassword;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	
	/** toString */
	@Override
	public String toString() {
		return "InsertUserForm [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", confimationPassword=" + confimationPassword + ", zipcode=" + zipcode + ", address="
				+ address + ", telephone=" + telephone + "]";
	}

}
