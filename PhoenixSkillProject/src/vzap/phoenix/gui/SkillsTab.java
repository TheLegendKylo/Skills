package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import vzap.phoenix.DAO.EmployeeSkillDAO;
import vzap.phoenix.DAO.SkillDAO;
import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClient;
import vzap.phoenix.client.EmpSkillClientController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SkillsTab extends JPanel implements ActionListener
{
	private JLabel lblSkillTab;
	private JPanel panelTop;
	private JButton btnAddSkill;
	private JButton btnEditSkill;
	private JButton btnDeleteSkill;
	

	private JLabel lblSummaryOfSkills;
	private JTable tableSummarySkills;
	private JScrollPane scrollPaneSummarySkills;
	private JLabel lblDetails;
	private JTable tableBottom;
	private JScrollPane scrollPaneBottom;
	private JButton btnSubmit;
	private EmpSkillClient employeeSkillClient;
	private String addSkill;
	private ArrayList<EmployeeSkill> employeeSkillList;
	private DefaultTableModel model;
	private DefaultTableModel modelInsert;
	private Employee loggedOnEmployee;
	private ArrayList<Skill> skillList;
	private ArrayList<Capability> capabilityList;
	private JScrollPane scrollPaneAddSkill;
	private JTable tableCaptureSkills; 
	
	private Vector<String> vectorSkills = null;
	
	private int getSelectedRow;
	private JLabel lblSkill;
	private JComboBox<String> comboBoxSkillList;
	private EmpSkillClientController clientControl;
	private JComboBox comboBoxRating;
	private String[] ratingValues;
	Object[] HeaderForAddSkill;
	/**
	 * Create the panel.
	 */
	public SkillsTab(EmpSkillClientController clientControl)
	{
		setAutoscrolls(true);
		loggedOnEmployee = clientControl.getLogonEmployee();
		this.clientControl=clientControl;
		
		setLayout(null);
		
		lblSkillTab = new JLabel("Skill Tab");
		lblSkillTab.setBounds(421, 11, 46, 14);
		add(lblSkillTab);
		
		panelTop = new JPanel();
		panelTop.setBounds(10, 35, 928, 33);
		add(panelTop);
		
		btnEditSkill = new JButton("Edit Skill");
		panelTop.add(btnEditSkill);
		btnEditSkill.addActionListener(this);
		
		btnDeleteSkill = new JButton("Delete Skill");
		panelTop.add(btnDeleteSkill);
		btnDeleteSkill.addActionListener(this);
		
		
		lblSummaryOfSkills = new JLabel("Summary of your Skills");
		lblSummaryOfSkills.setBounds(10, 272, 928, 22);
		add(lblSummaryOfSkills);
		lblSummaryOfSkills.setHorizontalAlignment(JLabel.CENTER);
		
		scrollPaneSummarySkills = new JScrollPane();
		scrollPaneSummarySkills.setBounds(10, 305, 928, 92);
		add(scrollPaneSummarySkills);
		
		tableSummarySkills = new JTable();
		scrollPaneSummarySkills.setViewportView(tableSummarySkills);
		
		lblDetails = new JLabel("Details");
		lblDetails.setBounds(10, 408, 928, 22);
		add(lblDetails);
		lblDetails.setHorizontalAlignment(JLabel.CENTER);
		
		scrollPaneBottom = new JScrollPane();
		scrollPaneBottom.setBounds(10, 433, 928, 112);
		add(scrollPaneBottom);
		
		
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(429, 224, 89, 23);
		add(btnSubmit);
		btnSubmit.addActionListener(this);
		
		employeeSkillList = new ArrayList<>();
		
		
		Object[] skillsHeader = new String[]{"Skill","Your Ave Rating","Nominated Average",
				"Number of ratings"};
		
		
		
		employeeSkillList = clientControl.getEmployeeSkillList();
	model = clientControl.getEmpSkillAverage(employeeSkillList);
	skillList = clientControl.getSkillList();

		tableSummarySkills = new JTable(model);
		
		scrollPaneSummarySkills.setViewportView(tableSummarySkills);
		
		scrollPaneAddSkill = new JScrollPane();
		scrollPaneAddSkill.setBounds(193, 159, 745, 44);
		add(scrollPaneAddSkill);
		
		HeaderForAddSkill = new String[]{"Knowledge","Standard of Work", "Autonomy", "Coping with Complexity"
				,"Perception of Context", "Growth Capability", "Purposful Collaboration"};
		Object[][] ratingRow = new Object[1][HeaderForAddSkill.length];
		
		modelInsert = new DefaultTableModel(ratingRow, HeaderForAddSkill);
		
		tableCaptureSkills = new JTable(modelInsert);
		tableCaptureSkills.setAutoResizeMode(tableCaptureSkills.AUTO_RESIZE_OFF);
		tableCaptureSkills.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);;
		tableCaptureSkills.getRowSelectionAllowed();
		
	
		
		scrollPaneAddSkill.setViewportView(tableCaptureSkills);
		
		tableCaptureSkills.getRowSelectionAllowed();
		tableCaptureSkills.getSelectionModel();
		
		lblSkill = new JLabel("Skill");
		lblSkill.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSkill.setBounds(82, 155, 65, 20);
		add(lblSkill);
		
		
		vectorSkills = new Vector<>();
		
		for (int i = 0; i < skillList.size(); i++)
		{
			vectorSkills.addElement(skillList.get(i).getSkillDescription());
		}
		
		comboBoxSkillList = new JComboBox<>(vectorSkills);
		comboBoxSkillList.setToolTipText("If your skill does not exist please press \"ADD SKILL\" button.");
		Collections.sort(vectorSkills);
		comboBoxSkillList.setSelectedIndex(0);
		comboBoxSkillList.setBounds(10, 183, 173, 20);
		add(comboBoxSkillList);
		
		btnAddSkill = new JButton("Add Skill");
		btnAddSkill.setBounds(336, 224, 76, 23);
		add(btnAddSkill);
		btnAddSkill.addActionListener(this);
		
		
		ratingValues = new String[] {"1", "2", "3", "4", "5"};
		comboBoxRating = new JComboBox(ratingValues);
		
		for (int i = 0; i < HeaderForAddSkill.length; i++)
		{
			tableCaptureSkills.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(comboBoxRating) );
		}
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source == btnAddSkill)
		{
			String description = JOptionPane.showInputDialog(this, "Please enter new Skill");
			short skillID = clientControl.addSkill(description);
						
			if(! (skillID ==0))
			{
				String employeeID = loggedOnEmployee.getEmployeeID();
				String raterID = employeeID;
				Date createdDate = new Date();
				
				EmployeeSkill employeeSkill = new EmployeeSkill(employeeID,skillID,raterID, createdDate);
				boolean success = clientControl.addEmployeeSkill(employeeSkill);
				employeeSkillList = clientControl.getEmployeeSkillList();
				Object[] newRow = new Object[]{description, 0, 0, 0};
				model.addRow(newRow);
				model.fireTableDataChanged();
				
				vectorSkills.addElement(description);
				Collections.sort(vectorSkills);
				clientControl.getSkillList();
				JOptionPane.showMessageDialog(this, "Successfully added " + description +" skill");
				comboBoxSkillList.removeAll();
				DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vectorSkills);
				comboBoxSkillList.setModel(dcbm);
				this.repaint();
			}
			else
			{
				JOptionPane.showMessageDialog(this, "No Skill Added");
				
				
			}
			
		}
		
		/*
		 * This submit button will be used to submit your final rating...
		 */
		
		if(source == btnSubmit)
		{
			System.out.println("Submit button was pressed");
			String employeeID = loggedOnEmployee.getEmployeeID();
			String raterID = employeeID;
			Date createdDate = new Date();
			
			String selectedCombo = comboBoxSkillList.getSelectedItem().toString();
			short skillID = clientControl.addHobby(selectedCombo);
			
			EmployeeSkill employeeSkill = new EmployeeSkill(employeeID,skillID,raterID, createdDate);
			boolean success = clientControl.addEmployeeSkill(employeeSkill);
			employeeSkillList = clientControl.getEmployeeSkillList();
			Object[] newRow = new Object[]{selectedCombo, 0, 0, 0};
			model.addRow(newRow);
			model.fireTableDataChanged();
			
			vectorSkills.addElement(selectedCombo);
			Collections.sort(vectorSkills);
			clientControl.getSkillList();
			JOptionPane.showMessageDialog(this, "Successfully added " + selectedCombo +" skill");
			comboBoxSkillList.removeAll();
			DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vectorSkills);
			comboBoxSkillList.setModel(dcbm);
			this.repaint();

		}
		
	}


	}
