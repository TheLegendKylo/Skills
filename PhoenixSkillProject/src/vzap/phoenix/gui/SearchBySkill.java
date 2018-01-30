package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JButton;

public class SearchBySkill extends JPanel implements ActionListener
{
	private JLabel searchBySkillLab;
	private JComboBox skillComboBox;
	private JScrollPane skillScrollPane;
	private JTable skillTable;
	
	private ArrayList<Capability> capabilityList;
 	private ArrayList<EmployeeSkill> empSkillList;
	private Employee loggedOnEmployee;
  	private EmpSkillClientController clientControl;
  	
	private Vector<String> vectSkill; 
	private ArrayList<Employee> employeeList; 
	private Vector<String> vectTabSkill;
 	private Vector comboSkill; 
	
	private ArrayList<Skill> skillList;
	private Object[] HeaderForSkillsDetails;
	private DefaultTableModel modelInsert;
	private JButton clearBut;
  	


	/**
	 * Create the panel.
	 */
	public SearchBySkill(EmpSkillClientController clientControl)
	{
		this.clientControl = clientControl;
		loggedOnEmployee = clientControl.getLogonEmployee();
		capabilityList = clientControl.getCapabilityList();
		
		vectSkill = new Vector<String>();
		vectTabSkill = new Vector<String>();
		skillList = clientControl.getSkillList();
		comboSkill = new Vector<>();
		for(int i = 0 ; i < skillList.size() ; i++)
		{
			comboSkill.add(skillList.get(i).getSkillDescription());	
		}
		
		setLayout(null);
		
		searchBySkillLab = new JLabel("Choose SKILL from dropdown list");
		searchBySkillLab.setBounds(302, 57, 197, 14);
		add(searchBySkillLab);
		
		skillComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skillComboBox);
		skillComboBox.addActionListener(this);
		skillComboBox.setBounds(542, 54, 243, 20);
		add(skillComboBox);
		
		skillScrollPane = new JScrollPane();
		modelInsert = new DefaultTableModel();
		skillScrollPane.setBounds(20, 139, 1011, 485);
		add(skillScrollPane);
		skillTable = new JTable(modelInsert);
//		skillTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);;
		skillTable.getRowSelectionAllowed();
		skillTable.getSelectionModel();

		skillScrollPane.setViewportView(skillTable);
		
		clearBut = new JButton("CLEAR");
		clearBut.addActionListener(this);
		clearBut.setBounds(460, 95, 89, 23);
		add(clearBut);
		

	}



	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source == clearBut)
		{
			skillTable = new JTable();
			skillScrollPane.setViewportView(skillTable);
		}


		if(source == skillComboBox)
		{
			empSkillList = new ArrayList<>();
System.out.println("searchbyskill - skillcombobox - index " + skillComboBox.getSelectedIndex() );
		
//          store the index and skillId of the cell the user has chosen from the combobox 			
			int x = skillComboBox.getSelectedIndex();
			int y = skillList.get(x).getSkillId();
System.out.println("searchbyskill - skill search "+skillList.get(x).getSkillDescription()+" "+skillList.get(x).getSkillId() );

//          now go and search the EmployeeSkill table for all employees that have that skill			
			empSkillList = clientControl.searchEmployeeSkill(y);
System.out.println("searchbymenu - number of employees with this skill - " + empSkillList.size());


//          check if there are any matches for users search criteria 
			if(empSkillList.size() == 0)
			{
				JOptionPane.showMessageDialog(this,"No employees found with your search criteria");
				return;				
			}
			
		
//          move the employeeID from returned array into a vector so that it can be sorted  			
			Vector vect = new Vector<String>();
			for (int i=0; i < empSkillList.size(); i++)
	        {
System.out.println("searchbyskill - creating vector "  +empSkillList.get(i).getEmpSkillID()+" "+empSkillList.get(i).getEmployeeID() );
				vect.addElement(empSkillList.get(i).getEmployeeID()); 
	        }
			Collections.sort(vect);    		
System.out.println("searchbyskill - after sort vect size: "+vect.size());	
		

//          now go thru the vector that contains the employeeID and fetch the employee details one at a time and populate
//          the table - do not print duplicate employees
System.out.println("searchbyskill - before for - " + empSkillList.size() );

			Object[] tabCols = new Object[5];
			employeeList = clientControl.searchEmployee((String)vect.elementAt(0));
			for (int i=0; i < vect.size(); i++)
	        {
				if (i>0 && (vect.elementAt(i).equals(vect.elementAt(i-1))))
				{
					continue;
				}
				employeeList = clientControl.searchEmployee((String)vect.elementAt(i));	    	   	
	    	   	tabCols[0] = employeeList.get(0).getEmployeeID();
	    	   	tabCols[1] = employeeList.get(0).getFirstName();
	    	   	tabCols[2] = employeeList.get(0).getSurname();
	 //     	   	model.addRow(tabCols);
	        }
			
//			modelInsert = clientControl.getEmpSkillList(empSkillList, loggedOnEmployee);
			modelInsert = clientControl.getEmpSkillDetail(empSkillList);
//			skillTable.setAutoCreateRowSorter(true);
			skillTable.setAutoCreateRowSorter(isEnabled());
			skillScrollPane.remove(skillTable);
			skillTable = new JTable(modelInsert);
			skillScrollPane.setViewportView(skillTable);
			modelInsert.fireTableDataChanged();
			this.repaint();
		
		}
	}
}
