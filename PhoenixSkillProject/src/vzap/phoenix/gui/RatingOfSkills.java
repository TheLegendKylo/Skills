package vzap.phoenix.gui;

import javax.swing.JLabel;
import javax.swing.JList;
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

import java.awt.Color;
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
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import java.awt.ComponentOrientation;
import javax.swing.JRadioButton;


public class RatingOfSkills extends JPanel implements MouseListener, ActionListener
{
	private Employee emp;
	private EmpSkillClientController clientControl;
	private JLabel lblRatingOfSkills;
	private JButton btnCannotRate;
	private JLabel lblExplianation;
	private JButton btnSubmitRating;
	private JTable tableTop;
	private DefaultTableModel outstandingModel;
	private JScrollPane scrollPaneTop;
	private Object source;
	private ArrayList<EmployeeSkill> outstandingRatersList,ratingList;
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
	private JButton btnClearRatings;
	private int rowSelected = -1;
	private JRadioButton rdbtnAvailableAsA;
	private String coaching = "N";
	 
	private CapabilityRating capRating;
	/**
	 * Create the panel.
	 */
	public RatingOfSkills(EmpSkillClientController clientControl,Employee emp) 
	{
		
		setAutoscrolls(true);
		
		this.emp = emp;
		this.clientControl=clientControl;
		setLayout(null);
		employeeList = new ArrayList<Employee>();
		
		lblRatingOfSkills = new JLabel("Rate Skills");
		lblRatingOfSkills.setFont(new Font("Arial", Font.BOLD, 20));
		lblRatingOfSkills.setBounds(555, 13, 627, 14);
		add(lblRatingOfSkills);
		lblRatingOfSkills.setHorizontalAlignment(JLabel.CENTER);
		
	    ratingList = new ArrayList<EmployeeSkill>();
		outstandingRatersList = new ArrayList<EmployeeSkill>();
		
		tableTop = new JTable(outstandingModel);
		tableTop.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneTop = new JScrollPane(tableTop);
		scrollPaneTop.setBounds(58, 42, 1689, 154);
		add(scrollPaneTop);
		tableTop.addMouseListener(this);
		
		this.setup();
		
		btnCannotRate = new JButton("Cannot Rate");
		btnCannotRate.setFont(new Font("Arial", Font.BOLD, 15));
		btnCannotRate.setBounds(947, 212, 150, 33);
		btnCannotRate.setToolTipText("Select if you are unable to offer a rating for an employee request");
		add(btnCannotRate);
		btnCannotRate.addActionListener(this);
		
		lblExplianation = new JLabel("Please Provide Ratings for Selected Employee below");
		lblExplianation.setBounds(657, 244, 482, 24);
		lblExplianation.setFont(new Font("Arial", Font.BOLD, 15));
		lblExplianation.setHorizontalAlignment(JLabel.CENTER);
		lblExplianation.setVerticalAlignment(JLabel.CENTER);
		add(lblExplianation);
		
		btnSubmitRating = new JButton("Submit Rating");
		btnSubmitRating.setFont(new Font("Arial", Font.BOLD, 15));
		btnSubmitRating.setBounds(714, 523, 155, 33);
		add(btnSubmitRating);
		btnSubmitRating.addActionListener(this);

		this.createRatingTable();
		this.disableRating();
		tableTop.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		ratingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		ratingTable.setBackground(this.getBackground());

	}
	
	public void setup()
	{
		ratingList = new ArrayList<EmployeeSkill>();
		outstandingRatersList = new ArrayList<EmployeeSkill>();
		
		ratingList = clientControl.searchEmployeeSkillByRaterID(emp.getEmployeeID());
		for (int i = 0; i < ratingList.size(); i++)
		{
			if( ratingList.get(i).getStatus() == 0 )
			{
				outstandingRatersList.add(ratingList.get(i));
			}
		}
		outstandingModel = clientControl.getEmpSkillList(outstandingRatersList, emp);
		tableTop.setModel(outstandingModel);
		
	}
	private void createRatingTable()
	{
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
	    ratingTable.setFont(new Font("Arial", Font.PLAIN, 15));
	    ratingArray = new short[7];
	    TableColumn ratingColumn = ratingTable.getColumnModel().getColumn(1);
		this.initColumnSizes(ratingTable);
		this.setUpRatingColumn(ratingTable, ratingColumn);
       	
		JScrollPane scrollPaneBottom = new JScrollPane(ratingTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneBottom.setBounds(58, 281, 1689, 229);
		scrollPaneBottom.setMaximumSize(new Dimension(32767, 10000));
		
		scrollPaneBottom.setViewportView(ratingTable);

		add(scrollPaneBottom);
		
		btnClearRatings = new JButton("Clear Ratings");
		btnClearRatings.setFont(new Font("Arial", Font.BOLD, 15));
		btnClearRatings.setBounds(968, 523, 129, 33);
		add(btnClearRatings);
		btnClearRatings.addActionListener(this);
		
		rdbtnAvailableAsA = new JRadioButton("Available as a coach");
		rdbtnAvailableAsA.setFont(new Font("Arial", Font.BOLD, 15));
		rdbtnAvailableAsA.setBounds(714, 212, 182, 33);
		add(rdbtnAvailableAsA);
		
		rdbtnAvailableAsA.setEnabled(false);
	}
	
	private void disableRating()
	{
		btnCannotRate.setEnabled(false);
		btnSubmitRating.setEnabled(false);
		
		ratingTable.setEnabled(false);
		System.out.println("After setting to false");
		btnClearRatings.setEnabled(false);
	}
	
	private void enableRating()
	{
		btnCannotRate.setEnabled(true);
		ratingTable.setEnabled(true);
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
		short ratingValue=1;
    	JComboBox<Integer> ratingBox = new JComboBox<Integer>();
    	
    	for (int j = 1; j < 6; j++)
		{
			ratingBox.addItem(j);
		}

    	ratingBox.setRenderer(new ItemRenderer());
		rating.setCellEditor(new DefaultCellEditor(ratingBox));
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		rating.setCellRenderer(renderer);
    }
    class ItemRenderer extends BasicComboBoxRenderer
    {
        public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus)
        {
            super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
            if ((value != null) && (!((int)value==-1)))
            {
				int intValue = (int)value;
                short shortValue = (short)intValue;
                int rowSelected = ratingTable.getSelectedRow();
				for (int i = 0; i < capRatingList.size(); i++)
				{
					if((capRatingList.get(i).getCapabilityID()==rowSelected+1)
						&& (capRatingList.get(i).getRating()==shortValue))
					{
						setText(index+1+": "+capRatingList.get(i).getDescription());
					}
				}
            }

            if (index == -1)
            {
                setText( ""+value.toString() );
            }


            return this;
        }
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

   	   		tableRow[row][1] = selRating;
   	   		tableRow[row][2] = desc;
   	   		ratingArray[row] = (short)selRating;
   	        fireTableCellUpdated(row, 2);
   	        
   	        boolean enableSubmitBtn = true;
   	        for (int i = 0; i < ratingArray.length; i++)
			{
				if(ratingArray[i] == 0 )
				{
					enableSubmitBtn = false;
					break;
				}
			}
   	        
   	     for (int i = 0; i < ratingArray.length; i++)
			{
				if(! (ratingArray[i] == 0) )
				{
					btnClearRatings.setEnabled(true);
					break;
				}
			}
   	        
   	        this.isCellEditable(row, 2);
 
   	        if(enableSubmitBtn == true)
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
		rowSelected = tableTop.getSelectedRow();
		String empID = (String)tableTop.getModel().getValueAt(rowSelected, 0);
		
		ratingTable.setBackground(Color.WHITE);
		
		if(empID.equalsIgnoreCase(emp.getEmployeeID()))
		{

			int valueReturnCoaching = JOptionPane.showConfirmDialog(this, "Are you able to act as a coach?"
					+ " for the selected skill?", "Coaching", JOptionPane.YES_NO_OPTION);
			if(valueReturnCoaching == JOptionPane.YES_OPTION)
			{
				rdbtnAvailableAsA.setEnabled(true);
				rdbtnAvailableAsA.setSelected(true);
				System.out.println("you are were you need to be");
			}
			else
			{
				rdbtnAvailableAsA.setSelected(false);
				rdbtnAvailableAsA.setEnabled(true);
			}
				
		}
		else
		{
			rdbtnAvailableAsA.setSelected(false);
			rdbtnAvailableAsA.setEnabled(false);
		}
		
		String empName = (String)tableTop.getModel().getValueAt(rowSelected, 1);
		String skillName = (String)tableTop.getModel().getValueAt(rowSelected, 2);
		short skillID = 0;
		skillList = clientControl.getSkillList();
		for (int i = 0; i < skillList.size(); i++)
		{
			if(skillName.equalsIgnoreCase(skillList.get(i).getSkillDescription()))
			{
				skillID = (short)skillList.get(i).getSkillId();
			}
		}
		
		for (int i = 0; i < outstandingRatersList.size(); i++)
		{

			
			if((outstandingRatersList.get(i).getEmployeeID().equalsIgnoreCase(empID))
				&& (outstandingRatersList.get(i).getSkillID()==skillID))
			{
				selectedEmpSkill = outstandingRatersList.get(i);
				ratingTable.setEnabled(true);
				btnCannotRate.setEnabled(true);
			}
		
		}
	}
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == btnSubmitRating)
		{
			
			
			int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to Submit?",
					"Confirm Submit",JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION)
			{
				if(rdbtnAvailableAsA.isSelected())
				{
					coaching = "Y";
				}
				else
				{	
					coaching = "N";
				}
				
				selectedEmpSkill.setCoachingAvailability(coaching);
				
				rdbtnAvailableAsA.setSelected(false);
				rdbtnAvailableAsA.setEnabled(false);
				
				capListArray = new ArrayList<Short>();
				ArrayList<Short> ratingArrayList = new ArrayList<Short>();
				for (int i = 0; i < capList.size(); i++)
				{

					capListArray.add(capList.get(i).getID());
					
					ratingArrayList.add(ratingArray[i]);
					
				}
				selectedEmpSkill.setCapabilityList(capListArray);
				selectedEmpSkill.setRatingList(ratingArrayList);
				selectedEmpSkill.setRatedDate(new Date());
				boolean success = clientControl.rateEmployeeSkill(selectedEmpSkill);
				
				if(!success)
				{
					JOptionPane.showMessageDialog(this, clientControl.getErrorMsg());
				}
				
			}
			for (int i = 0; i < tableRow.length; i++)
			{
				tableRow[i][1]= "Select rating";
				tableRow[i][2]="";

				System.out.println("ValueAt... " +ratingModel.getValueAt(i, 1));
				
			}
			ratingModel.fireTableDataChanged();
			ratingTable.repaint();
			this.disableRating();
			setup();
		}
		if (source == btnCannotRate)
		{
			System.out.println("Cannot Rate button was pressed");
			selectedEmpSkill.setStatus((short)9);
			String comment = JOptionPane.showInputDialog(this, "Please supply a comment");
			selectedEmpSkill.setComment(comment);
			boolean success = clientControl.updateEmployeeSkill(selectedEmpSkill);
			if(!success)
			{
				JOptionPane.showMessageDialog(this, clientControl.getErrorMsg());
			}
			for (int i = 0; i < tableRow.length; i++)
			{
				tableRow[i][1]= "Select rating";
				tableRow[i][2]="";

				System.out.println("ValueAt... " +ratingModel.getValueAt(i, 1));
				
			}
			ratingModel.fireTableDataChanged();
			ratingTable.repaint();
			this.disableRating();
			setup();
			
			this.disableRating();			
		}
		
		if (source == btnClearRatings)
		{
			
			int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to Clear?","Clear Ratings",JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION)
			{
				for (int i = 0; i < tableRow.length; i++)
					{
						tableRow[i][1]="Select rating";
						tableRow[i][2]="";
						System.out.println("ValueAt... " +ratingModel.getValueAt(i, 1));
						
					}	
					
					ratingModel.fireTableDataChanged();
					ratingTable.repaint();
					
			}
		}
		
	}
}
