package vzap.phoenix.Employee;

import java.io.Serializable;

public class Employee implements Serializable
{
	private String employeeID = null;
	private String surname = null;
	private String firstName = null;
	private String contactNo = null;
	
	public Employee(String employeeID, String surname, String firstName, String contactNo)
	{
		this.employeeID = employeeID;
		this.surname = surname;
		this.firstName = firstName;
		this.contactNo = contactNo;
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
		return contactNo;
	}
	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}

}
