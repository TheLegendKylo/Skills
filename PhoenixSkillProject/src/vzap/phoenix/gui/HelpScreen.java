package vzap.phoenix.gui;

import java.awt.Component;

import javax.swing.JPanel;

import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;
import java.awt.Font;

public class HelpScreen extends JPanel
{
	private DefaultTableModel modelInsert;
	private JScrollPane dreyfusScroll;
	private JTable dreyfusTable;
 	private EmpSkillClientController clientControl;

	/**
	 * Create the panel.
	 */
	public HelpScreen(EmpSkillClientController clientControl)
	{
		this.clientControl = clientControl;
		
		setLayout(null);
		
		modelInsert = clientControl.getDreyfusModel(0);

		dreyfusScroll = new JScrollPane();
		dreyfusScroll.setBounds(10, 11, 430, 278);
		LineWrapCellRenderer myRenderer = new LineWrapCellRenderer();
		dreyfusTable = new JTable(modelInsert){

		    @Override
		    public TableCellRenderer getCellRenderer(int row, int column){
		        return myRenderer;
		    }
		};
		
		dreyfusScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dreyfusScroll.setViewportView(dreyfusTable);
		dreyfusScroll.setBounds(12, 37, 1793, 561);
		add(dreyfusScroll);
		dreyfusTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
		 
	}
	public class LineWrapCellRenderer extends JTextArea implements TableCellRenderer {

	    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	        int row, int column) {
	    this.setText((String) value);
	    this.setWrapStyleWord(true);
	    this.setLineWrap(true);
	    this.setFont(new Font("Arial", Font.BOLD, 15)); // not working must check
	    
	    if (isSelected) {
	        setForeground(table.getSelectionForeground());
	        setBackground(table.getSelectionBackground());
	    } else {
	        setForeground(table.getForeground());
	        setBackground(table.getBackground());
	    }
	    setSize(table.getColumnModel().getColumn(column).getWidth(),
	        getPreferredSize().height);
	    if (table.getRowHeight(row) < getPreferredSize().height) {
	        table.setRowHeight(row, getPreferredSize().height);
	    }
	    
	    return this;
	 }

	}
}
