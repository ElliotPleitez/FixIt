package com.Pleitez.FixIt.PExplore;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import com.Pleitez.FixIt.PExplore.Address.AddressActionListener;
import com.Pleitez.FixIt.PExplore.PExplore.ContentHeader;

//AddressActionListener doesn't need to be implemented because it's not being used
public class ContentList extends JPanel implements TableModelListener, AddressActionListener {
	private static final long serialVersionUID = 3828800695663620331L;
	private ArrayList<String> currentPath;
	public JTable contentTable;
	public TableRowSorter<ContentTableModel> contentTableSorter;
	public TableColumnHider contentTableHider;
    public ContentTableModel contentTableModel;
    public ArrayList<ArrayList<Object>> contentData;
    
	//<//Event Call
	public interface ContentActionListener{
		public void updateData(String address);
	}
	private List<ContentActionListener> contentListenerList = new ArrayList<ContentActionListener>();
	public void addContentListener(ContentActionListener toAdd){
		contentListenerList.add(toAdd);
	}
	
    @Override //TableModelListener
    public void tableChanged(TableModelEvent event){
//    	System.out.println("TableModelListener fire");
    }
    
    @Override //AddressActionListener
    public void changeAddress(String address){
    	contentTableModel.updatePath(address);
    }
	//>//Event Call
    
	public ContentList(){
		JPanel panel = this;
		panel.setLayout(new BorderLayout());
		
		contentTableModel = new ContentTableModel();
		contentTableModel.addTableModelListener(this);
		contentTable = new JTable(contentTableModel);

		contentTableSorter = new TableRowSorter<ContentTableModel>(contentTableModel);
		contentTable.setRowSorter(contentTableSorter);
		
		contentTableHider = new TableColumnHider(contentTable);
		contentTableHider.hide(TableHeader.ItemSystemType.toName());
		
		contentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		contentTable.getColumnModel().getColumn(0).setMaxWidth(25);
		
		contentTable.addMouseListener(
			new MouseAdapter(){
				public void mousePressed(MouseEvent event){
					if(event.getClickCount() == 2){
						JTable table = (JTable)event.getSource();
						int row = table.rowAtPoint(event.getPoint());
//						int col = table.columnAtPoint(event.getPoint());
//						contentTableModel.updatePath(table.getValueAt(row, col).toString());
						contentTableModel.updatePath(table.getValueAt(row, 1).toString());
					}
				}
			}
		);

		JScrollPane scrollTable = new JScrollPane(contentTable);
		panel.add(scrollTable);
	}
	
	public ContentList(ArrayList<ContentHeader> contentHeader){
		JPanel panel = this;
		panel.setLayout(new BorderLayout());
		
		
		
		JScrollPane scrollTable = new JScrollPane(contentTable);
		panel.add(scrollTable);
	}
	
	public void UpdateContent(ArrayList<ContentHeader> contentHeader){
		
	}
	
	public enum TableHeader{
		ItemSystemType{
			@Override public String toName(){
				return "System Type";
			}
		},
		ItemSelect{
			@Override public String toName(){
				return "";
			}
		},
		ItemName{
			@Override public String toName(){
				return "Name";
			}
		};
		
		public String toName(){
			return null;
		}
	}
	
	private enum SystemType{
		FOLDER,
		FILE
	}

	public class ContentItem{
		public SystemType itemSystemType;
		public boolean itemSelect = false;
		public String itemName = null;
		public String itemDateCreated = null;
		public String itemDateModified = null;
		public String itemType = null;
		public int itemSize = 0;
		public int itemFreeSpace = 0;
		
//		public ContentItem(boolean itemSelect, String itemName){
//			this.itemSelect = itemSelect;
//			this.itemName = itemName;
//		}
	}
	
    private class ContentTableModel extends AbstractTableModel{
		private static final long serialVersionUID = -5430861967175624811L;
		public ArrayList<ContentHeader> columns;
		
		//<//Event Call
        @Override
        public int getRowCount(){
            return contentData.size();
        }
        
		@Override 
        public int getColumnCount(){
            return columns.size();
        }

        @Override
        public String getColumnName(int colIndex){
        	return columns.get(colIndex).toString();
        }
        
        @Override
        public Object getValueAt(int rowIndex, int colIndex){
            return contentData.get(rowIndex).get(colIndex);
        }
        
        @Override
        public void setValueAt(Object aValue, int rowIndex, int colIndex){
        	contentData.get(rowIndex).set(colIndex, aValue);
            fireTableCellUpdated(rowIndex, colIndex);
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex){
        	if(new File(currentPath + "\\" + getValueAt(rowIndex, 2)).isFile() & (colIndex == 1)){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        
        @Override
    	public Class<?> getColumnClass(int colIndex){
    		return getValueAt(0, colIndex).getClass();
    	}
        //>//Event Call
		
        public ContentTableModel(){
        	//Shouldnt this be a class and not an Enum?
        	columns = new ArrayList<ContentHeader>(){{
        		this.add(ContentHeader.Name);
        		this.add(ContentHeader.Size);
            	this.add(ContentHeader.EditDate);
            	this.add(ContentHeader.CreationDate);
            	this.add(ContentHeader.User);        		
        	}};
        	contentData = new ArrayList<ArrayList<Object>>();
        	
	        for(final File item : File.listRoots()){
	        	final ContentItem contentItem = new ContentItem();
	        	contentItem.itemSelect = false;
	        	contentItem.itemName = item.getPath();
	        	contentData.add(
	    			new ArrayList<Object>(){
						private static final long serialVersionUID = -8561961948184598344L;
						{
							add(SystemType.FOLDER);
	    					add(contentItem.itemSelect);
	    					add(contentItem.itemName);
	    				}
	    			}
				);
	        }
        }
		
        public void updatePath(File file) {
        	currentPath.add(file.getPath());
			updateContent();
		}
        
		public void updatePath(String address){
			if(isValidDirectory(currentPath + "\\" + address)){
				updateContent();
			}
		}
		
		private void updateContent(){
			contentData.clear();
			System.out.println("This path may be wrong: " + currentPath.get(currentPath.size()));
			for(final String item : new File(currentPath.get(currentPath.size())).list()){
				File file = new File(currentPath + "\\" + item);
				final ContentItem contentItem = new ContentItem();
				if(file.isDirectory()){
					contentItem.itemSystemType = SystemType.FOLDER;
				}
				else{
					contentItem.itemSystemType = SystemType.FILE;
				}
				contentItem.itemSelect = false;
				contentItem.itemName = file.getName();
				contentData.add(
					new ArrayList<Object>(){
						private static final long serialVersionUID = 3650847114863934434L;
						{
							add(contentItem.itemSystemType);
							add(contentItem.itemSelect);
							add(contentItem.itemName);
						}
					}
				);
			}
			contentTableModel.fireTableDataChanged();
			
			for(ContentActionListener listener : contentListenerList){
				System.out.println("This path may be wrong: " + currentPath.get(currentPath.size()));
				listener.updateData(currentPath.get(currentPath.size()));
			}
		}
		
		private boolean isValidDirectory(String address){
			File file = new File(address);
			if(file.exists() & file.isDirectory()){
				currentPath.add(file.getPath());
				return true;
			}
			else{
				System.out.println("CONTENT reports that path: " + currentPath + " does NOT exist");
				return false;
			}
		}
		
		/*private boolean isValidFile(String address){
			File file = new File(address);
			if(file.exists() & file.isFile()){
				return true;
			}
			else{
				System.out.println("CONTENT reports that file: " + currentPath + " does NOT exist");
				return false;
			}
		}*/
    }
    
    private class TableColumnHider{
        //private JTable table;
        private TableColumnModel tableColumnModel;
        private Map<String, IndexedColumn> hidden = new HashMap<String, IndexedColumn>();

        public TableColumnHider(JTable table) {
			//this.table = table;
			this.tableColumnModel = table.getColumnModel();
        }

        public void hide(String columnName) {
			int index = tableColumnModel.getColumnIndex(columnName);
			TableColumn column = tableColumnModel.getColumn(index);
			IndexedColumn ic = new IndexedColumn(index, column);
			if(hidden.put(columnName, ic) != null) {
				throw new IllegalArgumentException("Duplicate column name.");
			}
            tableColumnModel.removeColumn(column);
        }

        /*public void show(String columnName) {
            IndexedColumn ic = hidden.remove(columnName);
            if (ic != null) {
                tableColumnModel.addColumn(ic.column);
                int lastColumn = tableColumnModel.getColumnCount() - 1;
                if (ic.index < lastColumn) {
                    tableColumnModel.moveColumn(lastColumn, ic.index);
                }
            }
        }*/
        
        //private static class IndexedColumn {
        private class IndexedColumn {
            //private Integer index;
            //private TableColumn column;

            public IndexedColumn(Integer index, TableColumn column) {
                //this.index = index;
                //this.column = column;
            }
        }
    }
    
    public void viewAll(){
    	RowFilter<ContentTableModel, Object> rf = null;
    	contentTableSorter.setRowFilter(rf);
    }
    
    public void viewFolders() {
    	RowFilter<ContentTableModel, Object> rf = null;
    	try{
    		rf = RowFilter.regexFilter(SystemType.FOLDER.toString(), 0);
    	}
    	catch(java.util.regex.PatternSyntaxException e){
    		return;
    	}
    	contentTableSorter.setRowFilter(rf);
    	
		/*contentData.clear();
		for(final String item : new File(currentPath).list()){
			File file = new File(item);
			System.out.println(file.getPath() + " is: " + file.isDirectory());
			if(file.isDirectory()){
				final ContentItem contentItem = new ContentItem();
				contentItem.itemSelect = false;
				contentItem.itemName = file.getName();
				contentData.add(
					new ArrayList<Object>(){
						private static final long serialVersionUID = 3650847114863934434L;
						{
							add(contentItem.itemSelect);
							add(contentItem.itemName);
						}
					}
				);
			}
		}
		contentTableModel.fireTableDataChanged();
    	
    	Comparator<Boolean> comparator1 = new Comparator<Boolean>(){
			@Override
			public int compare(Boolean o1, Boolean o2) {
				
				return 0;
			}
    	};*/
	}
    
    public void viewFiles() {
    	RowFilter<ContentTableModel, Object> rf = null;
    	try{
    		rf = RowFilter.regexFilter(SystemType.FILE.toString(), 0);
    	}
    	catch(java.util.regex.PatternSyntaxException e){
    		return;
    	}
    	contentTableSorter.setRowFilter(rf);
	}

	public void renameFiles() {
		System.out.println("Below is your list of items selected: ");
		for(int i = 0; i < contentData.size(); i++){
			System.out.println(contentData.get(i));
		}
		System.out.println("...");
		TableColumn tableColumn = new TableColumn();
		tableColumn.setHeaderValue("Preview");
		contentTable.addColumn(tableColumn);
		
		
		
		
		
		
		
	}
}