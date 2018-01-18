package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Popup;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.*;

import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class SearchCriteria extends JPanel implements ActionListener
{
	private JPanel tablePanel;

	private JTable table;
	private JScrollPane scrollPane;
	
	String [] [] empData;
	String [] empColumnNames;
	String [] skillColumnNames;
	String [] [] skillData;
	
	String [] capability; 
	
	private JLabel searchBySkillLab;
	private JTextField searchBySkillJTF;
	private JLabel searchbybemployeeLab;
	private JTextField searchByEmployeeJTF;
	private JButton empSearchBut;
	private JLabel searchByHobbyLab;
	private JTextField searchByHobbyJTF;
	
	public ArrayList<Capability> capabilityList;	
	
	public SearchCriteria()
	{
		
		tablePanel = new JPanel();
		EmpSkillClient esc = new EmpSkillClient();
		capabilityList = esc.getCapabilityList();
		
		empColumnNames = new String[]{"UserId","First Name","Surname","Alias"};
		empData = new String[][]{ {"a043410","patsy","de kock","pat"},{"a01189","elizabeth","maiden","libby"},
			{"c40989","gerald","hammond","gess"} };

			
		skillColumnNames = new String[]{"Skill","UserId","First Name","Surname","Average"};
		skillData = new String[][]{ {"java","a043410","patsy","de kock","3.4"} ,
		                        	{"java","c40989","gerald","hammond","2.6"} };

		                        	
		searchbybemployeeLab = new JLabel("Search by employee");
		
		searchByEmployeeJTF = new JTextField();
		searchByEmployeeJTF.setColumns(10);
		
		empSearchBut = new JButton("GO");
		empSearchBut.addActionListener(this);
		
		searchBySkillLab = new JLabel("Search by skill");
		
		searchBySkillJTF = new JTextField();
		searchBySkillJTF.setColumns(10);
		
		searchByHobbyLab = new JLabel("Search by hobby ");
		
		searchByHobbyJTF = new JTextField();
		searchByHobbyJTF.setColumns(10);
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
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(searchBySkillJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(38)
										.addComponent(empSearchBut))
									.addComponent(searchByEmployeeJTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(empSearchBut))
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
		
		if (source == empSearchBut)
		{
			if ( (searchByEmployeeJTF.getText().isEmpty()) && (searchBySkillJTF.getText().isEmpty()))
			{
				JOptionPane.showMessageDialog(this,  "Please enter either NAME or SKILL before clicking GO button");
			}
			else 
			{
				if ( (!searchByEmployeeJTF.getText().isEmpty()) && (!searchBySkillJTF.getText().isEmpty()))
				{
					JOptionPane.showMessageDialog(this,  "Cannot search on both NAME and SKILL - choose one");
					tablePanel.removeAll();
					tablePanel.validate();
					tablePanel.repaint();
					table = new JTable();
					scrollPane = new JScrollPane();
					scrollPane.setViewportView(table);
					tablePanel.add(scrollPane);
					tablePanel.validate();
					tablePanel.repaint();

					searchByEmployeeJTF.setText("");
					searchBySkillJTF.setText("");
				}
				else
				{
					if(! (searchByEmployeeJTF.getText().isEmpty() )) 
					{
						
						tablePanel.removeAll();
						tablePanel.validate();
						tablePanel.repaint();
						table = new JTable(empData,empColumnNames);
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
							tablePanel.removeAll();
							tablePanel.validate();
							tablePanel.repaint();
							table = new JTable(skillData,skillColumnNames);
							scrollPane = new JScrollPane();
							scrollPane.setViewportView(table);
							tablePanel.add(scrollPane);
							tablePanel.validate();
							tablePanel.repaint();	
						}
					}
				}
			}
		}
		
		
	}
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.getContentPane().add(new SearchCriteria());
		frame.setVisible(true);
	}
	
}
