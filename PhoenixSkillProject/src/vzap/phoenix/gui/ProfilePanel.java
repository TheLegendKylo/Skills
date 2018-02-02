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

public class ProfilePanel extends JPanel implements ActionListener
{
	
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
	
	public ProfilePanel(Employee emp,EmpSkillClientController clientControl)
	{
		setBorder(null);
		
		this.emp = emp;
		this.clientControl = clientControl;
		
		setLayout(null);
		
		lblNewLabel = new JLabel("User ID");
		lblNewLabel.setBounds(286, 103, 56, 16);
		add(lblNewLabel);
		
		lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(286, 166, 76, 16);
		add(lblFirstName);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setBounds(286, 195, 76, 16);
		add(lblSurname);
		
		lblContact = new JLabel("Contact");
		lblContact.setBounds(286, 230, 76, 16);
		add(lblContact);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(286, 270, 76, 16);
		add(lblEmail);
		
		tfUserID = new JTextField();
		tfUserID.setEditable(false);
		tfUserID.setBounds(385, 100, 116, 22);
		add(tfUserID);
		tfUserID.setColumns(10);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(385, 163, 116, 22);
		add(tfName);
		
		tfSurname = new JTextField();
		tfSurname.setColumns(10);
		tfSurname.setBounds(385, 189, 116, 22);
		add(tfSurname);
		
		tfContact = new JTextField();
		tfContact.setColumns(10);
		tfContact.setBounds(385, 224, 116, 22);
		add(tfContact);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(15);
		tfEmail.setBounds(385, 259, 116, 22);
		add(tfEmail);
		
		lblHobbies = new JLabel("Hobbies");
		lblHobbies.setBounds(286, 303, 76, 16);
		add(lblHobbies);
		
		tfAddHobby = new JTextField();
		tfAddHobby.setColumns(10);
		tfAddHobby.setBounds(385, 300, 116, 22);
		add(tfAddHobby);
		
		btnAddHobby = new JButton("Add Hobby");
		btnAddHobby.setBounds(519, 299, 97, 25);
		btnAddHobby.addActionListener(this);
		add(btnAddHobby);
		
		btnDeleteHobby = new JButton("Delete Hobby");
		btnDeleteHobby.setToolTipText("Select a Hobby from the list above before clicking");
		btnDeleteHobby.setBounds(745, 265, 116, 25);
		btnDeleteHobby.addActionListener(this);
		add(btnDeleteHobby);
		
		btnUpdateEmployee = new JButton("Update Employee");
		btnUpdateEmployee.setBounds(286, 373, 157, 25);
		btnUpdateEmployee.addActionListener(this);
		add(btnUpdateEmployee);
		
		lblAlias = new JLabel("Alias");
		lblAlias.setBounds(286, 137, 76, 16);
		add(lblAlias);
		
		tfAlias = new JTextField();
		tfAlias.setColumns(10);
		tfAlias.setBounds(385, 134, 116, 22);
		add(tfAlias);
		
		setup();
		list = new JList(vectHobby);
		list.setVisibleRowCount(5);
    	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    	scrollPane = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(745, 100, 135, 149);
		add(scrollPane);
		

	}
	public void setup()
	{
		System.out.println("We in setup");
		//get logged in user
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
					empHobby.add(allHobby.get(j).getHobbyID());
					
					//added loop for testing purposes
					for(int i=0;i< empHobby.size();i++)
					{
						System.out.println(" existing employee hoobbbbyyyy : " + empHobby.get(i));
					}
					emp.setEmpHobbies(empHobby);
				
					//clientControl.updateEmployee(emp);	
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
				System.out.println("we in the emp loop : " + empHobby.get(i) + " del Hobby : " + delhobbyID );
				
				if(empHobby.get(i) == delhobbyID)
				{
					System.out.println("we in the delete : " + empHobby.get(i) + " del Hobby : " + delhobbyID );
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
