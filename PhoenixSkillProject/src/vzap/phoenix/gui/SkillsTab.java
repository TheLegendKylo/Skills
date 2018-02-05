package vzap.phoenix.gui;
//
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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

public class SkillsTab extends JPanel implements ActionListener, MouseListener
{
	private JLabel lblSkillTab;
	private JButton btnAddSkillToList;
	private JButton btnEditSkillRating;
	private JButton btnDeleteSkill;
	

	private JLabel lblSummaryOfSkills;
	private JTable tableSummarySkills;
	private JScrollPane scrollPaneSummarySkills;
	private JLabel lblDetails;
	private JTable tableBottom;
	private JScrollPane scrollPaneBottom;
	private JButton btnUpdate;
	private EmpSkillClient employeeSkillClient;
	private String addSkill;
	private ArrayList<EmployeeSkill> employeeSkillList;
	private DefaultTableModel model;
	private DefaultTableModel modelInsert;
	private Employee loggedOnEmployee;
	private ArrayList<Skill> skillList;
	private ArrayList<Capability> capabilityList;
	//private JScrollPane scrollPaneAddSkill;
	//private JTable tableCaptureSkills; 
	
	private Vector<String> vectorSkills = null;
	
	private int getSelectedRow;
	private JLabel lblSkill;
	private JComboBox<String> comboBoxSkillList;
	private EmpSkillClientController clientControl;
	private JComboBox comboBoxRating;
	private String[] ratingValues;
	Object[] HeaderForAddSkill;
	private DefaultTableModel detailTableModel;
	private JTable detailedTable;
	private JScrollPane detailedScrollPane;
	private EmployeeSkill selectedEmpSkill;
	
	private int rowSelected = -1;
	
	
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
		lblSkillTab.setBounds(451, 11, 129, 14);
		add(lblSkillTab);
		
		
		lblSummaryOfSkills = new JLabel("Summary of your Skills");
		lblSummaryOfSkills.setBounds(10, 158, 928, 22);
		add(lblSummaryOfSkills);
		lblSummaryOfSkills.setHorizontalAlignment(JLabel.CENTER);
		
		scrollPaneSummarySkills = new JScrollPane();
		scrollPaneSummarySkills.setBounds(10, 236, 928, 161);
		add(scrollPaneSummarySkills);
		
		lblDetails = new JLabel("Details");
		lblDetails.setBounds(10, 408, 928, 22);
		add(lblDetails);
		lblDetails.setHorizontalAlignment(JLabel.CENTER);
		
		
		
		
		
		btnUpdate = new JButton("Update your skills");
		btnUpdate.setToolTipText("Once a skill has been selected from the drop down you can add it to your profile");
		btnUpdate.setBounds(390, 99, 173, 23);
		add(btnUpdate);
		btnUpdate.addActionListener(this);
		
		employeeSkillList = new ArrayList<>();
		
		
		Object[] skillsHeader = new String[]{"Skill","Your Ave Rating","Nominated Average",
				"Number of ratings"};
		
		
		
		employeeSkillList = clientControl.getEmployeeSkillList();
		model = clientControl.getEmpSkillAverage(employeeSkillList);
		skillList = clientControl.getSkillList();
		
		tableSummarySkills = new JTable(model);
		tableSummarySkills.addMouseListener(this);
		
		scrollPaneSummarySkills.setViewportView(tableSummarySkills);
		

		lblSkill = new JLabel("Select a skill to add to your profile");
		lblSkill.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSkill.setHorizontalAlignment(JLabel.CENTER);
		lblSkill.setBounds(147, 36, 661, 20);
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
		comboBoxSkillList.setBounds(390, 68, 173, 20);
		add(comboBoxSkillList);
		
		btnAddSkillToList = new JButton("Add New Skill To List");
		btnAddSkillToList.setToolTipText("Select if your skill is not in the drop down menu");
		btnAddSkillToList.setBounds(587, 67, 168, 23);
		add(btnAddSkillToList);
		btnAddSkillToList.addActionListener(this);
		
		
		ratingValues = new String[] {"1", "2", "3", "4", "5"};
		comboBoxRating = new JComboBox(ratingValues);
		
//		for (int i = 0; i < HeaderForAddSkill.length; i++)
//		{
//			tableCaptureSkills.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(comboBoxRating) );
//		}
		
		
		detailTableModel = clientControl.getEmpSkillDetail(employeeSkillList);
		detailedTable = new JTable(detailTableModel);
		TableColumn col = detailedTable.getColumnModel().getColumn(0);
		tableSummarySkills.removeColumn(col);
		scrollPaneBottom = new JScrollPane(detailedTable);
		scrollPaneBottom.setBounds(10, 433, 928, 152);
		add(scrollPaneBottom);
		
		btnEditSkillRating = new JButton("Edit Skill Rating");
		btnEditSkillRating.setBounds(339, 191, 148, 23);
		add(btnEditSkillRating);
		btnEditSkillRating.addActionListener(this);
		
		btnDeleteSkill = new JButton("Delete Skill");
		btnDeleteSkill.setBounds(497, 191, 98, 23);
		add(btnDeleteSkill);
		btnDeleteSkill.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
				
		if (source == btnEditSkillRating)
		{
			System.out.println("Edit Skill Rating pressed");
		}
		if(source == btnDeleteSkill)
		{
			System.out.println("Delete button pressed");
			 int warning = JOptionPane.showConfirmDialog(this, "Deleting this skill will remove "
			 		+ "all previous rating of this skill from the record. Do you wish to delete?");
			 System.out.println("Printing warning = " + warning);
			 System.out.println("Selected employeeSkill");
			 if(warning == 0)
			 {
				 selectedEmpSkill.setStatus((short)9);
					model.removeRow(rowSelected);
					String raterID = null;
					ArrayList<EmployeeSkill> removeRatings = clientControl.searchEmployeeSkill
							(loggedOnEmployee.getEmployeeID(), (short)selectedEmpSkill.getSkillID(), raterID);
					boolean success = false;
					if(removeRatings != null)
					{
						for (int i = 0; i < removeRatings.size(); i++)
						{
							removeRatings.get(i).setStatus((short)9);
							success = clientControl.updateEmployeeSkill(removeRatings.get(i));
							
						}
					}
					
					model.fireTableDataChanged();
			 }
			 else
			 {
				 System.out.println("Nothing was done");
			 }
			
			
		}
		if(source == btnAddSkillToList)
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
		
		if(source == btnUpdate)
		{
			System.out.println("Update button was pressed");
			String employeeID = loggedOnEmployee.getEmployeeID();
			String raterID = employeeID;
			Date createdDate = new Date();
			
			 String selectedCombo = comboBoxSkillList.getSelectedItem().toString();
			 System.out.println("Selected combo item = " + selectedCombo);
			 
			 short skillID = -1;
			for (int i = 0; i < skillList.size(); i++)
			{
				if(selectedCombo.equals(skillList.get(i).getSkillDescription()) )
				{
					skillID = (short)skillList.get(i).getSkillId();
				}
						
			}
			
			EmployeeSkill employeeSkill = new EmployeeSkill(employeeID,skillID,raterID, createdDate);
			boolean success = clientControl.addEmployeeSkill(employeeSkill);
			System.out.println("Printing boolean = " + success);
			
			if(success == true)
			{
				JOptionPane.showConfirmDialog(this, "Successfully added Skill");
			}
			else
			{
				JOptionPane.showConfirmDialog(this, "Something went wrong adding a skill");
			}
			
			employeeSkillList = clientControl.getEmployeeSkillList();
			Object[] newRow = new Object[]{selectedCombo, 0, 0, 0};
			model.addRow(newRow);
			model.fireTableDataChanged();
			
			vectorSkills.addElement(selectedCombo);
			Collections.sort(vectorSkills);
			clientControl.getSkillList();
			
			comboBoxSkillList.removeAll();
			DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vectorSkills);
			comboBoxSkillList.setModel(dcbm);
			this.repaint();

		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
//		Getting ready to delete and delete skills
		
		System.out.println("*****Mouse Clicked*****");
		rowSelected = tableSummarySkills.getSelectedRow();
		String skillName = (String)tableSummarySkills.getModel().getValueAt(rowSelected, 0);
		
		short skillID = 0;
		
		for (int i = 0; i < skillList.size(); i++)
		{
			if(skillName.equals(skillList.get(i).getSkillDescription()))
			{
				skillID = (short)skillList.get(i).getSkillId();
			}
		}
		
		//get the object of employee skill
		for (int i = 0; i < employeeSkillList.size(); i++)
		{

			
			if((employeeSkillList.get(i).getEmployeeID().equalsIgnoreCase(loggedOnEmployee.getEmployeeID()))
				&& (employeeSkillList.get(i).getSkillID()==skillID))
			{
				selectedEmpSkill = employeeSkillList.get(i);
				break;
			}
		}
		ArrayList<EmployeeSkill> empRatingList = clientControl.searchEmployeeSkill(skillID);
		System.out.println("info retruned : " + clientControl.getEmpCapabilityDetail(empRatingList).getRowCount());

		detailedTable.setModel(clientControl.getEmpCapabilityDetail(empRatingList));

		TableColumn col = detailedTable.getColumnModel().getColumn(0);
		tableSummarySkills.removeColumn(col);
		
		detailedTable.updateUI();
		this.repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	}
