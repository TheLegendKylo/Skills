package vzap.phoenix.gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class TestingEmployeeSkill extends JPanel
{
	private JTabbedPane tabbedPane;
	private JPanel profilePanel;
	private JPanel skillPanel;
	private JLabel lblEmployeeid;
	private JLabel lblFirstName;
	private JLabel lblSurname;
	private JLabel lblContactNo;
	private JLabel lblEmail;
	private JLabel outEmployeeID;
	private JLabel outFName;
	private JLabel outSurname;
	private JLabel outContactNo;
	private JLabel outEmail;
	private JPanel addSkillPanel;
	private JLabel lblSkill;
	private JComboBox comboBox;
	private JLabel lblCap1;
	private JLabel lblCap2;
	private JLabel lblCap3;
	private JLabel lblCap4;
	private JLabel lblCap5;
	private JLabel lblCap6;
	private JLabel lblCap7;
	private JTextField txtJtfrate;
	private JTextField jtfRate2;
	private JTextField jtfRate3;
	private JTextField jtfRate4;
	private JTextField jtfRate5;
	private JTextField jtfRate6;
	private JTextField jtfRate7;

	/**
	 * Create the panel.
	 */
	public TestingEmployeeSkill()
	{
		setLayout(new GridLayout(1, 1, 0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		profilePanel = new JPanel();
		tabbedPane.addTab("New tab", null, profilePanel, null);
		
		lblEmployeeid = new JLabel("EmployeeID");
		
		lblFirstName = new JLabel("First Name");
		
		lblSurname = new JLabel("Surname");
		
		lblContactNo = new JLabel("Contact No");
		
		lblEmail = new JLabel("Email");
		
		outEmployeeID = new JLabel("New label");
		outEmployeeID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outFName = new JLabel("New label");
		outFName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outSurname = new JLabel("New label");
		outSurname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outContactNo = new JLabel("New label");
		outContactNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		outEmail = new JLabel("New label");
		outEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_profilePanel = new GroupLayout(profilePanel);
		gl_profilePanel.setHorizontalGroup(
			gl_profilePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profilePanel.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblEmail)
						.addComponent(lblContactNo)
						.addComponent(lblSurname)
						.addComponent(lblFirstName)
						.addComponent(lblEmployeeid))
					.addGap(62)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profilePanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(outFName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(outEmployeeID, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
						.addComponent(outSurname, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
						.addComponent(outContactNo, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
						.addComponent(outEmail, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_profilePanel.setVerticalGroup(
			gl_profilePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profilePanel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmployeeid)
						.addComponent(outEmployeeID))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFirstName)
						.addComponent(outFName, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSurname)
						.addComponent(outSurname, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblContactNo)
						.addComponent(outContactNo, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_profilePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(outEmail, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(106, Short.MAX_VALUE))
		);
		profilePanel.setLayout(gl_profilePanel);
		
		skillPanel = new JPanel();
		tabbedPane.addTab("New tab", null, skillPanel, null);
		skillPanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		addSkillPanel = new JPanel();
		tabbedPane.addTab("New tab", null, addSkillPanel, null);
		
		lblSkill = new JLabel("Skill");
		
		comboBox = new JComboBox();
		
		lblCap1 = new JLabel("New label");
		
		lblCap2 = new JLabel("New label");
		
		lblCap3 = new JLabel("New label");
		
		lblCap4 = new JLabel("New label");
		
		lblCap5 = new JLabel("New label");
		
		lblCap6 = new JLabel("New label");
		
		lblCap7 = new JLabel("New label");
		
		txtJtfrate = new JTextField();
		txtJtfrate.setText("jtfRate1");
		txtJtfrate.setColumns(10);
		
		jtfRate2 = new JTextField();
		jtfRate2.setColumns(10);
		
		jtfRate3 = new JTextField();
		jtfRate3.setColumns(10);
		
		jtfRate4 = new JTextField();
		jtfRate4.setColumns(10);
		
		jtfRate5 = new JTextField();
		jtfRate5.setColumns(10);
		
		jtfRate6 = new JTextField();
		jtfRate6.setColumns(10);
		
		jtfRate7 = new JTextField();
		jtfRate7.setColumns(10);
		GroupLayout gl_addSkillPanel = new GroupLayout(addSkillPanel);
		gl_addSkillPanel.setHorizontalGroup(
			gl_addSkillPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addSkillPanel.createSequentialGroup()
					.addGap(51)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCap2)
						.addComponent(lblSkill)
						.addComponent(lblCap1)
						.addComponent(lblCap7)
						.addComponent(lblCap6)
						.addComponent(lblCap5)
						.addComponent(lblCap4)
						.addComponent(lblCap3))
					.addGap(41)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(jtfRate7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtJtfrate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
						.addComponent(jtfRate2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jtfRate3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jtfRate4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jtfRate5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jtfRate6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(121, Short.MAX_VALUE))
		);
		gl_addSkillPanel.setVerticalGroup(
			gl_addSkillPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addSkillPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSkill))
					.addGap(18)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCap1)
						.addComponent(txtJtfrate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCap2)
						.addComponent(jtfRate2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(jtfRate3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCap3))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(jtfRate4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCap4))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(jtfRate5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCap5))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(jtfRate6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCap6))
					.addGap(12)
					.addGroup(gl_addSkillPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(jtfRate7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCap7))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		addSkillPanel.setLayout(gl_addSkillPanel);

	}
}
