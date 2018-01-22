package vzap.phoenix.testing;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Level;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.client.EmpSkillClient;

public class TestingPhoenix
{
	private Employee employee;
	private EmpSkillClient loginSession;
	private EmployeeSkill empSkill;
	private ArrayList<Skill> skillList;
	private Vector<Hobby> hobbyList;
	private ArrayList<Capability> capList;
	private ArrayList<Level> levelList;
	private ArrayList<EmployeeSkill> empSkillList;
	
	public TestingPhoenix()
	{
		loginSession = new EmpSkillClient();
		String employeeID = "a159842";
		String password = "123";
		short returnCode = loginSession.loginEmployee(employeeID, password);
		
		if(returnCode==0)//.equals("Login Successfull"))
		{
			employee = loginSession.getLogonEmployee();
			System.out.println(employee.toString());
		} else {
			System.out.println(loginSession.getErrorMsg());
		}
		skillList = loginSession.getSkillList();
		hobbyList = loginSession.getHobbyList();
		capList = loginSession.getCapabilityList();
		levelList = loginSession.getLevelList();
		this.getEmpSkills();
		this.searchEmployeeSkill();
//		this.rateSkill();
//		boolean b = this.addEmpSkill();
//		boolean b = this.nominateRater();
//		this.addHobby();
//		this.addSkill();
//		System.out.println("Write Message:" +loginSession.getErrorMsg());
		loginSession.closeConnections();
	}
	public void addHobby()
	{
//		System.out.println("New HobbyID= "+loginSession.addHobby("AppDev"));
		System.out.println("Write Message:" +loginSession.getErrorMsg());
	}
	public void addSkill()
	{
//		System.out.println("New SkillID= "+loginSession.addSkill("Natural Adabas"));
		System.out.println("Write Message:" +loginSession.getErrorMsg());
	}
	public boolean addEmpSkill()
	{
		empSkill = new EmployeeSkill();
		empSkill.setEmployeeID(employee.getEmployeeID());
		int skillID = skillList.get(20).getSkillId();
		empSkill.setSkillID(skillID);
		Date d = new Date();
		empSkill.setCreatedDate(d);
		empSkill.setRaterID(employee.getEmployeeID());
		short i=1;
		empSkill.setStatus(i);
		empSkill.setCoachingAvailability("N");
		boolean saveSuccess = loginSession.addEmployeeSkill(empSkill);
		System.out.println("Write Message:" +loginSession.getErrorMsg());
		System.out.println(empSkill.toString());
		return saveSuccess;
	}
	public boolean nominateRater()
	{
		empSkill = new EmployeeSkill();
		empSkill.setEmployeeID(employee.getEmployeeID());
		int skillID = skillList.get(20).getSkillId();
		empSkill.setSkillID(skillID);
		Date d = new Date();
		empSkill.setCreatedDate(d);
		empSkill.setRaterID("a043410");
		short i=1;
		empSkill.setStatus(i);
		boolean saveSuccess = loginSession.nominateRater(empSkill);
		System.out.println("Write Message:" +loginSession.getErrorMsg());
		System.out.println(empSkill.toString());
		return saveSuccess;
	}
	public boolean rateSkill()
	{
		empSkill = empSkillList.get(0);
		Date d = new Date();
		empSkill.setRatedDate(d);
		short i=2;
		empSkill.setStatus(i);
		// set capability ratings
		short [] capability = {1,2,3,4,5,6,7};
		short [] rating = {3,2,4,2,3,4,1}; 
		empSkill.setCapability(capability);
		empSkill.setRating(rating);
		boolean saveSuccess = loginSession.rateEmployeeSkill(empSkill);
		System.out.println("Write Message:" +loginSession.getErrorMsg());
		System.out.println(empSkill.toString());
		return saveSuccess;
	}
	public void getEmpSkills()
	{
		empSkillList = loginSession.getEmpSkillList();
		for (int i = 0; i < empSkillList.size(); i++)
		{
			String skillDesc=null;
			String capDesc=null;
			for (int j = 0; j < skillList.size(); j++)
			{
				if(skillList.get(j).getSkillId()==empSkillList.get(i).getSkillID())
				{
					skillDesc = skillList.get(j).getSkillDescription();
				}
			}
			System.out.println("Rater :"+empSkillList.get(i).getRaterID()+"\t"+
					" Skill: "+skillDesc+"\t"+" Created Date: "+empSkillList.get(i).getCreatedDate());
			if(!(empSkillList.get(i).getRating()==null))
			{
				for (int j = 0; j < empSkillList.get(i).getRating().length; j++)
				{
					for (int j2 = 0; j2 < capList.size(); j2++)
					{
						if(capList.get(j).getID()==empSkillList.get(i).getCapabilityID()[j2])
						{
							capDesc=capList.get(j).getName();
						}
					}
					System.out.println("Capability: "+capDesc+" \tRating :"+empSkillList.get(i).getRating()[j]);
				}
			}
		}
	}
	public void searchEmployeeSkill()
	{
		short empSkillID = loginSession.searchEmployeeSkill("a159842", 1, "a159842");
		System.out.println("searchEmp"+empSkillID+" "+loginSession.getErrorMsg());
	}
	public Employee getEmployee()
	{
		return employee;
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new TestingPhoenix();
	}

}