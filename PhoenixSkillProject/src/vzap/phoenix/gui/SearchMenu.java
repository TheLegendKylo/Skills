package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.*;

import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.Color;
import javax.swing.JList;

public class SearchMenu extends JPanel implements ActionListener, MouseListener
{

	String [] empHeader;
	String [] [] tabCols;
	String [] skillHeader;
	String [] [] skillRows;
	String [] hobbyHeader;
	String [] [] hobbyRows;
	
	String [] capability; 
	Object source; 
	private JTextField inputJTF;
	
	private ArrayList<Capability> capabilityList;
	private ArrayList<Employee> employeeList; 
  	private Vector<Hobby> hobbyList;
  	private Vector comboHobby = null;
	private ArrayList<EmployeeSkill> empSkillList;
	private ArrayList<Skill> skillList;
 	private Vector comboSkill = null;
 	
	private Employee loggedOnEmployee;
	
  	private EmpSkillClientController clientControl;
	private JButton empBut;
	private JButton clearBut;
	private JScrollPane scrollPane;
	private JTable table;
	
	private DefaultTableModel model=null;
	private JComboBox hobbyComboBox;
	private JLabel hobbyLab;
	private JLabel skillLab;
	private JComboBox skillComboBox;
	private JLabel lblEnterEmployeeSearch;
	private JLabel contentsOfTable;
	private JScrollPane scrollPane_Skills;
	private JList skillsJlist;
	private JScrollPane scrollPane_Hobby;
	private JList hobbyJlist;

	public SearchMenu(EmpSkillClientController clientControl)
	{
		this.clientControl = clientControl;
		loggedOnEmployee = clientControl.getLogonEmployee();
		capabilityList = clientControl.getCapabilityList();
		
		hobbyList = clientControl.getHobbyList();
		comboHobby = new Vector<>();
		for(int i = 0 ; i < hobbyList.size();i++)
		{
			comboHobby.add(hobbyList.get(i).getHobbyDescription());
		}
		
		skillList = clientControl.getSkillList();
		comboSkill = new Vector<>();
		for(int i = 0 ; i < skillList.size() ; i++)
		{
			comboSkill.add(skillList.get(i).getSkillDescription());	
		}

		setLayout(null);
//		for(int pos = 0; pos<7; pos++)
//		{
//			skillHeader[pos] = capabilityList.get
//		}
//			
									
		inputJTF = new JTextField();
		inputJTF.setToolTipText("Enter your employee search criteria ..... and then click on the \"search EMPLOYEE\" button");
		inputJTF.setBounds(337, 23, 346, 20);
		inputJTF.setColumns(10);
		add(inputJTF);

		clearBut = new JButton("CLEAR");
		clearBut.setToolTipText("click this CLEAR button between each different search - this will initialise the tables ");
		clearBut.setBounds(865, 112, 114, 23);
		clearBut.addActionListener(this);
		add(clearBut);
		
		empBut = new JButton("search EMPLOYEE ");
		empBut.setBounds(693, 22, 159, 23);
		empBut.addActionListener(this);
		add(empBut);
		
		model = new DefaultTableModel();
		
		Object[] tabHeader = new String[]{"UserId","First Name","Surname","Alias","Contact"};
		
		model.setColumnIdentifiers(tabHeader);
		table = new JTable(model);

		table.getRowSelectionAllowed();
		table.getSelectionModel();
		table.addMouseListener(this);
//		model.setColumnIdentifiers(tabHeader);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setToolTipText("This will be populated once you have chosen one of the above options  ");
		scrollPane.setEnabled(false);
		scrollPane.setBounds(71, 156, 919, 138);
		add(scrollPane);
		 
		hobbyComboBox = new JComboBox(comboHobby);
		hobbyComboBox.setBorder(null);
		AutoCompletion.enable(hobbyComboBox);
		hobbyComboBox.addActionListener(this);
		hobbyComboBox.setBounds(337, 84, 346, 20);
		add(hobbyComboBox);
		
		hobbyLab = new JLabel("Choose HOBBY from dropdown list");
		hobbyLab.setBounds(42, 87, 205, 14);
		add(hobbyLab);
		
		skillLab = new JLabel("Choose SKILL from dropdown list");
		skillLab.setBounds(42, 58, 205, 14);
		add(skillLab);
		
		skillComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skillComboBox);
		skillComboBox.addActionListener(this);
		skillComboBox.setBounds(337, 55, 346, 20);
		add(skillComboBox);
		
		lblEnterEmployeeSearch = new JLabel("enter EMPLOYEE search criteria");
		lblEnterEmployeeSearch.setBounds(42, 24, 219, 14);
		add(lblEnterEmployeeSearch);
		
		contentsOfTable = new JLabel("");
		contentsOfTable.setBorder(null);
		contentsOfTable.setBounds(250, 131, 451, 14);
		add(contentsOfTable);
		
		scrollPane_Skills = new JScrollPane();
		scrollPane_Skills.setBounds(71, 324, 451, 187);
		add(scrollPane_Skills);
		
		skillsJlist = new JList();
		skillsJlist.setToolTipText("This will only be populated once you have entered \"EMPLOYEE search criteria\" option");
		skillsJlist.setEnabled(false);
		scrollPane_Skills.setViewportView(skillsJlist);
		
		scrollPane_Hobby = new JScrollPane();
		scrollPane_Hobby.setBounds(761, 324, 218, 187);
		add(scrollPane_Hobby);
		
		hobbyJlist = new JList();
		hobbyJlist.setToolTipText("This will only be populated once you have entered \"EMPLOYEE search criteria\" optionb");
		hobbyJlist.setEnabled(false);
		scrollPane_Hobby.setViewportView(hobbyJlist);
		//scrollPane.setViewportView(table);

	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		source = ae.getSource();
		
		if(source == clearBut)
		{
			//model.setColumnIdentifiers(skillHeader);
            contentsOfTable.setText(" ");			
			inputJTF.setText("");
			while (model.getRowCount() > 0)
			{
				model.removeRow(0);
			}
			hobbyJlist = null;
			skillsJlist = null;
		}

	
//---------------------------------  EMPLOYEE
			
		if (source == empBut)
		{	
//			check if user has entered search criteria for the employee search 
//
			if(!inputJTF.getText().isEmpty() ) 
			{

//			
System.out.println("searchmenu - employee search " + inputJTF.getText());
				employeeList = null;
				employeeList = clientControl.searchEmployee(inputJTF.getText());
System.out.println("searchmenu - employee search size " + employeeList.size());						
				
                String heading1 = "List of employees that match your search criteria \"";
                String heading2 = inputJTF.getText();
                String heading3 = "\"";
                String completeHeading = heading1 + heading2 + heading3;
				contentsOfTable.setText(completeHeading);
				
				Object[] tabCols = new Object[5];
				for (int i = 0; i < employeeList.size(); i++)
		        {	
System.out.println("searchmenu - employee - in for ind " + i + " " + employeeList.get(i).getEmployeeID());		                  		            
		    	   	tabCols[0] = employeeList.get(i).getEmployeeID();
		    	   	tabCols[1] = employeeList.get(i).getFirstName();
		    	   	tabCols[2] = employeeList.get(i).getSurname();
		    	   	tabCols[3] = employeeList.get(i).getAlias();
		    	   	tabCols[4] = employeeList.get(i).getContactNo();
		    	   	model.addRow(tabCols);
		    	   	
		    	   	//int rowSelected = table.getSelectedRow()
		        }
System.out.println("searchmenu - getrowcount - " + model.getRowCount());
				JOptionPane.showMessageDialog(this,"Please select a row from the table");
			}
			else
			{
				JOptionPane.showMessageDialog(this,  "Please enter search criteria");
			}
		}
	
		
		
		
		
//---------------------------   SKILLS 
		
		if(source == skillComboBox)
		{
			empSkillList = new ArrayList<>();
System.out.println("searchmenu - skillcombobox - index " + skillComboBox.getSelectedIndex() );
			
//     store the index and skillId of the cell the user has chosen from the combobox 			
			int x = skillComboBox.getSelectedIndex();
			int y = skillList.get(x).getSkillId();
System.out.println("searchmenu - employee skill search " + skillList.get(x).getSkillDescription());
System.out.println("searchmenu - employee skill search " + skillList.get(x).getSkillId());


//     now go and search the EmployeeSkill table for all employees that have that skill			
    		empSkillList = clientControl.searchEmployeeSkill(y);
System.out.println("searchmenu - eback from searchemployeeskill " + empSkillList.size());
 

//     check if there are any matches for users search criteria 
			if(empSkillList.size() == 0)
			{
				JOptionPane.showMessageDialog(this,"No employees found with your search criteria");
				return;				
			}
			
			
//     move the employeeID from returned array into a vector so that it can be sorted  			
    		Vector vect = new Vector<String>();
			for (int i=0; i < empSkillList.size(); i++)
	        {
System.out.println("searchmenu - creating vector "  +empSkillList.get(i).getEmpSkillID()+" "+empSkillList.get(i).getEmployeeID() );
				vect.addElement(empSkillList.get(i).getEmployeeID()); 
	        }
    		Collections.sort(vect);    		
System.out.println("searchmenu - after sort vect size: "+vect.size());	
			

//     now go thru the vector that contains the employeeID and fetch the employee details one at a time and populate
//     the table - do not print duplicate employees
System.out.println("searchmenu - before for - " + empSkillList.size() );

			String heading1 = "List of employees with \"";
			String heading2 = skillList.get(x).getSkillDescription();
			String heading3 = "\" as a skill";
			String completeHeading = heading1 + heading2 + heading3;
			contentsOfTable.setText(completeHeading);


			Object[] tabCols = new Object[5];
			employeeList = clientControl.searchEmployee((String)vect.elementAt(0));
			for (int i=0; i < vect.size(); i++)
	        {
System.out.println("searchmenu - before if - " + i + " " + vect.elementAt(i)); 
				if (i>0 && (vect.elementAt(i).equals(vect.elementAt(i-1))))
				{
					continue;
				}
				employeeList = clientControl.searchEmployee((String)vect.elementAt(i));	    	   	

System.out.println("searchmenu - in for - " + vect.get(i) + " " + i);
	    	   	tabCols[0] = employeeList.get(0).getEmployeeID();
	    	   	tabCols[1] = employeeList.get(0).getFirstName();
	    	   	tabCols[2] = employeeList.get(0).getSurname();
	    	   	tabCols[3] = employeeList.get(0).getAlias();
	    	   	tabCols[3] = employeeList.get(0).getAlias();
	    	   	model.addRow(tabCols);
	        }
			
			JOptionPane.showMessageDialog(this,"Please select a row from the table");
			
//			int row = table.getSelectedRow();
//System.out.println("searchmenu - selecting employee skill " + row);
//			if (row <0)
//			{
//				System.out.println("searchmenu - display details of employee skill : " + table.getValueAt(row, 0));
//				System.out.println("searchmenu - display details of employee skill : " + table.getValueAt(row, 1));
//				System.out.println("searchmenu - display details of employee skill : " + table.getValueAt(row, 2));
//							
//							//get your userid / relevant info
//							//call database with info and do as you wish.
//				return;
//			}
//
//			
//			//ensure the row value is initialised once you done.
//			row = 0;	
//			employeeList = empSkillClient.searchEmployee((String)vect.elementAt(0));
		}
		
		
		
//--------------------------    HOBBY 	
		
		
		if(source == hobbyComboBox)
		{
			
//		Hobby combobox was setup at the beginning of class - AutoCompletion was enabled at beginning.
//		when you come into this IF statement it means user has selected a hobby to search
System.out.println("searchmenu - hobbycombobox - index selected: " + hobbyComboBox.getSelectedIndex() );
			int x = hobbyComboBox.getSelectedIndex();
System.out.println("searchmenu - hobbycombobox - hobby description: " + hobbyList.get(x).getHobbyDescription());
			
//		clear the employee arraylist and go and get all the employees that partake in this hobby		
			employeeList = null;
			employeeList = clientControl.searchEmployeeHobby(hobbyList.get(x).getHobbyID());

System.out.println("searchmenu - employee hobby search size: " + employeeList.size());
			if(employeeList.size() == 0)
			{
				JOptionPane.showMessageDialog(this,"No employees have this hobby");
				return;
			}
			
//		declare 4 column in the table 			
			Object[] tabCols = new Object[5];
			
//		go thru the employeeList array and move information into the table 			
			for (int i = 0; i < employeeList.size(); i++)
	        {	
System.out.println("searchmenu - hobby for - current i = " + i);
                String heading1 = "List of employees that have \"";
                String heading2 = hobbyList.get(x).getHobbyDescription();
                String heading3 = "\" as a hobby";
                String completeHeading = heading1 + heading2 + heading3;
				contentsOfTable.setText(completeHeading);

	    	   	tabCols[0] = employeeList.get(i).getEmployeeID();
	    	   	tabCols[1] = employeeList.get(i).getFirstName();
	    	   	tabCols[2] = employeeList.get(i).getSurname();
	    	   	tabCols[3] = employeeList.get(i).getAlias();
	    	   	tabCols[4] = employeeList.get(i).getContactNo();
	    	   	model.addRow(tabCols);
	        }
		}


	}//end of action performed


	@Override
	public void mouseClicked(MouseEvent t)
	{
		if(source == empBut)
		{
			System.out.println("searchmenu - mouseclicked - empBut");
		}
		
		if(source == skillComboBox)
		{
			System.out.println("searchmenu - mouseclicked - skillComboBox");
		}
		int row = table.getSelectedRow();

System.out.println("searchmenu mouseclicked - row = " + row );
		if (!(row <0))
		{
			String x = (String)table.getValueAt(row, 0);
			//get your userid / relevant info
			//call database with info and do as you wish.
			System.out.println("searchmenu - after caste : " + x);
			System.out.println("searchmenu - select employee to display profile : " + table.getValueAt(row, 0));
			System.out.println("searchmenu - select employee to display profile : " + table.getValueAt(row, 1));
			System.out.println("searchmenu - select employee to display profile : " + table.getValueAt(row, 2));

//			return;
		}

		
		//ensure the row value is initialised once you done.
		row = 0;
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		
	}
}

