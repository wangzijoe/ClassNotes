package com.example.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class MyJTable_04 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new MyJTable_04().setVisible(true);
	}

	public MyJTable_04() {
		super();
		this.setTitle("自定义表格");
		this.setBounds(500, 500, 500, 375);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JScrollPane jScrollPane = new JScrollPane();
		this.getContentPane().add(jScrollPane, BorderLayout.CENTER);
		String[] columnNames = { "A", "B", "C", "D", "E", "F", "G" };
		Vector<String> columnNameV = new Vector<>();

		for (int column = 0; column < columnNames.length; column++) {
			columnNameV.add(columnNames[column]);
		}

		Vector<Vector<String>> tableValueV = new Vector<>();
		for (int row = 1; row < 21; row++) {
			Vector<String> rowV = new Vector<>();
			for (int column = 0; column < columnNames.length; column++) {
				rowV.add(columnNames[column] + row);
			}
			tableValueV.add(rowV);
		}
		
		JTable jTable = new JTable(tableValueV, columnNameV);
		// 关闭表格列的自动调整功能
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// 选择模式为单选
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 被选择行的背景色为黄色
		jTable.setSelectionBackground(Color.YELLOW);
		// 被选中行的前景色（文字颜色）红色
		jTable.setSelectionForeground(Color.RED);
		// 行高30像素
		jTable.setRowHeight(30);
		jScrollPane.setViewportView(jTable);
		
	}
}
