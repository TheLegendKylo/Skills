package vzap.phoenix.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Level;
import vzap.phoenix.Server.Employee.Skill;

public class EmpSkillClientController
{
	private Employee employee;
	private EmpSkillClient client;
	
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
		return client.getHobbyList();
	}
	public ArrayList<Level> getLevelList()
	{
			return client.getLevelList();
	}
	public ArrayList<CapabilityRating> getCapabilityRatingList()
	{
		return client.getCapabilityRatingList();
	}
	public ArrayList<Capability> getCapabilityList()
	{
		return client.getCapabilityList();
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
	public ArrayList<EmployeeSkill> searchEmployeeSkill(int searchCriteria)
	{
		return client.searchEmployeeSkill(searchCriteria);
	}
	public boolean addEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		return client.addEmployeeSkill(addEmployeeSkill);
	}
	public ArrayList<EmployeeSkill> getEmployeeSkillList()
	{
		return client.getEmpSkillList();
	}
	public ArrayList<Skill> getSkillList()
	{
		return client.getSkillList();
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
		return client.addHobby(description);
	}
	
	public short addSkill(String description)
	{
		return client.addSkill(description);
	}
	
	
	
	
}
