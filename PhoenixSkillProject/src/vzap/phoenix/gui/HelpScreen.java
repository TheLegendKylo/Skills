package vzap.phoenix.gui;

import javax.swing.JPanel;

import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
		dreyfusScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dreyfusScroll.setViewportView(dreyfusTable);
		dreyfusScroll.setBounds(0, 97, 1453, 527);
		add(dreyfusScroll);


	}
}
