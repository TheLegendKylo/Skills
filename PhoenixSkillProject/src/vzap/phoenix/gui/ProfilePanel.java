package vzap.phoenix.gui;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.client.EmpSkillClientController;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class ProfilePanel extends JPanel implements ActionListener
{
	//real1
	private JPanel basePanel;
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
	private boolean hobbyChange = false;
	private boolean newUser = false;
	private MainGui mainGui;
	private JLabel lblListOfYour;
	

	public ProfilePanel(MainGui mainGui, Boolean newUser, Employee emp,EmpSkillClientController clientControl)
	{
		setBorder(null);
		
		this.mainGui = mainGui;
		this.newUser = newUser;
		this.emp = emp;
		this.clientControl = clientControl;
		
		setLayout(null);
		
		lblNewLabel = new JLabel("User ID");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(387, 103, 76, 28);
		add(lblNewLabel);
		
		lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Arial", Font.BOLD, 15));
		lblFirstName.setBounds(387, 192, 76, 28);
		add(lblFirstName);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Arial", Font.BOLD, 15));
		lblSurname.setBounds(387, 233, 76, 28);
		add(lblSurname);
		
		lblContact = new JLabel("Contact");
		lblContact.setFont(new Font("Arial", Font.BOLD, 15));
		lblContact.setBounds(387, 274, 76, 28);
		add(lblContact);
		
		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.BOLD, 15));
		lblEmail.setBounds(387, 315, 76, 28);
		add(lblEmail);
		
		tfUserID = new JTextField();
		tfUserID.setFont(new Font("Arial", Font.BOLD, 15));
		tfUserID.setEditable(false);
		tfUserID.setBounds(493, 103, 395, 28);
		add(tfUserID);
		tfUserID.setColumns(10);
		
		tfName = new JTextField();
		tfName.setFont(new Font("Arial", Font.BOLD, 15));
		tfName.setColumns(10);
		tfName.setBounds(493, 192, 395, 28);
		add(tfName);
		
		tfSurname = new JTextField();
		tfSurname.setFont(new Font("Arial", Font.BOLD, 15));
		tfSurname.setColumns(10);
		tfSurname.setBounds(493, 233, 395, 28);
		add(tfSurname);
		
		tfContact = new JTextField();
		tfContact.setFont(new Font("Arial", Font.BOLD, 15));
		tfContact.setColumns(10);
		tfContact.setBounds(493, 274, 395, 28);
		add(tfContact);
		
		tfEmail = new JTextField();
		tfEmail.setFont(new Font("Arial", Font.BOLD, 15));
		tfEmail.setColumns(15);
		tfEmail.setBounds(493, 315, 395, 28);
		add(tfEmail);
		
		lblHobbies = new JLabel("Hobby Text:");
		lblHobbies.setFont(new Font("Arial", Font.BOLD, 15));
		lblHobbies.setBounds(387, 356, 101, 28);
		add(lblHobbies);
		
		tfAddHobby = new JTextField();
		tfAddHobby.setFont(new Font("Arial", Font.BOLD, 15));
		tfAddHobby.setColumns(10);
		tfAddHobby.setBounds(493, 356, 232, 28);
		add(tfAddHobby);
		
		btnAddHobby = new JButton("Add Hobby");
		btnAddHobby.setFont(new Font("Arial", Font.BOLD, 15));
		btnAddHobby.setBounds(755, 356, 133, 28);
		btnAddHobby.addActionListener(this);
		add(btnAddHobby);
		
		btnDeleteHobby = new JButton("Delete Hobby");
		btnDeleteHobby.setFont(new Font("Arial", Font.BOLD, 15));
		btnDeleteHobby.setToolTipText("Select a Hobby from the list above before clicking");
		btnDeleteHobby.setBounds(1247, 359, 148, 25);
		btnDeleteHobby.addActionListener(this);
		add(btnDeleteHobby);
		
		btnUpdateEmployee = new JButton("Update Profile Deatils");
		btnUpdateEmployee.setFont(new Font("Arial", Font.BOLD, 15));
		if(newUser)
		{
			btnUpdateEmployee.setText("Register Employee");
			btnAddHobby.setEnabled(false);
			btnDeleteHobby.setEnabled(false);
			tfAddHobby.setEnabled(false);
		}
		btnUpdateEmployee.setBounds(387, 417, 501, 28);
		btnUpdateEmployee.addActionListener(this);
		add(btnUpdateEmployee);
		
		lblAlias = new JLabel("Alias");
		lblAlias.setFont(new Font("Arial", Font.BOLD, 15));
		lblAlias.setBounds(387, 151, 76, 28);
		add(lblAlias);
		
		tfAlias = new JTextField();
		tfAlias.setFont(new Font("Arial", Font.BOLD, 15));
		tfAlias.setColumns(10);
		tfAlias.setBounds(493, 151, 395, 28);
		add(tfAlias);
		
		setup();
		list = new JList(vectHobby);
		list.setFont(new Font("Arial", Font.BOLD, 15));
		list.setVisibleRowCount(5);
    	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    	scrollPane = new JScrollPane(list,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(1094, 161, 300, 185);
		add(scrollPane);
		
		lblListOfYour = new JLabel("List of Your Hobbies");
		lblListOfYour.setFont(new Font("Arial", Font.BOLD, 15));
		lblListOfYour.setBounds(1093, 120, 162, 28);
		add(lblListOfYour);
		

	}
	public void setup()
	{
		loggedInUser = emp.getEmployeeID();
		allHobby = clientControl.getHobbyList();
		vectHobby = new Vector<String>();
		
		empHobby = emp.getEmpHobbies();
		if(empHobby!=null)
		{
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
		}
		tfAlias.setText(emp.getAlias());
		tfName.setText(emp.getFirstName());
		tfSurname.setText(emp.getSurname());
		tfContact.setText(emp.getContactNo());
		tfEmail.setText(emp.getEmail());
		tfUserID.setText(loggedInUser);
		//list.updateUI();
		
	}
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();	
		
		if(source == btnUpdateEmployee)
		{
			
			if(tfName.getText().equals("") || tfName.getText() == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Name ?");
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

			if(newUser)
			{
				if(clientControl.registerEmployee(emp));
				{
					int errorCode = clientControl.loginEmployee(emp.getEmployeeID(), emp.getPassword());
					if(errorCode==0)
					{
						emp=clientControl.getLogonEmployee();
						btnAddHobby.setEnabled(true);
						btnDeleteHobby.setEnabled(true);
						newUser = false;
						btnUpdateEmployee.setText("Update Employee");
						mainGui.addTabs();
						tfAddHobby.setEnabled(true);
					}
					
				}
			} else {
			
				if(clientControl.updateEmployee(emp))
				{
					if(!hobbyChange)
					{
						JOptionPane.showMessageDialog(this, "Successfully Updated employee details");
					}
					tfAlias.setText(emp.getAlias());
					tfName.setText(emp.getFirstName());
					tfSurname.setText(emp.getSurname());
					tfContact.setText(emp.getContactNo());
					tfEmail.setText(emp.getEmail());
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Error Updating Employee?");
				}
			}
			
		}
		
		if(source == btnAddHobby)
		{
			hobbyChange = true;
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
			//call database to insert new hobby if successful add it to the list.
			boolean existingHobby = false;
			for(int j = 0;j < allHobby.size();j++)
			{
				
				if(addHobbyValue.equalsIgnoreCase(allHobby.get(j).getHobbyDescription()))
				{
					System.out.println("existing hobby");
					
					existingHobby = true;
					vectHobby.add(addHobbyValue);
					if(empHobby==null)
					{
						empHobby = new ArrayList<Short>();
					}
					empHobby.add(allHobby.get(j).getHobbyID());	
					btnUpdateEmployee.doClick();
					tfAddHobby.grabFocus();
					tfAddHobby.setText("");
					break;
				}
			}
			if(!existingHobby)
			{
				short hobbyiddddd = clientControl.addHobby(addHobbyValue);
				//adding a hobby that does'nt exist in the current users profile.
				if(empHobby==null)
				{
					empHobby = new ArrayList<Short>();
				}
				empHobby.add(hobbyiddddd);
				emp.setEmpHobbies(empHobby);
				
				//added loop for testing purposes
				for(int i=0;i< empHobby.size();i++)
				{
					System.out.println("new employee hoobbbbyyyy : " + empHobby.get(i));
				}
				btnUpdateEmployee.doClick();
				//clientControl.updateEmployee(emp);
				vectHobby.addElement(addHobbyValue);
			}
			hobbyChange = false;
			tfAddHobby.setText("");
						
		}
		if(source == btnDeleteHobby)
		{
			hobbyChange = true;
			deleteHobbyValue = (String) list.getSelectedValue();
			if(deleteHobbyValue == null || deleteHobbyValue.equals("") )
			{
				JOptionPane.showMessageDialog(this, "Please select Your Hobby from the list to remove ?");
				return;
			}
			//if hobby populated
			//call database to delete existing hobby if successful remove it to the list.
			//database must happen here
			//vectHobby.removeElement(deleteHobbyValue);
			short delhobbyID=0;
			//first ensure we have the ID for the hobby
			for(int j = 0;j < allHobby.size();j++)
			{
				if (deleteHobbyValue.equalsIgnoreCase(allHobby.get(j).getHobbyDescription()))
				{
					System.out.println("delete hobby value: " + allHobby.get(j).getHobbyID());
					delhobbyID = allHobby.get(j).getHobbyID();
					break;
				}
			}
			//use the ID to match to the employee short array of hobby IDs
			for(int i=0;i< empHobby.size();i++)
			{	
				if(empHobby.get(i) == delhobbyID)
				{
					//remove the element where the match occurred
					empHobby.remove(i);
					//set the employees hobbies
					emp.setEmpHobbies(empHobby);
					// update the employee 
					btnUpdateEmployee.doClick();
					//clientControl.updateEmployee(emp);
					//remove from display
					vectHobby.removeElement(deleteHobbyValue);
					break;
				}
			}
			hobbyChange = false;
			deleteHobbyValue = null;
			
		}
		list.updateUI();
	}
}
