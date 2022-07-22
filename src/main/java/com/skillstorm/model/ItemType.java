package com.skillstorm.model;

import java.time.LocalDate;

public class ItemType {
	
	private int id;
	private String name;
	private String manufacturer;
	private String color;
	private String unit;
	private LocalDate dateDiscontinued;
	
	public ItemType() {
		super();
	}

	public ItemType(String name, String manufacturer, String color, String unit, LocalDate dateDiscontinued) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.color = color;
		this.unit = unit;
		this.dateDiscontinued = dateDiscontinued;
	}

	public ItemType(int id, String name, String manufacturer, String color, String unit, LocalDate dateDiscontinued) {
		super();
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.color = color;
		this.unit = unit;
		this.dateDiscontinued = dateDiscontinued;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public LocalDate getDateDiscontinued() {
		return dateDiscontinued;
	}

	public void setDateDiscontinued(LocalDate dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}

	@Override
	public String toString() {
		return "ItemType [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", color=" + color + ", unit="
				+ unit + ", dateDiscontinued=" + dateDiscontinued + "]";
	}

}
