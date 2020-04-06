package com.example.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import lombok.Data;

public class JdbcTest {

	@Data
	class Item {
		private String id;
		private String title;
		private Long price;
		private String image;
	}

	@Test
	public void test() {
		String driverName = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://101.37.148.209:3306/notes";
		String user = "root";
		String pasword = "Wolf9527-admin";

		DBInfo dbInfo = new DBInfo(driverName, url, user, pasword);
		JdbcHelper jdbcHelper = new JdbcHelper(dbInfo);
		String sql = "select * from item";
		List<Item> result = jdbcHelper.query(sql, new RowMapper<Item>() {

			@Override
			public Item mapRow(ResultSet rs) {
				Item item = new Item();
				try {
					item.setId(rs.getString("id"));
					item.setTitle(rs.getString("title"));
					item.setPrice(rs.getLong("price"));
					item.setImage(rs.getString("image"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return item;
			}
		}, new Object[] {});
		System.err.println(JSON.toJSONString(result, true));
	}
}
