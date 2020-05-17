package com.example.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

public class ExampleFrame_01 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new ExampleFrame_01().setVisible(true);
	}

	public ExampleFrame_01() {
		super();
		setTitle("分割面板");
		setBounds(500, 500, 500, 375);
		setDefaultCloseOperation(EXIT_ON_CLOSE);     
		JSplitPane hjSplitPane = new JSplitPane();
		hjSplitPane.setDividerLocation(40);
		getContentPane().add(hjSplitPane,BorderLayout.CENTER);
		
		hjSplitPane.setLeftComponent(new JLabel("000001"));
		
		JSplitPane vjSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vjSplitPane.setDividerLocation(30);
		vjSplitPane.setDividerSize(8);
		vjSplitPane.setOneTouchExpandable(true);
		hjSplitPane.setRightComponent(vjSplitPane);
		vjSplitPane.setLeftComponent(new JLabel("000002"));
		vjSplitPane.setRightComponent(new JLabel("000003"));
	}
}
