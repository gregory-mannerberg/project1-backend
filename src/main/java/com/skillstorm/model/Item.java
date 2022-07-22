package com.skillstorm.model;

import java.time.LocalDate;

public class Item {

	private int id;
	private int amount;
	private LocalDate datePurchased;
	private float purchasePrice;
	private int itemTypeId;
	private int locationId;
	
	public Item() {
		super();
	}

	public Item(int amount, LocalDate datePurchased, float purchasePrice, int itemTypeId, int locationId) {
		super();
		this.amount = amount;
		this.datePurchased = datePurchased;
		this.purchasePrice = purchasePrice;
		this.itemTypeId = itemTypeId;
		this.locationId = locationId;
	}

	public Item(int id, int amount, LocalDate datePurchased, float purchasePrice, int itemTypeId, int locationId) {
		super();
		this.id = id;
		this.amount = amount;
		this.datePurchased = datePurchased;
		this.purchasePrice = purchasePrice;
		this.itemTypeId = itemTypeId;
		this.locationId = locationId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDate getDatePurchased() {
		return datePurchased;
	}

	public void setDatePurchased(LocalDate datePurchased) {
		this.datePurchased = datePurchased;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", amount=" + amount + ", datePurchased=" + datePurchased + ", purchasePrice="
				+ purchasePrice + ", itemTypeId=" + itemTypeId + ", locationId=" + locationId + "]";
	}
	
}
