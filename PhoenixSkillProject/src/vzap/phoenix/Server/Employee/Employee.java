package vzap.phoenix.Server.Employee;

import java.io.Serializable;
import java.util.Arrays;

public class Employee implements Serializable
{
	
	private String employeeID = null;
	private String surname = null;
	private String firstName = null;
	private String cellNumber = null;
	private String email = null;
	private String alias = null;
	private String password = null;
	private short hobbyID[];
	
	public Employee(String employeeID, String surname, String firstName)
	{
		this.employeeID = employeeID;
		this.surname = surname;
		this.firstName = firstName;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public Employee() //for creating a new employee at Login
	{
		
	}
	public String getEmployeeID()
	{
		return employeeID;
	}
	public void setEmployeeID(String employeeID)
	{
		this.employeeID = employeeID;
	}
	public String getSurname()
	{
		return surname;
	}
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getContactNo()
	{
		return cellNumber;
	}
	public void setContactNo(String contactNo)
	{
		this.cellNumber = contactNo;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getAlias()
	{
		return alias;
	}
	public void setAlias(String alias)
	{
		this.alias = alias;
	}
	public short[] getEmpHobbies()
	{
		return this.hobbyID;
	}
	public void setEmpHobbies(short[] hobbyID)
	{
		this.hobbyID = hobbyID;
	}
	@Override
	public String toString()
	{
		return "Employee kyle [employeeID=" + employeeID + ", surname=" + surname + ", firstName=" + firstName
				+ ", cellNumber=" + cellNumber + ", email=" + email + ", alias=" + alias + ", password=" + password
				+ ", hobbyID=" + Arrays.toString(hobbyID) + "]";
	}
}
