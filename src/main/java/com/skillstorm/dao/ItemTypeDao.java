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
import com.skillstorm.model.ItemType;

public class ItemTypeDao {
	
	public List<ItemType> findAll() {
		String sql = "SELECT * FROM item_type";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			List<ItemType> itemTypes = new LinkedList<>();
			while(rs.next()) {
				itemTypes.add(
						new ItemType(
								rs.getInt("id"),
								rs.getString("name"),
								rs.getString("manufacturer"),
								rs.getString("color"),
								rs.getString("unit"),
								(rs.getDate("date_discontinued") != null) ? rs.getDate("date_discontinued").toLocalDate() : null));
			}
			return itemTypes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ItemType findById(int id) {
		String sql = "SELECT * FROM item_type WHERE id = ?";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new ItemType(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("manufacturer"),
						rs.getString("color"),
						rs.getString("unit"),
						(rs.getDate("date_discontinued") != null) ? rs.getDate("date_discontinued").toLocalDate() : null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ItemType save(ItemType itemType) {
		String sql = "INSERT INTO item_type (name, manufacturer, color, unit, date_discontinued) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, itemType.getName());
			ps.setString(2, itemType.getManufacturer());
			ps.setString(3, itemType.getColor());
			ps.setString(4, itemType.getUnit());
			ps.setDate(5, (itemType.getDateDiscontinued() == null) ? null : Date.valueOf(itemType.getDateDiscontinued()));
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					itemType.setId(key);
					return itemType;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update(ItemType itemType) {
		String sql = "UPDATE item_type SET name = ?, manufacturer = ?, color = ?, unit = ?, date_discontinued = ? WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itemType.getName());
			ps.setString(2, itemType.getManufacturer());
			ps.setString(3, itemType.getColor());
			ps.setString(4, itemType.getUnit());
			ps.setDate(5, (itemType.getDateDiscontinued() == null) ? null : Date.valueOf(itemType.getDateDiscontinued()));
			ps.setInt(6, itemType.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM item_type WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMany(int[] ids) {
		String sql = "DELETE FROM item_type WHERE id = ?";
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
