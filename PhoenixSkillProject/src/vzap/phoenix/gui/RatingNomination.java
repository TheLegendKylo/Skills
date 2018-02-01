package vzap.phoenix.gui;

import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.*;

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
	private Employee emp;
	private JTable table_1;
	private JTextField raterName;
	private JScrollPane outstandingRatesScrollP;
	private JTable outstandingRatesTable;
	private EmpSkillClientController clientControl;
	private Color colour;


	/**
	 * Create the panel.
	 */
	public RatingNomination(EmpSkillClientController clientControl,Employee emp)
	{
		setLayout(null);
		this.clientControl = clientControl;
		this.emp=emp;
		
//		employeeSkill = new EmployeeSkill();
        
		empSkillList = clientControl.getEmpSkillList();
		skillList = clientControl.getSkillList();
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
		colour = new Color(255,255,255);
        
        empHeader = new String[]{"UserId","First Name","Surname","Alias"};
        selectModel.setColumnIdentifiers(empHeader);
        selectTable = new JTable(selectModel);
		
		selectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectTable.addMouseListener(this);
		
		selectRaterlbl = new JLabel("Select Raters");
		selectRaterlbl.setFont(new Font("Arial", Font.PLAIN, 20));
		selectRaterlbl.setBounds(268, 11, 142, 32);
		add(selectRaterlbl);
		
		nomineelbl = new JLabel("Add Rater");
		nomineelbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nomineelbl.setBounds(10, 234, 201, 22);
		add(nomineelbl);
		
		scrollPane = new JScrollPane(selectTable);
		scrollPane.setBounds(10, 110, 623, 112);
		add(scrollPane);
		
		btnSubmit = new JButton("SUBMIT RATERS");
		btnSubmit.setBounds(10, 423, 153, 22);
		add(btnSubmit);
		btnSubmit.addActionListener(this);
		
		raterIDJTF = new JTextField();
		raterIDJTF.setBounds(109, 267, 85, 22);
		raterIDJTF.setEditable(false);
		raterIDJTF.setBackground(colour);
		add(raterIDJTF);
		raterIDJTF.setColumns(10);
		
		btnDeleteNomination = new JButton("DELETE NOMINATION");
		btnDeleteNomination.setBounds(10, 629, 153, 22);
		add(btnDeleteNomination);
		btnDeleteNomination.addActionListener(this);
		
		empSearchJTF = new JTextField();
		empSearchJTF.setBounds(109, 67, 211, 22);
		add(empSearchJTF);
		empSearchJTF.setColumns(10);
		
		btnAdd = new JButton("ADD");
		btnAdd.setBounds(10, 266, 89, 23);
		btnAdd.addActionListener(this);
		add(btnAdd);
		
		btnSearch = new JButton("SEARCH");
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
		nominateScrollPane.setBounds(10, 300, 623, 112);
		add(nominateScrollPane);

		
		nominateScrollPane.setViewportView(nominateTable);
		
		raterName = new JTextField();
		raterName.setColumns(10);
		raterName.setEditable(false);
		raterName.setBackground(colour);
		raterName.setBounds(204, 267, 146, 22);
		add(raterName);
		
		outStandingHeader = new String[]{"Rater ID", "Rater Name", "Skill","Date Requested"};
		outStandingRow = new Object [4];
		outstandingModel = new DefaultTableModel();
		outstandingModel.setColumnIdentifiers(outStandingHeader);
		
		outstandingRatesTable = new JTable(outstandingModel);


		outstandingRatesScrollP = new JScrollPane(outstandingRatesTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outstandingRatesScrollP.setBounds(10, 506, 623, 112);
		add(outstandingRatesScrollP);
		outstandingRatesScrollP.setViewportView(outstandingRatesTable);
		
		JLabel lblOutStandingNomination = new JLabel("Outstanding Norminations");
		lblOutStandingNomination.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOutStandingNomination.setBounds(10, 472, 236, 23);
		add(lblOutStandingNomination);
		
		empOutSkillList = new ArrayList<EmployeeSkill>();
		
		setup();
	}

	public void setup()
	{
		
		empSkillList = clientControl.getEmpSkillByEmpID(emp.getEmployeeID());
		skillList = clientControl.getSkillList();
		
		outstandingModel.setRowCount(0);
		empOutSkillList.clear();
		
		for (int i = 0; i < empSkillList.size(); i++)
		{
			if((empSkillList.get(i).getStatus()==0) && (!(empSkillList.get(i).getEmployeeID().equals(emp.getEmployeeID()))))
			{
				empOutSkillList.add(empSkillList.get(i));
				
			}
		}

        for(int i = 0 ; i < empOutSkillList.size() ; i++)
        {
        	empList = clientControl.searchEmployee((String) empOutSkillList.get(i).getRaterID());
        	for(int j = 0; j < empList.size(); j++)        
            {
        		if(empList.get(j).getEmployeeID().equalsIgnoreCase(empOutSkillList.get(i).getRaterID()) )
            	{
                    outStandingRow[0] = empOutSkillList.get(i).getRaterID();
                    outStandingRow[1] = empList.get(j).getSurname() + ", " + empList.get(j).getFirstName();
                    outStandingRow[3] = empOutSkillList.get(i).getCreatedDate();

                    //to get the description of the skill based on ID.
                    for(int k = 0; k < skillList.size(); k++)
                    {
                    	if(skillList.get(k).getSkillId() == (empOutSkillList.get(i).getSkillID()))
                    	{
                    		outStandingRow[2] = skillList.get(k).getSkillDescription();
                    		break;
                    	}
                    }
            	}
        		outstandingModel.addRow(outStandingRow);  
            }
        }
        //updateUI();
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
				System.out.println("Print logged on employee = " + emp.getEmployeeID());
	            if (nominateTable.getValueAt(i, 2) == null)
	            {
	                        JOptionPane.showMessageDialog(this,"Please select a Skill from the Nomination table");
	                        return;
	            }
				
				//change to new employee skill as opposed to a set method
				String employeeID = emp.getEmployeeID();
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
				boolean success = clientControl.nominateRater(employeeSkill);
				//clear input fields
				raterIDJTF.setText("");
				raterName.setText("");	
			}
			nominateModel.setRowCount(0);
					
		}
		if(source == btnAdd)
        {
			if(raterIDJTF.getText().isEmpty() && raterName.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(this,"Rater/Nominee cannot be blank, Please select a rater from the search list");
                return;
			}
			nominateRow[0]= raterIDJTF.getText();
			nominateRow[1]= raterName.getText();
			nominateModel.addRow(nominateRow);
			
        }
		if(source == btnDeleteNomination)
        {
            int row = outstandingRatesTable.getSelectedRow();
            System.out.println("row = " + row);
            if (row < 0)
            {
                        JOptionPane.showMessageDialog(this,"Please select a row from the Oustanding Ratings table");
                        return;
            }
            String raterID = (String) outstandingRatesTable.getValueAt(row, 0);
            
            	int skillID = 0;
                for(int j = 0; j < skillList.size(); j++)
                {
                	
                	if(skillList.get(j).getSkillDescription().equals(outstandingRatesTable.getValueAt(row, 2)))
                	{
                		skillID = skillList.get(j).getSkillId();  
                	}
                }
                //getting the object that needs to change
                boolean success = false;
                for (int z=0; z < empSkillList.size();z++)
                {
                	if(empSkillList.get(z).getRaterID().equals(outstandingRatesTable.getValueAt(row, 0)) && 
                		empSkillList.get(z).getSkillID() ==  skillID)
                	{
                		empSkillList.get(z).setStatus((short) 9);
                		success = clientControl.updateEmployeeSkill(empSkillList.get(z));
                		break;
                	}
                }
             
            
            
            //get your userid / relevant info
            //call database with info and do as you wish.
            
            //ensure the row value is initialised once you done.
            
            
            //deleting something 
            if(success)
            {
            	outstandingModel.removeRow(row);
            }
            else
            {
            	JOptionPane.showMessageDialog(this, "major Problems removing the outstanding Nominations");
            }
            
        }
		if(source == btnSearch)
		{
			searchCriteria = empSearchJTF.getText();
	        selectModel.setRowCount(0);
	        //selectModel.setColumnIdentifiers(empHeader);
			empList = clientControl.searchEmployee(empSearchJTF.getText());
	        empRow = new Object[4];
	        
	        if(!(empSearchJTF.getText().isEmpty()))
	        {
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
			else
			{
				JOptionPane.showMessageDialog(this,  "Please enter search criteria");
			}
		}
		updateUI();
		
	}
    public void setUpSkillColumn(JTable table,
            TableColumn skill) 
    {
		//Set up the editor for the skill cells.
		JComboBox skillBox = new JComboBox();
	    for(int i = 0 ; i < empSkillList.size() ; i++)
	    	{
	            for(int j = 0; j < skillList.size(); j++)
	            {
	            	if((skillList.get(j).getSkillId() == empSkillList.get(i).getSkillID()) 
	            			&& (empSkillList.get(i).getEmployeeID().equals(empSkillList.get(i).getRaterID()))
	            			&& (empSkillList.get(i).getStatus() == 1))
	            	{
	            		skillBox.addItem(skillList.get(j).getSkillDescription());  
	            	}
	            }
	    	}
		skill.setCellEditor(new DefaultCellEditor(skillBox));
		
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer =
		new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		skill.setCellRenderer(renderer);
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
	public void mousePressed(MouseEvent e) 
	{}

	@Override
	public void mouseReleased(MouseEvent e) 
	{}

	@Override
	public void mouseEntered(MouseEvent e) 
	{}

	@Override
	public void mouseExited(MouseEvent e) 
	{}
}
