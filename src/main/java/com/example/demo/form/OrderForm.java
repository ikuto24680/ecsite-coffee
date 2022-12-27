package com.example.demo.form;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 注文情報を受け取るフォーム.
 * 
 * @author inagakisaia
 *
 */
public class OrderForm {

	/** ID */
	private String id;
	/** 注文日 */
	private Date orderDate;
	/** 宛先氏名 */
	@NotBlank(message = "名前を入力してください")
	private String destinationName;
	/** 宛先Eメール */
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message="メールアドレスの形式が不正です")
	private String destinationEmail;
	/** 宛先郵便番号 */
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$",message="郵便番号はXXX-XXXXの形式で入力してください")
	private String destinationZipcode;
	/** 宛先住所 */
	@NotBlank(message="住所を入力してください")
	private String destinationAddress;
	/** 宛先TEL */
	@Pattern(regexp="^\\d{2,4}-\\d{2,4}-\\d{4}$",message="電話番号は-を含んで正しい入力をしてください")
	private String destinationTel;
	/** 配達日付 */
	private Date deliveryDate;
	/** 配達時間 */
	private Integer deliveryTime;
	/** 支払い方法 */
	private String paymentMethod;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "OrderForm [id=" + id + ", orderDate=" + orderDate + ", destinationName=" + destinationName
				+ ", destinationEmail=" + destinationEmail + ", destinationZipcode=" + destinationZipcode
				+ ", destinationAddress=" + destinationAddress + ", destinationTel=" + destinationTel
				+ ", deliveryDate=" + deliveryDate + ", deliveryTime=" + deliveryTime + ", paymentMethod="
				+ paymentMethod + "]";
	}

}
