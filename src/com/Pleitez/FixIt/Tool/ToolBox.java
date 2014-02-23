package com.Pleitez.FixIt.Tool;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class ToolBox extends FixItTool {
	private static final long serialVersionUID = -7611112058036601146L;
	
	//<//Event Call
	public interface ToolBoxAction {
		public void startTool(Tool tool);
	}
	private List<ToolBoxAction> toolBoxListenerList = new ArrayList<ToolBoxAction>();
	public void addToolBoxListener(ToolBoxAction toAdd){
		if(toolBoxListenerList.size() > 1){
			System.out.println("FixIt should be the only item in the ToolBox listener list");
		}
		else{
			toolBoxListenerList.add(toAdd);
		}
	}
	
	private void startToolBoxTool(Tool tool){
		for(ToolBoxAction listener : toolBoxListenerList){
			listener.startTool(tool);
		}
	}
	//>//Event Call
	
	public ToolBox(){
		super.toolName = Tool.ToolBox;
		this.setLayout(new GridBagLayout());
		
		initialize();
	}
	
	private void initialize(){
		GridBagConstraints gbc = new GridBagConstraints();
		JButton fileNodeRename = new JButton();
		fileNodeRename.setText("File Rename");
		fileNodeRename.addMouseListener(
			new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					startToolBoxTool(Tool.FileNodeRename);
				}
			}
		);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(fileNodeRename, gbc);
		
		JButton autoCADBackup = new JButton();
		autoCADBackup.setText("AutoCAD Backup");
		autoCADBackup.addMouseListener(
			new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					startToolBoxTool(Tool.AutoCADBackup);
				}
			}
		);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(autoCADBackup, gbc);
	}
}
