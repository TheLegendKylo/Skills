package vzap.phoenix.Server.Employee;

import java.io.Serializable;

public class Employee implements Serializable
{
	private String employeeID = null;
	private String surname = null;
	private String firstName = null;
	private String cellNumber = null;
	private String email = null;
	public Employee(String employeeID, String surname, String firstName)
	{
		this.employeeID = employeeID;
		this.surname = surname;
		this.firstName = firstName;
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

}
