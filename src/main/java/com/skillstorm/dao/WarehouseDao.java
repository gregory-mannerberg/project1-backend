package com.skillstorm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.configuration.InventoryDbCreds;
import com.skillstorm.model.Warehouse;

public class WarehouseDao {
	
	public List<Warehouse> findAll() {
		String sql = "SELECT * FROM warehouse";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			List<Warehouse> warehouses = new LinkedList<>();
			while(rs.next()) {
				warehouses.add(
						new Warehouse(
								rs.getInt("id"),
								rs.getString("address"),
								rs.getString("city"),
								rs.getString("state"),
								rs.getString("country"),
								rs.getString("postal_code")));
			}
			return warehouses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Warehouse findById(int id) {
		String sql = "SELECT * FROM warehouse WHERE id = ?";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Warehouse(
						rs.getInt("id"),
						rs.getString("address"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("country"),
						rs.getString("postal_code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Warehouse save(Warehouse warehouse) {
		String sql = "INSERT INTO warehouse (address, city, state, country, postal_code) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, warehouse.getAddress());
			ps.setString(2, warehouse.getCity());
			ps.setString(3, warehouse.getState());
			ps.setString(4, warehouse.getCountry());
			ps.setString(5, warehouse.getPostalCode());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					warehouse.setId(key);
					return warehouse;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update(Warehouse warehouse) {
		String sql = "UPDATE warehouse SET address = ?, city = ?, state = ?, country = ?, postal_code = ? WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, warehouse.getAddress());
			ps.setString(2, warehouse.getCity());
			ps.setString(3, warehouse.getState());
			ps.setString(4, warehouse.getCountry());
			ps.setString(5, warehouse.getPostalCode());
			ps.setInt(6, warehouse.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM warehouse WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMany(int[] ids) {
		String sql = "DELETE FROM warehouse WHERE id = ?";
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
