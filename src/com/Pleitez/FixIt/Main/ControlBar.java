package com.Pleitez.FixIt.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.Pleitez.FixIt.Tool.Tool;

public class ControlBar extends JPanel {
	private static final long serialVersionUID = 8475856269170176415L;
	private JComboBox<String> comboBox;
	DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();

	//{//Events Calls
	public interface ControlBarAction {
		public void changeTool(Tool tool);
	}
	private List<ControlBarAction> controlBarListenerList = new ArrayList<ControlBarAction>();
	public void addListener(ControlBarAction toAdd){
		controlBarListenerList.add(toAdd);
	}
	
	private void changeControlBarTool(Tool tool){
		for(ControlBarAction listener : controlBarListenerList){
			listener.changeTool(tool);
		}
	}
	//}//Event Calls

	public ControlBar(){
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(100, 100, 100, 100));
		
		initialize();
	}

	private void initialize() {
		JPanel panelLeft = new JPanel(new FlowLayout());
		panelLeft.setBackground(null);
			JButton toolboxButton = new JButton();
			toolboxButton.setToolTipText("Click here to open other tools");
			toolboxButton.setIcon(new ImageIcon(Class.class.getResource("/icon/toolbox.png")));
			toolboxButton.setPreferredSize(new Dimension(18, 18));
		panelLeft.add(toolboxButton);
		
		JPanel panelCenter = new JPanel(new FlowLayout());
			panelCenter.setBackground(null);
			comboBox = new JComboBox<String>();
			comboBox.setModel(comboBoxModel);
			comboBox.setToolTipText("Click here to change between tools");
			comboBox.setPreferredSize(new Dimension(150, 18));
			comboBox.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
//					System.out.println("ControlBar.initialize item changed.");
					changeControlBarTool(
						Tool.getTool(
							comboBox.getSelectedItem().toString()
						)
					);
				}
			});
		panelCenter.add(comboBox);

		JPanel panelRight = new JPanel(new FlowLayout());
			panelRight.setBackground(null);
			JButton aboutButton = new JButton();
			aboutButton.setToolTipText("Click here to view information about this utility");
			aboutButton.setIcon(new ImageIcon(Class.class.getResource("/icon/about.png")));
			aboutButton.setPreferredSize(new Dimension(18, 18));
		panelRight.add(aboutButton);
		JButton settingsButton = new JButton();
			settingsButton.setToolTipText("Click here to change utility settings");
			settingsButton.setIcon(new ImageIcon(Class.class.getResource("/icon/settings.png")));
			settingsButton.setPreferredSize(new Dimension(18, 18));
		panelRight.add(settingsButton);
		
		this.add(panelLeft, BorderLayout.LINE_START);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelRight, BorderLayout.LINE_END);
	}

	public void addTool(Tool tool){
		//If tool is already added, simply select it, otherwise add. Method accessed by FixIt
		if(comboBoxModel.getSize() == 0){
			comboBoxModel.addElement(tool.toolName());
		}
		else{
			boolean addElement = Boolean.TRUE;
			for(int i = 0; i < comboBoxModel.getSize(); i++){
				if(Tool.getTool(comboBoxModel.getElementAt(i)) == tool){
					addElement = Boolean.FALSE;
					break;
				}
			}
			if(addElement){
				comboBoxModel.addElement(tool.toolName());
			}
			comboBox.setSelectedItem(tool.toolName());;
		}
	}
	
	public void removeTool(Tool tool){
		comboBoxModel.removeElement(tool.toolName());
	}
}
