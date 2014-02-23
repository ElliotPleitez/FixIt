package com.Pleitez.FixIt.PExplore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.Pleitez.FixIt.PExplore.Content.ContentActionListener;

class ContentTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -1118041604375717569L;
    
    // Enums
    public enum SystemType {
        FOLDER, FILE
    }
    
//    public enum TableHeader {
//        ItemSystemType {
//            @Override
//            public String toName() {
//                return "System Type";
//            }
//        },
//        ItemSelect {
//            @Override
//            public String toName() {
//                return "";
//            }
//        },
//        ItemName {
//            @Override
//            public String toName() {
//                return "Name";
//            }
//        };
//        
//        public String toName() {
//            return null;
//        }
//    }
    
    // Imports
    private class ContentData {
        public SystemType itemSystemType;
        public boolean itemSelect;
        public String itemName;
        public String itemDateCreated;
        public String itemDateModified;
        public String itemType;
        public int itemSize;
        public int itemFreeSpace;
    }
    
    private class TableColumnHider {
        // Enums
        // Imports
        // Constructor & Deconstructor
        public TableColumnHider(ContentTableModel contentTableModel) {
            this.tableColumnModel = (TableColumnModel) contentTableModel;
        }
        
        // Private Members
        private TableColumnModel tableColumnModel;
        private Map<String, IndexedColumn> hidden = new HashMap<String, IndexedColumn>();
        
        // Public Members
        public void hideColumn(String columnName) {
            int index = tableColumnModel.getColumnIndex(columnName);
            TableColumn column = tableColumnModel.getColumn(index);
            IndexedColumn indexedColumn = new IndexedColumn(index, column);
            if(hidden.put(columnName, indexedColumn) != null) {
                throw new IllegalArgumentException("Duplicate column name.");
            }
            tableColumnModel.removeColumn(column);
        }
        
        public void showColumn(String columnName) {
            IndexedColumn ic = hidden.remove(columnName);
            if(ic != null) {
                tableColumnModel.addColumn(ic.column);
                int lastColumn = tableColumnModel.getColumnCount() - 1;
                if(ic.index < lastColumn) {
                    tableColumnModel.moveColumn(lastColumn, ic.index);
                }
            }
        }
        
        private class IndexedColumn {
            private int index;
            private TableColumn column;
            
            public IndexedColumn(Integer index, TableColumn column) {
                this.index = index;
                this.column = column;
            }
        }
        // IDispose Implementation
    }
    
    // Constructor & Deconstructor
    public ContentTableModel(String directoryRoot) {
        currentDirectory = directoryRoot;
        columns = new String[ContentData.class.getFields().length];
        for(int i = 0; i < ContentData.class.getFields().length; i++) {
            columns[i] = ContentData.class.getFields()[i].getName();
        }
        contentDataTable = new ArrayList<ArrayList<Object>>();
        
        //contentTableHider = new TableColumnHider(this);
        
        if(directoryRoot.isEmpty()) {
            for(final File item : File.listRoots()) {
                final ContentData contentData = new ContentData() {
                    {
                        itemSystemType = SystemType.FOLDER;
                        itemSelect = false;
                        itemName = item.getName();
                        itemDateCreated = "created";
                        itemDateModified = "modified";
                        itemType = "type";
                        itemSize = 100;
                        itemFreeSpace = 1000;
                    }
                };
                contentDataTable.add(new ArrayList<Object>() {
                    private static final long serialVersionUID = -8561961948184598344L;
                    {
                        this.add(contentData.itemSystemType);
                        this.add(contentData.itemSelect);
                        this.add(contentData.itemName);
                        this.add(contentData.itemDateCreated);
                        this.add(contentData.itemDateModified);
                        this.add(contentData.itemType);
                        this.add(contentData.itemSize);
                        this.add(contentData.itemFreeSpace);
                    }
                });
            }
        }
        else {
            for(final String item : new File(directoryRoot).list()) {
                final ContentData contentData = new ContentData() {
                    {
                        File file = new File(currentDirectory + "\\" + item);
                        itemSystemType = file.isDirectory() ? SystemType.FOLDER : SystemType.FILE;
                        itemSelect = false;
                        itemName = file.getName();
                        itemDateCreated = "created";
                        itemDateModified = "modified";
                        itemType = "type";
                        itemSize = 100;
                        itemFreeSpace = 1000;
                    }
                };
                contentDataTable.add(new ArrayList<Object>() {
                    private static final long serialVersionUID = 3650847114863934434L;
                    {
                        this.add(contentData.itemSystemType);
                        this.add(contentData.itemSelect);
                        this.add(contentData.itemName);
                        this.add(contentData.itemDateCreated);
                        this.add(contentData.itemDateModified);
                        this.add(contentData.itemType);
                        this.add(contentData.itemSize);
                        this.add(contentData.itemFreeSpace);
                    }
                });
            }
        }
    }
    
    // Private Members
    private void updateContent() {
        contentDataTable.clear();
        
        System.out.println(this.getColumnName(2));
        
        //contentTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("He!!o");
        
        for(final String item : new File(currentDirectory).list()) {
            final ContentData contentData = new ContentData() {
                {
                    File file = new File(currentDirectory + "\\" + item);
                    itemSystemType = file.isDirectory() ? SystemType.FOLDER : SystemType.FILE;
                    itemSelect = false;
                    itemName = file.getName();
                    itemDateCreated = "created";
                    itemDateModified = "modified";
                    itemType = "type";
                    itemSize = 100;
                    itemFreeSpace = 1000;
                }
            };
            contentDataTable.add(new ArrayList<Object>() {
                private static final long serialVersionUID = 3650847114863934434L;
                {
                    this.add(contentData.itemSystemType);
                    this.add(contentData.itemSelect);
                    this.add(contentData.itemName);
                    this.add(contentData.itemDateCreated);
                    this.add(contentData.itemDateModified);
                    this.add(contentData.itemType);
                    this.add(contentData.itemSize);
                    this.add(contentData.itemFreeSpace);
                }
            });
        }
        this.fireTableDataChanged();
        
//        for(ContentActionListener listener : contentListenerList) {
//            listener.updateData(currentDirectory);
//        }
    }
    
    private boolean isValidDirectory(String address) {
        File file = new File(address);
        if(file.exists() & file.isDirectory()) {
            currentDirectory = file.getPath();
            return true;
        } else {
            System.out.println("CONTENT reports that directory: " + currentDirectory + " does NOT exist");
            return false;
        }
    }
    
    private boolean isValidFile(String address) {
        File file = new File(address);
        if(file.exists() & file.isFile()) {
            return true;
        }
        else {
            System.out.println("CONTENT reports that file: " + address + " does NOT exist");
            return false;
        }
    }
    
    // Public Members
    private String currentDirectory;
    private TableColumnHider contentTableHider;
    private ArrayList<ArrayList<Object>> contentDataTable;
    public String columns[];
    
    @Override
    public int getRowCount() {
        return contentDataTable.size();
    }
    
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int colIndex) {
        return columns[colIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        return contentDataTable.get(rowIndex).get(colIndex);
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int colIndex) {
        contentDataTable.get(rowIndex).set(colIndex, aValue);
        fireTableCellUpdated(rowIndex, colIndex);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        if(new File(currentDirectory + "\\" + getValueAt(rowIndex, 2)).isFile() & (colIndex == 1)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int colIndex) {
        return getValueAt(0, colIndex).getClass();
    }
    
    public void updatePath(String address, Boolean isChange) {
        if(isChange) {
            currentDirectory = address;
            updateContent();
        }
        else if(isValidDirectory(currentDirectory + "\\" + address)) {
            updateContent();
        }
    }
    
    // IDispose Implementation
}