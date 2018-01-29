package vzap.phoenix.gui;
//Comment
import javax.swing.JLabel;
import javax.swing.JPanel;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class RatingOfSkills extends JPanel implements MouseListener
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
	private JScrollPane scrollPaneTop;
	private Object source;

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
		panelTop.setLayout(null);
		tableTop = new JTable(outstandingModel);
		scrollPaneTop = new JScrollPane(tableTop);
		scrollPaneTop.setBounds(63, 5, 568, 154);
		panelTop.add(scrollPaneTop);
		tableTop.addMouseListener(this);
		
//		scrollPane.setViewportView(tableTop);

		
		
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

	@Override
	public void mouseClicked(MouseEvent e)
	{
		source = e.getSource();
		if(source == e)
		{
			System.out.println("Mouse clicked");
			int row = tableTop.getSelectedRow();
			String individualEmpID = (String)tableTop.getValueAt(row, 0);
			System.out.println("Mouse clicked and the empID = " + individualEmpID);
		}
		
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
