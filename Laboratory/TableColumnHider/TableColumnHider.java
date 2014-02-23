package TableColumnHider;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/** @see http://stackoverflow.com/questions/6796673 */
public class TableColumnHider {

    private JTable table;
    private TableColumnModel tcm;
    private Map<String, IndexedColumn> hidden =
        new HashMap<String, IndexedColumn>();

    public TableColumnHider(JTable table) {
        this.table = table;
        this.tcm = table.getColumnModel();
    }

    public void hide(String columnName) {
        int index = tcm.getColumnIndex(columnName);
        TableColumn column = tcm.getColumn(index);
        IndexedColumn ic = new IndexedColumn(index, column);
        if (hidden.put(columnName, ic) != null) {
            throw new IllegalArgumentException("Duplicate column name.");
        }
        tcm.removeColumn(column);
    }

    public void show(String columnName) {
        IndexedColumn ic = hidden.remove(columnName);
        if (ic != null) {
            tcm.addColumn(ic.column);
            int lastColumn = tcm.getColumnCount() - 1;
            if (ic.index < lastColumn) {
                tcm.moveColumn(lastColumn, ic.index);
            }
        }
    }

    private static class IndexedColumn {

        private Integer index;
        private TableColumn column;

        public IndexedColumn(Integer index, TableColumn column) {
            this.index = index;
            this.column = column;
        }
    }

    public static void main(String[] args) {
        String[] columnNames = {
            "Name", "Size", "Type", "Date Modified", "Permissions"
        };
        String[][] data = {
            {"bin", "2", "dir", "Jun 9", "drwxr-xr-x"},
            {"boot", "3", "dir", "Jun 9", "drwxr-xr-x"},
            {"dev", "6", "dir", "Jul 12", "drwxr-xr-x"},
            {"etc", "34", "dir", "Jul 12", "drwxr-xr-x"}
        };
        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        final TableColumnHider hider = new TableColumnHider(table);
        JPanel checkBoxes = new JPanel();
        for (int i = 0; i < columnNames.length; i++) {
            JCheckBox checkBox = new JCheckBox(columnNames[i]);
            checkBox.setSelected(true);
            checkBox.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    JCheckBox cb = (JCheckBox) evt.getSource();
                    String columnName = cb.getText();

                    if (cb.isSelected()) {
                        hider.show(columnName);
                    } else {
                        hider.hide(columnName);
                    }
                }
            });
            checkBoxes.add(checkBox);
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(checkBoxes, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}