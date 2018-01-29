package vzap.phoenix.client;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Level;
import vzap.phoenix.Server.Employee.Skill;

public class EmpSkillClientController
{
	private EmpSkillClient client;
	private Vector<Hobby> hobbyList;
	private ArrayList <Skill> skillList;
	private ArrayList <Level> levelList;
	private ArrayList <Capability> capabilityList;
	private ArrayList <CapabilityRating> capabilityRatingList;
	private ArrayList <EmployeeSkill> empSkillList;
	
	public EmpSkillClientController()
	{
		client = new EmpSkillClient();
	}
	public short loginEmployee(String employeeID, String password)
	{
		//0 success
		//1 emp not found/invalid userName
		//2 password
		return client.loginEmployee(employeeID, password);	
	}
	public Employee getLogonEmployee()
	{
		//if null no employee found.
		return client.getLogonEmployee();
	}
	public Vector<Hobby> getHobbyList()
	{
		if(hobbyList==null)
		{
			hobbyList = client.getHobbyList();			
		}
		return hobbyList;
	}
	public ArrayList<Skill> getSkillList()
	{
		if(skillList==null)
		{
			skillList = client.getSkillList();			
		}
		return skillList;
	}
	public ArrayList<Level> getLevelList()
	{
		if(levelList==null)
		{
			levelList = client.getLevelList();			
		}
		return levelList;
	}
	public ArrayList<CapabilityRating> getCapabilityRatingList()
	{
		if(capabilityRatingList==null)
		{
			capabilityRatingList = client.getCapabilityRatingList();
		}
		return capabilityRatingList;
	}
	public ArrayList<Capability> getCapabilityList()
	{
		if(capabilityRatingList==null)
		{
			capabilityList = client.getCapabilityList();
		}
		return capabilityList;
	}
	public boolean registerEmployee(Employee newEmployee)
	{
		return client.registerEmployee(newEmployee);
	}
	public boolean updateEmployee(Employee updateEmployee)
	{
		return client.updateEmployee(updateEmployee);
	}
	public ArrayList<Employee> searchEmployee(String searchCriteria)
	{
		return client.searchEmployee(searchCriteria);
	}
	public ArrayList<Employee> searchEmployeeHobby(short searchCriteria)
	{
		return client.searchEmployeeHobby(searchCriteria);
	}
	public ArrayList<EmployeeSkill> getEmpSkillByEmpID(String employeeID)
	{
		return client.getEmpSkillByEmpID(employeeID);
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkillByRaterID(String raterID)
	{
		return client.searchEmployeeSkillByRaterID(raterID);
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkill(int searchCriteria)
	{
		return client.searchEmployeeSkill(searchCriteria);
	}
	public boolean addEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		boolean success = client.addEmployeeSkill(addEmployeeSkill);
		empSkillList = client.getEmpSkillList();
		return success;
	}
	public ArrayList<EmployeeSkill> getEmployeeSkillList()
	{
		if(empSkillList==null)
		{
			empSkillList = client.getEmpSkillList();
		}
		return empSkillList;
	}
	
	public ArrayList<EmployeeSkill> getOutstandingRatings()
	{
		ArrayList<EmployeeSkill> outstRatings = new ArrayList<EmployeeSkill>();
		for (int i = 0; i < empSkillList.size(); i++)
		{
			if(empSkillList.get(i).getStatus()==0)
			{
				outstRatings.add(empSkillList.get(i));
			}
		}
		return outstRatings;
	}
	public boolean nominateRater(EmployeeSkill nominateRater)
	{
		return client.nominateRater(nominateRater);
	}
	public boolean rateEmployeeSkill(EmployeeSkill rateEmployeeSkill)
	{
		return client.rateEmployeeSkill(rateEmployeeSkill);
	}
	public short getErrorCode()
	{
		return client.getErrorCode();
	}
	public String getErrorMsg()
	{
		return client.getErrorMsg();
	}
	public void closeConnections()
	{
		client.closeConnections();
	}
	public short addHobby(String description)
	{
		short hobbyID = client.addHobby(description);
		hobbyList = client.getHobbyList();
		return hobbyID;
	}
	public short addSkill(String description)
	{
		short skillID = client.addSkill(description);
		skillList = client.getSkillList();
		return skillID;
	}
	public DefaultTableModel getEmpSkillAverage(ArrayList<EmployeeSkill> employeeSkillList)
	{
		EmpSkillCommonMethods empSkillMethod = new EmpSkillCommonMethods();
		return empSkillMethod.getEmpSkillAverage(this, employeeSkillList);
	}
	public DefaultTableModel getEmpSkillList(ArrayList<EmployeeSkill> employeeSkillList, Employee employee)
	{
		EmpSkillCommonMethods empSkillMethod = new EmpSkillCommonMethods();
		return empSkillMethod.getEmpSkillList(this, employeeSkillList, employee);
	}

}
