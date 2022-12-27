package com.example.demo.form;

import java.util.List;

/**
 * Cart登録時のForm
 * @author kaneko
 *
 */
public class CartForm {

	/** アイテムID　*/
	private Integer ItemId;
	
	/** サイズ　*/
	private char size;
	
	/**　量　*/
	private Integer quantity;
	
	/** トッピングリスト　*/
	private List<Integer> toppingList;

	@Override
	public String toString() {
		return "CartForm [ItemId=" + ItemId + ", size=" + size + ", quantity=" + quantity + ", toppingList="
				+ toppingList + "]";
	}

	public Integer getItemId() {
		return ItemId;
	}

	public void setItemId(Integer itemId) {
		ItemId = itemId;
	}

	public char getSize() {
		return size;
	}

	public void setSize(char size) {
		this.size = size;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<Integer> getToppingList() {
		return toppingList;
	}

	public void setToppingList(List<Integer> toppingList) {
		this.toppingList = toppingList;
	}
}
