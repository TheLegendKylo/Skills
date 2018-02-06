package vzap.phoenix.gui;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Level;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClientController;
import javax.swing.JLabel;



public class HelpMenu extends JPanel
{

	private ArrayList<Level> levelList = null;
	private ArrayList<Capability> capabilityList = null;
	private ArrayList<CapabilityRating> capabilityRatingList = null;
	
	private Employee emp=null;
  	private EmpSkillClientController clientControl;
  	private JLabel levelHead;
  	private JLabel levelRow1;
  	private JLabel levelRow2;
  	private JLabel levelRow3;
  	private JLabel levelRow4;
  	private JLabel levelRow5;
  	private String levelName;

	public HelpMenu(EmpSkillClientController clientControl)
	{
		
		this.clientControl = clientControl;
		this.emp = emp;
		setLayout(null);
		
		levelHead = new JLabel("New label");
		levelHead.setBounds(26, 104, 144, 53);
		add(levelHead);
		
		levelRow1 = new JLabel("New label");
		levelRow1.setBounds(26, 179, 104, 36);
		add(levelRow1);
		
		levelRow2 = new JLabel("New label");
		levelRow2.setBounds(26, 238, 104, 36);
		add(levelRow2);
		
		levelRow3 = new JLabel("New label");
		levelRow3.setBounds(26, 285, 104, 48);
		add(levelRow3);
		
		levelRow4 = new JLabel("New label");
		levelRow4.setBounds(26, 374, 120, 36);
		add(levelRow4);
		
		levelRow5 = new JLabel("New label");
		levelRow5.setBounds(26, 458, 120, 36);
		add(levelRow5);
			
		levelList = clientControl.getLevelList(); 
		capabilityList = clientControl.getCapabilityList();
		capabilityRatingList = clientControl.getCapabilityRatingList();
				
		for(int i = 0 ; i < levelList.size(); i++)
		{
			levelName = levelList.get(i).getName();
			System.out.println("helpmenu in for: " + i);
			int levelNo      = levelList.get(i).getRate();
			switch (levelNo)
			{
				case 1: 
					levelRow1.setText(levelName);
					
				case 2: 
					levelRow2.setText(levelName);
					
				case 3: 
					levelRow3.setText(levelName);
					
				case 4: 
					levelRow4.setText(levelName);
					
				case 5: 
					levelRow5.setText(levelName);
					
				default:
					break;
			}
	
		}	
		
	}
}
