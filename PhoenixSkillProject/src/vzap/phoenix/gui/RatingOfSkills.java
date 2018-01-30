package vzap.phoenix.gui;
//Comment
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClientController;


import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import java.awt.ComponentOrientation;


public class RatingOfSkills extends JPanel implements MouseListener, ActionListener
{
	private Employee loggedOnEmployee;
	private EmpSkillClientController clientControl;
	private JLabel lblRatingOfSkills;
	private JPanel panelTop;
	private JButton btnCannotRate;
	private JPanel panelBottom;
	private JLabel lblMessageIdentifier;
	private JButton btnSubmitRating;
	private JTable tableTop;
	private ArrayList<EmployeeSkill> employeeSkillList;
	private DefaultTableModel outstandingModel;
	private JScrollPane scrollPaneTop;
	private Object source;
	private ArrayList<EmployeeSkill> outstandingRatersList;
	private ArrayList<Capability> capList;
	private ArrayList<CapabilityRating> capRatingList;
	private Object[][] tableRow;
	private String[] tableHeader;
	private JTable ratingTable;
	private DefaultTableModel ratingModel;
	private ArrayList<Employee> employeeList;
	private EmployeeSkill selectedEmpSkill;
	private ArrayList<Skill> skillList;
	private ArrayList<Short> capListArray =null;
	private short[] ratingArray;

	/**
	 * Create the panel.
	 */
	public RatingOfSkills(EmpSkillClientController clientControl) 
	{
		
		setAutoscrolls(true);
		loggedOnEmployee = clientControl.getLogonEmployee();
		this.clientControl=clientControl;
		employeeSkillList = clientControl.getOutstandingRatings();
		setLayout(null);
		employeeList = new ArrayList<Employee>();
		
		
		lblRatingOfSkills = new JLabel("Rate Skills");
		lblRatingOfSkills.setBounds(43, 10, 627, 14);
		add(lblRatingOfSkills);
		lblRatingOfSkills.setHorizontalAlignment(JLabel.CENTER);
		
		panelTop = new JPanel();
		panelTop.setBounds(10, 35, 694, 159);
		add(panelTop);
		
		ArrayList<EmployeeSkill> ratingList = new ArrayList<EmployeeSkill>();
		ratingList = clientControl.searchEmployeeSkillByRaterID(loggedOnEmployee.getEmployeeID());
		
		
		outstandingRatersList = new ArrayList<EmployeeSkill>();
		
		for (int i = 0; i < ratingList.size(); i++)
		{
			if( ratingList.get(i).getStatus() == 0 )
			{
				outstandingRatersList.add(ratingList.get(i));
			}
		}
		outstandingModel = clientControl.getEmpSkillList(outstandingRatersList, loggedOnEmployee);
		
		panelTop.setLayout(null);
		tableTop = new JTable(outstandingModel);
		scrollPaneTop = new JScrollPane(tableTop);
		scrollPaneTop.setBounds(10, 5, 621, 154);
		panelTop.add(scrollPaneTop);
		tableTop.addMouseListener(this);
	
//		outstandingModel.addTableModelListener(this);
		
//		scrollPane.setViewportView(tableTop);

		
		
		btnCannotRate = new JButton("Cannot Rate");
		btnCannotRate.setBounds(299, 205, 116, 23);
		btnCannotRate.setToolTipText("Select if you are unable to offer a rating for an employee request");
		add(btnCannotRate);
		btnCannotRate.addActionListener(this);
		btnCannotRate.setEnabled(false);
		
		panelBottom = new JPanel();
		panelBottom.setBounds(-24, 267, 763, 234);
		add(panelBottom);
		
		
		lblMessageIdentifier = new JLabel("Message Identifier");
		lblMessageIdentifier.setBounds(166, 242, 382, 14);
		lblMessageIdentifier.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMessageIdentifier.setHorizontalAlignment(JLabel.CENTER);
		lblMessageIdentifier.setVerticalAlignment(JLabel.CENTER);
		add(lblMessageIdentifier);
		
		btnSubmitRating = new JButton("Submit Rating");
		btnSubmitRating.setBounds(299, 523, 116, 23);
		add(btnSubmitRating);
		btnSubmitRating.addActionListener(this);
		btnSubmitRating.setEnabled(false);
		

		capList = clientControl.getCapabilityList();
		capRatingList = clientControl.getCapabilityRatingList();
		tableRow = new Object[capList.size()][3];
        tableHeader = new String[]{"Capability","Rating","Description"};       	
        for (int i=0; i<capList.size(); i++)
        {
            
    	   	tableRow[i][0]=capList.get(i).getName();
       	   	tableRow[i][1]="Select Rating...";
       }
	    ratingModel = new DefaultTableModel(tableRow,tableHeader);
	    ratingTable = new JTable(new MyTableModel());
	    ratingTable.setEnabled(false);
	    ratingArray = new short[7];
	    TableColumn ratingColumn = ratingTable.getColumnModel().getColumn(1);
		this.initColumnSizes(ratingTable);
		this.setUpRatingColumn(ratingTable, ratingColumn);
System.out.println(">>>>>Are you looping her too?");
		panelBottom.setLayout(null);
       	
		JScrollPane scrollPaneBottom = new JScrollPane(ratingTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneBottom.setBounds(46, 5, 620, 229);
		scrollPaneBottom.setMaximumSize(new Dimension(32767, 10000));
		
		scrollPaneBottom.setViewportView(ratingTable);

		panelBottom.add(scrollPaneBottom);
		
		
	//	ratingPanel.setLayout(new GridLayout(1, 1, 0, 0));
		

	}
	/*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable ratingTable) 
    {
        MyTableModel ratingModel = (MyTableModel)ratingTable.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = ratingModel.longValues;
        TableCellRenderer headerRenderer =
            ratingTable.getTableHeader().getDefaultRenderer();
 
        for (int i = 0; i < 3; i++) {
            column = ratingTable.getColumnModel().getColumn(i);
 
            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
 
            comp = ratingTable.getDefaultRenderer(ratingModel.getColumnClass(i)).
                             getTableCellRendererComponent(
                            		 ratingTable, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
 
            
 
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }
 
	
	
	/*
	 * This method sets up the Combo Box used in the table
	 */
    public void setUpRatingColumn(JTable table,
            TableColumn rating) 
    {
		//Set up the editor for the sport cells.
		JComboBox<Integer> ratingBox = new JComboBox<Integer>();
		for (int j = 1; j < 6; j++)
		{
			ratingBox.addItem(j);
		}
		rating.setCellEditor(new DefaultCellEditor(ratingBox));
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		rating.setCellRenderer(renderer);
    }
   class MyTableModel extends AbstractTableModel 
   {

    	public String getColumnName(int col) {
   	        return tableHeader[col].toString();
   	    }
   	    public int getRowCount() 
   	    { 
   	    	return tableRow.length; 
   	    }
   	    public int getColumnCount() 
   	    { 
   	    	return tableHeader.length; 
   	    }
   	    public Object getValueAt(int row, int col) 
   	    {
   	    	return tableRow[row][col];
   	    }
   	    public boolean isCellEditable(int row, int col)
   	    { 
   	    	return true; 
   	    }
   	    public void setValueAt(Object value, int row, int col) 
   	    {
   	  		int selRating = (int)value;
   	  		System.out.println("Value: "+(int)value+" selRating: "+selRating);
   	    	String desc = null;
   	   		for (int j = 0; j < capRatingList.size(); j++)
   			{
   				if(capRatingList.get(j).getCapabilityID()==row+1
   						&& capRatingList.get(j).getRating()==selRating)
   				{
   					desc = capRatingList.get(j).getDescription();
   					break;
   				}
   			}
   			System.out.println("Rating Description: "+desc);
   	   		tableRow[row][1] = selRating;
   	   		tableRow[row][2] = desc;
   	   		ratingArray[row] = (short)selRating;
   	        fireTableCellUpdated(row, 2);
   	        boolean enableSubmitBtn = true;
   	        for (int i = 0; i < ratingArray.length; i++)
			{
				if(ratingArray[i] == 0)
				{
					enableSubmitBtn = false;
				}
			}
   	        this.isCellEditable(row, 2);
   	        if(enableSubmitBtn = true)
   	        {
   	        	btnSubmitRating.setEnabled(true);
   	        }
   	    }
        public final Object[] longValues = {"Purposeful Collaboration", "Select rating...",
                "Straightforward tasks likely to be completed to an acceptable standard",
                new Integer(20), Boolean.TRUE};
   }


		
	
	
	
	
	


	@Override
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("*****Mouse Clicked*****");
		int rowSelected = tableTop.getSelectedRow();
		String empID = (String)tableTop.getModel().getValueAt(rowSelected, 0);
		String empName = (String)tableTop.getModel().getValueAt(rowSelected, 1);
		String skillName = (String)tableTop.getModel().getValueAt(rowSelected, 2);
		short skillID = 0;
		skillList = clientControl.getSkillList();
		for (int i = 0; i < skillList.size(); i++)
		{
			if(skillName.equals(skillList.get(i).getSkillDescription()))
			{
				skillID = (short)skillList.get(i).getSkillId();
			}
		}
System.out.println("empID: "+empID);		
		for (int i = 0; i < outstandingRatersList.size(); i++)
		{
			System.out.println("empSkillList ID: " + outstandingRatersList.get(i).getEmployeeID()+ " vs +empID: "+empID);	
			
			if((outstandingRatersList.get(i).getEmployeeID().equalsIgnoreCase(empID))
				&& (outstandingRatersList.get(i).getSkillID()==skillID))
			{
				selectedEmpSkill = outstandingRatersList.get(i);
				System.out.println("selectedEmpSkill: "+selectedEmpSkill);
				ratingTable.setEnabled(true);
				btnCannotRate.setEnabled(true);
			}
		
		}
		
		
		
		
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == btnSubmitRating)
		{
			capListArray = new ArrayList<Short>();
			ArrayList<Short>ratingArrayList = new ArrayList<Short>();
			System.out.println("btnSubmitRating was pressed and the capList size = " + capList.size());
			for (int i = 0; i < capList.size(); i++)
			{
				System.out.println("Printing ValueAt for ratingModel ... " +capList.get(i).getID());
				System.out.println("CapList size = " + capList.size());
				capListArray.add(capList.get(i).getID());
				
				ratingArrayList.add(ratingArray[i]);
				
			}
			if(selectedEmpSkill==null)
			{
				System.out.println("selectedEmpSkill is NULL>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
			selectedEmpSkill.setCapabilityList(capListArray);
			selectedEmpSkill.setRatingList(ratingArrayList);
			selectedEmpSkill.setRatedDate(new Date());
			clientControl.rateEmployeeSkill(selectedEmpSkill);
			
			
			
		}
		
		if (source == btnCannotRate)
		{
			System.out.println("Cannot Rate button was pressed");
			selectedEmpSkill.setStatus((short)9);
			String comment = JOptionPane.showInputDialog(this, "Please supply a comment");
			selectedEmpSkill.setComment(comment);
			boolean success = clientControl.updateEmployeeSkill(selectedEmpSkill);
			System.out.println("Printing boolean result--- = " + success);
			
		}
		
	}
}
