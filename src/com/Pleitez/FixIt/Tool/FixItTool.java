package com.Pleitez.FixIt.Tool;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.Pleitez.FixIt.Main.ControlBar.ControlBarAction;

public class FixItTool extends JPanel implements ControlBarAction {
	private static final long serialVersionUID = 2638061054032344811L;
	public Tool toolName;
	
	//<//Event Call
	public interface FixItToolAction {
		public void changeTool(Tool tool);
	}
	private List<FixItToolAction> fixItToolListenerList = new ArrayList<FixItToolAction>();
	public void addFixItToolListener(FixItToolAction toAdd){
		fixItToolListenerList.add(toAdd);
	}

	@Override
	public void changeTool(Tool tool) {
		if(tool == this.toolName){
//			System.out.println("FixItTool.changeTool: " + tool.toString() + " - " + toolName + " ON");
			this.setVisible(true);
		}
		else{
//			System.out.println("FixItTool.changeTool: " + tool.toString() + " - " + toolName + " OFF");
			this.setVisible(false);
		}
	}
	//>//Event Call
	
	public FixItTool(){
		initialize();
	}
	
	private void initialize(){
		
	}
//	private void saveSettings(){
//	Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
//	preferences.put("title", this.title);
//	preferences.putInt("sizeX", this.size.width);
//	preferences.putInt("sizeY", this.size.height);
//	preferences.putInt("locationX", this.location.width);
//	preferences.putInt("locationY", this.location.height);
//	preferences.putBoolean("alwaysOnTop", this.alwaysOnTop);
//}
//
//private void loadSettings(){
//	Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
//	this.title = "Utilities v1.0";
//	
//	this.size = new Dimension(
//			preferences.getInt("sizeX", 300),
//			preferences.getInt("sizeY",  200)
//			);
//	
//	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//	this.location = new Dimension(
//			preferences.getInt("locationX", dimension.width / 2 - size.width / 2),
//			preferences.getInt("locationY",  dimension.height / 2 - size.height / 2)
//			);
//	//jframe.setLocationRelativeTo(null);
//	
//	this.alwaysOnTop = preferences.getBoolean("alwaysOnTop", new Boolean(false));
//}
//
//private void exportSettings() throws Exception{
//	Preferences preferences = Preferences.userRoot().node(this.getClass().getName());		
//	OutputStream stream;
//	stream = new FileOutputStream("Preferences.xml");
//	preferences.exportNode(stream);
//	stream.close();
//}
}
