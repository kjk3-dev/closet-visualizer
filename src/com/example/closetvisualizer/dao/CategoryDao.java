package com.example.closetvisualizer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.closetvisualizer.model.Category;
import com.example.closetvisualizer.util.DBConnection;

public class CategoryDao {
	public List<Category> findAll() throws SQLException {
		List<Category> categories = new ArrayList<>();
		String sql = "SELECT id, name, display_order FROM categories ORDER BY display_order ASC";

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				category.setDisplayOrder(rs.getInt("display_order"));
				categories.add(category);
			}
		}

		return categories;
	}
}
