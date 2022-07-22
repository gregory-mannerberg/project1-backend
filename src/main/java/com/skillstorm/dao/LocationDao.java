package com.skillstorm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.configuration.InventoryDbCreds;
import com.skillstorm.model.Location;

public class LocationDao {
	
	public List<Location> findAll() {
		String sql = "SELECT * FROM location";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			List<Location> locations = new LinkedList<>();
			while(rs.next()) {
				locations.add(
						new Location(
								rs.getInt("id"),
								rs.getInt("row"),
								rs.getInt("block"),
								rs.getInt("shelf"),
								rs.getInt("warehouse_id")));
			}
			return locations;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Location findById(int id) {
		String sql = "SELECT * FROM location WHERE id = ?";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Location(
						rs.getInt("id"),
						rs.getInt("row"),
						rs.getInt("block"),
						rs.getInt("shelf"),
						rs.getInt("warehouse_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Location save(Location location) {
		String sql = "INSERT INTO location (`row`, block, shelf, warehouse_id) VALUES (?, ?, ?, ?)";
		try (Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, location.getRow());
			ps.setInt(2, location.getBlock());
			ps.setInt(3, location.getShelf());
			ps.setInt(4, location.getWarehouseId());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1);
					location.setId(key);
					return location;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update(Location location) {
		String sql = "UPDATE location SET `row` = ?, block = ?, shelf = ?, warehouse_id = ? WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, location.getRow());
			ps.setInt(2, location.getBlock());
			ps.setInt(3, location.getShelf());
			ps.setInt(4, location.getWarehouseId());
			ps.setInt(5, location.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM location WHERE id = ?";
		try(Connection conn = InventoryDbCreds.getInstance().getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMany(int[] ids) {
		String sql = "DELETE FROM location WHERE id = ?";
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
