package com.Pleitez.FixIt.Tool;

public enum Tool {
	ToolBox{
		@Override
		public String toolName(){
			return "Tool Box";
		}
	},
	FileNodeRename{
		@Override
		public String toolName(){
			return "File Node Rename";
		}
	},
	AutoCADBackup{
		@Override
		public String toolName(){
			return "AutoCAD Backup";
		}
	};
	
	public String toolName(){
		return null;
	}
	
	public static Tool getTool(String value){
		switch(value){
			case "Tool Box": return Tool.ToolBox;
			case "File Node Rename": return Tool.FileNodeRename;
			case "AutoCAD Backup": return Tool.AutoCADBackup;
		}
		return null;
	}
}
