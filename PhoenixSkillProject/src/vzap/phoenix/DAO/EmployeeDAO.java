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
	private PreparedStatement ps;
	
	public EmployeeDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = myDBCon.getDBCon();
	}
	public Employee loginEmployee(String employeeID, String password)
	{
		try
		{
			ps = dbCon.prepareStatement("select * from Employee where employeeID = ?");
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
				}
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}
	
	public void registerEmployee(Employee employee)
	{
		try
		{
			ps = dbCon.prepareStatement("insert into user values(?,?,?,?,?,?)");
			ps.setString(1, employee.getEmployeeID());
			ps.setString(2, employee.getFirstName());
			ps.setString(3, employee.getSurname());
			ps.setString(4, employee.getAlias());
			ps.setString(5, employee.getEmail());
			ps.setString(6, employee.getContactNo());
			
			ps.executeUpdate();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateEmployee(Employee employee)
	{
		try 
		{
			ps = dbCon.prepareStatement("update users set firstName = ?,Surname = ?,alias = ?,email = ?,cellNumber = ?"
						+ "where employeeID = ?");
			
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getSurname());
			ps.setString(3, employee.getAlias());
			ps.setString(4, employee.getEmail());
			ps.setString(5, employee.getContactNo());
						
			ps.executeUpdate();
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void deleteEmployee(Employee employee)
	{
		try 
		{
			ps = dbCon.prepareStatement("select * from Employee where employeeID = ?");
			
			ps.setString(1,employee.getEmployeeID());
			
			ps.executeQuery();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void searchEmployee(Employee employee)
	{
		try 
		{
			String enteredQuery = "";
			String input = "%" + enteredQuery + "%";
						
			ps = dbCon.prepareStatement("select * from Employee where firstName like ? or surname like ? or alias like ?");

			ps.setString(1, input);
			ps.setString(2, input);
			ps.setString(3, input);
						
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
