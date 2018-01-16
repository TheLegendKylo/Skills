package vzap.phoenix.Server.Employee;

import java.util.ArrayList;

import vzap.phoenix.DAO.EmployeeDAO;
import vzap.phoenix.DAO.EmployeeSkillDAO;
import vzap.phoenix.DAO.SkillDAO;
import vzap.phoenix.DAO.CapabilityDAO;
import vzap.phoenix.DAO.CapabilityLevelDAO;
import vzap.phoenix.DAO.SkillStageDAO;

public class EmployeeController
{
	private Employee logonEmployee;
	private EmployeeDAO employeeDAO;
	private EmployeeSkillDAO empSkillDAO;
	static ArrayList <Skill> skillList;
	static ArrayList <SkillStage> skillStageList;
	static ArrayList <Capability> skillDimensionList;
	static ArrayList <CapabilityLevel> skillDimensionLevelList;
	private ArrayList <EmployeeSkill> empSkillList;

	public EmployeeController(String employeeID, String password)
	{
		this.employeeDAO = new EmployeeDAO();
		this.logonEmployee = employeeDAO.loginEmployee(employeeID, password);
		if(!(logonEmployee==null))
		{
			new SkillDAO();
			SkillDAO.getSkillList();
			new SkillStageDAO();
			SkillStageDAO.getSkillStageList();
			new CapabilityDAO();
			CapabilityDAO.getCapabilityList();
			new CapabilityLevelDAO();
			CapabilityLevelDAO.getCapabilityLevelList();

			empSkillDAO = new EmployeeSkillDAO();
			empSkillList = empSkillDAO.getEmpSkillList();
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
	public Employee getLogonEmployee()
	{
		return this.logonEmployee;
	}
	public static ArrayList<Skill> getSkillList()
	{
		return skillList;
	}
	public static ArrayList<SkillStage> getSkillStageList()
	{
		return skillStageList;
	}
	public ArrayList<EmployeeSkill> getEmpSkillList()
	{
		return empSkillList;
	}
	public static ArrayList<Capability> getSkillDimensionList()
	{
		return skillDimensionList;
	}
	public static ArrayList<CapabilityLevel> getSkillDimensionLevelList()
	{
		return skillDimensionLevelList;
	}

}
