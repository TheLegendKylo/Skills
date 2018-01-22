package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import vzap.phoenix.DAO.SkillDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnSubmit;

	/**
	 * Create the panel.
	 */
	public SkillsTab()
	{
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
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(378, 127, 89, 23);
		add(btnSubmit);
		btnSubmit.addActionListener(this);

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
		}
		
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Admin GUI");
		frame.setSize(700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SkillsTab panel = new SkillsTab();
		frame.add(panel);
		frame.setVisible(true);
	}
}
