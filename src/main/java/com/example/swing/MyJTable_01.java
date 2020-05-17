package com.example.swing;

import java.awt.*;
import javax.swing.*;

public class MyJTable_01 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		MyJTable_01 myJTable = new MyJTable_01();
		myJTable.setVisible(true);
	}

	public MyJTable_01() {
		super();
		setTitle("创建可以滚动的表格");
		setBounds(100, 100, 240, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		String[] columnNames = { "A", "B" };
		String[][] tableValues = { { "A1", "B1" }, { "A2", "B2" }, { "A3", "B3" }, { "A4", "B4" }, { "A5", "B5" },
				{ "A6", "B6" }, { "A7", "B7" } };
		JTable jTable = new JTable(tableValues, columnNames);
		JScrollPane jScrollPane = new JScrollPane(jTable);
		getContentPane().add(jScrollPane, BorderLayout.CENTER);
	}
}
