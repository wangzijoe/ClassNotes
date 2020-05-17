package com.example.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;

public class MyJTable_03 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		MyJTable_03 myJTable_03 = new MyJTable_03();
		myJTable_03.setVisible(true);
	}

	public MyJTable_03() {
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
		jTable.setCellEditor(new TableCellEditor() {
			
			@Override
			public boolean stopCellEditing() {
				return false;
			}
			
			@Override
			public boolean shouldSelectCell(EventObject anEvent) {
				return false;
			}
			
			@Override
			public void removeCellEditorListener(CellEditorListener l) {

			}
			
			@Override
			public boolean isCellEditable(EventObject anEvent) {
				return false;
			}
			
			@Override
			public Object getCellEditorValue() {
				return null;
			}
			
			@Override
			public void cancelCellEditing() {

			}
			
			@Override
			public void addCellEditorListener(CellEditorListener l) {

				
			}
			
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

				return null;
			}
		});
		getContentPane().add(jTable,BorderLayout.CENTER);
		JTableHeader tableHeader = jTable.getTableHeader();
		getContentPane().add(tableHeader,BorderLayout.NORTH);
	}
}
