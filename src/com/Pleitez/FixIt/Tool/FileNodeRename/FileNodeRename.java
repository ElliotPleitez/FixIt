package com.Pleitez.FixIt.Tool.FileNodeRename;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.Pleitez.FixIt.PExplore.PExplore;
import com.Pleitez.FixIt.Tool.FixItTool;
import com.Pleitez.FixIt.Tool.Tool;

public class FileNodeRename extends FixItTool {
	private static final long serialVersionUID = -4226836685176236933L;
	
	public FileNodeRename(){
		super.toolName = Tool.FileNodeRename;
		this.setSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());

		initialize();
	}
	
	private void initialize(){
		GridBagConstraints gbc = new GridBagConstraints();
		
		JPanel renameHeader = new JPanel();
		renameHeader.setLayout(new BorderLayout());
		final PExplore pExplore = new PExplore("");
		pExplore.viewFolders();
		renameHeader.add(pExplore.displayAddress(), BorderLayout.PAGE_START);
		renameHeader.add(pExplore.displayContent(), BorderLayout.CENTER);
		renameHeader.setSize(100, 100);
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(renameHeader, BorderLayout.PAGE_START);
		
		JPanel renameBody = new JPanel();
		renameBody.setLayout(new FlowLayout());
		renameBody.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		JButton viewAll = new JButton("View All");
		viewAll.addMouseListener(
			new MouseAdapter(){
				public void mousePressed(MouseEvent event){
					pExplore.viewAll();
				}
			}
		);
		
		JButton selectFolder = new JButton("Folder");
		selectFolder.addMouseListener(
			new MouseAdapter(){
				public void mousePressed(MouseEvent event){
					pExplore.viewFolders();
				}
			}
		);
		JButton selectFile = new JButton("File");
		selectFile.addMouseListener(
			new MouseAdapter(){
				public void mousePressed(MouseEvent event){
					pExplore.viewFiles();
				}
			}
		);
		JButton selectRename = new JButton("Rename");
		selectRename.addMouseListener(
				new MouseAdapter(){
					public void mousePressed(MouseEvent event){
						pExplore.renameFiles();
					}
				}
			);
		renameBody.add(viewAll);
		renameBody.add(selectFolder);
		renameBody.add(selectFile);
		renameBody.add(selectRename);
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(renameBody, BorderLayout.LINE_START);
		
//		this.add(pExplore.displayAddress(), BorderLayout.PAGE_START);
//		this.add(pExplore.displayContent(), BorderLayout.CENTER);
	}
}
