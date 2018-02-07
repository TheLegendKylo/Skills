package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.client.EmpSkillClientController;

import javax.swing.*;
import java.awt.Color;

public class MainGui extends JPanel implements ActionListener,ChangeListener
{
	//real
	private JTabbedPane tabbedPane;
	private JPanel basePanel;
	private JButton btnMainExit;
	private JButton btnLogoff;
	private EmpSkillClientController clientControl = null;
	private Employee emp = null;
	private ProfilePanel pp = null;
	private SkillsTab sk = null;
	private RatingOfSkills ratingSkillsTab = null;
	private SearchMenu searchMenu =null;
	private SearchBySkill searchBySkill =null;
	private RatingNomination ratingNom = null;
	private HelpScreen helpScreen = null;
	/**
	 * ...
	 * Create the panel.
	 */
	public MainGui(JPanel basePanel, boolean newUser,Employee emp,EmpSkillClientController clientControl)
	{
		setBorder(null);
		
		this.basePanel = basePanel;
		this.emp = emp;
		this.clientControl = clientControl;
		setLayout(null);
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(42, 104, 1812, 711);
		tabbedPane.addChangeListener(this);
		tabbedPane.setFont(new Font("Arial",Font.BOLD,20));
		add(tabbedPane);
	
		pp = new ProfilePanel(this, newUser,emp, clientControl);
		tabbedPane.addTab(" MY PROFILE ", null, pp, null);
		
		if(!newUser)
		{
			this.addTabs();
		}

		btnMainExit = new JButton("Exit");
		btnMainExit.setBackground(Color.WHITE);
		btnMainExit.setFont(new Font("Arial", Font.BOLD, 17));
		btnMainExit.setBounds(386, 827, 209, 25);
		btnMainExit.addActionListener(this);
		add(btnMainExit);
		
		btnLogoff = new JButton("Log Off");
		btnLogoff.setBackground(Color.WHITE);
		btnLogoff.setFont(new Font("Arial", Font.BOLD, 17));
		btnLogoff.setBounds(1054, 828, 209, 25);
		btnLogoff.addActionListener(this);
		add(btnLogoff);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();	
		
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
				clientControl.closeConnections();
		
				this.clientControl = new EmpSkillClientController();
				
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
	}
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if( tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(" RATING NOMINATIONS "))
		{
			ratingNom.setup();
		}
		if( tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(" SEARCH BY EMPLOYEE "))
		{
			searchMenu.setup();
		}
		if( tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(" MY SKILLS "))
		{
			sk.setup();
		}
		if( tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(" MY SKILLS RATINGS "))
		{
			ratingSkillsTab.setup();
		}
	}
	public void addTabs()
	{
		System.out.println("<<<<Adding tabs>>>>>");
		sk = new SkillsTab(clientControl,emp);
		tabbedPane.addTab(" MY SKILLS ", null, sk, null);
		
		helpScreen = new HelpScreen(clientControl);
		tabbedPane.addTab(" HELP ", null, helpScreen, null);

		ratingSkillsTab = new RatingOfSkills(clientControl,emp);
		tabbedPane.addTab(" MY SKILLS RATINGS ", null, ratingSkillsTab, null);
		
		ratingNom = new RatingNomination(clientControl, emp);
		tabbedPane.addTab(" RATING NOMINATIONS ", null, ratingNom, null);
		
		searchMenu = new SearchMenu(clientControl,emp);
		tabbedPane.addTab(" SEARCH BY EMPLOYEE ", null, searchMenu, null);
		
		searchBySkill = new SearchBySkill(clientControl);
		tabbedPane.addTab(" SEARCH BY SKILL ", null, searchBySkill, null);
		
		helpScreen = new HelpScreen(clientControl);
		tabbedPane.addTab(" HELP ", null, helpScreen, null);
	}
}
