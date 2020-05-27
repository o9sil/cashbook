package com.gdu.cashbook.vo;

public class MonthPrice {
	private int month;
	private int price;
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "MonthPrice [month=" + month + ", price=" + price + "]";
	}
	
}
