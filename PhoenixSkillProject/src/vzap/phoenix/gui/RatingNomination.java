package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import vzap.phoenix.DAO.EmployeeSkillDAO;
import vzap.phoenix.DAO.SkillDAO;import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;

public class RatingNomination extends JPanel implements ActionListener
{
	private JLabel selectRaterlbl;
	private JButton btnOutRatings;
	private JButton btnDeleteSkill;
	private JLabel nomineelbl;
	private JTextField rater1JTF;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnSubmit;
	private JTextField rater2JTF;
	private JTextField rater3JTF;
	private JTextField rater4JTF;
	private JTextField rater5JTF;
	private JLabel lblSkill;
	private JTextField rater6JTF;
	private Vector comboSkill, comboRaters;
	private ArrayList<EmployeeSkill> empSkillList;
	private EmpSkillClient empSkillClient;
	private String [] skillHeader;
	private ArrayList<Employee> empList;
	private ArrayList<Skill> skillList;
	private String searchCriteria;
	private JTextField empSearchJTF;
	private JButton btnAdd;
	private JButton btnAdd1;
	private JButton btnAdd2;
	private JButton btnAdd3;
	private JButton btnAdd4;
	private JButton btnAdd5;
	private Object[] empHeader,empRow;
	private DefaultTableModel model;
	private JButton btnSearch;
	private int row;


	/**
	 * Create the panel.
	 */
	public RatingNomination()
	{
		setLayout(null);
		
		empSkillClient = new EmpSkillClient();
        empSkillClient.loginEmployee("A119685","1234");
        System.out.println("RatingNomination - back from loginEmployee ");
		
		empSkillList = empSkillClient.getEmpSkillList();
		skillList = empSkillClient.getSkillList();
        System.out.println("RatingNomination - skillList size - " + empSkillList.size());
        comboSkill = new Vector<>();
        for(int i = 0 ; i < empSkillList.size() ; i++)
        {
            for(int j = 0; j < skillList.size(); j++)
            {
            	if(skillList.get(j).getSkillId() == empSkillList.get(i).getSkillID())
            	{
            		comboSkill.add(skillList.get(j).getSkillDescription());            		
            	}
            }
        }

    	skillHeader = new String[]{"Skill","UserId","First Name","Surname","","","Average"};
		
		model = new DefaultTableModel();
        
        empHeader = new String[]{"UserId","First Name","Surname","Alias"};
        
        model.setColumnIdentifiers(empHeader);
        table = new JTable(model);
		
		selectRaterlbl = new JLabel("Select Raters");
		selectRaterlbl.setFont(new Font("Arial", Font.PLAIN, 19));
		selectRaterlbl.setBounds(268, 11, 142, 32);
		add(selectRaterlbl);
		
		nomineelbl = new JLabel("Rater/Nominee");
		nomineelbl.setFont(new Font("Arial", Font.PLAIN, 14));
		nomineelbl.setBounds(160, 121, 125, 16);
		add(nomineelbl);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 365, 535, 112);
		add(scrollPane);
		
//		table = new JTable();
//		scrollPane.setViewportView(table);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(163, 488, 89, 23);
		add(btnSubmit);
		btnSubmit.addActionListener(this);
		
		btnOutRatings = new JButton("List Outstanding Ratings");
		btnOutRatings.setBounds(10, 331, 184, 23);
		add(btnOutRatings);
		btnOutRatings.addActionListener(this);
		
		rater1JTF = new JTextField();
		rater1JTF.setBounds(109, 148, 202, 20);
		add(rater1JTF);
		rater1JTF.setColumns(10);
		
		rater2JTF = new JTextField();
		rater2JTF.setColumns(10);
		rater2JTF.setBounds(109, 179, 202, 20);
		add(rater2JTF);
		
		rater3JTF = new JTextField();
		rater3JTF.setColumns(10);
		rater3JTF.setBounds(109, 208, 202, 20);
		add(rater3JTF);
		
		rater4JTF = new JTextField();
		rater4JTF.setColumns(10);
		rater4JTF.setBounds(109, 236, 202, 20);
		add(rater4JTF);
		
		rater5JTF = new JTextField();
		rater5JTF.setColumns(10);
		rater5JTF.setBounds(109, 267, 202, 20);
		add(rater5JTF);
		
		rater6JTF = new JTextField();
		rater6JTF.setColumns(10);
		rater6JTF.setBounds(109, 293, 202, 20);
		add(rater6JTF);
		
		lblSkill = new JLabel("Skill");
		lblSkill.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSkill.setBounds(391, 121, 64, 16);
		add(lblSkill);
		
		btnDeleteSkill = new JButton("Delete nomination");
		btnDeleteSkill.setBounds(10, 488, 142, 23);
		add(btnDeleteSkill);
		btnDeleteSkill.addActionListener(this);
		
		JComboBox skill1ComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skill1ComboBox);
		skill1ComboBox.addActionListener(this);
		skill1ComboBox.setBounds(324, 148, 179, 20);
		add(skill1ComboBox);
		
		JComboBox skill2ComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skill2ComboBox);
		skill2ComboBox.addActionListener(this);
		skill2ComboBox.setBounds(324, 179, 179, 20);
		add(skill2ComboBox);
		
		JComboBox skill3ComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skill3ComboBox);
		skill3ComboBox.addActionListener(this);
		skill3ComboBox.setBounds(324, 208, 179, 20);
		add(skill3ComboBox);
		
		JComboBox skill4ComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skill4ComboBox);
		skill4ComboBox.addActionListener(this);
		skill4ComboBox.setBounds(324, 236, 179, 20);
		add(skill4ComboBox);
		
		JComboBox skill5ComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skill5ComboBox);
		skill5ComboBox.addActionListener(this);
		skill5ComboBox.setBounds(324, 264, 179, 20);
		add(skill5ComboBox);
		
		JComboBox skill6ComboBox = new JComboBox(comboSkill);
		AutoCompletion.enable(skill6ComboBox);
		skill6ComboBox.addActionListener(this);
		skill6ComboBox.setBounds(324, 295, 179, 20);
		add(skill6ComboBox);
		
		empSearchJTF = new JTextField();
		empSearchJTF.setBounds(109, 67, 211, 20);
		add(empSearchJTF);
		empSearchJTF.setColumns(10);
		
		btnAdd = new JButton("ADD:");
		btnAdd.setBounds(10, 147, 89, 23);
		btnAdd.addActionListener(this);
		add(btnAdd);
		
		btnAdd1 = new JButton("ADD:");
		btnAdd1.setBounds(10, 178, 89, 23);
		btnAdd1.addActionListener(this);
		add(btnAdd1);
		
		btnAdd2 = new JButton("ADD:");
		btnAdd2.setBounds(10, 207, 89, 23);
		btnAdd2.addActionListener(this);
		add(btnAdd2);
		
		btnAdd3 = new JButton("ADD:");
		btnAdd3.setBounds(10, 235, 89, 23);
		btnAdd3.addActionListener(this);
		add(btnAdd3);
		
		btnAdd4 = new JButton("ADD:");
		btnAdd4.setBounds(10, 263, 89, 23);
		btnAdd4.addActionListener(this);
		add(btnAdd4);
		
		btnAdd5 = new JButton("ADD:");
		btnAdd5.setBounds(10, 292, 89, 23);
		btnAdd5.addActionListener(this);
		add(btnAdd5);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(10, 66, 89, 23);
		btnSearch.addActionListener(this);
		add(btnSearch);
		

        		
		
				
		

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		
		if(source == btnSubmit)
		{
			System.out.println("Submit button was pressed");
			
			
			if(rater1JTF.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "You need to list a skill");
				rater1JTF.grabFocus();
			}
			
//			EmployeeSkillDAO esd = new EmployeeSkillDAO(employeeID);
//			employeeSkillList = esd.getEmpSkillList();
					
		}
		if(source == btnAdd)
        {
                    //model.setColumnIdentifiers(skillHeader);
                    
                   // inputJTF.setText("");
                    
                    
                    row = table.getSelectedRow();
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
                    
                    rater1JTF.setText("("+(String) table.getValueAt(row, 0) +") "+ (String) table.getValueAt(row, 1) +" "+(String) table.getValueAt(row, 2));
                   // System.out.println("Into 2nd IF Statement: value = " + table.getValueAt(row, 1));
                    //ensure the row value is initialised once you done.
                    
                    
                    //deleting something 
                    //model.removeRow(row);
                    
                    row = 0;           

        }
		if(source == btnAdd1)
        {
                    //model.setColumnIdentifiers(skillHeader);
                    
                   // inputJTF.setText("");
                    
                    
                    row = table.getSelectedRow();
                    System.out.println("row = " + row);
                    if (row <0)
                    {
                                JOptionPane.showMessageDialog(this,"Please select a row from the table");
                                return;
                    }
                    System.out.println("value = " + table.getValueAt(row, 0));
                    System.out.println("value = " + table.getValueAt(row, 1));
                    System.out.println("value = " + table.getValueAt(row, 2));
                    
                    rater2JTF.setText("("+(String) table.getValueAt(row, 0) +") "+ (String) table.getValueAt(row, 1) +" "+(String) table.getValueAt(row, 2));
                    row = 0;           

        }
		if(source == btnAdd2)
        {
                    //model.setColumnIdentifiers(skillHeader);
                    
                   // inputJTF.setText("");
                    
                    
                    row = table.getSelectedRow();
                    System.out.println("row = " + row);
                    if (row <0)
                    {
                                JOptionPane.showMessageDialog(this,"Please select a row from the table");
                                return;
                    }
                    System.out.println("value = " + table.getValueAt(row, 0));
                    System.out.println("value = " + table.getValueAt(row, 1));
                    System.out.println("value = " + table.getValueAt(row, 2));
                    
                    rater3JTF.setText("("+(String) table.getValueAt(row, 0) +") "+ (String) table.getValueAt(row, 1) +" "+(String) table.getValueAt(row, 2));
                    row = 0;           

        }
		if(source == btnAdd3)
        {
                    //model.setColumnIdentifiers(skillHeader);
                    
                   // inputJTF.setText("");
                    
                    
                    row = table.getSelectedRow();
                    System.out.println("row = " + row);
                    if (row <0)
                    {
                                JOptionPane.showMessageDialog(this,"Please select a row from the table");
                                return;
                    }
                    System.out.println("value = " + table.getValueAt(row, 0));
                    System.out.println("value = " + table.getValueAt(row, 1));
                    System.out.println("value = " + table.getValueAt(row, 2));
                    
                    rater4JTF.setText("("+(String) table.getValueAt(row, 0) +") "+ (String) table.getValueAt(row, 1) +" "+(String) table.getValueAt(row, 2));
                    row = 0;           

        }
		if(source == btnAdd4)
        {
                    //model.setColumnIdentifiers(skillHeader);
                    
                   // inputJTF.setText("");
                    
                    
                    row = table.getSelectedRow();
                    System.out.println("row = " + row);
                    if (row <0)
                    {
                                JOptionPane.showMessageDialog(this,"Please select a row from the table");
                                return;
                    }
                    System.out.println("value = " + table.getValueAt(row, 0));
                    System.out.println("value = " + table.getValueAt(row, 1));
                    System.out.println("value = " + table.getValueAt(row, 2));
                    
                    rater5JTF.setText("("+(String) table.getValueAt(row, 0) +") "+ (String) table.getValueAt(row, 1) +" "+(String) table.getValueAt(row, 2));
                    row = 0;           

        }
		if(source == btnAdd5)
        {
                    //model.setColumnIdentifiers(skillHeader);
                    
                   // inputJTF.setText("");
                    
                    
                    row = table.getSelectedRow();
                    System.out.println("row = " + row);
                    if (row <0)
                    {
                                JOptionPane.showMessageDialog(this,"Please select a row from the table");
                                return;
                    }
                    System.out.println("value = " + table.getValueAt(row, 0));
                    System.out.println("value = " + table.getValueAt(row, 1));
                    System.out.println("value = " + table.getValueAt(row, 2));
                    
                    rater6JTF.setText("("+(String) table.getValueAt(row, 0) +") "+ (String) table.getValueAt(row, 1) +" "+(String) table.getValueAt(row, 2));
                    row = 0;           

        }
		if(source == btnSearch)
		{
			searchCriteria = empSearchJTF.getText();
	        
			empList = empSkillClient.searchEmployee(empSearchJTF.getText());
	        System.out.println("RatingNomination - Employee size - " + empList.size());
	        empRow = new Object[empList.size()];
	        comboRaters = new Vector<>();
	        for(int i = 0 ; i < empList.size() ; i++)
	        {
	                    System.out.println("RatingNomination - skillList for -  " + i + " desc " + empList.get(i).getEmployeeID());
                        empRow[0] = empList.get(i).getEmployeeID();
                        empRow[1] = empList.get(i).getFirstName();
                        empRow[2] = empList.get(i).getSurname();
                        empRow[3] = empList.get(i).getAlias();
                        model.addRow(empRow);
                        
	        }
	        
		}
		
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Admin GUI");
		frame.setSize(700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RatingNomination panel = new RatingNomination();
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
}
