package vzap.phoenix.client;

import java.util.ArrayList;
import java.util.Date;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;

public class EmpSkillClientController
{
	private Employee employee;
	private EmpSkillClient loginSession;
	private EmployeeSkill empSkill;
	private ArrayList<Skill> skillList;
	
	public EmpSkillClientController()
	{
		loginSession = new EmpSkillClient();
		String employeeID = "a159842";
		String password = "123";
		short returnCode = loginSession.loginEmployee(employeeID, password);
		
		if(returnCode==0)//.equals("Login Successfull"))
		{
			employee = loginSession.getLogonEmployee();
			System.out.println(employee.getSurname());
		} else {
			System.out.println(loginSession.getErrorMsg());
		}
		boolean b = addEmpSkill();
		System.out.println("Write Message:" +loginSession.getErrorMsg());
//		System.out.println("HobbyID= "+loginSession.addHobby("AppDev"));
//		System.out.println("SkillID= "+loginSession.addSkill("Natural Adabas"));
		
	}
	public boolean addEmpSkill()
	{
		empSkill = new EmployeeSkill();
		empSkill.setEmployeeID(employee.getEmployeeID());
		skillList = loginSession.getSkillList();
		int skillID = skillList.get(20).getSkillId();
		empSkill.setSkillID(skillID);
		Date d = new Date();
		empSkill.setCreatedDate(d);
		empSkill.setRaterID(employee.getEmployeeID());
		short i=1;
		empSkill.setStatus(i);
		empSkill.setCoachingAvailability("N");
		boolean saveSuccess = loginSession.addEmployeeSkill(empSkill);
		return saveSuccess;
	}
	public Employee getEmployee()
	{
		return employee;
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new EmpSkillClientController();
	}

}
