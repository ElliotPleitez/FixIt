package com.Pleitez.FixIt.Tool.AutoCADBackup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.Pleitez.FixIt.PExplore.PExplore;
import com.Pleitez.FixIt.Tool.FixItTool;
import com.Pleitez.FixIt.Tool.Tool;

public class AutoCADBackup extends FixItTool {
	private static final long serialVersionUID = 7703769194156560604L;
	private ArrayList<String> downloadList;
	private PExplore pExplore;

	private class RadioListener implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			if(e.getStateChange() == 1){
				System.out.println(e.getItem());
//				switch(selectedOffice){
//					case Arizona: System.out.println("//jbaaz01fs01/autocad_backup$/");
//					case "California": System.out.println("//jbaca01fs01/autocad_backup$/");
//					case "Hong Kong": System.out.println("//jbahk01fs01/autocad_backup$/");
//					case "Louisiana": System.out.println("//jbala01fs01/autocad_backup$/");
//					case "Macau": System.out.println("//jbamc01fs01/autocad_backup$/");
//					case "Nevada": System.out.println("//jbanv01ds01/autocad_backup$/");
//					case "Vietnam": System.out.println("//jbavn01fs01/autocad_backup$/");
//				}
			}
		}
	}
	
	private enum Office{
		Arizona{
			@Override public String toString(){
				return "Arizona";
			}
		},
		California{
			@Override public String toString(){
				return "California";
			}
		},
		Hong_Kong{
			@Override public String toString(){
				return "Hong Kong";
			}
		},
		Louisiana{
			@Override public String toString(){
				return "Louisiana";
			}
		},
		Macau{
			@Override public String toString(){
				return "Macau";
			}
		},
		Nevada{
			@Override public String toString(){
				return "Nevada";
			}
		},
		Vietnam{
			@Override public String toString(){
				return "Vietnam";
			}
		};
	}
	
	public AutoCADBackup(){
		super.toolName = Tool.AutoCADBackup;
		this.setSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());

		JPanel backupHeader = new JPanel();
		backupHeader.setSize(100, 100);
		backupHeader.setLayout(new FlowLayout());
//		final JCheckBox arizona = new JCheckBox("Arizona");
//		final JCheckBox california = new JCheckBox("California");
//		final JCheckBox hongkong = new JCheckBox("Hong Kong");
//		final JCheckBox louisiana = new JCheckBox("Louisiana");
//		final JCheckBox macau = new JCheckBox("Macau");
//		final JCheckBox nevada = new JCheckBox("Nevada");
//		final JCheckBox vietnam = new JCheckBox("Vietnam");
//
//		JButton apply = new JButton("Apply");
//		apply.addMouseListener(
//			new MouseAdapter(){
//				@SuppressWarnings("serial")
//				public void mousePressed(MouseEvent event){
//					UpdateDirectory(new ArrayList<JCheckBox>(){{
//						this.add(arizona);
//						this.add(california);
//						this.add(hongkong);
//						this.add(louisiana);
//						this.add(macau);
//						this.add(vietnam);
//					}});
//				}
//			}
//		);
		final JRadioButton arizona = new JRadioButton(Office.Arizona.toString());
		final JRadioButton california = new JRadioButton(Office.California.toString());
		final JRadioButton hongkong = new JRadioButton(Office.Hong_Kong.toString());
		final JRadioButton louisiana = new JRadioButton(Office.Louisiana.toString());
		final JRadioButton macau = new JRadioButton(Office.Macau.toString());
		final JRadioButton nevada = new JRadioButton(Office.Nevada.toString());
		final JRadioButton vietnam = new JRadioButton(Office.Vietnam.toString());
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(arizona);
		buttonGroup.add(california);
		buttonGroup.add(hongkong);
		buttonGroup.add(louisiana);
		buttonGroup.add(macau);
		buttonGroup.add(nevada);
		buttonGroup.add(vietnam);
		
		RadioListener radioListener = new RadioListener();
		arizona.addItemListener(radioListener);
		california.addItemListener(radioListener);
		hongkong.addItemListener(radioListener);
		louisiana.addItemListener(radioListener);
		macau.addItemListener(radioListener);
		nevada.addItemListener(radioListener);
		vietnam.addItemListener(radioListener);
		
//		JButton apply = new JButton("Apply");
//		apply.addMouseListener(
//			new MouseAdapter(){
//				@SuppressWarnings("serial")
//				public void mousePressed(MouseEvent event){
//					UpdateDirectory(new ArrayList<JRadioButton>(){{
//						this.add(arizona);
//						this.add(california);
//						this.add(hongkong);
//						this.add(louisiana);
//						this.add(macau);
//						this.add(vietnam);
//					}});
//				}
//			}
//		);
		
		backupHeader.add(arizona);
		backupHeader.add(california);
		backupHeader.add(hongkong);
		backupHeader.add(louisiana);
		backupHeader.add(macau);
		backupHeader.add(nevada);
		backupHeader.add(vietnam);
//		backupHeader.add(apply);
		this.add(backupHeader, BorderLayout.PAGE_START);

		pExplore = new PExplore("\\\\jbanv01ds01\\autocad_backup$\\");
		this.add(pExplore.displayContent(), BorderLayout.CENTER);
		
		JPanel backupFooter = new JPanel();
		backupHeader.setSize(100, 100);
		backupHeader.setLayout(new FlowLayout());
		JButton download = new JButton("Download");
		download.addMouseListener(
			new MouseAdapter(){
				public void mousePressed(MouseEvent event){
					JOptionPane.showMessageDialog(null, "The file selected has been downloaded to...", "Backup Download", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		);
		backupFooter.add(download);
		this.add(backupFooter, BorderLayout.PAGE_END);
	}
	
//	public void UpdateDirectory(ArrayList<JCheckBox> checkBoxList){
//		for(int i = 0; i < checkBoxList.size(); i++){
//			if(checkBoxList.get(i).isSelected()){
//				//Create list here with offices selected
//			}
//		}
//		
//		File file = new File("//jbanv01ds01/autocad_backup$/");
//		//File file = new File("s:\\");
//		ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
//		for(int i = 0; i < files.size(); i++){
//			if(files.get(i).isDirectory()){
////				System.out.println("Directory: " + files.get(i));
//			}
//			else if(files.get(i).isFile()){
////				System.out.println("File: " + files.get(i));
//			}
//		}
//		System.out.println("Apply what?");
//	}	
	public void UpdateDirectory(String selectedOffice){
		System.out.println(selectedOffice);
//		File file = new File("//jbanv01ds01/autocad_backup$/");
//		ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
//		for(int i = 0; i < files.size(); i++){
//			if(files.get(i).isDirectory()){
////				System.out.println("Directory: " + files.get(i));
//			}
//			else if(files.get(i).isFile()){
////				System.out.println("File: " + files.get(i));
//			}
//		}
		System.out.println("Apply what?");
	}
}
