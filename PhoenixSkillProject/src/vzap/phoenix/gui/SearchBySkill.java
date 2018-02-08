package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class SearchBySkill extends JPanel implements ActionListener
{
	private JLabel searchBySkillLab;
	private JComboBox skillComboBox;
	private JScrollPane skillScrollPane;
	private JTable skillTable;
	
	private ArrayList<Capability> capabilityList;
 	private ArrayList<EmployeeSkill> empSkillList;
	private Employee loggedOnEmployee;
  	private EmpSkillClientController clientControl;
  	
	private Vector<String> vectSkill; 
	private ArrayList<Employee> employeeList; 
	private Vector<String> vectTabSkill;
 	private Vector comboSkill; 
	
	private ArrayList<Skill> skillList;
	private Object[] HeaderForSkillsDetails;
	private DefaultTableModel modelInsert;
	private JButton exportBut;
	
	String displaySaveMsg = " "; 
  	


	/**
	 * Create the panel.
	 */
	public SearchBySkill(EmpSkillClientController clientControl)
	{
		this.clientControl = clientControl;
		loggedOnEmployee = clientControl.getLogonEmployee();
		capabilityList = clientControl.getCapabilityList();
		
		vectSkill = new Vector<String>();
		vectTabSkill = new Vector<String>();
		skillList = clientControl.getSkillList();
		comboSkill = new Vector<>();
		for(int i = 0 ; i < skillList.size() ; i++)
		{
			comboSkill.add(skillList.get(i).getSkillDescription());	
		}
		Collections.sort(comboSkill);    
		setLayout(null);
		
		searchBySkillLab = new JLabel("Choose SKILL from dropdown list");
		searchBySkillLab.setFont(new Font("Arial", Font.BOLD, 15));
		searchBySkillLab.setBounds(568, 36, 506, 38);
		add(searchBySkillLab);
		
		skillComboBox = new JComboBox(comboSkill);
		skillComboBox.setFont(new Font("Arial", Font.BOLD, 15));
		AutoCompletion.enable(skillComboBox);
		skillComboBox.addActionListener(this);
		skillComboBox.setBounds(832, 36, 242, 38);
		add(skillComboBox);
		
		skillScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		modelInsert = new DefaultTableModel();
		skillScrollPane.setBounds(23, 97, 1741, 527);
		add(skillScrollPane);
		skillTable = new JTable(modelInsert){
		    @Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		    	Component comp = super.prepareRenderer(renderer, row, col);
	            comp.setBackground(Color.WHITE);

		    	if(col>2 && col<11)
		        {
			        double dValue = (Double.parseDouble(getModel().getValueAt(row, col).toString()));
			        int value = (int)dValue;
			        switch (value)
			        {
				        case 1:
				        {
				            comp.setBackground(new Color(255,91,13));
				            break;
				        }
				        case 2:
				        {
				            comp.setBackground(new Color(255,172,117));
				            break;
				        }
				        case 3:
				        {
				            comp.setBackground(new Color(176,255,176));
				            break;
				        }
				        case 4:
				        {
				            comp.setBackground(new Color(0,202,0));
				            break;
				        }
				        case 5:
				        {
				            comp.setBackground(new Color(0,136,0));
				            break;
				        }
			        }
			    }
		        return comp;
	        }
		};
		
		skillTable.setRowSorter(new TableRowSorter(modelInsert)); 
		skillTable.setAutoCreateRowSorter(true); 
		skillTable.setAutoCreateRowSorter(isEnabled());
		skillTable.getRowSelectionAllowed();
		skillTable.getSelectionModel(); 
		
		skillScrollPane.setViewportView(skillTable);
		
		exportBut = new JButton("Export to excel");
		exportBut.setFont(new Font("Arial", Font.BOLD, 15));
		exportBut.addActionListener(this);
		exportBut.setBounds(1553, 36, 211, 38);
		add(exportBut);
		exportBut.setEnabled(false);
		
	}



	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == exportBut)
		{
			File file;
			JFileChooser chooser = new JFileChooser("c:\\Users"); 
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Delimited File","csv");
			chooser.setFileFilter(filter);
			
			int choice1 = chooser.showSaveDialog(this);
			if(choice1 == JFileChooser.APPROVE_OPTION)
			{
				String filePath = chooser.getSelectedFile().getPath();
				if(chooser.getSelectedFile().getName().contains("."))
				{
					JOptionPane.showMessageDialog(this,"ERROR: " + "Please dont add the extension to your file name: ","ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(filePath.endsWith(".csv"))
				{
					file =  new File(filePath);
				}
				else
				{
					file =  new File(filePath + ".csv");
				}
				
				FileWriter fw = null; // opens connection
				PrintWriter pw = null;//wraps in FileWriter
				String row = "";
				try
				{
					fw = new FileWriter(file);
					pw = new PrintWriter(fw);
					
					for(int i = 0 ; i < modelInsert.getColumnCount() ;i++)
					{
						row = row + (modelInsert.getColumnName(i) + ",");
					}
					pw.println(row);
					
					
					for(int i = 0 ; i < modelInsert.getRowCount();i++)
					{
						row = "";
						for(int c = 0 ; c < modelInsert.getColumnCount() ;c++)
						{
							row = row +	(modelInsert.getValueAt(i, c) + ","); 
						}
						pw.println(row);
					}
					String displaySaveMsg = "Your extract file has been saved to : " + filePath;
					JOptionPane.showMessageDialog(this,displaySaveMsg);
					System.out.println("Data saved to file");
					fw.close();
					pw.close();
					System.out.println("All Io connections closed");
					
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(this,"Error : " + e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

			}		
		}


		if(source == skillComboBox)
		{
			empSkillList = new ArrayList<>();
		
//          store the index and skillId of the cell the user has chosen from the combobox 			
			int comboSkill_pointer = skillComboBox.getSelectedIndex();
			int skillList_pointer = 0;
			System.out.println("searchbyskill - get skilldesc " + skillComboBox.getSelectedItem());
			
			
//			System.out.println("searchby skill - value x " + x + " y " + y);
			
			for(int i = 0 ; i < skillList.size() ; i++)
			{
				System.out.println("searchbyskill - in for - skillist " + skillList.get(i).getSkillDescription());
				System.out.println("searchbyskill - in for - skillComboBox " +skillComboBox.getSelectedItem() );
				if(skillList.get(i).getSkillDescription() == skillComboBox.getSelectedItem())
				{
					skillList_pointer = i;
					System.out.println("searchbyskill - skill found - " + skillList_pointer);
					break;
				}
			}

//          now go and search the EmployeeSkill table for all employees that have that skill			
			empSkillList = clientControl.searchEmployeeSkill(skillList_pointer+1);

//          check if there are any matches for users search criteria 
			if(empSkillList.size() == 0)
			{
				JOptionPane.showMessageDialog(this,"No employees found with your search criteria");
				return;				
			}
		
//          move the employeeID from returned array into a vector so that it can be sorted  			
			Vector vect = new Vector<String>();
			for (int i=0; i < empSkillList.size(); i++)
	        {
				vect.addElement(empSkillList.get(i).getEmployeeID()); 
	        }
			Collections.sort(vect);    		
		

//          now go thru the vector that contains the employeeID and fetch the employee details one at a time and populate
//          the table - do not print duplicate employees
			
			modelInsert = clientControl.getEmpSkillDetail(empSkillList);
			skillScrollPane.remove(skillTable);
			skillTable = new JTable(modelInsert){
			    @Override
			    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
			    	Component comp = super.prepareRenderer(renderer, row, col);
		            comp.setBackground(Color.WHITE);

			    	if(col>2 && col<11)
			        {
				        double dValue = (Double.parseDouble(getModel().getValueAt(row, col).toString()));
				        int value = (int)dValue;
				        switch (value)
				        {
					        case 1:
					        {
					            comp.setBackground(new Color(255,91,13));
					            break;
					        }
					        case 2:
					        {
					            comp.setBackground(new Color(255,172,117));
					            break;
					        }
					        case 3:
					        {
					            comp.setBackground(new Color(176,255,176));
					            break;
					        }
					        case 4:
					        {
					            comp.setBackground(new Color(0,202,0));
					            break;
					        }
					        case 5:
					        {
					            comp.setBackground(new Color(0,136,0));
					            break;
					        }
				        }
				    }
			        return comp;
		        }
			};
			skillTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
			skillTable.setFont(new Font("Arial", Font.PLAIN, 15));
			skillTable.setRowSorter(new TableRowSorter(modelInsert)); 
			skillScrollPane.setViewportView(skillTable);
			skillTable.setAutoCreateRowSorter(true);
			skillTable.setAutoCreateRowSorter(isEnabled());
			skillTable.getRowSelectionAllowed();
			modelInsert.fireTableDataChanged();
			skillTable.getSelectionModel();
			this.repaint();
			
			exportBut.setEnabled(true);
		}
	}
}
