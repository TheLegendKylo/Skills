package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class SearchMenu extends JPanel implements ActionListener
{
	

	String [] empHeader;
	String [] [] empRows;
	String [] skillHeader;
	String [] [] skillRows;
	String [] hobbyHeader;
	String [] [] hobbyRows;
	
	String [] capability; 
	private JTextField inputJTF;
	
	private ArrayList<Capability> capabilityList;
	private ArrayList<Employee> employeeList; 
  	private Vector<Hobby> hobbyList;
  	private Vector comboHobby = null;
	private ArrayList<EmployeeSkill> empSkillList;
	private ArrayList<Skill> skillList;
 	private Vector comboSkill = null;
	
	EmpSkillClient empSkillClient;
	private JButton empBut;
	private JButton skillBut;
	private JButton clearBut;
	private JScrollPane scrollPane;
	private JTable table;
	
	private DefaultTableModel model=null;
	private JComboBox hobbyComboBox;
	private JLabel hobbyLab;
	private JLabel skillLab;
	private JComboBox skillComboBox;
	private JLabel lblEnterEmployeeSearch;
	
	
	public SearchMenu()
	{
		
		empSkillClient = new EmpSkillClient();
		empSkillClient.loginEmployee("A043410","1234");
		System.out.println("searchcriteria - back from loginEmployee ");
		capabilityList = empSkillClient.getCapabilityList();
		
		hobbyList = empSkillClient.getHobbyList();
		comboHobby = new Vector<>();
		for(int i = 0 ; i < hobbyList.size();i++)
		{
			comboHobby.add(hobbyList.get(i).getHobbyDescription());
		}
		
		skillList = empSkillClient.getSkillList();
		System.out.println("searchmenu - skillList size - " + skillList.size());
		comboSkill = new Vector<>();
		for(int i = 0 ; i < skillList.size() ; i++)
		{
			System.out.println("searchmenu - skilllist for -  " + i + " desc " + skillList.get(i).getSkillDescription());
			comboSkill.add(skillList.get(i).getSkillDescription());	
		}

		skillHeader = new String[]{"Skill","UserId","First Name","Surname","","","Average"};
		hobbyHeader = new String[]{"HOBBY"};
		setLayout(null);
//		for(int pos = 0; pos<7; pos++)
//		{
//			skillHeader[pos] = capabilityList.get
//		}
//			

//		skillRows = new String[][]{ {"java","a043410","patsy","de kock","1","2"},
//									{"java","a043410","patsy","de Kock","2","1"} };
									
		inputJTF = new JTextField();
		inputJTF.setBounds(277, 21, 346, 20);
		inputJTF.setColumns(10);
		add(inputJTF);

		clearBut = new JButton("CLEAR");
		clearBut.setBounds(829, 54, 114, 23);
		clearBut.addActionListener(this);
		add(clearBut);
		
		empBut = new JButton("EMPLOYEE ");
		empBut.setBounds(698, 20, 124, 23);
		empBut.addActionListener(this);
		add(empBut);
		
		skillBut = new JButton("SKILL ");
		skillBut.setBounds(695, 54, 124, 23);
		skillBut.addActionListener(this);
		add(skillBut);
		
		model = new DefaultTableModel();
		
		Object[] empHeader = new String[]{"UserId","First Name","Surname","Alias"};
		
		model.setColumnIdentifiers(empHeader);
		table = new JTable(model);
//		model.setColumnIdentifiers(empHeader);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(73, 140, 870, 138);
		add(scrollPane);
		 
		hobbyComboBox = new JComboBox(comboHobby);
		AutoCompletion.enable(hobbyComboBox);
		hobbyComboBox.addActionListener(this);
		hobbyComboBox.setBounds(277, 84, 346, 20);
		add(hobbyComboBox);
		
		hobbyLab = new JLabel("Choose HOBBY from drop down");
		hobbyLab.setBounds(42, 87, 177, 14);
		add(hobbyLab);
		
		skillLab = new JLabel("Choose SKILL from drop down");
		skillLab.setBounds(42, 58, 156, 14);
		add(skillLab);
		
		skillComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skillComboBox);
		skillComboBox.addActionListener(this);
		skillComboBox.setBounds(277, 55, 346, 20);
		add(skillComboBox);
		
		lblEnterEmployeeSearch = new JLabel("enter EMPLOYEE search criteria");
		lblEnterEmployeeSearch.setBounds(42, 24, 177, 14);
		add(lblEnterEmployeeSearch);
		//scrollPane.setViewportView(table);

	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == clearBut)
		{
			//model.setColumnIdentifiers(skillHeader);
			
			inputJTF.setText("");
			
//  kyle's code ... commented out as this is used in hobby and skill selection
//
//			int row = table.getSelectedRow();
//			System.out.println("row = " + row);
//			if (row <0)
//			{
//				JOptionPane.showMessageDialog(this,"Please select a row from the table");
//				return;
//			}
//			System.out.println("value = " + table.getValueAt(row, 0));
//			System.out.println("value = " + table.getValueAt(row, 1));
//			System.out.println("value = " + table.getValueAt(row, 2));
//			
//			//get your userid / relevant info
//			//call database with info and do as you wish.
//			
//			//ensure the row value is initialised once you done.
//			row = 0;				
//			
//			//deleting something 
//			model.removeRow(row);
//			
//			row = 0;	

		}

	
		if(source == hobbyComboBox)
		{
			System.out.println("searchmenu - hobbycombobox - index " + hobbyComboBox.getSelectedIndex() );
			int x = hobbyComboBox.getSelectedIndex();
			
			employeeList = null;
			System.out.println("searchmenu - employee hobby search " + hobbyList.get(x).getHobbyDescription());
//  		employeeList = empSkillClient.searchEmployeeHobby(hobbyList.get(i).getHobbyID());
// 			employeeList = empSkillClient.searchEmployee(inputJTF.getText());
			System.out.println("searchmenu - employee hobby search size" + employeeList.size());						
			
			Object[] empRow = new Object[employeeList.size()];
			for (int i=0; i < employeeList.size(); i++)
	        {	
	    	   	empRow[0] = employeeList.get(i).getEmployeeID();
	    	   	empRow[1] = employeeList.get(i).getFirstName();
	    	   	empRow[2] = employeeList.get(i).getSurname();
	    	   	empRow[3] = employeeList.get(i).getAlias();
	    	   	model.addRow(empRow);
	        }
			
			int row = table.getSelectedRow();
			System.out.println("row = " + row);
			if (row <0)
			{
				JOptionPane.showMessageDialog(this,"Please select a row from the table");
				return;
			}
			System.out.println("value = " + table.getValueAt(row, 0));
			System.out.println("value = " + table.getValueAt(row, 1));
			System.out.println("value = " + table.getValueAt(row, 2));
			
			//get your userid / relevant info
			//call database with info and do as you wish.
			
			//ensure the row value is initialised once you done.
			row = 0;				
		}

			
		if (source == empBut)
		{	
//			check if user has entered search criteria for the employee search 
//
			if(inputJTF.getText().isEmpty() ) 
			{
				JOptionPane.showMessageDialog(this,  "Please enter search criteria");
			}
//			
			System.out.println("searchmenu - employee search " + inputJTF.getText());
			employeeList = null;
			employeeList = empSkillClient.searchEmployee(inputJTF.getText());
			System.out.println("searchmenu - employee search size" + employeeList.size());						
			
			Object[] empRow = new Object[employeeList.size()];
	       // JTableHeader empHeader = new String[]{"UserId","First Name","Surname","Alias"};
	        //table
			for (int i=0; i < employeeList.size(); i++)
	        {	
                  		            
	    	   	empRow[0] = employeeList.get(i).getEmployeeID();
	    	   	empRow[1] = employeeList.get(i).getFirstName();
	    	   	empRow[2] = employeeList.get(i).getSurname();
	    	   	empRow[3] = employeeList.get(i).getAlias();
	    	   	model.addRow(empRow);
	        }
//			tablePanel.removeAll();
//			tablePanel.validate();
//			tablePanel.repaint();
//			table.setCellSelectionEnabled(true);
//			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//table = new JTable(empRow,empHeader);
	       
	        
//			table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
//			scrollPane = new JScrollPane();
//			scrollPane.setViewportView(table);
//			tablePanel.add(scrollPane);
//			tablePanel.validate();
//			tablePanel.repaint();
			
//			int rowIndex = table.getSelectedRow();
//			int colIndex = table.getSelectedColumn();
//			System.out.println("IN CELL" + table.getValueAt(rowIndex, colIndex) );
		}
		if(source == skillComboBox)
		{
			System.out.println("Run run run we in combobox");
			empSkillList = new ArrayList<>();
			System.out.println("searchmenu - skillcombobox - index " + skillComboBox.getSelectedIndex() );
			int x = skillComboBox.getSelectedIndex();
			int y = skillList.get(x).getSkillId();
			System.out.println("searchmenu - employee skill search " + skillList.get(x).getSkillDescription());
			System.out.println("searchmenu - employee skill search " + skillList.get(x).getSkillId());
			
    		empSkillList = empSkillClient.searchEmployeeSkill(y);
//// 			employeeList = empSkillClient.searchEmployee(inputJTF.getText());
								
			
			Object[] empRow = new Object[empSkillList.size()];
			for (int i=0; i < empSkillList.size(); i++)
	        {	
	    	   	empRow[0] = empSkillList.get(i).getEmployeeID();
	    	   	empRow[1] = empSkillList.get(i).getSkillID();
	    	   	//empRow[2] = empSkillList.get(i).getSurname();
	    	   	//empRow[3] = empSkillList.get(i).getAlias();
	    	   	model.addRow(empRow);
	        }
		}
//			System.out.println("searchcriteria - employee skills " + inputJTF.getText());
//			empSkillList = empSkillClient.searchEmployeeSkill(inputJTF.getText());
//			System.out.println("searchcriteria - employee skill size" + skillList.size());						
//			
//			Object[][] empRow = new Object[skillList.size()][2];
//	        String[] empHeader = new String[]{"EmployeeId","Skill ID"/*,"Surname","Alias"**/};
//	        for (int i=0; i<skillList.size(); i++)
//	        {				            
//	    	   	empRow[i][0]=empSkillList.get(i).getEmployeeID();
//	    	   	empRow[i][1]=empSkillList.get(i).getEmpSkillID();
////			    	   	empRow[i][2]=skillList.get(i).getSurname();
////			    	   	empRow[i][3]=skillList.get(i).getAlias();
//	        }
//
//		}
	}//end of action performed
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 700);
		frame.getContentPane().add(new SearchMenu());
		frame.setVisible(true);

	}
}

