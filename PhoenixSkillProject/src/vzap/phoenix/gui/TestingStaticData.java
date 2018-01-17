package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Level;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClient;

import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class TestingStaticData extends JPanel implements ActionListener
{
	private JPanel centrePanel;
	private JScrollPane scrollPane;
	private JPanel southPanel;
	private JPanel northPanel;
	private JLabel lblTableName;
	private JButton btnSkillList;
	private JButton btnHobbyList;
	private JButton btnLevels;
	private JButton btnCapability;
	private JButton btnCaplevel;

	private ArrayList<Skill> skillList;
	private ArrayList<Level> levelList;
	private ArrayList<Capability> capabilityList;
	private ArrayList<CapabilityRating> capabilityLevelList;
	private EmpSkillClient staticData;
	
	private JTable staticDataTable;
	private TableModel staticModel;

	/**
	 * Create the panel.
	 */
	public TestingStaticData(EmpSkillClient staticData)
	{
		this.staticData = staticData;
		
		setLayout(new BorderLayout(0, 0));
		
		lblTableName = new JLabel("lblTableName");
		centrePanel = new JPanel();		
		
		southPanel = new JPanel();
		add(southPanel, BorderLayout.SOUTH);
		
		btnSkillList = new JButton("Skill List");
		btnSkillList.addActionListener(this);
		southPanel.add(btnSkillList);

//		lblTableName.setText("Skill List");
//        skillList = staticData.getSkillList();
//		Object[][] tableRow = new Object[skillList.size()][2];
//        String[] tableHeader = new String[]{"SkillID","Description"};       	
//        for (int i=0; i<skillList.size(); i++)
//        {
//            
//    	   	tableRow[i][0]=skillList.get(i).getSkillId();
//    	   	tableRow[i][1]=skillList.get(i).getSkillDescription();
//        }
//	    staticModel = new DefaultTableModel(tableRow,tableHeader);
//       	staticDataTable = new JTable(staticModel);
//       	staticDataTable.repaint();
		
		btnHobbyList = new JButton("Hobby List");
		btnHobbyList.addActionListener(this);
		southPanel.add(btnHobbyList);
		
		btnLevels = new JButton("Levels");
		btnLevels.addActionListener(this);
		southPanel.add(btnLevels);
		
		btnCapability = new JButton("Capability");
		btnCapability.addActionListener(this);
		southPanel.add(btnCapability);
		
		btnCaplevel = new JButton("CapLevel");
		btnCaplevel.addActionListener(this);
		southPanel.add(btnCaplevel);
		
		northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		northPanel.add(lblTableName);

		add(centrePanel, BorderLayout.CENTER);
		centrePanel.setLayout(new GridLayout(1, 1, 0, 0));

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source.equals(btnSkillList))
		{
			
			lblTableName.setText("Skill List");
	        skillList = staticData.getSkillList();
			Object[][] tableRow = new Object[skillList.size()][3];
	        String[] tableHeader = new String[]{"SkillID","Name","Description"};       	
	        for (int i=0; i<skillList.size(); i++)
	        {
	            
	    	   	tableRow[i][0]=skillList.get(i).getSkillId();
	    	   	tableRow[i][1]=skillList.get(i).getSkillDescription();
	        }
		    staticModel = new DefaultTableModel(tableRow,tableHeader);
	       	staticDataTable = new JTable(staticModel);
//	       	staticDataTable.repaint();
			scrollPane = new JScrollPane(staticDataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setMaximumSize(new Dimension(32767, 10000));
			
			scrollPane.setViewportView(staticDataTable);
			centrePanel.add(scrollPane);
		}
		if(source.equals(btnLevels))
		{
			
			lblTableName.setText("Level List");
	        levelList = staticData.getLevelList();
			Object[][] tableRow = new Object[levelList.size()][3];
	        String[] tableHeader = new String[]{"Level","Name","Description"};       	
	        for (int i=0; i<levelList.size(); i++)
	        {
	            
	    	   	tableRow[i][0]=levelList.get(i).getRate();
	    	   	tableRow[i][1]=levelList.get(i).getName();
	    	   	tableRow[i][1]=levelList.get(i).getDescription();
	        }
		    staticModel = new DefaultTableModel(tableRow,tableHeader);
	       	staticDataTable = new JTable(staticModel);
//	       	staticDataTable.repaint();
			scrollPane = new JScrollPane(staticDataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setMaximumSize(new Dimension(32767, 10000));
			
			scrollPane.setViewportView(staticDataTable);
			centrePanel.add(scrollPane);
		}
		if(source.equals(btnCapability))
		{
			
			lblTableName.setText("Capability List");
			capabilityList = staticData.getCapabilityList();
			Object[][] tableRow = new Object[capabilityList.size()][3];
	        String[] tableHeader = new String[]{"Level","Name","Description"};       	
	        for (int i=0; i<capabilityList.size(); i++)
	        {
	            
	    	   	tableRow[i][0]=capabilityList.get(i).getID();
	    	   	tableRow[i][1]=capabilityList.get(i).getName();
	    	   	tableRow[i][2]=capabilityList.get(i).getDescription();
	        }
		    staticModel = new DefaultTableModel(tableRow,tableHeader);
	       	staticDataTable = new JTable(staticModel);
//	       	staticDataTable.repaint();
			scrollPane = new JScrollPane(staticDataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setMaximumSize(new Dimension(32767, 10000));
			
			scrollPane.setViewportView(staticDataTable);
			centrePanel.add(scrollPane);
		}
		if(source.equals(btnCaplevel))
		{
			
			lblTableName.setText("Capability Level List");
			capabilityLevelList = staticData.getCapabilityRatingList();
			Object[][] tableRow = new Object[capabilityLevelList.size()][3];
	        String[] tableHeader = new String[]{"Skill","Level","Description"};       	
	        for (int i=0; i<capabilityLevelList.size(); i++)
	        {
	            
	    	   	tableRow[i][0]=capabilityLevelList.get(i).getCapabilityID();
	    	   	tableRow[i][1]=capabilityLevelList.get(i).getRating();
	    	   	tableRow[i][2]=capabilityLevelList.get(i).getDescription();
	        }
		    staticModel = new DefaultTableModel(tableRow,tableHeader);
	       	staticDataTable = new JTable(staticModel);
//	       	staticDataTable.repaint();
			scrollPane = new JScrollPane(staticDataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setMaximumSize(new Dimension(32767, 10000));
			
			scrollPane.setViewportView(staticDataTable);
			centrePanel.add(scrollPane);
		}

	}

}
