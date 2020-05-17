package com.example.swing;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class ExampleFrame_03 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
	}
	
	public ExampleFrame_03() {
		super();
		setTitle("选项卡面板");
		setBounds(500, 500, 300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JTabbedPane jTabbedPane = new JTabbedPane();
	}
}
