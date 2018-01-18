package vzap.phoenix.Server.Employee;

import java.util.ArrayList;
import java.util.Vector;

import vzap.phoenix.DAO.EmployeeDAO;
import vzap.phoenix.DAO.EmployeeSkillDAO;
import vzap.phoenix.DAO.HobbyDAO;
import vzap.phoenix.DAO.CapabilityDAO;
import vzap.phoenix.DAO.CapabilityLevelDAO;
import vzap.phoenix.DAO.LevelDAO;
import vzap.phoenix.DAO.SkillDAO;

public class EmployeeController
{
	private Employee logonEmployee;
	private EmployeeDAO employeeDAO;
	private EmployeeSkillDAO empSkillDAO;
	private String errorMsg;
	static Vector<Hobby> hobbyList;
	static ArrayList <Skill> skillList;
	static ArrayList <Level> levelList;
	static ArrayList <Capability> capabilityList;
	static ArrayList <CapabilityRating> capabilityRatingList;
	private ArrayList <EmployeeSkill> empSkillList;

	public EmployeeController(String employeeID, String password)
	{
		this.employeeDAO = new EmployeeDAO();
		this.logonEmployee = employeeDAO.loginEmployee(employeeID, password);
		if(!(logonEmployee==null))
		{
//			new SkillDAO();
//			skillList = SkillDAO.getSkillList();
//			new HobbyDAO();
//			hobbyList = HobbyDAO.getHobbyList();
//			new LevelDAO();
//			levelList = LevelDAO.getLevelList();
			new CapabilityDAO();
			capabilityList = CapabilityDAO.getCapabilityList();
//			new CapabilityLevelDAO();
//			capabilityRatingList = CapabilityLevelDAO.getCapabilityLevelList();

//			empSkillDAO = new EmployeeSkillDAO(logonEmployee.getEmployeeID());
//			empSkillList = empSkillDAO.getEmpSkillList();
		} else {
			errorMsg = employeeDAO.getErrorMsg();
		}
	}
	public boolean registerEmployee(Employee newEmployee)
	{
		this.employeeDAO = new EmployeeDAO();
		if(employeeDAO.registerEmployee(newEmployee))
		{
			this.logonEmployee = newEmployee;
			return true;
		}
		System.out.println("Registration Failed");
		return false;
	}
	public boolean updateEmployee(Employee updateEmployee)
	{
		if(employeeDAO==null)
		{
			this.employeeDAO = new EmployeeDAO();			
		}
		if(employeeDAO.updateEmployee(updateEmployee))
		{
			this.logonEmployee = updateEmployee;
			return true;
		}
		System.out.println("Employee Update Failed");
		return false;
	}
	public ArrayList<Employee> searchEmployee(String searchCriteria)
	{
		ArrayList<Employee> empSearchResultList = employeeDAO.searchEmployee(searchCriteria);
		return empSearchResultList;
	}
	public int addEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		int responseCode = empSkillDAO.insertEmployeeSkill(addEmployeeSkill);
		if(responseCode==0)// employeeSkill record not added successfully
		{
			this.errorMsg = empSkillDAO.getErrorMsg();
		}
		return responseCode;
	}
	public Employee getLogonEmployee()
	{
		return this.logonEmployee;
	}
	public String getErrorMsg()
	{
		return this.errorMsg;
	}
	public static ArrayList<Skill> getSkillList()
	{
		return skillList;
	}
	public static Vector<Hobby> getHobbyList()
	{
		return hobbyList;
	}
	public static ArrayList<Level> getLevelList()
	{
		return levelList;
	}
	public ArrayList<EmployeeSkill> getEmpSkillList()
	{
		return empSkillList;
	}
	public static ArrayList<Capability> getCapabilityList()
	{
		return capabilityList;
	}
	public static ArrayList<CapabilityRating> getCapabilityRatingList()
	{
		return capabilityRatingList;
	}

}
