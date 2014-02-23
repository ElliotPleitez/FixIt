package com.Pleitez.FixIt.PExplore;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.Pleitez.FixIt.PExplore.Content.ContentActionListener;

public class Address extends JPanel implements ContentActionListener {
	private static final long serialVersionUID = 7125238188117157449L;
	private File currentFile;
	private JTextField currentAddress;
	
	//<//Event Call
	public interface AddressActionListener{
		public void changeAddress(String address);
	}
	private List<AddressActionListener> addressListenerList = new ArrayList<AddressActionListener>();
	public void addAddressListener(AddressActionListener toAdd){
		addressListenerList.add(toAdd);
	}
	
	@Override
	public void updateData(String address) {
		this.currentFile = new File(address);
		currentAddress.setText(address);
	}
	
	private void changeAddress(String address){
		if(new File(address).exists()){
			this.currentFile = new File(address);
			for(AddressActionListener listener : addressListenerList){
				listener.changeAddress(this.currentFile.getPath());
			}
		}
		else{
			System.out.println("ADDRESS reports that path: " + address + " does NOT exist");
		}
	}
	//>//Event Call
	
	public Address(){
		initialize();
	}
	
	private void initialize(){
		JPanel panel = this;
		
		JPanel contents = new JPanel();
		
		currentAddress = new JTextField();
		currentAddress.setPreferredSize(new Dimension(700, 20));
		currentAddress.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					changeAddress(currentAddress.getText());
				}
			}
		});

		contents.add(new JLabel("Address: "), BorderLayout.LINE_START);
		contents.add(currentAddress, BorderLayout.CENTER);
		panel.add(contents);
	}
}
