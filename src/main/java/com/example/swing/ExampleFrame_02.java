package com.example.swing;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class ExampleFrame_02 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new ExampleFrame_02().setVisible(true);
	}
	
	public ExampleFrame_02() {
		super();
		setTitle("选项卡面板");
		setBounds(500, 500, 300, 572);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.addTab("选项卡一", null);
		jTabbedPane.addTab("选项卡而", null);
		jTabbedPane.addTab("选项卡三", null);
		
		getContentPane().add(jTabbedPane);
	}
	
}
