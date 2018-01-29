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
	private JLabel lblAddYourSkill;
	private JTextField jtfAddSkill;
	private JLabel lblKnowledgeable;
	private JLabel lblStandardOfWork;
	private JLabel lblAutonomy;
	private JLabel lblCopingWithComplexity;
	private JLabel lblPerceptionOfContext;
	private JLabel lblGrowingCapability;
	private JLabel lblPurposefulCollaboration;
	private JTextField jtf0;
	private JTextField jtf1;
	private JTextField jtf2;
	private JTextField jtf3;
	private JTextField jtf4;
	private JTextField jtf5;
	private JTextField jtf6;
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
		
		
		
//		employeeSkillList.get(0).getCapabilityList();
//		employeeSkillList.get(0).getRatingList();
//		employeeSkillList.get(0).getSkillID();
//		for (int i = 0; i < skillList.size(); i++)
//		{
//			if(skillList.get(i).getSkillId() == employeeSkillList.get(1).getSkillID())
//			{
//				String skillDesc = skillList.get(i).getSkillDescription();
//			}
//		}
		
		
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
		
		lblAddYourSkill = new JLabel("Add Your Skill");
		lblAddYourSkill.setBounds(10, 96, 76, 14);
		add(lblAddYourSkill);
		
		jtfAddSkill = new JTextField();
		jtfAddSkill.setBounds(82, 93, 56, 20);
		add(jtfAddSkill);
		jtfAddSkill.setColumns(10);
		
		lblKnowledgeable = new JLabel("Knowledgeable");
		lblKnowledgeable.setBounds(160, 79, 76, 14);
		add(lblKnowledgeable);
		
		lblStandardOfWork = new JLabel("Standard of Work");
		lblStandardOfWork.setBounds(246, 79, 85, 14);
		add(lblStandardOfWork);
		
		lblAutonomy = new JLabel("Autonomy");
		lblAutonomy.setBounds(341, 79, 49, 14);
		add(lblAutonomy);
		
		lblCopingWithComplexity = new JLabel("Coping with Complexity");
		lblCopingWithComplexity.setBounds(400, 79, 112, 14);
		add(lblCopingWithComplexity);
		
		lblPerceptionOfContext = new JLabel("Perception of Context");
		lblPerceptionOfContext.setBounds(522, 79, 106, 14);
		add(lblPerceptionOfContext);
		
		lblGrowingCapability = new JLabel("Growing Capability");
		lblGrowingCapability.setBounds(638, 79, 89, 14);
		add(lblGrowingCapability);
		
		lblPurposefulCollaboration = new JLabel("Purposeful Collaboration");
		lblPurposefulCollaboration.setBounds(737, 79, 117, 14);
		add(lblPurposefulCollaboration);
		
		jtf0 = new JTextField();
		jtf0.setBounds(170, 93, 40, 20);
		add(jtf0);
		jtf0.setColumns(10);
		
		jtf1 = new JTextField();
		jtf1.setBounds(270, 93, 40, 20);
		add(jtf1);
		jtf1.setColumns(10);
		
		jtf2 = new JTextField();
		jtf2.setBounds(351, 93, 40, 20);
		add(jtf2);
		jtf2.setColumns(10);
		
		jtf3 = new JTextField();
		jtf3.setBounds(437, 93, 40, 20);
		add(jtf3);
		jtf3.setColumns(10);
		
		jtf4 = new JTextField();
		jtf4.setBounds(550, 93, 40, 20);
		add(jtf4);
		jtf4.setColumns(10);
		
		jtf5 = new JTextField();
		jtf5.setBounds(663, 93, 40, 20);
		add(jtf5);
		jtf5.setColumns(10);
		
		jtf6 = new JTextField();
		jtf6.setBounds(771, 93, 40, 20);
		add(jtf6);
		jtf6.setColumns(10);
		
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
//		int counter = 0;
//		
//		
//		String [] skillDesc = new String[99];
//		skillList = clientControl.getSkillList();
//		double yourAveRating[] = new double[99];
//		double nominateeAveRating[] = new double[99];
//		int numberOfRatings[] = new int[99];
//		int ratingCount = 0;
//		double averageRating = 0;
//		int skillIDCheck = 0;
//		
//		
//		for (int i = 0; i < employeeSkillList.size(); i++)
//		{
//	System.out.println(">>>Skillcheck: "+skillIDCheck+" empSkill: "+employeeSkillList.get(i).getSkillID()
//	+" Counter: "+counter);
//
//			// Check whether a new SkillId has been read
//			if(!(skillIDCheck==employeeSkillList.get(i).getSkillID()))
//			{
//				// if this is not the first record found
//				// update the previous totals to the relevant variables
//				if(i>0)
//				{
//					if(ratingCount >0)
//					{
//						System.out.println("---Nominee ID " + " counter: "+counter) ;
//						System.out.println("---Rating count = " +nominateeAveRating[counter]+ " ssss" + ratingCount);
//						nominateeAveRating[counter]= nominateeAveRating[counter]/ratingCount;
//						ratingCount = 0;
//					}
//					counter++;
//				}
//				// iterate through the static skill array to obtain the skilldescription
//				for (int j = 0; j < skillList.size(); j++)
//				{
//					System.out.println(">>>SkillList: "+skillList.get(j).getSkillId()+" empSkill: "+employeeSkillList.get(i).getSkillID());
//					if(skillList.get(j).getSkillId() == employeeSkillList.get(i).getSkillID())
//						{
//						skillIDCheck = skillList.get(j).getSkillId();
//						System.out.println(">>>set new skillcheck: "+skillIDCheck+" counter: "+counter);
//						skillDesc[counter] = skillList.get(j).getSkillDescription();
//						System.out.println(">>>set new skillcheck: "+skillIDCheck+" counter: "+counter+" desc: "+skillDesc[counter]);
//							break;
//						}//comment
//				}
//			}
//			// check whether this record is of the employee rating him/herself
//			// only one such record should exist
//			if(employeeSkillList.get(i).getEmployeeID().equals(
//					employeeSkillList.get(i).getRaterID()))
//			{
//				yourAveRating[counter] = employeeSkillList.get(i).getOverAllAverageRating();
//			}
//			else
//				// this record if of a nominee rating
//			{
//				averageRating = employeeSkillList.get(i).getOverAllAverageRating();
//				System.out.println("Nominee ID " + employeeSkillList.get(i).getRaterID());
//				System.out.println("Skill ID " + employeeSkillList.get(i).getSkillID());
//				System.out.println("Average rating = " + averageRating);
//				System.out.println("Rating count = " + ratingCount);
//				// only include in the average ratings if it is not 0;
//				if(averageRating > 0)
//				{
//					nominateeAveRating[counter] =+ averageRating;
//					ratingCount++;
//					numberOfRatings[i] = ratingCount;
//				}
//				
//			}
//		}
//		// ensure that the final nominee ratings have been taken into account
//		if(ratingCount >0)
//		{
//			System.out.println("---Nominee ID " + " counter: "+counter) ;
//			nominateeAveRating[counter]= nominateeAveRating[counter]/ratingCount;
//			ratingCount = 0;
//		}
//
//		Object[][] skillsRow = new Object[counter+1][5];
//		System.out.println("Before for loop + length of skillDesc " + skillDesc.length);
//		System.out.println("Testing the counter " + counter);
//		
//		for (int i = 0; i < (counter+1); i++)
//		{
//			
//			System.out.println("Index number " + i +" skillDesc "+skillDesc[i]);
//				skillsRow[i][0] = skillDesc[i];
//				skillsRow[i][1] = yourAveRating[i];
//				skillsRow[i][2] = nominateeAveRating[i];
//				skillsRow[i][3] = numberOfRatings[i];
//		}
//	
//		model = new DefaultTableModel(skillsRow, skillsHeader);
//		
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
			
			
			
			int row = tableCaptureSkills.getSelectedRow();
			
			if(!(row < 0))
			{
				for (int i = 0; i < HeaderForAddSkill.length; i++)
				{
					String skillToBeAdded = tableCaptureSkills.getValueAt(row, i).toString();
					System.out.println("Skill rating " + skillToBeAdded);
				}
				
			}
//				String x = comboBoxSkillList.getSelectedItem().toString();
				
				Object object = tableCaptureSkills.getValueAt(row, 0);
				String skillToBeAdded = (String)tableCaptureSkills.getValueAt(row, 0);
				
				//Still need to figure out how to clear the cell that was selected so that the
				//skill can be sent directly
//				tableCaptureSkills.clearSelection();
//				System.out.println("Printing out StringValue " + x);
//				jtfAddSkill.setText(x);
				
				
			
			
							
//			}
			

		}
		
	}


	}
