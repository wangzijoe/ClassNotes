package com.example.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

public class MyJTable_02 extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		MyJTable_02 myJTable_02 = new MyJTable_02();
		myJTable_02.setVisible(true);
	}

	public MyJTable_02() {
		super();
		setTitle("不可滚动表格");
		setBounds(500, 500, 240, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Vector<String> columnNameV = new Vector<>();
		columnNameV.add("A");
		columnNameV.add("B");
		Vector<Vector<String>> tableValueV = new Vector<>();
		for (int row = 1; row < 6; row++) {
			Vector<String> rowV = new Vector<>();
			rowV.add("A" + row);
			rowV.add("B" + row);
			tableValueV.add(rowV);
		}
		JTable jTable = new JTable(tableValueV, columnNameV);
		jTable.setSelectionMode(0);
		jTable.setSelectionBackground(new Color(0, 0, 0));
		getContentPane().add(jTable,BorderLayout.CENTER);
		JTableHeader tableHeader = jTable.getTableHeader();
		getContentPane().add(tableHeader,BorderLayout.NORTH);
	}

}
