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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

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
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.SwingConstants;
// mport javax.swing.UIManager;

public class SearchMenu extends JPanel implements ActionListener, MouseListener
{
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
	
	private DefaultTableModel model=null;
	private DefaultTableModel modelInsert=null;
	private DefaultTableModel individualSkillsModel=null;
	
	private JComboBox hobbyComboBox;
	 	
	private Employee emp=null;
  	private EmpSkillClientController clientControl;
  	
	private JButton empBut;
	
	
	private JLabel hobbyLab;
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
	private JTable individualSkillsTable_1;
	
	private JTextField inputJTF;
	
//	Object[] HeaderForSkillsDetails;
	private JLabel skillsDetailsLab;
	
	public SearchMenu(EmpSkillClientController clientControl, Employee emp)
	{
//		
		this.clientControl = clientControl;
//		 incase you want to do anything with the logged on employee
		this.emp = emp;
		setLayout(null);
	
		vectHobby = new Vector<String>();
		vectSkill = new Vector<String>();
		vectTabSkill = new Vector<String>();
		comboHobby = new Vector<>();
		comboSkill = new Vector<>();
		
									
		inputJTF = new JTextField();
		inputJTF.setToolTipText("Enter your employee search criteria ..... and then click on the \"search EMPLOYEE\" button");
		inputJTF.setBounds(641, 11, 346, 29);
		inputJTF.setColumns(10);
		inputJTF.addActionListener(this);
		add(inputJTF);

		
		empBut = new JButton("search EMPLOYEE ");
		empBut.setFont(new Font("Arial", Font.BOLD, 15));
		empBut.setBounds(1009, 9, 199, 30);
		empBut.addActionListener(this);
		add(empBut);
		 
		hobbyComboBox = new JComboBox(comboHobby);
		hobbyComboBox.setFont(new Font("Arial", Font.BOLD, 15));
		hobbyComboBox.setBorder(null);
		AutoCompletion.enable(hobbyComboBox);
		hobbyComboBox.addActionListener(this);
		hobbyComboBox.setBounds(641, 51, 346, 32);
		add(hobbyComboBox);
		
		hobbyLab = new JLabel("Choose HOBBY from dropdown list");
		hobbyLab.setFont(new Font("Arial", Font.BOLD, 15));
		hobbyLab.setBounds(343, 54, 267, 29);
		add(hobbyLab);
		
		lblEnterEmployeeSearch = new JLabel("Enter EMPLOYEE search criteria");
		lblEnterEmployeeSearch.setFont(new Font("Arial", Font.BOLD, 15));
		lblEnterEmployeeSearch.setBounds(346, 11, 278, 27);
		add(lblEnterEmployeeSearch);
		
		contentsOfTable = new JLabel("");
		contentsOfTable.setHorizontalAlignment(SwingConstants.CENTER);
		contentsOfTable.setFont(new Font("Arial", Font.BOLD, 15));
		contentsOfTable.setBorder(null);
		contentsOfTable.setBounds(501, 112, 628, 37);
		add(contentsOfTable);
		

		scrollHobby = new JScrollPane();
		scrollHobby.setBounds(1425, 327, 309, 158);
		add(scrollHobby);
		
		individualSkillsModel = new DefaultTableModel();
		individualSkillsTable = new JTable(individualSkillsModel);
				
		hobbyJlist = new JList(vectHobby);
		hobbyJlist.setFont(new Font("Arial", Font.BOLD, 15));
		hobbyJlist.setToolTipText("This will only be populated with chosen Employee's hobbies once you have entered \"EMPLOYEE search criteria\" option");
		hobbyJlist.setEnabled(false);
		scrollHobby.setViewportView(hobbyJlist);
		
		individualSkillListLab = new JLabel("Chosen Employee's SKILL list ");
		individualSkillListLab.setFont(new Font("Arial", Font.BOLD, 20));
		individualSkillListLab.setToolTipText("This will lsit ALL skills for chosen Employee");
		individualSkillListLab.setBounds(579, 295, 294, 30);
		add(individualSkillListLab);
		
		individualHobbyListLab = new JLabel("Chosen Employee's HOBBY list");
		individualHobbyListLab.setToolTipText("This will show all chosen Employee's hobbies");
		individualHobbyListLab.setFont(new Font("Arial", Font.BOLD, 20));
		individualHobbyListLab.setBounds(1425, 302, 309, 23);
		add(individualHobbyListLab);
		
		scrollSkillsDetails = new JScrollPane();
		scrollSkillsDetails.setToolTipText("This will give capability ratings for chosen Employee's skill");
		scrollSkillsDetails.setBounds(10, 521, 1724, 137);
		add(scrollSkillsDetails);
		
		skillsDetailsLab = new JLabel("Chosen Employee skill's RATING");
		skillsDetailsLab.setFont(new Font("Arial", Font.BOLD, 20));
		skillsDetailsLab.setToolTipText("This will give capability ratings for chosen Employee's skill");
		skillsDetailsLab.setBounds(574, 487, 346, 35);
		add(skillsDetailsLab);
		

		model = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		Object[] tabHeader = new String[]{"UserId","First Name","Surname","Alias","Contact"};
		model.setColumnIdentifiers(tabHeader);
		tableEmployee = new JTable(model);
		tableEmployee.setFont(new Font("Arial", Font.PLAIN, 15));
		tableEmployee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEmployee.addMouseListener(this);
		tableEmployee.setRowSorter(new TableRowSorter(model));

		
		tableEmployee.setAutoCreateRowSorter(true); 
		tableEmployee.setAutoCreateRowSorter(isEnabled());
		scrollEmployee = new JScrollPane(tableEmployee);
		scrollEmployee.setToolTipText("This will be populated once you have chosen one of the above options  ");
		scrollEmployee.setEnabled(false);
		scrollEmployee.setBounds(24, 160, 1710, 138);
		add(scrollEmployee);

		individualSkillsTable_1 = new JTable(individualSkillsModel);
		individualSkillsTable_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		individualSkillsTable_1.getRowSelectionAllowed();
		individualSkillsTable_1.addMouseListener(this);
		scrollIndividualSkills = new JScrollPane(individualSkillsTable_1);
		scrollIndividualSkills.setToolTipText("This will only be populated with chosen Employee's skills once you have entered \"EMPLOYEE search criteria\" option");
		scrollIndividualSkills.setEnabled(false);
		scrollIndividualSkills.setBounds(24, 327, 1386, 152);
		add(scrollIndividualSkills);
		scrollIndividualSkills.setViewportView(individualSkillsTable_1);

		tableSkillsDetails = new JTable(modelInsert){
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		    	Component comp = super.prepareRenderer(renderer, row, col);
	            comp.setBackground(Color.WHITE);

		    	if(col>2 && col<11)
		        {
			        double dValue = (Double.parseDouble(getModel().getValueAt(row, col).toString()));
			        int value = (int)dValue;
			        switch (value)
			        {
				        case 1:
				        {
				            comp.setBackground(new Color(255,91,13));
				            break;
				        }
				        case 2:
				        {
				            comp.setBackground(new Color(255,172,117));
				            break;
				        }
				        case 3:
				        {
				            comp.setBackground(new Color(176,255,176));
				            break;
				        }
				        case 4:
				        {
				            comp.setBackground(new Color(0,202,0));
				            break;
				        }
				        case 5:
				        {
				            comp.setBackground(new Color(0,136,0));
				            break;
				        }
			        }
			    }
		        return comp;
	        }
		};

		tableSkillsDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  		tableSkillsDetails.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);;
		scrollSkillsDetails.setViewportView(tableSkillsDetails);
		tableSkillsDetails.getTableHeader().setPreferredSize(new Dimension(tableSkillsDetails.getColumnModel().getTotalColumnWidth(),32));
		tableSkillsDetails.getRowSelectionAllowed();
		tableSkillsDetails.getSelectionModel();
		
		setup();
		
		tableEmployee.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		
	}
	public void setup()
	{
		// must add database calls here
		vectHobby.clear();
		vectSkill.clear();
		vectTabSkill.clear();
		comboHobby.clear();
		comboSkill.clear();
		hobbyJlist.removeAll();
		
		
		capabilityList = clientControl.getCapabilityList();
		model.setRowCount(0);
		
		if(modelInsert != null)
		{
			modelInsert.setRowCount(0);
		}
		if(individualSkillsModel != null)
		{
			individualSkillsModel.setRowCount(0);
		}
		//modelInsert.setRowCount(0);
		
		contentsOfTable.setText(" ");			
		inputJTF.setText("");
		
		hobbyList = clientControl.getHobbyList();
		
		System.out.println("hobby size: " + hobbyList.size());
		
		for(int i = 0 ; i < hobbyList.size();i++)
		{
			comboHobby.add(hobbyList.get(i).getHobbyDescription());
		}
		
		skillList = clientControl.getSkillList();
		
		for(int i = 0 ; i < skillList.size() ; i++)
		{
			comboSkill.add(skillList.get(i).getSkillDescription());	
		}


	}
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		source = ae.getSource();
			
		if (source == empBut)
		{	
			vectHobby.clear(); 
//			check if user has entered search criteria for the employee search 
			model.setRowCount(0);
			if(modelInsert != null)
			{
				modelInsert.setRowCount(0);
			}
			if(individualSkillsModel != null)
			{
				individualSkillsModel.setRowCount(0);
			}
			
			if(!inputJTF.getText().isEmpty() ) 
			{
				employeeList = null;
				employeeList = clientControl.searchEmployee(inputJTF.getText());
					
				if(employeeList.size() == 0)
				{
					JOptionPane.showMessageDialog(this,"No employees found with your search criteria");
					return;				
				}	
				
                String heading1 = "List of EMPLOYEES that match your search criteria \"";
                String heading2 = inputJTF.getText();
                String heading3 = "\"";
                String completeHeading = heading1 + heading2 + heading3;
				contentsOfTable.setText(completeHeading);
				contentsOfTable.setFont(new Font("Arial", Font.BOLD, 20));
				
				Object[] tabCols = new Object[5];
				for (int i = 0; i < employeeList.size(); i++)
		        {	
							                  		            
		    	   	tabCols[0] = employeeList.get(i).getEmployeeID();
		    	   	tabCols[1] = employeeList.get(i).getFirstName();
		    	   	tabCols[2] = employeeList.get(i).getSurname();
		    	   	tabCols[3] = employeeList.get(i).getAlias();
		    	   	tabCols[4] = employeeList.get(i).getContactNo();
		    	   	model.addRow(tabCols);
		        }
				JOptionPane.showMessageDialog(this,"Please select a row from the listed Employees");
			}
			else
			{
				JOptionPane.showMessageDialog(this,  "Please enter search criteria");
			}
			inputJTF.setText("");
		}
		
		
		
//--------------------------    HOBBY 	
		
		if(source == inputJTF)
		{
			empBut.doClick();
		}
		if(source == hobbyComboBox)
		{
			model.setRowCount(0);
			vectHobby.clear();
			hobbyJlist.removeAll();
			if(modelInsert != null)
			{
				modelInsert.setRowCount(0);
			}
			if(individualSkillsModel != null)
			{
				individualSkillsModel.setRowCount(0);
			}
//		Hobby combobox was setup at the beginning of class - AutoCompletion was enabled at beginning.
//		when you come into this IF statement it means user has selected a hobby to search
			
			int x = hobbyComboBox.getSelectedIndex();			
//		clear the employee arraylist and go and get all the employees that partake in this hobby		
			employeeList = null;
			employeeList = clientControl.searchEmployeeHobby(hobbyList.get(x).getHobbyID());

			if(employeeList.size() == 0)
			{
				JOptionPane.showMessageDialog(this,"No employees have this hobby");
				//contentsOfTable.setText(" ");
				return;
			}
//		declare 4 column in the table 			
			Object[] tabCols = new Object[5];
			
//		go thru the employeeList array and move information into the table 			
			for (int i = 0; i < employeeList.size(); i++)
	        {	
                String heading1 = "List of EMPLOYEES that have \"";
                String heading2 = hobbyList.get(x).getHobbyDescription();
                String heading3 = "\" as a hobby";
                String completeHeading = heading1 + heading2 + heading3;
				contentsOfTable.setText(completeHeading);
				contentsOfTable.setFont(new Font("Arial", Font.BOLD, 20));

	    	   	tabCols[0] = employeeList.get(i).getEmployeeID();
	    	   	tabCols[1] = employeeList.get(i).getFirstName();
	    	   	tabCols[2] = employeeList.get(i).getSurname();
	    	   	tabCols[3] = employeeList.get(i).getAlias();
	    	   	tabCols[4] = employeeList.get(i).getContactNo();
	    	   	model.addRow(tabCols);
	        }
			JOptionPane.showMessageDialog(this,"Please select a row from the listed Employees ");
		}
	}//end of action performed


	@Override
	public void mouseClicked(MouseEvent t)
	{
//		if(source == empBut)
//		{
			vectHobby.clear();
			hobbyJlist.removeAll();
			int row = tableEmployee.getSelectedRow();
			String individualEmpID = (String)tableEmployee.getValueAt(row, 0);
			individualEmp = clientControl.searchEmployee(individualEmpID);
			
			individualEmpHobbyList = individualEmp.get(0).getEmpHobbies();
			
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
			//hobbyJlist.setListData(vectHobby);
			hobbyJlist.updateUI();	
		
			
			
//		     build JTable of selected employee's skills	
			individualEmpSkillList = clientControl.getEmpSkillByEmpID(individualEmp.get(0).getEmployeeID());

			if(individualEmpSkillList.size()<1)
			{
				JOptionPane.showMessageDialog(this,"Employee has no skills ");
				return;			
			}

			individualSkillsModel = clientControl.getEmpSkillAverage(individualEmpSkillList);
			
			scrollIndividualSkills.remove(individualSkillsTable_1);
			individualSkillsTable_1 = new JTable(individualSkillsModel);
			scrollIndividualSkills.setViewportView(individualSkillsTable_1);			
			individualSkillsModel.fireTableDataChanged(); 
			individualSkillsTable_1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
			individualSkillsTable_1.setFont(new Font("Arial", Font.PLAIN, 15));
			this.repaint();
			
			modelInsert = clientControl.getEmpSkillDetail(individualEmpSkillList);
			scrollSkillsDetails.remove(tableSkillsDetails);
			tableSkillsDetails = new JTable(modelInsert){
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		    	Component comp = super.prepareRenderer(renderer, row, col);
	            comp.setBackground(Color.WHITE);

		    	if(col>2 && col<11)
		        {
			        double dValue = (Double.parseDouble(getModel().getValueAt(row, col).toString()));
			        int value = (int)dValue;
			        switch (value)
			        {
				        case 1:
				        {
				            comp.setBackground(new Color(255,91,13));
				            break;
				        }
				        case 2:
				        {
				            comp.setBackground(new Color(255,172,117));
				            break;
				        }
				        case 3:
				        {
				            comp.setBackground(new Color(176,255,176));
				            break;
				        }
				        case 4:
				        {
				            comp.setBackground(new Color(0,202,0));
				            break;
				        }
				        case 5:
				        {
				            comp.setBackground(new Color(0,136,0));
				            break;
				        }
			        }
			    }
		        return comp;
	        }
		};
			scrollSkillsDetails.setViewportView(tableSkillsDetails);
			modelInsert.fireTableDataChanged();
			tableSkillsDetails.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
			tableSkillsDetails.setFont(new Font("Arial", Font.PLAIN, 15));
			this.repaint();
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

