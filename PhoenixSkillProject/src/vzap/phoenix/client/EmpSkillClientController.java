package vzap.phoenix.client;

import vzap.phoenix.Server.Employee.Employee;

public class EmpSkillClientController
{
	private Employee employee;
	private EmpSkillClient loginSession;
	
	public EmpSkillClientController(String employeeID, String password)
	{
		loginSession = new EmpSkillClient();
		employeeID = "a159842";
		password = "123";
		short returnCode = loginSession.loginEmployee(employeeID, password);
		
		if(returnCode==0)//.equals("Login Successfull"))
		{
			employee = loginSession.getLogonEmployee();
			System.out.println(employee.getSurname());
		} else {
			System.out.println(loginSession.getErrorMsg());
		}
	}
	public Employee getEmployee()
	{
		return employee;
	}
//	public static void main(String[] args)
//	{
//		// TODO Auto-generated method stub
//		new EmpSkillClientController();
//	}

}
