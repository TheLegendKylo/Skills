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
  	private Vector comboItems = null;
	private ArrayList<EmployeeSkill> skillList;
	
	EmpSkillClient empSkillClient;
	private JButton empBut;
	private JButton skillBut;
	private JButton clearBut;
	private JScrollPane scrollPane;
	private JTable table;
	
	private DefaultTableModel model=null;
	private JComboBox hobbyComboBox;
	
	
	public SearchMenu()
	{
		
		empSkillClient = new EmpSkillClient();
		empSkillClient.loginEmployee("A043410","1234");
		System.out.println("searchcriteria - back from loginEmployee ");
		capabilityList = empSkillClient.getCapabilityList();
		hobbyList = empSkillClient.getHobbyList();
		comboItems = new Vector<>();
		for(int i = 0 ; i < hobbyList.size();i++)
		{
			comboItems.add(hobbyList.get(i).getHobbyDescription());
		}
		
		
		
        empHeader = new String[]{"UserId","First Name","Surname","Alias"};
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
		inputJTF.setBounds(189, 21, 346, 20);
		inputJTF.setColumns(10);
		add(inputJTF);
		
		
		clearBut = new JButton("CLEAR");
		clearBut.setBounds(636, 20, 114, 23);
		clearBut.addActionListener(this);
		add(clearBut);
		
		empBut = new JButton("EMPLOYEE ");
		empBut.setBounds(42, 20, 124, 23);
		empBut.addActionListener(this);
		add(empBut);
		
		skillBut = new JButton("SKILL ");
		skillBut.setBounds(199, 50, 124, 23);
		skillBut.addActionListener(this);
		add(skillBut);
		
		
		model = new DefaultTableModel();
		
		Object[] empHeader = new String[]{"UserId","First Name","Surname","Alias"};
		
		model.setColumnIdentifiers(empHeader);
		table = new JTable(model);
//		model.setColumnIdentifiers(empHeader);
		
		
		
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(73, 140, 751, 138);
		add(scrollPane);
		 
		hobbyComboBox = new JComboBox(comboItems);
		AutoCompletion.enable(hobbyComboBox);
		
		hobbyComboBox.setBounds(202, 84, 124, 20);
		add(hobbyComboBox);
		//scrollPane.setViewportView(table);

	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == clearBut)
		{
			model.setColumnIdentifiers(skillHeader);
			
			inputJTF.setText("");
			
//			tablePanel.removeAll();
//			tablePanel.validate();
//			tablePanel.repaint();
//			table = new JTable();
//			scrollPane = new JScrollPane();
//			scrollPane.setViewportView(table);
//			tablePanel.add(scrollPane);
//			tablePanel.validate();
//			tablePanel.repaint();
		}
		
		

		if(inputJTF.getText().isEmpty() ) 
		{
			JOptionPane.showMessageDialog(this,  "Please enter search criteria");
		}
		
			
		if (source == empBut)
		{				
			System.out.println("searchmenu - employee " + inputJTF.getText());
			employeeList = null;
			employeeList = empSkillClient.searchEmployee(inputJTF.getText());
			System.out.println("searchmenu - employee size" + employeeList.size());						
			
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
		if(source == skillBut)
		{
			System.out.println("searchcriteria - skills .. emp " + inputJTF.getText());
			skillList = empSkillClient.searchEmployeeSkill(inputJTF.getText());
			System.out.println("searchcriteria - employee skill size" + skillList.size());						
			
			Object[][] empRow = new Object[skillList.size()][2];
	        String[] empHeader = new String[]{"EmployeeId","Skill ID"/*,"Surname","Alias"**/};
	        for (int i=0; i<skillList.size(); i++)
	        {				            
	    	   	empRow[i][0]=skillList.get(i).getEmployeeID();
	    	   	empRow[i][1]=skillList.get(i).getEmpSkillID();
//			    	   	empRow[i][2]=skillList.get(i).getSurname();
//			    	   	empRow[i][3]=skillList.get(i).getAlias();
	        }
//					tablePanel.removeAll();
//					tablePanel.validate();
//					tablePanel.repaint();
//					table = new JTable(skillRows,skillHeader);
//					table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
//					scrollPane = new JScrollPane();
//					scrollPane.setViewportView(table);
//					tablePanel.add(scrollPane);
//					tablePanel.validate();
//					tablePanel.repaint();	
		}
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

