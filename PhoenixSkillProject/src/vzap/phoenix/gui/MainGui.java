package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

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
	private JLabel lblNewLabel_1;
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
	/**
	 * Create the panel.
	 */
	public MainGui(JPanel basePanel, boolean newUser,String loggedInUser)
	{
		//My added code
		this.basePanel = basePanel;
		this.newUser = newUser;
		this.loggedInUser = loggedInUser;
		
		vectHobby = new Vector<String>();
		vectHobby.add("Running");
		vectHobby.add("Smoking");
		vectHobby.add("Drinking");
		vectHobby.add("Pool");
		vectHobby.add("Killing");
		//end my added code
		
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1141, 780);
		add(tabbedPane);
		
		panelProfile = new JPanel();
		tabbedPane.addTab("PROFILE", null, panelProfile, null);
		panelProfile.setLayout(null);
		
		lblNewLabel = new JLabel("User ID");
		lblNewLabel.setBounds(65, 102, 56, 16);
		panelProfile.add(lblNewLabel);
		
		lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(65, 131, 76, 16);
		panelProfile.add(lblFirstName);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setBounds(65, 160, 76, 16);
		panelProfile.add(lblSurname);
		
		lblContact = new JLabel("Contact");
		lblContact.setBounds(65, 195, 76, 16);
		panelProfile.add(lblContact);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(65, 235, 76, 16);
		panelProfile.add(lblEmail);
		
		tfUserID = new JTextField();
		tfUserID.setBounds(164, 99, 116, 22);
		panelProfile.add(tfUserID);
		tfUserID.setColumns(10);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(164, 128, 116, 22);
		panelProfile.add(tfName);
		
		tfSurname = new JTextField();
		tfSurname.setColumns(10);
		tfSurname.setBounds(164, 157, 116, 22);
		panelProfile.add(tfSurname);
		
		tfContact = new JTextField();
		tfContact.setColumns(10);
		tfContact.setBounds(164, 192, 116, 22);
		panelProfile.add(tfContact);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(164, 227, 116, 22);
		panelProfile.add(tfEmail);
		
		lblHobbies = new JLabel("Hobbies");
		lblHobbies.setBounds(65, 268, 76, 16);
		panelProfile.add(lblHobbies);
		
		tfAddHobby = new JTextField();
		tfAddHobby.setColumns(10);
		tfAddHobby.setBounds(164, 265, 116, 22);
		panelProfile.add(tfAddHobby);
		
		btnAddHobby = new JButton("Add Hobby");
		btnAddHobby.setBounds(297, 264, 97, 25);
		panelProfile.add(btnAddHobby);
		
		btnDeleteHobby = new JButton("Delete Hobby");
		btnDeleteHobby.setToolTipText("Select a Hobby from the llist above before clicking");
		btnDeleteHobby.setBounds(566, 264, 116, 25);
		panelProfile.add(btnDeleteHobby);
		
		
		list = new JList(vectHobby);
		list.setToolTipText("List Of Hobbies Only 5 allowed");
		list.setVisibleRowCount(5);
    	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	list.addListSelectionListener(this);

    	scrollPane = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(566, 102, 135, 149);
		panelProfile.add(scrollPane);
		
		panelSkills = new JPanel();
		tabbedPane.addTab("SKILLS", null, panelSkills, null);
		panelSkills.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Skillls");
		lblNewLabel_1.setBounds(70, 161, 393, 169);
		panelSkills.add(lblNewLabel_1);
		
		btnMainExit = new JButton("Exit");
		btnMainExit.setBounds(12, 866, 209, 25);
		add(btnMainExit);

	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this,	"Test2: " + list.getSelectedValue().toString());
		
	}
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object source = e.getSource();
		
		if(list == source && (!(e.getValueIsAdjusting()))) // isValueAdjusting does'nt check other events only when mouse released will get through 
		{
			JOptionPane.showMessageDialog(this,	"Test: " + list.getSelectedValue().toString());
		}
		
	}
}
