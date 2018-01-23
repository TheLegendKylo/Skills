package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Employee;

public class EmployeeDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	private Employee employee;
	private ArrayList<Employee> empList;
	private PreparedStatement ps;
	private String errorMsg = null;
	private short errorCode = 0;
	
	public EmployeeDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = MyDBCon.getDBCon();
		empList = new ArrayList<Employee>();
	}
	public Employee loginEmployee(String employeeID, String password)
	{
		this.errorCode = 1; //Employee does not exist
		try
		{
			ps = dbCon.prepareStatement("select password from Employee where employeeID = ?");
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
					employee = this.getEmployee(employeeID);
				} else {
					errorCode = 2; // Invalid password 
					errorMsg = "Invalid password entered";
					break;
				}
				this.getEmpHobby(employeeID);
				this.errorCode = 0; //Employee found and password matched
				this.errorMsg = "Login successful";
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMsg = "Employee "+employeeID+" not found";

		}
		return employee;
	}
	public void getEmpHobby(String employeeID)
	{
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select * from employeeHobby where employeeId=?");
			ps.setString(1, employeeID);
			ResultSet rs = ps.executeQuery();
			int resultCount = 0;
			if (rs.last()) 
			{
				resultCount = rs.getRow();
				  rs.beforeFirst(); 
			}
			System.out.println("Hobby result count: "+resultCount);
			ArrayList<Short> empHobbyList= new ArrayList<Short>();
			
			while(rs.next())
			{
				int j=0;
				empHobbyList.add(new Short(rs.getShort("hobbyId")));
				j++;
			}
			employee.setEmpHobbies(empHobbyList);
		} catch (SQLException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		this.errorMsg = "Employee Hobby: Select statement failed for EmployeeHobby: "+employeeID;
		}
	}
	public short getErrorCode()
	{
		return this.errorCode;
	}
	public String getErrorMsg()
	{
		return this.errorMsg;
	}
	public boolean registerEmployee(Employee employee)
	{
		try
		{
			ps = dbCon.prepareStatement("insert into employee values(?,?,?,?,?,?)");
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
			this.errorMsg = "Employee : Insert statement failed for Employee: "+employee.getEmployeeID();
			return false;
		}
		return true;
	}
	public boolean updateEmployee(Employee employee)
	{
		boolean updSuccess = true;
		try 
		{
			ps = dbCon.prepareStatement("update Employee set firstName = ?,Surname = ?,alias = ?,email = ?,contact = ?"
						+ "where employeeID = ?");
			
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getSurname());
			ps.setString(3, employee.getAlias());
			ps.setString(4, employee.getEmail());
			ps.setString(5, employee.getContactNo());
			ps.setString(6, employee.getEmployeeID());
						
			ps.executeUpdate();
			ArrayList<Short> empHobbyList = employee.getEmpHobbies();
			if(!(empHobbyList==null))
			{
				updSuccess = this.addEmpHobby(empHobbyList);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		return updSuccess;
	}
	public boolean addEmpHobby(ArrayList<Short> empHobbyList)
	{
		for (int i = 0; i < empHobbyList.size(); i++)
		{
			boolean hobbyExists = this.searchEmpHobby(employee.getEmployeeID(), empHobbyList.get(i));
			if(hobbyExists)
			{
				continue;
			}
			PreparedStatement ps = null;
			try
			{
				ps = dbCon.prepareStatement("insert into employeeHobby values (null, ?,?)");
				ps.setString(1, employee.getEmployeeID());
				ps.setShort(2, empHobbyList.get(i));
				int resultCount = ps.executeUpdate();
			} catch (SQLException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorMsg = "Employee Hobby: Select statement failed for EmployeeHobby: "+employee.getEmployeeID();
			return false;
			}
		}
		return true;
	}
	public boolean deleteEmployee(Employee employee)
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
			return false;
		}
		return true;
	}
	public ArrayList<Employee> searchEmployee(String enteredQuery)
	{
		String employeeID = null;
		try 
		{
			String input = "%" + enteredQuery + "%";
						
			ps = dbCon.prepareStatement("select employeeID from Employee where employeeID like ? "
					+ "or firstName like ? or surname like ? or alias like ?");

			ps.setString(1, input);
			ps.setString(2, input);
			ps.setString(3, input);
			ps.setString(4, input);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				employeeID = rs.getString("employeeId");
				empList.add(this.getEmployee(employeeID));
			}
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return empList;
	}
	public boolean searchEmpHobby(String employeeID, short hobbyID)
	{
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select id from employeeHobby where employeeId=? and hobbyId=?");
			ps.setString(1, employeeID);
			ps.setShort(2, hobbyID);
			ResultSet rs = ps.executeQuery();
			int resultCount = 0;
			if (rs.last()) 
			{
				resultCount = rs.getRow();
			}
			if(resultCount>0)
			{
				return true;
			}
		} catch (SQLException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		this.errorMsg = "Employee Hobby: Search statement failed for EmployeeHobby: "+employeeID;
		}
		return false;
	}
	public Employee getEmployee(String employeeID)
	{
		
		try
		{
			ps = dbCon.prepareStatement("select * from Employee where employeeID = ?");
System.out.println("Search: What is the EmployeeID: "+employeeID);
			ps.setString(1,employeeID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				String surname = rs.getString("surname");
				String firstName = rs.getString("firstName");
				employee = new Employee(employeeID, surname, firstName);
				String alias = rs.getString("alias");
				String contactNo = rs.getString("contact");
				String email = rs.getString("email");
				employee.setContactNo(contactNo);
				employee.setEmail(email);
				employee.setAlias(alias);
				System.out.println("in result set emp object = " + employee.toString());
			}
     		this.getEmpHobby(employeeID);
			this.errorCode = 0; //Employee found and password matched
				
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMsg = "Employee Get "+employeeID+" not found";
		}
		System.out.println("out of result set emp object = " + employee.toString());
		return employee;
	}
	
}
