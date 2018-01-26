package vzap.phoenix.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RatingOfSkills extends JPanel
{
	private Employee loggedOnEmployee;
	private EmpSkillClientController clientControl;
	private JLabel lblRatingOfSkills;
	private JPanel panelTop;
	private JButton btnCannotRate;
	private JPanel panelBottom;
	private JLabel lblMessageIdentifier;
	private JButton btnSubmitRating;
	private JTable tableTop;
	private ArrayList<EmployeeSkill> employeeSkillList;
	private DefaultTableModel outstandingModel;

	/**
	 * Create the panel.
	 */
	public RatingOfSkills(EmpSkillClientController clientControl)
	{
		
		setAutoscrolls(true);
		loggedOnEmployee = clientControl.getLogonEmployee();
		this.clientControl=clientControl;
		employeeSkillList = clientControl.getOutstandingRatings();
		
		setLayout(null);
		
		lblRatingOfSkills = new JLabel("Rate Skills");
		lblRatingOfSkills.setBounds(317, 10, 79, 14);
		add(lblRatingOfSkills);
		
		panelTop = new JPanel();
		panelTop.setBounds(10, 35, 694, 159);
		add(panelTop);
		
		outstandingModel = clientControl.getEmpSkillList(employeeSkillList, loggedOnEmployee);
		tableTop = new JTable(outstandingModel);
		
		panelTop.add(tableTop);
		
		btnCannotRate = new JButton("Cannot Rate");
		btnCannotRate.setToolTipText("Select if you are unable to offer a rating for an employee request");
		btnCannotRate.setBounds(299, 205, 116, 23);
		add(btnCannotRate);
		
		panelBottom = new JPanel();
		panelBottom.setBounds(10, 267, 694, 234);
		add(panelBottom);
		
		lblMessageIdentifier = new JLabel("Message Identifier");
		lblMessageIdentifier.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMessageIdentifier.setBounds(166, 242, 382, 14);
		lblMessageIdentifier.setHorizontalAlignment(JLabel.CENTER);
		lblMessageIdentifier.setVerticalAlignment(JLabel.CENTER);
		add(lblMessageIdentifier);
		
		btnSubmitRating = new JButton("Submit Rating");
		btnSubmitRating.setBounds(299, 523, 116, 23);
		add(btnSubmitRating);
	}
}
