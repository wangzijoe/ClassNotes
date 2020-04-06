package com.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper {

	private DBInfo info;

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	public JdbcHelper(DBInfo info) {
		this.info = info;
	}

	private void registerDriver(String driverName) {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
		}
	}

	public Connection getConnection() {
		Connection con = null;
		this.registerDriver(info.getDriverName());
		try {
			con = DriverManager.getConnection(info.getUrl(), info.getUser(), info.getPasword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void close(ResultSet rs, Statement st, Connection con) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int update(String sql, Object obj[]) {
		int temp = 0;
		con = this.getConnection();
		try {
			ps = con.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i + 1, obj[i]);
			}
			temp = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(null, ps, con);
		}
		return temp;
	}

	public <E> List<E> query(String sql, RowMapper<E> rm, Object obj[]) {
		List<E> list = new ArrayList<E>();
		con = this.getConnection();
		try {
			ps = con.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i + 1, obj[i]);
			}
			E e;
			for (rs = ps.executeQuery(); rs.next(); list.add(e)) {
				e = rm.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, ps, con);
		}
		return list;
	}

}
