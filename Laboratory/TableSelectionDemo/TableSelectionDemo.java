package TableSelectionDemo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class TableSelectionDemo extends JPanel implements ActionListener { 
    private JTable table;
    private JCheckBox rowCheck;
    private JCheckBox columnCheck;
    private JCheckBox cellCheck;
    private ButtonGroup buttonGroup;
    private JTextArea output;

    public TableSelectionDemo() {
    	super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());
        this.add(new JScrollPane(table));

        this.add(new JLabel("Selection Mode"));
        buttonGroup = new ButtonGroup();
        this.addRadio("Multiple Interval Selection").setSelected(true);
        this.addRadio("Single Selection");
        this.addRadio("Single Interval Selection");

        this.add(new JLabel("Selection Options"));
        this.rowCheck = addCheckBox("Row Selection");
        this.rowCheck.setSelected(true);
        this.columnCheck = addCheckBox("Column Selection");
        this.cellCheck = addCheckBox("Cell Selection");
        this.cellCheck.setEnabled(false);

        this.output = new JTextArea(5, 40);
        this.output.setEditable(false);
        this.add(new JScrollPane(output));
    }

    private JCheckBox addCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.addActionListener(this);
        this.add(checkBox);
        return checkBox;
    }

    private JRadioButton addRadio(String text) {
        JRadioButton b = new JRadioButton(text);
        b.addActionListener(this);
        this.buttonGroup.add(b);
        this.add(b);
        return b;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if ("Row Selection" == command) {
            table.setRowSelectionAllowed(rowCheck.isSelected());
            if (!cellCheck.isEnabled()) {
                table.setColumnSelectionAllowed(!rowCheck.isSelected());
            }
        } else if ("Column Selection" == command) {
            table.setColumnSelectionAllowed(columnCheck.isSelected());
            if (!cellCheck.isEnabled()) {
                table.setRowSelectionAllowed(!columnCheck.isSelected());
            }
        } else if ("Cell Selection" == command) {
            table.setCellSelectionEnabled(cellCheck.isSelected());
        } else if ("Multiple Interval Selection" == command) { 
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            if (cellCheck.isSelected()) {
                cellCheck.setSelected(false);
                table.setCellSelectionEnabled(false);
            }
            cellCheck.setEnabled(false);
        } else if ("Single Interval Selection" == command) {
            table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            cellCheck.setEnabled(true);
        } else if ("Single Selection" == command) {
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cellCheck.setEnabled(true);
        }
        rowCheck.setSelected(table.getRowSelectionAllowed());
        columnCheck.setSelected(table.getColumnSelectionAllowed());
        if (cellCheck.isEnabled()) {
            cellCheck.setSelected(table.getCellSelectionEnabled());
        }
    }

    private void outputSelection() {
    	this.output.append(
    		String.format("Lead: %d, %d. ",
	            table.getSelectionModel().getLeadSelectionIndex(),
	            table.getColumnModel().getSelectionModel().getLeadSelectionIndex()
            )
        );
    	this.output.append("Rows:");
        for (int c : table.getSelectedRows()) {
        	this.output.append(String.format(" %d", c));
        }
        this.output.append(". Columns:");
        for (int c : table.getSelectedColumns()) {
        	this.output.append(String.format(" %d", c));
        }
        this.output.append(".\n");
    }

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("ROW SELECTION EVENT. ");
            outputSelection();
        }
    }

    private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("COLUMN SELECTION EVENT. ");
            outputSelection();
        }
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};
        private Object[][] data = {
    		{"Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false)},
			{"John", "Doe", "Rowing", new Integer(3), new Boolean(true)},
			{"Sue", "Black", "Knitting", new Integer(2), new Boolean(false)},
			{"Jane", "White", "Speed reading", new Integer(20), new Boolean(true)},
			{"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)}
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        
        public String getColumnName(int col) {
        	return columnNames[col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }

    private static void createAndShowGUI() {
        UIManager.put("swing.boldMetal", Boolean.FALSE); 

        JFrame frame = new JFrame("TableSelectionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TableSelectionDemo newContentPane = new TableSelectionDemo();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
    		new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
    		}
		);
    }
}
