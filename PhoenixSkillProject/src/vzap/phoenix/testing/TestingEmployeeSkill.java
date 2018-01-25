package vzap.phoenix.testing;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout.Alignment;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClient;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TestingEmployeeSkill extends JPanel
{
	private JTabbedPane tabbedPane;
	private JPanel profilePanel;
	private JPanel skillPanel;
	private JLabel lblEmployeeid;
	private JLabel lblFirstName;
	private JLabel lblSurname;
	private JLabel lblContactNo;
	private JLabel lblEmail;
	private JLabel outEmployeeID;
	private JLabel outFName;
	private JLabel outSurname;
	private JLabel outContactNo;
	private JLabel outEmail;
	private JPanel addSkillPanel;
	private JComboBox comboBox;
	private JLabel lblAlias;
	private JLabel outAlias;
	private JPanel skillSelPanel;
	private JPanel ratingPanel;
	private JScrollPane scrollPane;

	private JTable ratingTable;
	private DefaultTableModel ratingModel;
	private JTextArea textArea;
	Object[][] tableRow;
    String[] tableHeader;       	

    JComboBox ratingBox = null;	
	ArrayList<Capability> capList = null;
	ArrayList<CapabilityRating> capRatingList =null;
	/**
	 * Create the panel.
	 */
	public TestingEmployeeSkill(EmpSkillClient empClient, Employee logonEmployee) 
	{
		setLayout(new GridLayout(1, 1, 0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		profilePanel = new JPanel();
		tabbedPane.addTab("Profile", null, profilePanel, null);
		
		lblEmployeeid = new JLabel("EmployeeID");
		
		lblFirstName = new JLabel("First Name");
		
		lblSurname = new JLabel("Surname");
		
		lblContactNo = new JLabel("Contact No");
		
		lblEmail = new JLabel("Email");
		 
		lblAlias = new JLabel("Alias");
		
		outEmployeeID = new JLabel(logonEmployee.getEmployeeID());
		outEmployeeID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outFName = new JLabel(logonEmployee.getFirstName());
		outFName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outSurname = new JLabel(logonEmployee.getSurname());
		outSurname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outContactNo = new JLabel(logonEmployee.getContactNo());
		outContactNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outEmail = new JLabel(logonEmployee.getEmail());
		outEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outAlias = new JLabel(logonEmployee.getAlias());
		outAlias.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textArea = new JTextArea();
		Vector<Hobby> hobbyList = empClient.getHobbyList();
		int hobbyCount = logonEmployee.getEmpHobbies().size();
		for (int i = 0; i < hobbyCount; i++)
		{
			for (int j = 0; j < empClient.getHobbyList().size(); j++)
			{
				if(hobbyList.get(j).getHobbyID()==logonEmployee.getEmpHobbies().get(i))
				{
					textArea.setText(textArea.getText()+"\n"+hobbyList.get(j).getHobbyDescription());					
				}
			}
		}
		
		GroupLayout gl_profilePanel = new GroupLayout(profilePanel);
		gl_profilePanel.setHorizontalGroup(
			gl_profilePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profilePanel.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblAlias)
						.addComponent(lblEmail)
						.addComponent(lblContactNo)
						.addComponent(lblSurname)
						.addComponent(lblFirstName)
						.addComponent(lblEmployeeid))
					.addGap(62)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profilePanel.createSequentialGroup()
							.addGap(10)
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_profilePanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(outAlias, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(outEmail, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(outContactNo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(outSurname, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(outFName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(outEmployeeID, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
					.addContainerGap(158, Short.MAX_VALUE))
		);
		gl_profilePanel.setVerticalGroup(
			gl_profilePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profilePanel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmployeeid)
						.addComponent(outEmployeeID))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFirstName)
						.addComponent(outFName, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSurname)
						.addComponent(outSurname, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblContactNo)
						.addComponent(outContactNo, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(outEmail, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAlias)
						.addComponent(outAlias, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		profilePanel.setLayout(gl_profilePanel);
		
		skillPanel = new JPanel();
		tabbedPane.addTab("Skills", null, skillPanel, null);
		skillPanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		addSkillPanel = new JPanel();
		tabbedPane.addTab("Add Skill", null, addSkillPanel, null);
		ArrayList<Skill> skillList = empClient.getSkillList();
		comboBox = new JComboBox<String>();
		for (int i = 0; i < skillList.size(); i++)
		{
			comboBox.addItem(skillList.get(i).getSkillDescription());
		}
		comboBox.setMaximumRowCount(10);
		
		addSkillPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		skillSelPanel = new JPanel();
		skillSelPanel.add(comboBox);
		addSkillPanel.add(skillSelPanel);
		
		
		ratingPanel = new JPanel();
		capList = empClient.getCapabilityList();
		capRatingList = empClient.getCapabilityRatingList();
		tableRow = new Object[capList.size()][3];
        tableHeader = new String[]{"Capability","Rating","Description"};       	
        for (int i=0; i<capList.size(); i++)
        {
            
    	   	tableRow[i][0]=capList.get(i).getName();
       }
//		tableRow[selIdx,2] = ratingModel.setValueAt(desc, selIdx, 2);			
	    ratingModel = new DefaultTableModel(tableRow,tableHeader);
//	    ratingTable = new JTable(ratingModel);
       	
	    ratingTable = new JTable(new MyTableModel());
//		ratingTable.getColumn("Rating").setCellEditor(new DefaultCellEditor(ratingBox));
		TableColumn ratingColumn = ratingTable.getColumnModel().getColumn(1);
		this.setUpRatingColumn(ratingTable, ratingTable.getColumn("Rating"));
System.out.println(">>>>>Are you looping her too?");
       	
		scrollPane = new JScrollPane(ratingTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setMaximumSize(new Dimension(32767, 10000));
		
		scrollPane.setViewportView(ratingTable);

		ratingPanel.add(scrollPane);
		
		addSkillPanel.add(ratingPanel);
		ratingPanel.setLayout(new GridLayout(1, 1, 0, 0));
		

	}
    public void setUpRatingColumn(JTable table,
            TableColumn rating) 
    {
		//Set up the editor for the sport cells.
		JComboBox ratingBox = new JComboBox();
		for (int j = 1; j < 6; j++)
		{
			ratingBox.addItem(j);		
		}
		rating.setCellEditor(new DefaultCellEditor(ratingBox));
		
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer =
		new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		rating.setCellRenderer(renderer);
    }

	
	
   class MyTableModel extends AbstractTableModel 
   {

    	public String getColumnName(int col) {
   	        return tableHeader[col].toString();
   	    }
   	    public int getRowCount() 
   	    { 
   	    	return tableRow.length; 
   	    }
   	    public int getColumnCount() 
   	    { 
   	    	return tableHeader.length; 
   	    }
   	    public Object getValueAt(int row, int col) 
   	    {
   	        return tableRow[row][col];
   	    }
   	    public boolean isCellEditable(int row, int col)
   	    { 
   	    	return true; 
   	    }
   	    public void setValueAt(Object value, int row, int col) 
   	    {
   			// TODO Auto-generated method stub
   			int selIdx = ratingTable.getSelectedRow();
   			int selRating = (int)this.getValueAt(selIdx, 1);
   			String desc = null;
   			System.out.println("selIdx: "+selIdx+" selRating: "+selRating);
   			
   	   		for (int j = 0; j < capRatingList.size(); j++)
   			{
   				if(capRatingList.get(j).getCapabilityID()==selIdx+1
   						&& capRatingList.get(j).getRating()==selRating)
   				{
   					desc = capRatingList.get(j).getDescription();
   					break;
   				}
   			}
//   			System.out.println("Rating Description: "+desc);
   	   		tableRow[selIdx][2] = desc;
//   			ratingModel.setValueAt(desc, selIdx, 2);			
   	        fireTableCellUpdated(selIdx, 2);
   	    }
   }	
}
