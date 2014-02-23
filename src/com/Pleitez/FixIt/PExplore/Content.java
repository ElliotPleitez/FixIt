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

public class Content extends JPanel implements TableModelListener, AddressActionListener {
    private static final long serialVersionUID = 3828800695663620331L;
    
    // Interface
    public interface ContentActionListener {
        public void updateData(String address);
    }
    
    // Imports
    // Constructor & Deconstructor
    public Content(String directoryRoot) {
        JPanel panel = this;
        panel.setLayout(new BorderLayout());
        
        contentTableModel = new ContentTableModel(directoryRoot);
        contentTableModel.addTableModelListener(this);
        contentTable = new JTable(contentTableModel);
        
        contentTableSorter = new TableRowSorter<ContentTableModel>(contentTableModel);
        contentTable.setRowSorter(contentTableSorter);
        
        // Note: Fix this so when an item is selected, it uses the Name and not
        // contentTableHider.hideColumn("System Type");
        
        contentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        contentTable.getColumnModel().getColumn(0).setMaxWidth(25);
        
        contentTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    JTable table = (JTable) event.getSource();
                    int row = table.rowAtPoint(event.getPoint());
                    // int col = table.columnAtPoint(event.getPoint());
                    // contentTableModel.updatePath(table.getValueAt(row,
                    // col).toString());
                    
                    // Note: fix so it catches the column by name not index
                    contentTableModel.updatePath(table.getValueAt(row, 2).toString(), false);
                }
            }
        });
        
        JScrollPane scrollTable = new JScrollPane(contentTable);
        panel.add(scrollTable);
    }
    
    // Private Members
    private List<ContentActionListener> contentListenerList = new ArrayList<ContentActionListener>();
    private TableRowSorter<ContentTableModel> contentTableSorter;
    private ContentTableModel contentTableModel;
    
    // Public Members
    public void addContentListener(ContentActionListener toAdd) {
        contentListenerList.add(toAdd);
    }
    
    public JTable contentTable;
    
    @Override
    // TableModelListener
    public void tableChanged(TableModelEvent event) {
        // System.out.println("TableModelListener fire");
    }
    
    @Override
    // AddressActionListener
    public void changeAddress(String address) {
        contentTableModel.updatePath(address, true);
    }
    
    public void UpdateContent(ArrayList<Object> contentHeader) {
        // Null for now
    }
    
    // public void viewAll() {
    // RowFilter<ContentTableModel, Object> rf = null;
    // contentTableSorter.setRowFilter(rf);
    // }
    //
    // public void viewFolders() {
    // RowFilter<ContentTableModel, Object> rf = null;
    // try {
    // rf = RowFilter.regexFilter(SystemType.FOLDER.toString(), 0);
    // } catch(java.util.regex.PatternSyntaxException e) {
    // return;
    // }
    // contentTableSorter.setRowFilter(rf);
    //
    // /*
    // * contentData.clear(); for(final String item : new
    // * File(currentPath).list()){ File file = new File(item);
    // * System.out.println(file.getPath() + " is: " + file.isDirectory());
    // * if(file.isDirectory()){ final ContentItem contentItem = new
    // * ContentItem(); contentItem.itemSelect = false; contentItem.itemName =
    // * file.getName(); contentData.add( new ArrayList<Object>(){ private
    // * static final long serialVersionUID = 3650847114863934434L; {
    // * add(contentItem.itemSelect); add(contentItem.itemName); } } ); } }
    // * contentTableModel.fireTableDataChanged();
    // *
    // * Comparator<Boolean> comparator1 = new Comparator<Boolean>(){
    // *
    // * @Override public int compare(Boolean o1, Boolean o2) {
    // *
    // * return 0; } };
    // */
    // }
    //
    // public void viewFiles() {
    // RowFilter<ContentTableModel, Object> rf = null;
    // try {
    // rf = RowFilter.regexFilter(SystemType.FILE.toString(), 0);
    // } catch(java.util.regex.PatternSyntaxException e) {
    // return;
    // }
    // contentTableSorter.setRowFilter(rf);
    // }
    //
    // public void renameFiles() {
    // System.out.println("Below is your list of items selected: ");
    // for(int i = 0; i < contentDataTable.size(); i++) {
    // System.out.println(contentDataTable.get(i));
    // }
    // System.out.println("...");
    // TableColumn tableColumn = new TableColumn();
    // tableColumn.setHeaderValue("Preview");
    // contentTable.addColumn(tableColumn);
    //
    // }
    // IDispose Implementation
}