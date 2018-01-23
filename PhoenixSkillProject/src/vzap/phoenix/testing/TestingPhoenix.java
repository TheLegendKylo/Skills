package vzap.phoenix.testing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
//		this.searchEmployeeSkill();
//		this.searchEmployeeSkillBySkillID();
//		this.rateSkill();
		this.addEmployeeHobby();
//		boolean b = this.addEmpSkill();
//		boolean b = this.nominateRater();
//		this.addHobby();
//		this.addSkill();
//		this.updateEmployee();
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
		empSkill = empSkillList.get(1);
		Date d = new Date();
		empSkill.setRatedDate(d);
		short i=2;
		empSkill.setStatus(i);
		// set capability ratings
		short[] cap = {1,2,3,4,5,6,7};
		ArrayList<Short> capability = new ArrayList<Short>();
		capability.add(cap[0]);
		capability.add(cap[1]);
		capability.add(cap[2]);
		capability.add(cap[3]);
		capability.add(cap[4]);
		capability.add(cap[5]);
		capability.add(cap[6]);
		ArrayList<Short> rating = new ArrayList<Short>();
		short[] rate = {3,2,4,2,3,4,1}; 
		rating.add(rate[0]);
		rating.add(rate[1]);
		rating.add(rate[2]);
		rating.add(rate[3]);
		rating.add(rate[4]);
		rating.add(rate[5]);
		rating.add(rate[6]);
		empSkill.setCapabilityList(capability);
		empSkill.setRatingList(rating);
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
			if(!(empSkillList.get(i).getRatingList()==null))
			{
				for (int j = 0; j < empSkillList.get(i).getRatingList().size(); j++)
				{
					for (int j2 = 0; j2 < capList.size(); j2++)
					{
						if(capList.get(j).getID()==empSkillList.get(i).getCapabilityList().get(j2))
						{
							capDesc=capList.get(j).getName();
						}
					}
					System.out.println("Capability: "+capDesc+" \tRating :"+empSkillList.get(i).getRatingList().get(j));
				}
			}
		}
	}
	public void searchEmployeeSkill()
	{
		empSkill = loginSession.searchEmployeeSkill("a159842", 2, "a159842");
		System.out.println("searchEmp"+empSkill.getEmployeeID()+" "+empSkill.getRaterID()+" "+empSkill.getEmpSkillID()+" "+loginSession.getErrorMsg());
	}
	public void searchEmployeeSkillBySkillID()
	{
		empSkillList = loginSession.searchEmployeeSkill(1);
		for (int i = 0; i < empSkillList.size(); i++)
		{
			empSkill = empSkillList.get(i);
			System.out.println("searchEmp"+empSkill.getEmployeeID()+" "+empSkill.getRaterID()+" "+empSkill.getEmpSkillID()+" "+loginSession.getErrorMsg());
		}
	}
	public void addEmployeeHobby()
	{
		System.out.println("Update Hobby-Before "+employee.getEmpHobbies());
		employee.getEmpHobbies().add((short)5);
		boolean success = loginSession.updateEmployee(employee);
		System.out.println("Update Hobby-After "+employee.getEmpHobbies());
		System.out.println("Success "+success);
		
	}
	public Employee getEmployee()
	{
		return employee;
	}
	public void updateEmployee()
	{
//		employee.setContactNo("0824449999");
		boolean success = loginSession.updateEmployee(employee);
		System.out.println("Success: "+success);
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new TestingPhoenix();
	}

}