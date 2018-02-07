package vzap.phoenix.gui;

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
	private JButton btnAddSkillToList;
	private JButton btnEditSkillRating;
	private JButton btnDeleteSkill;


	private JLabel lblSummaryOfSkills;
	private JTable tableSummarySkills;
	private JScrollPane scrollPaneSummarySkills;
	private JLabel lblDetails;
	private JScrollPane scrollPaneBottom;
	private JButton btnUpdate;
	private EmpSkillClient employeeSkillClient;
	private String addSkill;
	private ArrayList<EmployeeSkill> employeeSkillList;
	//private DefaultTableModel model;
	private DefaultTableModel modelInsert;
	private Employee emp;
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
	private ArrayList<EmployeeSkill> empRatingList;
	private short selectedSkillID = 0;


	/**
	 * Create the panel.
	 */
	public SkillsTab(EmpSkillClientController clientControl,Employee emp)
	{
		this.emp = emp;

		setAutoscrolls(true);

		this.clientControl=clientControl;

		setLayout(null);


		lblSummaryOfSkills = new JLabel("Summary of your Skills Table");
		lblSummaryOfSkills.setFont(new Font("Arial", Font.BOLD, 20));
		lblSummaryOfSkills.setBounds(648, 148, 334, 35);
		add(lblSummaryOfSkills);
		lblSummaryOfSkills.setHorizontalAlignment(JLabel.CENTER);


		lblDetails = new JLabel("Detail Table");
		lblDetails.setFont(new Font("Arial", Font.BOLD, 20));
		lblDetails.setBounds(717, 402, 205, 36);
		add(lblDetails);
		lblDetails.setHorizontalAlignment(JLabel.CENTER);



		btnUpdate = new JButton("Update your skills");
		btnUpdate.setFont(new Font("Arial", Font.BOLD, 15));
		btnUpdate.setToolTipText("Once a skill has been selected from the drop down you can add it to your profile");
		btnUpdate.setBounds(833, 75, 213, 36);
		add(btnUpdate);
		btnUpdate.addActionListener(this);

		employeeSkillList = new ArrayList<>();
		vectorSkills = new Vector<>();

		tableSummarySkills = new JTable();            
		tableSummarySkills.setFont(new Font("Arial", Font.PLAIN, 15));

		comboBoxSkillList = new JComboBox<>(vectorSkills);
		comboBoxSkillList.setFont(new Font("Arial", Font.BOLD, 15));
		comboBoxSkillList.setToolTipText("If your skill does not exist please press \"ADD SKILL\" button.");
		comboBoxSkillList.setBounds(833, 26, 213, 36);
		add(comboBoxSkillList);

		detailedTable = new JTable(detailTableModel);
		detailedTable.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneBottom = new JScrollPane(detailedTable);
		scrollPaneBottom.setBounds(38, 438, 1701, 201);
		add(scrollPaneBottom);

		scrollPaneSummarySkills = new JScrollPane(tableSummarySkills);
		scrollPaneSummarySkills.setBounds(38, 186, 1701, 169);
		tableSummarySkills.addMouseListener(this);
		scrollPaneSummarySkills.setViewportView(tableSummarySkills);
		add(scrollPaneSummarySkills);

		setup();
		
		lblSkill = new JLabel("Select a skill to add to your profile :");
		lblSkill.setFont(new Font("Arial", Font.BOLD, 15));
		lblSkill.setHorizontalAlignment(JLabel.CENTER);
		lblSkill.setBounds(556, 28, 265, 30);
		add(lblSkill);

		btnAddSkillToList = new JButton("Add New Skill To List");
		btnAddSkillToList.setFont(new Font("Arial", Font.BOLD, 15));
		btnAddSkillToList.setToolTipText("Select if your skill is not in the drop down menu");
		btnAddSkillToList.setBounds(566, 75, 213, 36);
		add(btnAddSkillToList);
		btnAddSkillToList.addActionListener(this);


		ratingValues = new String[] {"1", "2", "3", "4", "5"};
		comboBoxRating = new JComboBox(ratingValues);

		btnEditSkillRating = new JButton("Edit Skill Rating");
		btnEditSkillRating.setFont(new Font("Arial", Font.BOLD, 15));
		btnEditSkillRating.setBounds(38, 368, 162, 37);
		add(btnEditSkillRating);
		btnEditSkillRating.addActionListener(this);

		btnDeleteSkill = new JButton("Delete Skill");
		btnDeleteSkill.setFont(new Font("Arial", Font.BOLD, 15));
		btnDeleteSkill.setBounds(223, 368, 130, 37);
		add(btnDeleteSkill);
		btnDeleteSkill.addActionListener(this);
		detailedTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		tableSummarySkills.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		
	}
	public void setup()
	{
		employeeSkillList = clientControl.getEmployeeSkillList();

		tableSummarySkills.setModel(clientControl.getEmpSkillAverage(employeeSkillList));

		skillList = clientControl.getSkillList();
		detailedTable.setModel(clientControl.getEmpSkillDetail(employeeSkillList));
//		for(int i = 0 ; i < detailedTable.getRowCount();i++)
//		{
//			
//			for(int c = 3 ; c < detailedTable.getColumnCount() ;c++)
//			{
//				if(Integer.parseInt(detailedTable.getValueAt(i, c).toString()) < 2 );
//				{
//					detailedTable.getCellRenderer(i, c).getTableCellRendererComponent(detailedTable, value, isSelected, hasFocus, row, column)
//				}
//			}
//			
//		}
		
		//populating skill list for combobox
		vectorSkills.clear();
		for (int i = 0; i < skillList.size(); i++)
		{
			vectorSkills.addElement(skillList.get(i).getSkillDescription());
		}
		Collections.sort(vectorSkills);
		comboBoxSkillList.setSelectedIndex(0);
		comboBoxSkillList.updateUI();
		detailedTable.updateUI();
		tableSummarySkills.updateUI();


	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();


		if (source == btnEditSkillRating)
		{
			System.out.println("Edit Skill Rating pressed");
			JOptionPane.showMessageDialog(this, "This function isn't working yet", "Warning",JOptionPane.WARNING_MESSAGE);
			if(rowSelected <0)
			{
				System.out.println("At this point in time there shouldnt be anything selected in "
						+ "the skill table ");
				JOptionPane.showMessageDialog(this, "Please chose a skill you wish to edit before pressing"
						+ " this button");
			}
			else
			{
				System.out.println("Printing rowSelected to make sure it is the right value " + rowSelected);

				for (int i = 0; i < employeeSkillList.size(); i++)
				{
					if(employeeSkillList.get(i).getSkillID() == selectedSkillID 
							&& employeeSkillList.get(i).getEmployeeID().equals(emp.getEmployeeID()))
					{
						System.out.println("Inside the IF... selectedSkillID = " +selectedSkillID);
						System.out.println("emp = " + employeeSkillList.get(i).getEmployeeID());
						selectedEmpSkill = employeeSkillList.get(i);
						selectedEmpSkill.setStatus((short)9);
						boolean success = clientControl.updateEmployeeSkill(selectedEmpSkill);
						if(success)
						{
							JOptionPane.showMessageDialog(this, "Please go to the"
									+ " MY SKILLS RATING tab to update your skill rating");
						}
						else
						{
							JOptionPane.showMessageDialog(this, "Something went wrong. "
									+ "Your skill has not changed", "Warning", JOptionPane.WARNING_MESSAGE);
						}
						
						success=this.addNewEmployeeSkill();
					}
				}


			}

		}


		if(source == btnDeleteSkill)
		{
			System.out.println("Delete button pressed");
			int choice = JOptionPane.showConfirmDialog(this, "Deleting this skill will remove "
					+ "all previous rating of this skill from the record. Do you wish to delete?","Delete Skill",JOptionPane.YES_NO_OPTION);

			if(choice == JOptionPane.YES_OPTION)
			{
				selectedEmpSkill.setStatus((short)9);
				clientControl.updateEmployeeSkill(selectedEmpSkill);
				//call the database for the new DATA
				employeeSkillList = clientControl.getEmployeeSkillList();
				tableSummarySkills.setModel(clientControl.getEmpSkillAverage(employeeSkillList));
				tableSummarySkills.updateUI();
			}
			else
			{
				System.out.println("Nothing was done");
			}

		}

		if(source == btnAddSkillToList)
		{
			String description = JOptionPane.showInputDialog(this, "Please enter new Skill");
			if(description == null || description.equals(""))
			{
				JOptionPane.showInputDialog(this, "Please capture your new skill");
				return;
			}
			selectedSkillID = clientControl.addSkill(description);
			if(! (selectedSkillID ==0))
			{
				boolean success = this.addNewEmployeeSkill();
				if(success)
				{
					employeeSkillList = clientControl.getEmployeeSkillList();


					//call the database for the new DATA
					tableSummarySkills.setModel(clientControl.getEmpSkillAverage(employeeSkillList));

					vectorSkills.addElement(description);
					Collections.sort(vectorSkills);
					JOptionPane.showMessageDialog(this, "Successfully added " + description +" skill");
					comboBoxSkillList.updateUI();
					tableSummarySkills.updateUI();

				}
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

			Date createdDate = new Date();

			String selectedCombo = comboBoxSkillList.getSelectedItem().toString();

			short skillID = -1;
			for (int i = 0; i < skillList.size(); i++)
			{
				if(selectedCombo.equals(skillList.get(i).getSkillDescription()) )
				{
					skillID = (short)skillList.get(i).getSkillId();
					break;
				}
			}

			EmployeeSkill employeeSkill = new EmployeeSkill(emp.getEmployeeID(),skillID,emp.getEmployeeID(), createdDate);
			boolean success = clientControl.addEmployeeSkill(employeeSkill);


			if(success)
			{
				employeeSkillList = clientControl.getEmployeeSkillList();
				tableSummarySkills.setModel(clientControl.getEmpSkillAverage(employeeSkillList));
				comboBoxSkillList.updateUI();
				tableSummarySkills.updateUI();	
				JOptionPane.showMessageDialog(this, "Successfully added Skill");
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Something went wrong adding a skill");
			}


		}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		//                            Getting ready to delete and delete skills

		System.out.println("*****Mouse Clicked*****");
		rowSelected = tableSummarySkills.getSelectedRow();
		String skillName = (String)tableSummarySkills.getModel().getValueAt(rowSelected, 0);


		for (int i = 0; i < skillList.size(); i++)
		{
			if(skillName.equalsIgnoreCase(skillList.get(i).getSkillDescription()))
			{
				selectedSkillID = (short)skillList.get(i).getSkillId();
				break;
			}
		}
		empRatingList = new ArrayList<EmployeeSkill>();
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			if(employeeSkillList.get(i).getSkillID() == selectedSkillID)
			{
				empRatingList.add(employeeSkillList.get(i));
			}
		}

		//		System.out.println("info retruned : " + clientControl.getEmpCapabilityDetail(empRatingList).getRowCount());

		detailedTable.setModel(clientControl.getEmpCapabilityDetail(empRatingList));
		//tableSummarySkills.removeColumn(detailedTable.getColumnModel().getColumn(0));
		detailedTable.updateUI();

		//get the object of employee skill for delete
		for (int i = 0; i < employeeSkillList.size(); i++)
		{

			if((employeeSkillList.get(i).getEmployeeID().equalsIgnoreCase(emp.getEmployeeID()))
					&& (employeeSkillList.get(i).getSkillID()==selectedSkillID))
			{
				selectedEmpSkill = employeeSkillList.get(i);
				break;
			}
		}
		//end
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
	public boolean addNewEmployeeSkill()
	{
		//brand new skill that must be rated by employee 
		Date createdDate = new Date();

		EmployeeSkill employeeSkill = new EmployeeSkill(emp.getEmployeeID(),selectedSkillID,emp.getEmployeeID(), createdDate);
		boolean success = clientControl.addEmployeeSkill(employeeSkill);
		if(!success)
		{
			JOptionPane.showMessageDialog(this, "Problem adding Skills: "+clientControl.getErrorMsg());
		}
		return success;
	}

}
