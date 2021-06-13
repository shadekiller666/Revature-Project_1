package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.revature.models.Editor;
import com.revature.utils.JDBCConnection;

public class EditorRepo implements GenericRepo<Editor> {
	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public Editor add(Editor e) {
		String sql = "insert into editors values(default, ?, ?, ?) returning *;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getFirstName());
			ps.setString(2, e.getLastName());
			ps.setString(3, e.getJobTitle());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				e.setId(rs.getInt("id"));
				return e;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Editor getById(Integer id) {
		String sql = "select * from editors where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return this.make(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Map<Integer, Editor> getAll() {
		String sql = "select * from editors;";
		try {
			Map<Integer, Editor> map = new HashMap<Integer, Editor>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Editor e = this.make(rs);
				map.put(e.getId(), e);
			}
			
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Editor e) {
//		String sql = "";
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		}
		
		return false;
	}

	@Override
	public boolean delete(Editor e) {
		String sql = "delete from editors where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, e.getId());
			return ps.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Editor make(ResultSet rs) throws SQLException {
		Editor e = new Editor();
		e.setId(rs.getInt("id"));
		e.setFirstName(rs.getString("first_name"));
		e.setLast_name(rs.getString("last_name"));
		e.setJobTitle(rs.getString("job_title"));
		return e;
	}
}
