package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import vzap.phoenix.DAO.EmployeeSkillDAO;
import vzap.phoenix.DAO.SkillDAO;import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeController;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClient;
import vzap.phoenix.client.EmpSkillClientController;

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
	private JButton btnDeleteNomination;
	private JLabel nomineelbl;
	private JTextField raterIDJTF;
	private JTable selectTable, nominateTable;
	private JScrollPane scrollPane, nominateScrollPane;
	private JButton btnSubmit;
	private Vector<String> comboSkill; 
	private Vector<Integer> selectedSkill;
	private Vector<String> comboRaters;
	private ArrayList<EmployeeSkill> empSkillList,empOutSkillList;
	private EmpSkillClient empSkillClient;
	private String [] skillHeader;
	private ArrayList<Employee> empList;
	private ArrayList<Skill> skillList;
	private String searchCriteria;
	private JTextField empSearchJTF;
	private JButton btnAdd;
	private Object[] empHeader,empRow;
	private DefaultTableModel selectModel, nominateModel, outstandingModel;
	private String[] nominateHeader, outStandingHeader;
	private Object[] nominateRow, outStandingRow;
	private JButton btnSearch;
	private int row;
	private EmployeeSkill employeeSkill;
	private Employee loggedOnEmployee;
	private JTable table_1;
	private JTextField raterName;
	private JScrollPane outstandingRatesScrollP;
	private JTable outstandingRatesTable;
	private EmpSkillClientController overdueRatings;
	private EmployeeController clientControl;


	/**
	 * Create the panel.
	 */
	public RatingNomination()
	{
		setLayout(null);
		
		empSkillClient = new EmpSkillClient();
		employeeSkill = new EmployeeSkill();
        empSkillClient.loginEmployee("A119685","1234");
 		loggedOnEmployee = empSkillClient.getLogonEmployee();
		empSkillList = empSkillClient.getEmpSkillList();
		skillList = empSkillClient.getSkillList();
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
		nomineelbl.setBounds(160, 247, 125, 16);
		add(nomineelbl);
		
		scrollPane = new JScrollPane(selectTable);
		scrollPane.setBounds(10, 110, 535, 112);
		add(scrollPane);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(163, 629, 89, 23);
		add(btnSubmit);
		btnSubmit.addActionListener(this);
		
		raterIDJTF = new JTextField();
		raterIDJTF.setBounds(109, 274, 85, 20);
		add(raterIDJTF);
		raterIDJTF.setColumns(10);
		
		btnDeleteNomination = new JButton("Delete nomination");
		btnDeleteNomination.setBounds(10, 629, 142, 23);
		add(btnDeleteNomination);
		btnDeleteNomination.addActionListener(this);
		
		empSearchJTF = new JTextField();
		empSearchJTF.setBounds(109, 67, 211, 20);
		add(empSearchJTF);
		empSearchJTF.setColumns(10);
		
		btnAdd = new JButton("ADD");
		btnAdd.setBounds(10, 273, 89, 23);
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
		nominateScrollPane.setBounds(10, 324, 535, 112);
		add(nominateScrollPane);

		
		nominateScrollPane.setViewportView(nominateTable);
		
		raterName = new JTextField();
		raterName.setColumns(10);
		raterName.setBounds(204, 274, 146, 20);
		add(raterName);
		
		outStandingHeader = new String[]{"Rater ID", "Rater Name", "Skill"};
		outStandingRow = new Object [3];
		outstandingModel = new DefaultTableModel();
		outstandingModel.setColumnIdentifiers(outStandingHeader);
		
		outstandingRatesTable = new JTable(outstandingModel);


		outstandingRatesScrollP = new JScrollPane(outstandingRatesTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outstandingRatesScrollP.setBounds(10, 481, 535, 112);
		add(outstandingRatesScrollP);
		outstandingRatesScrollP.setViewportView(outstandingRatesTable);
		
		JLabel lblOutStandingNomination = new JLabel("Outstanding Norminations");
		lblOutStandingNomination.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOutStandingNomination.setBounds(10, 447, 184, 23);
		add(lblOutStandingNomination);
		
		empOutSkillList = new ArrayList<EmployeeSkill>();
		overdueRatings = new EmpSkillClientController();
		empList = new ArrayList<Employee>();
		clientControl = new EmployeeController("A119685", "1234");
		for (int i = 0; i < empSkillList.size(); i++)
		{
			if(empSkillList.get(i).getStatus()==0)
			{
				empOutSkillList.add(empSkillList.get(i));
				System.out.println("XXXXX value after deleting list= " + empSkillList.get(i));
			}
		}

		outStandingRow = new Object[3];

        for(int i = 0 ; i < empOutSkillList.size() ; i++)
        {
            System.out.println("into first loop = " + i);
        	empList = clientControl.searchEmployee((String) empOutSkillList.get(i).getRaterID());
        	for(int j = 0; j < empList.size(); j++)        
            {
        		if(empList.get(j).getEmployeeID().equals(empOutSkillList.get(i).getRaterID()) )
            	{
                    outStandingRow[0] = empOutSkillList.get(i).getRaterID();
                    outStandingRow[1] = empList.get(j).getSurname() + ", " + empList.get(j).getFirstName();
                    
                    for(int k = 0; k < skillList.size(); k++)
                    {
                    	if(skillList.get(k).getSkillId() == (empOutSkillList.get(i).getSkillID()))
                    	{
                    		outStandingRow[2] = skillList.get(k).getSkillDescription();            		
                    	}
                    }
            	}
        		outstandingModel.addRow(outStandingRow);  
            }
        }
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
			//clear input fields
			raterIDJTF.setText("");
			raterName.setText("");
			for(int r = 0; r < nominateModel.getRowCount(); r++)
			{
				nominateModel.removeRow(r);
			}
			
		}
					
		}
		if(source == btnAdd)
        {
			nominateRow[0]= raterIDJTF.getText();
			nominateRow[1]= raterName.getText();
			nominateModel.addRow(nominateRow);

        }
		if(source == btnDeleteNomination)
        {
            int row = outstandingRatesTable.getSelectedRow();
            System.out.println("row = " + row);
            if (row <0)
            {
                        JOptionPane.showMessageDialog(this,"Please select a row from the Oustanding Ratings table");
                        return;
            }
            System.out.println("value = " + outstandingRatesTable.getValueAt(row, 0));
            System.out.println("value = " + outstandingRatesTable.getValueAt(row, 1));
            System.out.println("value = " + outstandingRatesTable.getValueAt(row, 2));
            
            String raterID = (String) outstandingRatesTable.getValueAt(row, 0);
            System.out.println("String Value is = " + (String) outstandingRatesTable.getValueAt(row, 0));
            int skillID = 0;
                for(int j = 0; j < skillList.size(); j++)
                {
                	
                	if(skillList.get(j).getSkillDescription().equals(outstandingRatesTable.getValueAt(row, 2)))
                	{
                		skillID = skillList.get(j).getSkillId();  
                		System.out.println("Value skillid = " + skillID);
                		System.out.println("Descr1 = " + skillList.get(j).getSkillDescription() +"= "+ outstandingRatesTable.getValueAt(row, 2));
                	}
                }
             
            boolean success = clientControl.updateRatingStatus(skillID, raterID);
            
            //get your userid / relevant info
            //call database with info and do as you wish.
            
            //ensure the row value is initialised once you done.
            
            
            //deleting something 
            outstandingModel.removeRow(row);


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
