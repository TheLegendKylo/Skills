package vzap.phoenix.Server.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import vzap.phoenix.DAO.EmployeeDAO;
import vzap.phoenix.DAO.EmployeeSkillDAO;
import vzap.phoenix.DAO.HobbyDAO;
import vzap.phoenix.DAO.CapabilityDAO;
import vzap.phoenix.DAO.CapabilityLevelDAO;
import vzap.phoenix.DAO.LevelDAO;
import vzap.phoenix.DAO.SkillDAO;

public class EmployeeController implements Runnable
{
	private Employee logonEmployee;
	private EmployeeDAO employeeDAO;
	private EmployeeSkillDAO empSkillDAO;
	private HobbyDAO hobbyDAO;
	private SkillDAO skillDAO;
	private short errorCode=0;
	private String errorMsg=null;
	static Vector<Hobby> hobbyList;
	static ArrayList <Skill> skillList;
	static ArrayList <Level> levelList;
	static ArrayList <Capability> capabilityList;
	static ArrayList <CapabilityRating> capabilityRatingList;
	private ArrayList <EmployeeSkill> empSkillList;
	
	private Thread thread;

	public EmployeeController(String employeeID, String password)
	{
		this.employeeDAO = new EmployeeDAO();
		this.logonEmployee = employeeDAO.loginEmployee(employeeID, password);
		this.errorCode = employeeDAO.getErrorCode();
		this.errorMsg = employeeDAO.getErrorMsg();
		System.out.println("getDAO Error Code: "+this.errorCode);
		if(this.errorCode==0)
		{
			hobbyDAO = new HobbyDAO();
			hobbyList = HobbyDAO.getHobbyList();
			empSkillDAO = new EmployeeSkillDAO(logonEmployee.getEmployeeID());
			empSkillList = empSkillDAO.getEmpSkillList();
			thread = new Thread(this);
			thread.start();

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
		this.errorCode = employeeDAO.getErrorCode();
		this.errorMsg = employeeDAO.getErrorMsg();
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
			System.out.println("empControl = after call to EmployeeDAO - returning true");
			this.logonEmployee = updateEmployee;
			return true;
		}
		System.out.println("Employee Update Failed");
		this.errorCode = employeeDAO.getErrorCode();
		this.errorMsg = employeeDAO.getErrorMsg();
		return false;
	}
	public ArrayList<Employee> searchEmployee(String searchCriteria)
	{
		ArrayList<Employee> empSearchResultList = employeeDAO.searchEmployee(searchCriteria);
		System.out.println("empcontroller - " + empSearchResultList.size());
		return empSearchResultList;
	}
	public boolean addEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		this.errorCode = 0;
		this.errorMsg = null;
		int responseCode = empSkillDAO.insertEmployeeSkill(addEmployeeSkill);
		if(responseCode==0)// employeeSkill record not added successfully
		{
			this.errorCode = empSkillDAO.getErrorCode();
			this.errorMsg = empSkillDAO.getErrorMsg();
			return false;
		}
		return true;
	}
	public boolean addNominee(EmployeeSkill addEmployeeSkill)
	{
		return this.addEmployeeSkill(addEmployeeSkill);
	}
	public boolean rateEmployeeSkill(EmployeeSkill rateEmployeeSkill)
	{
		this.errorCode = 0;
		this.errorMsg = null;
		int responseCode = empSkillDAO.rateEmployeeSkill(rateEmployeeSkill);
		if(responseCode!=0)// employeeSkill record not rated successfully
		{
			this.errorCode = empSkillDAO.getErrorCode();
			this.errorMsg = empSkillDAO.getErrorMsg();
			return false;
		}
		return true;
	}
	public boolean updateEmployeeSkill(EmployeeSkill updEmployeeSkill)
	{
		this.errorCode = 0;
		this.errorMsg = null;
		int responseCode = empSkillDAO.updateEmployeeSkill(updEmployeeSkill);
		if(responseCode!=0)// employeeSkill record not rated successfully
		{
			this.errorCode = empSkillDAO.getErrorCode();
			this.errorMsg = empSkillDAO.getErrorMsg();
			return false;
		}
		return true;
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkill(String employeeID, int skillID, String raterID)
	{
		return empSkillDAO.searchEmployeeSkill(employeeID, skillID, raterID);
	}
	public ArrayList<EmployeeSkill> getEmpSkillByEmpID(String employeeID)
	{
		return empSkillDAO.getEmployeeSkill(employeeID);
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkill(int skillID)
	{
		return empSkillDAO.searchEmployeeSkillByID(skillID);
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkillByRaterID(String raterID)
	{
		return empSkillDAO.searchEmployeeSkillByRaterID(raterID);
	}
	public ArrayList<Employee> searchEmployeeHobby(short hobbyID)
	{
		return employeeDAO.searchEmpHobby(hobbyID);
	}
	public Employee getLogonEmployee()
	{
		return this.logonEmployee;
	}
	public short getErrorCode()
	{
		return this.errorCode;
	}
	public String getErrorMsg()
	{
		return this.errorMsg;
	}
	public void resetErrorMsg()
	{
		this.errorCode=0;
		this.errorMsg="";
	}
	public static ArrayList<Skill> getSkillList()
	{
		return skillList;
	}
	public short addSkill(String description)
	{
		short skillID = skillDAO.addSkill(description);
		System.out.println("Server Controller SkillID: "+skillID);
		return skillID;
	}
	public static Vector<Hobby> getHobbyList()
	{
		return hobbyList;
	}
	public short addHobby(String description)
	{
		return hobbyDAO.addHobby(description);
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
	@Override
	public void run()
	{
		skillDAO = new SkillDAO();
		skillList = SkillDAO.getSkillList();
		new LevelDAO();
		levelList = LevelDAO.getLevelList();
		new CapabilityDAO();
		capabilityList = CapabilityDAO.getCapabilityList();
		new CapabilityLevelDAO();
		capabilityRatingList = CapabilityLevelDAO.getCapabilityLevelList();
	}

}
