package com.example.demo.domain;

import java.util.List;

public class OrderItem {
	/** id */
	private Integer id;
	/** 商品id */
	private Integer itemId;
	/** 注文id */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** トッピングリスト */
	private List<OrderTopping> orderToppingList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

	public int getSubTotal() {

		
		int subTotal = 0;
		int oneTotal = 0;
		List<OrderTopping> orderToppingList = null;
		if (this.getSize().compareTo('M') == 0) {
			oneTotal += this.getItem().getPriceM();
			orderToppingList = this.getOrderToppingList();
			for (OrderTopping orderTopping : orderToppingList) {
				oneTotal += orderTopping.getTopping().getPriceM();
			}
			subTotal += oneTotal*this.quantity;
		} else if (this.getSize().compareTo('L') == 0) {
			oneTotal += this.getItem().getPriceL();
			orderToppingList = this.getOrderToppingList();
			for (OrderTopping orderTopping : orderToppingList) {
				oneTotal += orderTopping.getTopping().getPriceL();
			}
				subTotal += oneTotal*this.quantity;
		}
		return subTotal;

	}
}
