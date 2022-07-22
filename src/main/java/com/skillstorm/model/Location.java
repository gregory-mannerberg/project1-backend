package com.skillstorm.model;

public class Location {
	
	private int id;
	private int row;
	private int block;
	private int shelf;
	private int warehouseId;
	
	public Location() {
		super();
	}

	public Location(int row, int block, int shelf, int warehouseId) {
		super();
		this.row = row;
		this.block = block;
		this.shelf = shelf;
		this.warehouseId = warehouseId;
	}

	public Location(int id, int row, int block, int shelf, int warehouseId) {
		super();
		this.id = id;
		this.row = row;
		this.block = block;
		this.shelf = shelf;
		this.warehouseId = warehouseId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getShelf() {
		return shelf;
	}

	public void setShelf(int shelf) {
		this.shelf = shelf;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", row=" + row + ", block=" + block + ", shelf=" + shelf + ", warehouseId="
				+ warehouseId + "]";
	}

}
