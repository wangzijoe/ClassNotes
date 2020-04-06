package com.example.jdbc;

import java.sql.ResultSet;

public interface RowMapper<T> {

	public abstract T mapRow(ResultSet resultset);
}
