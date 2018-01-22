package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Popup;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.*;

import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class SearchCriteria extends JPanel implements ActionListener, KeyListener
{
	private JPanel tablePanel;

	private JTable table;
	private JScrollPane scrollPane;
	
	String [] [] empData;
	String [] empColumnNames;
	String [] skillHeader;
	String [] [] skillRows;
	
	String [] capability; 
	
	private JLabel searchBySkillLab;
	private JTextField searchBySkillJTF;
	private JLabel searchbybemployeeLab;
	private JTextField searchByEmployeeJTF;
	private JButton empSearchBut;
	private JLabel searchByHobbyLab;
	private JTextField searchByHobbyJTF;
	
	private ArrayList<Capability> capabilityList;
	private ArrayList<Employee> employeeList; 
//	private ArrayList<Employee> employeeList; 
	EmpSkillClient empSkillClient;
	private JButton clearBut;
	
	public SearchCriteria()
	{
		
		tablePanel = new JPanel();
		
		empSkillClient = new EmpSkillClient();
		empSkillClient.loginEmployee("A043410","1234");
		System.out.println("searchcriteria - back from loginEmployee ");
		capabilityList = empSkillClient.getCapabilityList();
		
	
		
        String[] empHeader = new String[]{"UserId","First Name","Surname","Alias"};
		String [] skillHeader = new String[]{"Skill","UserId","First Name","Surname","","","Average"};
//		for(int pos = 0; pos<7; pos++)
//		{
//			skillHeader[pos] = capabilityList.get
//		}
//			

		skillRows = new String[][]{ {"java","a043410","patsy","de kock","1","2"},
									{"java","a043410","patsy","de Kock","2","1"} };
//		                        	{"java","c40989","gerald","hammond","2.6"} };
		                        	

		                        	
		searchbybemployeeLab = new JLabel("Search by employee");
		searchByEmployeeJTF = new JTextField();
		searchByEmployeeJTF.setColumns(10);
		searchByEmployeeJTF.addKeyListener(this);
		
		empSearchBut = new JButton("GO");
		empSearchBut.addActionListener(this);
		
		searchBySkillLab = new JLabel("Search by skill");
		searchBySkillJTF = new JTextField();
		searchBySkillJTF.setColumns(10);
		searchBySkillJTF.addKeyListener(this);
		
		searchByHobbyLab = new JLabel("Search by hobby ");
		searchByHobbyJTF = new JTextField();
		searchByHobbyJTF.setColumns(10);
		searchByHobbyJTF.addKeyListener(this);
		
		clearBut = new JButton("CLEAR");
		clearBut.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(54)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(searchbybemployeeLab)
								.addComponent(searchBySkillLab)
								.addComponent(searchByHobbyLab))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(searchBySkillJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(38)
									.addComponent(empSearchBut)
									.addGap(38)
									.addComponent(clearBut))
								.addComponent(searchByEmployeeJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(searchByHobbyJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(93)
							.addComponent(tablePanel, GroupLayout.PREFERRED_SIZE, 479, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(57, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchbybemployeeLab)
						.addComponent(searchByEmployeeJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchBySkillLab)
						.addComponent(searchBySkillJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(empSearchBut)
						.addComponent(clearBut))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchByHobbyLab)
						.addComponent(searchByHobbyJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(158)
					.addComponent(tablePanel, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addGap(26))
		);
		tablePanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == clearBut)
		{
			searchBySkillJTF.setEditable(true);
			searchByHobbyJTF.setEditable(true);
			searchByEmployeeJTF.setEditable(true);
			
			searchByEmployeeJTF.setText("");
			searchBySkillJTF.setText("");
			searchByHobbyJTF.setText("");

			tablePanel.removeAll();
			tablePanel.validate();
			tablePanel.repaint();
			table = new JTable();
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(table);
			tablePanel.add(scrollPane);
			tablePanel.validate();
			tablePanel.repaint();
		}
		
		if (source == empSearchBut)
		{
			if(! (searchByEmployeeJTF.getText().isEmpty() )) 
			{
				System.out.println("searchcriteria - employee " + searchByEmployeeJTF.getText());
				employeeList = empSkillClient.searchEmployee(searchByEmployeeJTF.getText());
				System.out.println("searchcriteria - employee size"); // + employeeList.size());						
				
				Object[][] empRow = new Object[employeeList.size()][4];
		        String[] empHeader = new String[]{"UserId","First Name","Surname","Alias"};
		        for (int i=0; i<employeeList.size(); i++)
		        {				            
		    	   	empRow[i][0]=employeeList.get(i).getEmployeeID();
		    	   	empRow[i][1]=employeeList.get(i).getFirstName();
		    	   	empRow[i][2]=employeeList.get(i).getSurname();
		    	   	empRow[i][3]=employeeList.get(i).getAlias();
		    	   	
		        }
				
				
				tablePanel.removeAll();
				tablePanel.validate();
				tablePanel.repaint();
				table = new JTable(empRow,empHeader);
				table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
				scrollPane = new JScrollPane();
				scrollPane.setViewportView(table);
				tablePanel.add(scrollPane);
				tablePanel.validate();
				tablePanel.repaint();
			}
			else
			{
				if(! (searchByHobbyJTF.getText().isEmpty()))
				{
//					System.out.println("searchcriteria - hobbies " + searchByHobbyJTF.getText());
//					employeeList = empSkillClient.searchEmpHobby(searchByHobbyJTF.getText());
//					System.out.println("searchcriteria - hobbiesemployee size"); // + employeeList.size());						
//					
//					Object[][] empRow = new Object[employeeList.size()][4];
//			        String[] empHeader = new String[]{"UserId","First Name","Surname","Alias"};
//			        for (int i=0; i<employeeList.size(); i++)
//			        {				            
//			    	   	empRow[i][0]=employeeList.get(i).getEmployeeID();
//			    	   	empRow[i][1]=employeeList.get(i).getFirstName();
//			    	   	empRow[i][2]=employeeList.get(i).getSurname();
//			    	   	empRow[i][3]=employeeList.get(i).getAlias();
//			    	   	
//			        }
					tablePanel.removeAll();
					tablePanel.validate();
					tablePanel.repaint();
//					table = new JTable(hobbyRows,hobbyHeader);
					table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
					scrollPane = new JScrollPane();
					scrollPane.setViewportView(table);
					tablePanel.add(scrollPane);
					tablePanel.validate();
					tablePanel.repaint();	
				}
				else
				{
				if(! (searchBySkillJTF.getText().isEmpty()))
				{
//					System.out.println("searchcriteria - skills .. emp " + searchBySkillJTF.getText());
//					employeeList = empSkillClient.searchEmpHobby(searchByHobbyJTF.getText());
//					System.out.println("searchcriteria - hobbiesemployee size"); // + employeeList.size());						
//					
//					Object[][] empRow = new Object[employeeList.size()][4];
//			        String[] empHeader = new String[]{"UserId","First Name","Surname","Alias"};
//			        for (int i=0; i<employeeList.size(); i++)
//			        {				            
//			    	   	empRow[i][0]=employeeList.get(i).getEmployeeID();
//			    	   	empRow[i][1]=employeeList.get(i).getFirstName();
//			    	   	empRow[i][2]=employeeList.get(i).getSurname();
//			    	   	empRow[i][3]=employeeList.get(i).getAlias();
//			    	   	
//			        }
					tablePanel.removeAll();
					tablePanel.validate();
					tablePanel.repaint();
					table = new JTable(skillRows,skillHeader);
					table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
					scrollPane = new JScrollPane();
					scrollPane.setViewportView(table);
					tablePanel.add(scrollPane);
					tablePanel.validate();
					tablePanel.repaint();	
				}
				}
			}
		}
	}//end of action performed
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().add(new SearchCriteria());
		frame.setVisible(true);

	}

	@Override
	public void keyPressed(KeyEvent ae)
	{
		Object source = ae.getSource();
		
		if( source == searchByEmployeeJTF)
		{
			searchBySkillJTF.setEditable(false);
			searchByHobbyJTF.setEditable(false);
		}
		
		if( source == searchBySkillJTF)
		{
			searchByEmployeeJTF.setEditable(false);
			searchByHobbyJTF.setEditable(false);
		}
		
		if( source == searchByHobbyJTF)
		{
			searchByEmployeeJTF.setEditable(false);
			searchBySkillJTF.setEditable(false);
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}

