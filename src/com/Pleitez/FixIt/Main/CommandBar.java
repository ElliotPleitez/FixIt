package com.Pleitez.FixIt.Main;

import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CommandBar extends JPanel{
	private static final long serialVersionUID = 1119387216635154704L;

	public CommandBar(){
		initialize();
	}
	
	private void initialize(){
		this.setBackground(new Color(100, 100, 100, 100));
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		editorScrollPane.setPreferredSize(this.getSize());
		
		this.add(editorScrollPane);
	}
}
