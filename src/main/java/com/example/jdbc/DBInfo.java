package com.example.jdbc;

import lombok.Data;

@Data
public class DBInfo {
	
	private String driverName;
	private String url;
	private String user;
	private String pasword;

	public DBInfo(String driverName, String url, String user, String pasword) {
		super();
		this.driverName = driverName;
		this.url = url;
		this.user = user;
		this.pasword = pasword;
	}
}
