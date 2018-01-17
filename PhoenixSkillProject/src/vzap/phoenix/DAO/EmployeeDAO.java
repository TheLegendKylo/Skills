package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vzap.phoenix.Server.Employee.Employee;


public class EmployeeDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	private Employee employee;
	private String errorMsg = null;
	
	public EmployeeDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = myDBCon.getDBCon();
	}
	public Employee loginEmployee(String employeeID, String password)
	{
		try
		{
			PreparedStatement ps=dbCon.prepareStatement("select * from Employee where employeeID = ?");
System.out.println("What is the EmployeeID: "+employeeID);
			ps.setString(1,employeeID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String rsPassword = rs.getString("password");
System.out.println("Compare rsPassword: "+rsPassword+" to "+password);
				if(password.equals(rsPassword))
				{
					System.out.println("Validation passed, create OBJECT");
					String surname = rs.getString("surname");
					String firstName = rs.getString("firstName");
					employee = new Employee(employeeID, surname, firstName);
					String contactNo = rs.getString("cellNumber");
					String email = rs.getString("email");
					employee.setContactNo(contactNo);
					employee.setEmail(email);
				} else {
					errorMsg = "Invalid password entered";
				}
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMsg = "Employee "+employeeID+" not found";

		}
		return employee;
	}
	public String getErrorMsg()
	{
		return this.errorMsg;
	}
	public boolean registerEmployee(Employee employee)
	{
		return true;
	}
	public boolean updateEmployee(Employee employee)
	{
		return true;
	}
}
