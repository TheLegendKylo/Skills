package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.client.EmpSkillClient;
import vzap.phoenix.client.EmpSkillClientController;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class MainGui extends JPanel implements ActionListener,ListSelectionListener
{
	private JTabbedPane tabbedPane;
	private JPanel basePanel;
	private boolean newUser=false;
	private JButton btnMainExit;
	private JPanel panelProfile;
	private JPanel panelSkills;
	private JLabel lblNewLabel;
	private JLabel lblFirstName;
	private JLabel lblSurname;
	private JLabel lblContact;
	private JLabel lblEmail;
	private JTextField tfUserID;
	private JTextField tfName;
	private JTextField tfSurname;
	private JTextField tfContact;
	private JTextField tfEmail;
	private JLabel lblHobbies;
	private JTextField tfAddHobby;
	private JButton btnAddHobby;
	private JButton btnDeleteHobby;
	private JScrollPane scrollPane;
	private JList list;
	private String loggedInUser = null;
	private Vector<String> vectHobby = null;
	private Vector<Hobby> allHobby = null;
//	private Vector<String> vectDisplayHobbyForEmp = null;
	//private Vector<String> vectHobby = null;
	private JButton btnLogoff;
	private JButton btnUpdateEmployee;
	private String deleteHobbyValue = null;
	private String addHobbyValue = null;
	private JLabel lblAlias;
	private JTextField tfAlias;
	private EmpSkillClientController clientControl = null;
	private Employee emp = null;
	private JComboBox comboBox;
	private ArrayList<Short> empHobby;
	/**
	 * Create the panel.
	 */
	public MainGui(JPanel basePanel, boolean newUser,Employee emp,EmpSkillClientController clientControl)
	{
		//My added code
		this.basePanel = basePanel;
		this.newUser = newUser;
		this.emp = emp;
		loggedInUser = emp.getEmployeeID();
		this.clientControl = clientControl;
		
		//adding the existing hobbies for a user
//		System.out.println("Size " + clientControl.getHobbyList().size() );
		allHobby = clientControl.getHobbyList();
		vectHobby = new Vector<String>();
		
		//will setup the array
				
		empHobby = emp.getEmpHobbies();
		
		for(int i = 0 ; i < empHobby.size() ; i ++)
		{
			for(int j = 0;j < allHobby.size();j++)
			{
				if (empHobby.get(i) == allHobby.get(j).getHobbyID())
				{
					
					vectHobby.add(allHobby.get(j).getHobbyDescription());
				}
			}
		}
			
//		//end my added code
//		
		
		//vectHobby.add("Test");
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1107, 700);
		add(tabbedPane);
		
		panelProfile = new JPanel();
		tabbedPane.addTab("PROFILE", null, panelProfile, null);
		panelProfile.setLayout(null);
		
		lblNewLabel = new JLabel("User ID");
		lblNewLabel.setBounds(65, 102, 56, 16);
		panelProfile.add(lblNewLabel);
		
		lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(65, 165, 76, 16);
		panelProfile.add(lblFirstName);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setBounds(65, 194, 76, 16);
		panelProfile.add(lblSurname);
		
		lblContact = new JLabel("Contact");
		lblContact.setBounds(65, 229, 76, 16);
		panelProfile.add(lblContact);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(65, 269, 76, 16);
		panelProfile.add(lblEmail);
		
		tfUserID = new JTextField(loggedInUser);
		tfUserID.setEditable(false);
		tfUserID.setBounds(164, 99, 116, 22);
		panelProfile.add(tfUserID);
		tfUserID.setColumns(10);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(164, 162, 116, 22);
		panelProfile.add(tfName);
		
		tfSurname = new JTextField();
		tfSurname.setColumns(10);
		tfSurname.setBounds(164, 188, 116, 22);
		panelProfile.add(tfSurname);
		
		tfContact = new JTextField();
		tfContact.setColumns(10);
		tfContact.setBounds(164, 223, 116, 22);
		panelProfile.add(tfContact);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(164, 258, 116, 22);
		panelProfile.add(tfEmail);
		
		lblHobbies = new JLabel("Hobbies");
		lblHobbies.setBounds(65, 302, 76, 16);
		panelProfile.add(lblHobbies);
		
		tfAddHobby = new JTextField();
		tfAddHobby.setColumns(10);
		tfAddHobby.setBounds(164, 299, 116, 22);
		panelProfile.add(tfAddHobby);
		
		btnAddHobby = new JButton("Add Hobby");
		btnAddHobby.setBounds(298, 298, 97, 25);
		btnAddHobby.addActionListener(this);
		panelProfile.add(btnAddHobby);
		
		btnDeleteHobby = new JButton("Delete Hobby");
		btnDeleteHobby.setToolTipText("Select a Hobby from the list above before clicking");
		btnDeleteHobby.setBounds(566, 264, 116, 25);
		btnDeleteHobby.addActionListener(this);
		panelProfile.add(btnDeleteHobby);
		
		
		list = new JList(vectHobby);
		list.setToolTipText("List Of Hobbies Only 5 allowed");
		list.setVisibleRowCount(5);
    	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	list.addListSelectionListener(this);

    	scrollPane = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(566, 102, 135, 149);
		panelProfile.add(scrollPane);
		
		btnUpdateEmployee = new JButton("Update Employee");
		btnUpdateEmployee.setBounds(65, 393, 157, 25);
		btnUpdateEmployee.addActionListener(this);
		panelProfile.add(btnUpdateEmployee);
		
		lblAlias = new JLabel("Alias");
		lblAlias.setBounds(65, 136, 76, 16);
		panelProfile.add(lblAlias);
		
		tfAlias = new JTextField();
		tfAlias.setColumns(10);
		tfAlias.setBounds(164, 133, 116, 22);
		panelProfile.add(tfAlias);
		
		SkillsTab sk = new SkillsTab(clientControl);
		
		
		//panelSkills = new JPanel();
		
		tabbedPane.addTab("SKILLS", null, sk, null);
		btnMainExit = new JButton("Exit");
		btnMainExit.setBounds(10, 711, 209, 25);
		btnMainExit.addActionListener(this);
		add(btnMainExit);
		
		SearchMenu searchMenuTab = new SearchMenu(clientControl);
		
//		tabbedPane.addTab("SEARCH", null, searchMenuTab, null);
//		btnMainExit = new JButton("Exit");
//		btnMainExit.setBounds(10, 711, 209, 25);
//		btnMainExit.addActionListener(this);
//		add(btnMainExit);
		
		btnLogoff = new JButton("Log Off");
		btnLogoff.setBounds(859, 713, 209, 25);
		btnLogoff.addActionListener(this);
		add(btnLogoff);
		//add info on employee here;
		tfAlias.setText(emp.getAlias());
		tfName.setText(emp.getFirstName());
		tfSurname.setText(emp.getSurname());
		tfContact.setText(emp.getContactNo());
		tfEmail.setText(emp.getEmail());	
		
		//	comboBox = new JComboBox(vectHobby);
		//AutoCompleteDe
		//AutoCompletion ac = new AutoCompletion(comboBox);
		//AutoCompletion.enable(comboBox);
		//comboBox.setEditable(true);
		//comboBox.setBounds(164, 331, 231, 22);
		//panelProfile.add(comboBox);
		RatingOfSkills ratingSkillsTab = new RatingOfSkills(clientControl);
		tabbedPane.addTab("RATING SKILLS", null, ratingSkillsTab, null);
		

	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();	
		
		if(source == btnUpdateEmployee)
		{
			
			if(tfName.getText().equals("") || tfName.getText() == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Hobby ?");
				tfName.grabFocus();
				return;
			}
			if(tfSurname.getText().equals("") || tfSurname.getText() == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Surname ?");
				tfSurname.grabFocus();
				return;
			}
			if(tfContact.getText().equals("") || tfName.getText() == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Contact Number ?");
				tfContact.grabFocus();
				return;
			}
			if(tfEmail.getText().equals("") || tfEmail.getText() == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Email Address ?");
				tfContact.grabFocus();
				return;
			}
			
			emp.setAlias(tfAlias.getText());
			emp.setFirstName(tfName.getText());
			emp.setSurname(tfSurname.getText());
			emp.setContactNo(tfContact.getText());
			emp.setEmail(tfEmail.getText());
			
			if(clientControl.updateEmployee(emp))
			{
				JOptionPane.showMessageDialog(this, "Successfully Updated employee details?");
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Error Updating Employee?");
			}
			
		}
		
		if(source == btnAddHobby)
		{
			tfAddHobby.grabFocus();
			addHobbyValue = tfAddHobby.getText();
			if(addHobbyValue.equals("") || addHobbyValue == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Hobby ?");
				tfAddHobby.grabFocus();
				return;
			}
			//if hobby populated
			if(vectHobby.contains(addHobbyValue))
			{
				JOptionPane.showMessageDialog(this, "Oops That Hobby already exists, please try another");
				tfAddHobby.grabFocus();
				tfAddHobby.selectAll();
				return; 
			}
//			if(vectHobby.size() >= 5)
//			{
//				JOptionPane.showMessageDialog(this, "Oops You cannot have more than 5 hobbies captured");
//				return;
//			}
			//call database to insert new hobby if successful add it to the list.
			//must test below
			boolean existingHobby = false;
			for(int j = 0;j < allHobby.size();j++)
			{
				
				if (addHobbyValue.equalsIgnoreCase(allHobby.get(j).getHobbyDescription()))
				{
					System.out.println("existing hobby");
					
					existingHobby = true;
					vectHobby.add(addHobbyValue);
					empHobby.add(allHobby.get(j).getHobbyID());
					emp.setEmpHobbies(empHobby);
					clientControl.updateEmployee(emp);	
					break;
				}
			}
			if(!existingHobby)
			{
				short hobbyiddddd = clientControl.addHobby(addHobbyValue);
				System.out.println("new hobby = " + hobbyiddddd);
				//adding a hobby that does'nt exist in the current users profile.
				empHobby.add(hobbyiddddd);
				for (int i = 0; i < empHobby.size();i++)
				{
					System.out.println("in emp hobby loop : " + empHobby.get(i).toString());
				}
				emp.setEmpHobbies(empHobby);
				clientControl.updateEmployee(emp);
				vectHobby.addElement(addHobbyValue);
			}
						
		}
		if(source == btnDeleteHobby)
		{
			if(deleteHobbyValue.equals("") || deleteHobbyValue == null )
			{
				JOptionPane.showMessageDialog(this, "Please select Your Hobby from the list to remove ?");
				return;
			}
			//if hobby populated
			//call database to delete existing hobby if successful remove it to the list.
			//database must happen here
			vectHobby.removeElement(deleteHobbyValue);
			
		}
		if(source == btnMainExit)
		{
			 int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to Exit ?",
					 				"Exit",JOptionPane.OK_CANCEL_OPTION);
			 if(JOptionPane.OK_OPTION == choice)
			 {
				 clientControl.closeConnections();
				 System.exit(0);
			 }
		}
		if(source == btnLogoff)
		{
			 int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to Log Off ?",
					 				"Log Off ",JOptionPane.OK_CANCEL_OPTION);
			 if(JOptionPane.OK_OPTION == choice)
			 {
				LoginPanel logP = new LoginPanel(basePanel,clientControl);
				this.basePanel.removeAll();
				this.basePanel.validate();
				this.basePanel.repaint();
				this.basePanel.add(logP);
				this.basePanel.validate();
				this.basePanel.repaint();
				this.basePanel.setVisible(true);
			 }
		}
		list.updateUI();
	}
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object source = e.getSource();
		if(list == source && (!(e.getValueIsAdjusting()))) // isValueAdjusting does'nt check other events only when mouse released will get through 
		{
			deleteHobbyValue = list.getSelectedValue().toString();
		}
	}
}
