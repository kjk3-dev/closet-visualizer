package com.example.closetvisualizer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.closetvisualizer.model.Clothes;
import com.example.closetvisualizer.util.DBConnection;

public class ClothesDao {
	public void insert(Clothes clothes) throws SQLException {
		String sql = "INSERT INTO clothes "
				+ "(name, category_id, color, scene, season, price, size, rating, memo, is_deleted) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, clothes.getName());
			stmt.setInt(2, clothes.getCategoryId());
			stmt.setString(3, clothes.getColor());
			stmt.setString(4, clothes.getScene());
			stmt.setString(5, clothes.getSeason());
			stmt.setObject(6, clothes.getPrice());
			stmt.setString(7, clothes.getSize());
			stmt.setObject(8, clothes.getRating());
			stmt.setString(9, clothes.getMemo());
			stmt.setBoolean(10, false);

			stmt.executeUpdate();
		}
	}

	public List<Clothes> findAll() throws SQLException {
		List<Clothes> clothesList = new ArrayList<>();
		String sql = "SELECT c.id, c.name, c.category_id, c.color, c.scene, c.season, "
				+ "c.price, c.size, c.rating, c.memo, c.is_deleted "
				+ "FROM clothes c "
				+ "JOIN categories cat ON c.category_id = cat.id "
				+ "WHERE c.is_deleted = false "
				+ "ORDER BY cat.display_order ASC, c.created_at ASC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Clothes clothes = new Clothes();
				clothes.setId(rs.getInt("id"));
				clothes.setName(rs.getString("name"));
				clothes.setCategoryId(rs.getInt("category_id"));
				clothes.setColor(rs.getString("color"));
				clothes.setScene(rs.getString("scene"));
				clothes.setSeason(rs.getString("season"));
				clothes.setPrice((Integer) rs.getObject("price"));
				clothes.setSize(rs.getString("size"));
				clothes.setRating((Integer) rs.getObject("rating"));
				clothes.setMemo(rs.getString("memo"));
				clothes.setDeleted(rs.getBoolean("is_deleted"));
				clothesList.add(clothes);
			}
		}

		return clothesList;
	}

	public void update(Clothes clothes) throws SQLException {
		String sql = "UPDATE clothes SET "
				+ "name = ?, category_id = ?, color = ?, scene = ?, season = ?, "
				+ "price = ?, size = ?, rating = ?, memo = ? "
				+ "WHERE id = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, clothes.getName());
			stmt.setInt(2, clothes.getCategoryId());
			stmt.setString(3, clothes.getColor());
			stmt.setString(4, clothes.getScene());
			stmt.setString(5, clothes.getSeason());
			stmt.setObject(6, clothes.getPrice());
			stmt.setString(7, clothes.getSize());
			stmt.setObject(8, clothes.getRating());
			stmt.setString(9, clothes.getMemo());
			stmt.setInt(10, clothes.getId());

			stmt.executeUpdate();
		}
	}

	public void moveToTrash(int id) throws SQLException {
		String sql = "UPDATE clothes SET is_deleted = true WHERE id = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	public void restore(int id) throws SQLException {
		String sql = "UPDATE clothes SET is_deleted = false WHERE id = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	public void deletePermanently(int id) throws SQLException {
		String sql = "DELETE FROM clothes WHERE id = ?";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	public List<Clothes> findTrashed() throws SQLException {
		List<Clothes> clothesList = new ArrayList<>();
		String sql = "SELECT c.id, c.name, c.category_id, c.color, c.scene, c.season, "
				+ "c.price, c.size, c.rating, c.memo, c.is_deleted "
				+ "FROM clothes c "
				+ "JOIN categories cat ON c.category_id = cat.id "
				+ "WHERE c.is_deleted = true "
				+ "ORDER BY c.updated_at ASC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Clothes clothes = new Clothes();
				clothes.setId(rs.getInt("id"));
				clothes.setName(rs.getString("name"));
				clothes.setCategoryId(rs.getInt("category_id"));
				clothes.setColor(rs.getString("color"));
				clothes.setScene(rs.getString("scene"));
				clothes.setSeason(rs.getString("season"));
				clothes.setPrice((Integer) rs.getObject("price"));
				clothes.setSize(rs.getString("size"));
				clothes.setRating((Integer) rs.getObject("rating"));
				clothes.setMemo(rs.getString("memo"));
				clothes.setDeleted(rs.getBoolean("is_deleted"));
				clothesList.add(clothes);
			}
		}

		return clothesList;
	}
}
