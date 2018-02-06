package vzap.phoenix.gui;

import java.awt.Component;

import javax.swing.JPanel;

import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;

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
		dreyfusTable = new JTable(modelInsert);
		LineWrapCellRenderer myRenderer = new LineWrapCellRenderer();
		myRenderer.getTableCellRendererComponent(dreyfusTable, 0, false,false,4,4);
		dreyfusScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dreyfusScroll.setViewportView(dreyfusTable);
		dreyfusScroll.setBounds(0, 97, 1453, 527);
		add(dreyfusScroll);


	}
	public class LineWrapCellRenderer extends JTextArea implements TableCellRenderer {

	    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	        int row, int column) {
	    this.setText((String) value);
	    this.setWrapStyleWord(true);
	    this.setLineWrap(true);

	    int fontHeight = this.getFontMetrics(this.getFont()).getHeight();
	    int textLength = this.getText().length();
	    int lines = textLength / this.getColumnWidth();
	    if (lines == 0) {
	        lines = 1;
	    }

	    int height = fontHeight * lines;
	    table.setRowHeight(row, height);

	    return this;
	 }

	}
}
