package com.skillstorm.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.configuration.InventoryDbCreds;
import com.skillstorm.model.Item;

public class ItemDao {
	
	public List<Item> findAll() {
		String sql = "SELECT * FROM item";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			List<Item> items = new LinkedList<>();
			while(rs.next()) {
				items.add(
						new Item(
								rs.getInt("id"),
								rs.getInt("amount"),
								(rs.getDate("date_purchased") != null) ? rs.getDate("date_purchased").toLocalDate() : null,
								rs.getFloat("purchase_price"),
								rs.getInt("item_type_id"),
								rs.getInt("location_id")));
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Item findById(int id) {
		String sql = "SELECT * FROM item WHERE id = ?";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Item(
						rs.getInt("id"),
						rs.getInt("amount"),
						(rs.getDate("date_purchased") != null) ? rs.getDate("date_purchased").toLocalDate() : null,
						rs.getFloat("purchase_price"),
						rs.getInt("item_type_id"),
						rs.getInt("location_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Item save(Item item) {
		String sql = "INSERT INTO item (amount, date_purchased, purchase_price, item_type_id, location_id) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, item.getAmount());
			ps.setDate(2, (item.getDatePurchased() == null) ? null : Date.valueOf(item.getDatePurchased()));
			ps.setFloat(3, item.getPurchasePrice());
			ps.setInt(4, item.getItemTypeId());
			if (item.getLocationId() <= 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, item.getLocationId());
			}					
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					item.setId(key);
					return item;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update(Item item) {
		String sql = "UPDATE item SET amount = ?, date_purchased = ?, purchase_price = ?, item_type_id = ?, location_id = ? WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, item.getAmount());
			ps.setDate(2, (item.getDatePurchased() == null) ? null : Date.valueOf(item.getDatePurchased()));
			ps.setFloat(3, item.getPurchasePrice());
			ps.setInt(4, item.getItemTypeId());
			if (item.getLocationId() <= 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, item.getLocationId());
			}	
			ps.setInt(6, item.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM item WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMany(int[] ids) {
		String sql = "DELETE FROM item WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			for (int id : ids) {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
