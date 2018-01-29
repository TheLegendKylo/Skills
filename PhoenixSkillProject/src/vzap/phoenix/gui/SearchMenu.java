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
import javax.swing.UIManager;

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

	private ArrayList<Capability> capabilityList;
	private ArrayList<Employee> employeeList; 
 	private ArrayList<EmployeeSkill> empSkillList;
	private ArrayList<Skill> skillList;
	private ArrayList<Employee> individualEmp;
	private ArrayList<Short> individualEmpHobbyList;
	private ArrayList<EmployeeSkill> individualEmpSkillList;
	
 	private Vector comboSkill;  
  	private Vector<Hobby> hobbyList;   
  	private Vector comboHobby; 
	private Vector<String> vectHobby;   
	private Vector<String> vectSkill; 
	private Vector<String> vectTabSkill;   
	
	private DefaultTableModel model;
	private DefaultTableModel modelInsert;
	private DefaultTableModel individualSkillsModel;
	
	private JComboBox hobbyComboBox;
	private JComboBox skillComboBox;
	 	
	private Employee loggedOnEmployee;
  	private EmpSkillClientController clientControl;
  	
	private JButton empBut;
	private JButton clearBut;
	
	private JLabel hobbyLab;
	private JLabel skillLab;
	private JLabel lblEnterEmployeeSearch;
	private JLabel contentsOfTable;
	private JLabel individualSkillListLab;
	private JLabel individualHobbyListLab;
	
	private JList hobbyJlist;

	private JScrollPane scrollEmployee;
	private JScrollPane scrollIndividualSkills;
	private JScrollPane scrollHobby;
	private JScrollPane scrollSkillsDetails;
	
	private JTable tableEmployee;
	private JTable tableSkillsDetails;
	private JTable individualSkillsTable;
	
	private JTextField inputJTF;
	
	Object[] HeaderForSkillsDetails;
	private JLabel skillsDetailsLab;
	
	public SearchMenu(EmpSkillClientController clientControl)
	{
		setBackground(UIManager.getColor("CheckBoxMenuItem.background"));
		this.clientControl = clientControl;
		loggedOnEmployee = clientControl.getLogonEmployee();
		capabilityList = clientControl.getCapabilityList();
		
		vectHobby = new Vector<String>();
		vectSkill = new Vector<String>();
		vectTabSkill = new Vector<String>();
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
									
		inputJTF = new JTextField();
		inputJTF.setToolTipText("Enter your employee search criteria ..... and then click on the \"search EMPLOYEE\" button");
		inputJTF.setBounds(337, 23, 346, 20);
		inputJTF.setColumns(10);
		add(inputJTF);

		clearBut = new JButton("CLEAR");
		clearBut.setBackground(UIManager.getColor("Button.shadow"));
		clearBut.setFont(new Font("Tahoma", Font.BOLD, 14));
		clearBut.setToolTipText("click this CLEAR button between each different search - this will initialise the tables ");
		clearBut.setBounds(711, 113, 111, 40);
		clearBut.addActionListener(this);
		add(clearBut);
		
		empBut = new JButton("search EMPLOYEE ");
		empBut.setBackground(UIManager.getColor("Button.background"));
		empBut.setFont(new Font("Tahoma", Font.BOLD, 11));
		empBut.setBounds(693, 22, 159, 23);
		empBut.addActionListener(this);
		add(empBut);
		 
		hobbyComboBox = new JComboBox(comboHobby);
		hobbyComboBox.setBorder(null);
		AutoCompletion.enable(hobbyComboBox);
		hobbyComboBox.addActionListener(this);
		hobbyComboBox.setBounds(337, 84, 346, 20);
		add(hobbyComboBox);
		
		hobbyLab = new JLabel("Choose HOBBY from dropdown list");
		hobbyLab.setFont(new Font("Tahoma", Font.BOLD, 11));
		hobbyLab.setBounds(42, 87, 205, 14);
		add(hobbyLab);
		
		skillLab = new JLabel("Choose SKILL from dropdown list");
		skillLab.setFont(new Font("Tahoma", Font.BOLD, 11));
		skillLab.setBounds(42, 58, 205, 14);
		add(skillLab);
		
		skillComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skillComboBox);
		skillComboBox.addActionListener(this);
		skillComboBox.setBounds(337, 55, 346, 20);
		add(skillComboBox);
		
		lblEnterEmployeeSearch = new JLabel("enter EMPLOYEE search criteria");
		lblEnterEmployeeSearch.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEnterEmployeeSearch.setBounds(42, 24, 219, 14);
		add(lblEnterEmployeeSearch);
		
		contentsOfTable = new JLabel("");
		contentsOfTable.setBorder(null);
		contentsOfTable.setBounds(250, 131, 451, 14);
		add(contentsOfTable);
		
		scrollHobby = new JScrollPane();
		scrollHobby.setBounds(791, 352, 84, 158);
		add(scrollHobby);
		hobbyJlist = new JList(vectHobby);
		hobbyJlist.setBackground(UIManager.getColor("Button.background"));
		hobbyJlist.setToolTipText("This will only be populated with chosen Employee's hobbies once you have entered \"EMPLOYEE search criteria\" option");
		hobbyJlist.setEnabled(false);
		scrollHobby.setViewportView(hobbyJlist);
		
		individualSkillListLab = new JLabel("Chosen Employee's SKILL list ");
		individualSkillListLab.setFont(new Font("Tahoma", Font.BOLD, 11));
		individualSkillListLab.setToolTipText("This will lsit ALL skills for chosen Employee");
		individualSkillListLab.setBounds(201, 328, 193, 14);
		add(individualSkillListLab);
		
		individualHobbyListLab = new JLabel("Chosen Employee's HOBBY list");
		individualHobbyListLab.setToolTipText("This will show all chosen Employee's hobbies");
		individualHobbyListLab.setFont(new Font("Tahoma", Font.BOLD, 11));
		individualHobbyListLab.setBounds(754, 328, 176, 14);
		add(individualHobbyListLab);
		
		scrollSkillsDetails = new JScrollPane();
		scrollSkillsDetails.setToolTipText("This will give capability ratings for chosen Employee's skill");
		scrollSkillsDetails.setBounds(71, 556, 927, 143);
		add(scrollSkillsDetails);
		
		skillsDetailsLab = new JLabel("Chosen Employee's skills ratings");
		skillsDetailsLab.setFont(new Font("Tahoma", Font.BOLD, 11));
		skillsDetailsLab.setToolTipText("This will give capability ratings for chosen Employee's skill");
		skillsDetailsLab.setBounds(201, 531, 231, 14);
		add(skillsDetailsLab);
		
		model = new DefaultTableModel();
		Object[] tabHeader = new String[]{"UserId","First Name","Surname","Alias","Contact"};
		model.setColumnIdentifiers(tabHeader);
		tableEmployee = new JTable(model);
		tableEmployee.getRowSelectionAllowed();
		tableEmployee.getSelectionModel();
		tableEmployee.addMouseListener(this);
		scrollEmployee = new JScrollPane(tableEmployee);
		scrollEmployee.setToolTipText("This will be populated once you have chosen one of the above options  ");
		scrollEmployee.setEnabled(false);
		scrollEmployee.setBounds(71, 179, 919, 138);
		add(scrollEmployee);
		
		individualSkillsModel = new DefaultTableModel();
		individualSkillsTable = new JTable(individualSkillsModel);
		individualSkillsTable.getRowSelectionAllowed();
		individualSkillsTable.getSelectionModel();
		individualSkillsTable.addMouseListener(this);
		scrollIndividualSkills = new JScrollPane(individualSkillsTable);
		scrollIndividualSkills.setToolTipText("This will only be populated with chosen Employee's skills once you have entered \"EMPLOYEE search criteria\" option");
		scrollIndividualSkills.setEnabled(false);
		scrollIndividualSkills.setBounds(71, 353, 451, 158);
		add(scrollIndividualSkills);
		scrollIndividualSkills.setViewportView(individualSkillsTable);

		HeaderForSkillsDetails = new String[]{"Knowledge","Standard of Work", "Autonomy", "Coping with Complexity"
				,"Perception of Context", "Growth Capability", "Purposful Collaboration"};
		Object[][] ratingRow = new Object[1][HeaderForSkillsDetails.length];
		modelInsert = new DefaultTableModel(ratingRow, HeaderForSkillsDetails);
		tableSkillsDetails = new JTable(modelInsert);
//		tableSkillsDetails.setAutoResizeMode(tableSkillsDetails.AUTO_RESIZE_OFF);
  		tableSkillsDetails.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);;
		scrollSkillsDetails.setViewportView(tableSkillsDetails);
		tableSkillsDetails.getRowSelectionAllowed();
		tableSkillsDetails.getSelectionModel();
//		tableSkillsDetails.addMouseListener(this);
		
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
			hobbyJlist.removeAll();
			vectHobby.removeAllElements();
			hobbyJlist.updateUI();	
			
			individualSkillsTable = new JTable();
			scrollIndividualSkills.setViewportView(individualSkillsTable);	
			
			tableSkillsDetails = new JTable();
			scrollSkillsDetails.setViewportView(tableSkillsDetails);

		}

	
//---------------------------------  EMPLOYEE
			
		if (source == empBut)
		{	
//			check if user has entered search criteria for the employee search 
//
			if(!inputJTF.getText().isEmpty() ) 
			{


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

			int row = tableEmployee.getSelectedRow();
			String individualEmpID = (String)tableEmployee.getValueAt(row, 0);
			individualEmp = clientControl.searchEmployee(individualEmpID);
	
			
			
//     build Jlist of selected employee's hobbies 
			
			individualEmpHobbyList = individualEmp.get(0).getEmpHobbies();
			vectHobby = new Vector<String>();
			for(int i = 0 ; i < individualEmpHobbyList.size() ; i++)
			{
				for(int j = 0;j < hobbyList.size();j++)
				{
					if (individualEmpHobbyList.get(i) == hobbyList.get(j).getHobbyID())
					{
						vectHobby.addElement(hobbyList.get(j).getHobbyDescription());
					}
				}
			}
			if(individualEmpHobbyList.size() < 1)
			{
				vectHobby.addElement("NO HOBBIES"); 
			}
			else
			{
System.out.println("searchmenu - indivhobby - vect = " + vectHobby.size() + " " +vectHobby.elementAt(0));
			}
			hobbyJlist.setListData(vectHobby);
			hobbyJlist.updateUI();	
			
			
			
//		     build JTable of selected employee's skills	
			
			individualEmpSkillList = clientControl.getEmpSkillByEmpID(individualEmp.get(0).getEmployeeID());
			if(individualEmpSkillList.size()<1)
			{
// display "no skills for selected employee"				
			}
			individualSkillsModel = clientControl.getEmpSkillAverage(individualEmpSkillList);
			scrollIndividualSkills.remove(individualSkillsTable);
			individualSkillsTable = new JTable(individualSkillsModel);
			scrollIndividualSkills.setViewportView(individualSkillsTable);			
			individualSkillsModel.fireTableDataChanged(); 
			this.repaint();
		} //  end of empBut
		

		
		
		if(source == skillComboBox)
		{
			int row = tableEmployee.getSelectedRow();
			String individualEmpID = (String)tableEmployee.getValueAt(row, 0);
			individualEmp = clientControl.searchEmployee(individualEmpID);
		}

//	     build JTable of selected employee's skills	
		
		individualEmpSkillList = clientControl.getEmpSkillByEmpID(individualEmp.get(0).getEmployeeID());
		if(individualEmpSkillList.size()<1)
		{
//  display "no skills for selected employee"
			return;
		}
		
		individualSkillsModel = clientControl.getEmpSkillAverage(individualEmpSkillList);
		scrollIndividualSkills.remove(individualSkillsTable);
		individualSkillsTable = new JTable(individualSkillsModel);
		scrollIndividualSkills.setViewportView(individualSkillsTable);			
		individualSkillsModel.fireTableDataChanged(); 
		this.repaint();
		
//		HeaderForSkillsDetails = new String[]{"Knowledge","Standard of Work", "Autonomy", "Coping with Complexity"
//				,"Perception of Context", "Growth Capability", "Purposful Collaboration"};
//		Object[][] ratingRow = new Object[1][HeaderForSkillsDetails.length];
////		
//		modelInsert = new DefaultTableModel(ratingRow, HeaderForSkillsDetails);
////		
//		tableSkillsDetails = new JTable(modelInsert);
//		tableSkillsDetails.setAutoResizeMode(tableSkillsDetails.AUTO_RESIZE_OFF);
//		tableSkillsDetails.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);;
//		tableSkillsDetails.getRowSelectionAllowed();
//		scrollSkillsDetails.setViewportView(tableSkillsDetails);
//		tableSkillsDetails.getRowSelectionAllowed();
//		tableSkillsDetails.getSelectionModel();
		
//   from ryan's program 
//		HeaderForSkillsDetails = new String[]{"Knowledge","Standard of Work", "Autonomy", "Coping with Complexity"
//				,"Perception of Context", "Growth Capability", "Purposful Collaboration"};
//		Object[][] ratingRow = new Object[1][HeaderForSkillsDetails.length];
////		
//		modelInsert = new DefaultTableModel(ratingRow, HeaderForSkillsDetails);
////		
//		tableSkillsDetails = new JTable(modelInsert);
//		tableSkillsDetails.setAutoResizeMode(tableSkillsDetails.AUTO_RESIZE_OFF);
//		tableSkillsDetails.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);;
//		tableSkillsDetails.getRowSelectionAllowed();
////		
////	
////		
//		scrollSkillsDetails.setViewportView(tableSkillsDetails);
////		
//		tableSkillsDetails.getRowSelectionAllowed();
//		tableSkillsDetails.getSelectionModel();
//		
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

