package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JScrollPane;

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
	private JTextField jtfKnowledge;
	private JTextField jtfStdOfWork;
	private JTextField jtfAutonomy;
	private JTextField jtfCopingWithComplexity;
	private JTextField jtfComplexity;
	private JTextField jtfGrowingCapability;
	private JTextField jtfPurposefulCollaboration;
	private JLabel lblSummaryOfSkills;
	private JTable tableSummarySkills;
	private JScrollPane scrollPaneSummarySkills;
	private JLabel lblDetails;
	private JTable tableBottom;
	private JScrollPane scrollPane;
	private JButton btnSubmit;
	private EmpSkillClient employeeSkillClient;
	private String addSkill;
	private ArrayList<EmployeeSkill> employeeSkillList;
	private DefaultTableModel model;
	private Employee loggedOnEmployee;
	private ArrayList<Skill> skillList;
	private ArrayList<Capability> capabilityList;
	/**
	 * Create the panel.
	 */
	public SkillsTab(EmpSkillClientController clientControl)
	{
		loggedOnEmployee = clientControl.getLogonEmployee();
		
		
		
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
		panelTop.setBounds(10, 35, 869, 33);
		add(panelTop);
		
		btnAddSkill = new JButton("Add Skill");
		panelTop.add(btnAddSkill);
		btnAddSkill.addActionListener(this);
		
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
		
		jtfKnowledge = new JTextField();
		jtfKnowledge.setBounds(170, 93, 40, 20);
		add(jtfKnowledge);
		jtfKnowledge.setColumns(10);
		
		jtfStdOfWork = new JTextField();
		jtfStdOfWork.setBounds(270, 93, 40, 20);
		add(jtfStdOfWork);
		jtfStdOfWork.setColumns(10);
		
		jtfAutonomy = new JTextField();
		jtfAutonomy.setBounds(351, 93, 40, 20);
		add(jtfAutonomy);
		jtfAutonomy.setColumns(10);
		
		jtfCopingWithComplexity = new JTextField();
		jtfCopingWithComplexity.setBounds(437, 93, 40, 20);
		add(jtfCopingWithComplexity);
		jtfCopingWithComplexity.setColumns(10);
		
		jtfComplexity = new JTextField();
		jtfComplexity.setBounds(550, 93, 40, 20);
		add(jtfComplexity);
		jtfComplexity.setColumns(10);
		
		jtfGrowingCapability = new JTextField();
		jtfGrowingCapability.setBounds(663, 93, 40, 20);
		add(jtfGrowingCapability);
		jtfGrowingCapability.setColumns(10);
		
		jtfPurposefulCollaboration = new JTextField();
		jtfPurposefulCollaboration.setBounds(771, 93, 40, 20);
		add(jtfPurposefulCollaboration);
		jtfPurposefulCollaboration.setColumns(10);
		
		lblSummaryOfSkills = new JLabel("Summary of your Skills");
		lblSummaryOfSkills.setBounds(10, 168, 107, 14);
		add(lblSummaryOfSkills);
		
		scrollPaneSummarySkills = new JScrollPane();
		scrollPaneSummarySkills.setBounds(10, 201, 859, 92);
		add(scrollPaneSummarySkills);
		
		tableSummarySkills = new JTable();
		scrollPaneSummarySkills.setViewportView(tableSummarySkills);
		
		lblDetails = new JLabel("Details");
		lblDetails.setBounds(10, 304, 46, 14);
		add(lblDetails);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 329, 869, 112);
		add(scrollPane);
		
		
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(378, 127, 89, 23);
		add(btnSubmit);
		btnSubmit.addActionListener(this);
		
		employeeSkillList = new ArrayList<>();
		
		
		Object[] skillsHeader = new String[]{"Skill","Your Ave Rating","Nominated Average",
				"Number of ratings","Details"};
		
		
		
		employeeSkillList = clientControl.getEmployeeSkillList();
		int counter = 0;
		
		
		String [] skillDesc = new String[99];
		skillList = clientControl.getSkillList();
		double yourAveRating[] = new double[99];
		double nominateeAveRating[] = new double[99];
		int numberOfRatings[] = new int[99];
		int ratingCount = 0;
		double averageRating = 0;
		int skillIDCheck = 0;
		
		
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
System.out.println(">>>Skillcheck: "+skillIDCheck+" empSkill: "+employeeSkillList.get(i).getSkillID()
+" Counter: "+counter);
			if(!(skillIDCheck==employeeSkillList.get(i).getSkillID()))
			{
				for (int j = 0; j < skillList.size(); j++)
				{
					if(i>0)
					{
						if(ratingCount >0)
						{
							System.out.println("---Nominee ID " + " counter: "+counter) ;
							System.out.println("---Rating count = " +nominateeAveRating[counter]+ " ssss" + ratingCount);
							nominateeAveRating[counter]= nominateeAveRating[counter]/ratingCount;
							ratingCount = 0;
						}
						counter++;
					}
					System.out.println(">>>SkillList: "+skillList.get(j).getSkillId()+" empSkill: "+employeeSkillList.get(i).getSkillID());
					if(skillList.get(j).getSkillId() == employeeSkillList.get(i).getSkillID())
						{
System.out.println(">>>set new skillcheck: "+skillIDCheck+"counter: "+counter);
						skillIDCheck = skillList.get(j).getSkillId();
						skillDesc[counter] = skillList.get(j).getSkillDescription();
							break;
						}
				}
				System.out.println(">>>>i: "+i);
			}
			if(employeeSkillList.get(i).getEmployeeID().equals(
					employeeSkillList.get(i).getRaterID()))
			{
				yourAveRating[counter] = employeeSkillList.get(i).getOverAllAverageRating();
			}
			else
			{
				
				averageRating = employeeSkillList.get(i).getOverAllAverageRating();
				System.out.println("Nominee ID " + employeeSkillList.get(i).getRaterID());
				System.out.println("Skill ID " + employeeSkillList.get(i).getSkillID());
				System.out.println("Average rating = " + averageRating);
				System.out.println("Rating count = " + ratingCount);
				
				if(averageRating > 0)
				{
					nominateeAveRating[counter] =+ averageRating;
					ratingCount++;
					numberOfRatings[i] = ratingCount;
				}
				
			}
		}
		if(ratingCount >0)
		{
			System.out.println("---Nominee ID " + " counter: "+counter) ;
			nominateeAveRating[counter]= nominateeAveRating[counter]/ratingCount;
			ratingCount = 0;
		}

		Object[][] skillsRow = new Object[counter][5];
		System.out.println("Before for loop + length of skillDesc " + skillDesc.length);
		System.out.println("Testing the counter " + counter);
		
		for (int i = 0; i < counter; i++)
		{
			
			System.out.println("Index number " + i);
				skillsRow[i][0] = skillDesc[i];
				skillsRow[i][1] = yourAveRating[i];
				skillsRow[i][2] = nominateeAveRating[i];
				skillsRow[i][3] = numberOfRatings[i];
		
		}
	
		model = new DefaultTableModel(skillsRow, skillsHeader);
		
		tableBottom = new JTable(model);
		scrollPane.setViewportView(tableBottom);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		
		if(source == btnSubmit)
		{
			System.out.println("Submit button was pressed");
		
			
			
			
			if(jtfAddSkill.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "You need to list a skill");
				jtfAddSkill.grabFocus();
			}
			else
			{
				addSkill = jtfAddSkill.getText();
				employeeSkillClient.addSkill(addSkill);
				System.out.println("Should have added " + addSkill + " to the database");

				
			}
			
//		
					
		}
		
	}
	
	}
