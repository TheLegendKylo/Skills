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
	//real1
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


	/**
	 * Create the panel.
	 */
	public SkillsTab(EmpSkillClientController clientControl,Employee emp)
	{
		this.emp = emp;

		setAutoscrolls(true);

		this.clientControl=clientControl;

		setLayout(null);

		lblSkillTab = new JLabel("Skill Tab");
		lblSkillTab.setBounds(451, 11, 129, 14);
		add(lblSkillTab);


		lblSummaryOfSkills = new JLabel("Summary of your Skills");
		lblSummaryOfSkills.setBounds(10, 158, 928, 22);
		add(lblSummaryOfSkills);
		lblSummaryOfSkills.setHorizontalAlignment(JLabel.CENTER);


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
		vectorSkills = new Vector<>();

		tableSummarySkills = new JTable();            
		
		comboBoxSkillList = new JComboBox<>(vectorSkills);
		comboBoxSkillList.setToolTipText("If your skill does not exist please press \"ADD SKILL\" button.");
		comboBoxSkillList.setBounds(390, 68, 173, 20);
		add(comboBoxSkillList);
		
		detailedTable = new JTable(detailTableModel);
		scrollPaneBottom = new JScrollPane(detailedTable);
		scrollPaneBottom.setBounds(10, 433, 928, 152);
		add(scrollPaneBottom);
		
		scrollPaneSummarySkills = new JScrollPane(tableSummarySkills);
		scrollPaneSummarySkills.setBounds(10, 236, 928, 161);
		tableSummarySkills.addMouseListener(this);
		scrollPaneSummarySkills.setViewportView(tableSummarySkills);
		add(scrollPaneSummarySkills);
		
		setup();

		
		//tableSummarySkills.removeColumn(detailedTable.getColumnModel().getColumn(0));


		lblSkill = new JLabel("Select a skill to add to your profile");
		lblSkill.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSkill.setHorizontalAlignment(JLabel.CENTER);
		lblSkill.setBounds(147, 36, 661, 20);
		add(lblSkill);

		btnAddSkillToList = new JButton("Add New Skill To List");
		btnAddSkillToList.setToolTipText("Select if your skill is not in the drop down menu");
		btnAddSkillToList.setBounds(587, 67, 168, 23);
		add(btnAddSkillToList);
		btnAddSkillToList.addActionListener(this);


		ratingValues = new String[] {"1", "2", "3", "4", "5"};
		comboBoxRating = new JComboBox(ratingValues);

		btnEditSkillRating = new JButton("Edit Skill Rating");
		btnEditSkillRating.setBounds(339, 191, 148, 23);
		add(btnEditSkillRating);
		btnEditSkillRating.addActionListener(this);

		btnDeleteSkill = new JButton("Delete Skill");
		btnDeleteSkill.setBounds(497, 191, 98, 23);
		add(btnDeleteSkill);
		btnDeleteSkill.addActionListener(this);

	}
	public void setup()
	{
		System.out.println("we in setup Ryan ");

		employeeSkillList = clientControl.getEmployeeSkillList();

		tableSummarySkills.setModel(clientControl.getEmpSkillAverage(employeeSkillList));

		skillList = clientControl.getSkillList();
		detailedTable.setModel(clientControl.getEmpSkillDetail(employeeSkillList));

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
			short skillID = clientControl.addSkill(description);
			if(! (skillID ==0))
			{
				//brand new skill that must be rated by employee 
				Date createdDate = new Date();

				EmployeeSkill employeeSkill = new EmployeeSkill(emp.getEmployeeID(),skillID,emp.getEmployeeID(), createdDate);
				boolean success = clientControl.addEmployeeSkill(employeeSkill);
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
				else
				{
					JOptionPane.showMessageDialog(this, "Problem adding Skills");
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

		short skillID = 0;

		for (int i = 0; i < skillList.size(); i++)
		{
			if(skillName.equalsIgnoreCase(skillList.get(i).getSkillDescription()))
			{
				skillID = (short)skillList.get(i).getSkillId();
				break;
			}
		}
		ArrayList<EmployeeSkill> empRatingList = new ArrayList<EmployeeSkill>();
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			if(employeeSkillList.get(i).getSkillID() == skillID)
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
							&& (employeeSkillList.get(i).getSkillID()==skillID))
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


}
