package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import vzap.phoenix.DAO.EmployeeSkillDAO;
import vzap.phoenix.DAO.SkillDAO;import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.table.TableModel;

public class RatingNomination extends JPanel implements ActionListener, MouseListener
{
	private JLabel selectRaterlbl;
	private JButton btnOutRatings;
	private JButton btnDeleteSkill;
	private JLabel nomineelbl;
	private JTextField raterIDJTF;
	private JTable selectTable, nominateTable;
	private JScrollPane scrollPane, nominateScrollPane;
	private JButton btnSubmit;
	private Vector<String> comboSkill; 
	private Vector<Integer> selectedSkill;
	private Vector<String> comboRaters;
	private ArrayList<EmployeeSkill> empSkillList;
	private EmpSkillClient empSkillClient;
	private String [] skillHeader;
	private ArrayList<Employee> empList;
	private ArrayList<Skill> skillList;
	private String searchCriteria;
	private JTextField empSearchJTF;
	private JButton btnAdd;
	private Object[] empHeader,empRow;
	private DefaultTableModel selectModel, nominateModel;
	private String[] nominateHeader;
	private Object[] nominateRow;
	private JButton btnSearch;
	private int row;
	private EmployeeSkill employeeSkill;
	private Employee loggedOnEmployee;
	private JTable table_1;
	private JTextField raterName;


	/**
	 * Create the panel.
	 */
	public RatingNomination()
	{
		setLayout(null);
		
		empSkillClient = new EmpSkillClient();
		employeeSkill = new EmployeeSkill();
        empSkillClient.loginEmployee("A159842","1234");
        System.out.println("RatingNomination - back from loginEmployee ");
		loggedOnEmployee = empSkillClient.getLogonEmployee();
		empSkillList = empSkillClient.getEmpSkillList();
		skillList = empSkillClient.getSkillList();
        System.out.println("RatingNomination - skillList size - " + empSkillList.size());
        comboSkill = new Vector<>();
        for(int i = 0 ; i < empSkillList.size() ; i++)
        {
            for(int j = 0; j < skillList.size(); j++)
            {
            	if(skillList.get(j).getSkillId() == empSkillList.get(i).getSkillID())
            	{
            		comboSkill.add(skillList.get(j).getSkillDescription());            		
            	}
            }
        }
	
		selectModel = new DefaultTableModel();
        
        empHeader = new String[]{"UserId","First Name","Surname","Alias"};
        selectModel.setColumnIdentifiers(empHeader);
        selectTable = new JTable(selectModel);
		
		selectTable.getSelectionModel();
		selectTable.addMouseListener(this);
		
		selectRaterlbl = new JLabel("Select Raters");
		selectRaterlbl.setFont(new Font("Arial", Font.PLAIN, 19));
		selectRaterlbl.setBounds(268, 11, 142, 32);
		add(selectRaterlbl);
		
		nomineelbl = new JLabel("Rater/Nominee");
		nomineelbl.setFont(new Font("Arial", Font.PLAIN, 14));
		nomineelbl.setBounds(160, 121, 125, 16);
		add(nomineelbl);
		
		scrollPane = new JScrollPane(selectTable);
		scrollPane.setBounds(10, 528, 535, 112);
		add(scrollPane);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(163, 651, 89, 23);
		add(btnSubmit);
		btnSubmit.addActionListener(this);
		
		btnOutRatings = new JButton("List Outstanding Ratings");
		btnOutRatings.setBounds(10, 331, 184, 23);
		add(btnOutRatings);
		btnOutRatings.addActionListener(this);
		
		raterIDJTF = new JTextField();
		raterIDJTF.setBounds(109, 148, 85, 20);
		add(raterIDJTF);
		raterIDJTF.setColumns(10);
		
		btnDeleteSkill = new JButton("Delete nomination");
		btnDeleteSkill.setBounds(10, 651, 142, 23);
		add(btnDeleteSkill);
		btnDeleteSkill.addActionListener(this);
		
		empSearchJTF = new JTextField();
		empSearchJTF.setBounds(109, 67, 211, 20);
		add(empSearchJTF);
		empSearchJTF.setColumns(10);
		
		btnAdd = new JButton("ADD:");
		btnAdd.setBounds(10, 147, 89, 23);
		btnAdd.addActionListener(this);
		add(btnAdd);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(10, 66, 89, 23);
		btnSearch.addActionListener(this);
		add(btnSearch);
		
		nominateHeader = new String[]{"Rater ID", "Rater Name", "Skill"};
		nominateRow = new Object [3];
		nominateModel = new DefaultTableModel();
		nominateModel.setColumnIdentifiers(nominateHeader);
			
		nominateTable = new JTable(nominateModel);
		TableColumn nominateColumn = nominateTable.getColumnModel().getColumn(2);
		this.setUpSkillColumn(nominateTable, nominateTable.getColumn("Skill"));
		
		nominateScrollPane = new JScrollPane(nominateTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		nominateScrollPane.setBounds(10, 192, 535, 112);
		add(nominateScrollPane);

		
		nominateScrollPane.setViewportView(nominateTable);
		
		raterName = new JTextField();
		raterName.setColumns(10);
		raterName.setBounds(204, 148, 146, 20);
		add(raterName);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		
		if(source == btnSubmit)
		{

		for(int i = 0; i < nominateModel.getRowCount();i++)
		{
			System.out.println("Into the Submit buitton loop = " + nominateModel.getValueAt(i, 0));
			System.out.println("Print logged on employee = " + loggedOnEmployee.getEmployeeID());
			//change to new employee skill as opposed to a set method
			String employeeID = loggedOnEmployee.getEmployeeID();
			int skillId = 0;
			for (int j = 0; j < skillList.size(); j++)
			{
				if(skillList.get(j).getSkillDescription()==(String)nominateModel.getValueAt(i, 2))
				{
					skillId = skillList.get(j).getSkillId() ;	
					break;
				}
			}
			String raterID = (String)nominateModel.getValueAt(i, 0);
			Date createdDate = new Date();
			employeeSkill = new EmployeeSkill(employeeID,skillId,raterID,createdDate);
			boolean success = empSkillClient.nominateRater(employeeSkill);
		}
					
		}
		if(source == btnAdd)
        {
			nominateRow[0]= raterIDJTF.getText();
			nominateRow[1]= raterName.getText();
			nominateModel.addRow(nominateRow);

        }
		if(source == btnSearch)
		{
			searchCriteria = empSearchJTF.getText();
	        
			empList = empSkillClient.searchEmployee(empSearchJTF.getText());
	        System.out.println("RatingNomination - Employee size - " + empList.size());
	        empRow = new Object[4];
	        //comboRaters = new Vector<>();
	        for(int i = 0 ; i < empList.size() ; i++)
	        {
	                    System.out.println("RatingNomination - skillList for -  " + i + " desc " + empList.get(i).getEmployeeID());
                        empRow[0] = empList.get(i).getEmployeeID();
                        empRow[1] = empList.get(i).getFirstName();
                        empRow[2] = empList.get(i).getSurname();
                        empRow[3] = empList.get(i).getAlias();
                        selectModel.addRow(empRow);
                        
	        }
	        
		}
		
	}
    public void setUpSkillColumn(JTable table,
            TableColumn skill) 
    {
		//Set up the editor for the sport cells.
		JComboBox skillBox = new JComboBox();
		for (int j = 1; j<skillList.size(); j++)
		{
			skillBox.addItem(skillList.get(j).getSkillDescription());
		}
		skill.setCellEditor(new DefaultCellEditor(skillBox));
		
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer =
		new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		skill.setCellRenderer(renderer);
    }

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Admin GUI");
		frame.setSize(700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RatingNomination panel = new RatingNomination();
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
        row = selectTable.getSelectedRow();
        System.out.println("row = " + row);
        if (row <0)
        {
                    JOptionPane.showMessageDialog(this,"Please select a row from the table");
                    return;
        }
        raterIDJTF.setText((String) selectTable.getValueAt(row, 0) );
        raterName.setText((String)selectTable.getValueAt(row, 2)+","+selectTable.getValueAt(row, 1));
        row = 0;           
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
