package com.Pleitez.FixIt.Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

import com.Pleitez.FixIt.Main.ControlBar.ControlBarAction;
import com.Pleitez.FixIt.Tool.FixItTool;
import com.Pleitez.FixIt.Tool.AutoCADBackup.AutoCADBackup;
import com.Pleitez.FixIt.Tool.FixItTool.FixItToolAction;
import com.Pleitez.FixIt.Tool.Tool;
import com.Pleitez.FixIt.Tool.ToolBox;
import com.Pleitez.FixIt.Tool.ToolBox.ToolBoxAction;
import com.Pleitez.FixIt.Tool.FileNodeRename.FileNodeRename;

public class FixIt extends JFrame implements ControlBarAction, FixItToolAction, ToolBoxAction {
	private static final long serialVersionUID = 6684361813038566794L;
	private boolean saveSettings = false;
	private String title;
	private Dimension size;
	private Dimension location;
	private boolean alwaysOnTop;
	
	private List<FixItTool> toolList;
	private ControlBar controlBar;
	private CommandBar commandBar;
	
	public static void main(String[] args) {
		new SplashScreen();
		new FixIt();
	}
	
	//<//Event Call
	@Override
	public void startTool(Tool tool) {
		System.out.println("FixIt.startTool: " + tool.toString());
		controlBar.addTool(tool);
	}
	
	@Override
	public void changeTool(Tool tool) {
		for(int i = 0; i < toolList.size(); i++){
			if(Tool.getTool(tool.toolName()) == (Tool) toolList.get(i).toolName){
				this.add(toolList.get(i), BorderLayout.CENTER);
			}
		}
	}
	//>//Event Call
	
	public FixIt(){
		loadSettings();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(this.title);
		this.setSize(this.size);
		this.setLocation(this.location.width, this.location.height);
		this.setAlwaysOnTop(alwaysOnTop);
		this.setMinimumSize(new Dimension(800, 600));

		initialize();
	}
	
	private void initialize(){
		final JFrame mainWindow = this;
		
		//Setup UI
		controlBar = new ControlBar();
		controlBar.setPreferredSize(new Dimension(0, 25));
		this.add(controlBar, BorderLayout.PAGE_START);
		commandBar = new CommandBar();
		commandBar.setPreferredSize(new Dimension(0, 25));
		this.add(commandBar, BorderLayout.PAGE_END);
		

		//Setup Tool List
		toolList = new ArrayList<FixItTool>();
		ToolBox toolBox = new ToolBox();
		FileNodeRename fileNodeRename = new FileNodeRename();
		AutoCADBackup autoCADBackup = new AutoCADBackup();
		toolList.add(toolBox);
		toolList.add(fileNodeRename);
		toolList.add(autoCADBackup);

		//Setup Event Handlers
		toolBox.addToolBoxListener(this);
		
		controlBar.addListener(this);
		controlBar.addListener(toolBox);
		controlBar.addListener(fileNodeRename);
		controlBar.addListener(autoCADBackup);
		
		controlBar.addTool(Tool.ToolBox);
		
		this.setVisible(true);
		
		this.addComponentListener(
			new ComponentAdapter(){
				public void componentMoved(ComponentEvent arg0){
					if(mainWindow.getLocation().x != location.width){
						location.width = mainWindow.getLocation().x;
						saveSettings = true;
					}
					if(mainWindow.getLocation().y != location.height){
						location.height = mainWindow.getLocation().y;
						saveSettings = true;
					}
				}
				public void componentResized(ComponentEvent arg0){
					if(mainWindow.getWidth() != size.width){
						size.width = mainWindow.getWidth();
						saveSettings = true;
					}
					if(mainWindow.getHeight() != size.height){
						size.height = mainWindow.getHeight();
						saveSettings = true;
					}
				}
			}
		);
		
		this.addMouseListener(
			new MouseAdapter(){
				public void mouseReleased(MouseEvent e){
					if(saveSettings){
						saveSettings();
					}
					System.out.println("mouse released");					
				}
			}
		);
	}
	
	private void saveSettings(){
		Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
		preferences.put("title", this.title);
		preferences.putInt("sizeX", this.size.width);
		preferences.putInt("sizeY", this.size.height);
		preferences.putInt("locationX", this.location.width);
		preferences.putInt("locationY", this.location.height);
		preferences.putBoolean("alwaysOnTop", this.alwaysOnTop);
		saveSettings = false;
	}
	
	private void loadSettings(){
		Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
		this.title = "Utilities v1.0";
		
		this.size = new Dimension(
				preferences.getInt("sizeX", 300),
				preferences.getInt("sizeY",  200)
				);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.location = new Dimension(
				preferences.getInt("locationX", dimension.width / 2 - size.width / 2),
				preferences.getInt("locationY",  dimension.height / 2 - size.height / 2)
				);
		//jframe.setLocationRelativeTo(null);
		
		this.alwaysOnTop = preferences.getBoolean("alwaysOnTop", new Boolean(false));
	}

	/*private void exportSettings() throws Exception{
		Preferences preferences = Preferences.userRoot().node(this.getClass().getName());		
		OutputStream stream;
		stream = new FileOutputStream("Preferences.xml");
		preferences.exportNode(stream);
		stream.close();
	}*/
}
