package vzap.JunitTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import vzap.phoenix.DAO.*;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.Hobby;

public class EmployeeDAO_JTest
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	private Employee employee;
	private ArrayList<Employee> empList;
	private Vector<Hobby> hobbyList;
	private PreparedStatement ps;
	private String errorMsg = null;
	private short errorCode = 0;
	private short tempHobbyId;
	
	
	public EmployeeDAO_JTest()
	{
		myDBCon = new MyDBCon();
		dbCon = MyDBCon.getDBCon();
		empList = new ArrayList<Employee>();
	}

	@Test
	public void testEmployeeDAO() throws Exception
	{
	}

	
	@Test
	public void testLoginEmployeeStringString() throws Exception
	{
//   insert a record to enable testing  
		ps = dbCon.prepareStatement("insert into employee values (?,?,?,?,?,?,?)");
		ps.setString(1, "aaaa");
		ps.setString(2, "1stName");
		ps.setString(3, "surName");
		ps.setString(4, "alias");
		ps.setString(5, "email");
		ps.setString(6, "contactNo");
		ps.setString(7, "psword");
		ps.executeUpdate();
		
		
		ps = dbCon.prepareStatement("select password from Employee where employeeID = ?");
		ps.setString(1, "aaaa");
		ResultSet rs = ps.executeQuery();
		rs.next();
		String psw = rs.getString(1);
		assertTrue("psword".equals(psw));
		
//  delete the record used for testing
		ps = dbCon.prepareStatement("delete from  employee where employeeID = ?");
		ps.setString(1, "aaaa");
		ps.executeUpdate();
	}

	

	@Test
	public void testGetEmpHobbyString() throws Exception
	{
		
//   insert record into employeeHobby to enable testing
		short hobbyId = 3;
		ps = dbCon.prepareStatement("insert into EmployeeHobby values (null, ?, ?) ");
		ps.setString(1, "bbbb");
		ps.setShort (2, hobbyId);
		ps.executeUpdate();

		
		
		
		PreparedStatement ps = null;
		ps = dbCon.prepareStatement("select * from employeeHobby where employeeId=?");
		ps.setString(1, "bbbb");
		ResultSet rs = ps.executeQuery();
		int resultCount = 0;
		if (rs.last()) 
		{
			resultCount = rs.getRow();
			  rs.beforeFirst(); 
		}
		ArrayList<Short> empHobbyList= new ArrayList<Short>();
		
		while(rs.next())
		{
			int j=0;
			empHobbyList.add(new Short(rs.getShort("hobbyId")));
			j++;
		}
		
		assertTrue(empHobbyList.size() > 0);
		
//  delete the record used for testing
		ps = dbCon.prepareStatement("delete from  employeeHobby where employeeID = ?");
		ps.setString(1, "bbbb");
		ps.executeUpdate();
	}

	
	
	@Test
	public void testRegisterEmployeeEmployee() throws Exception
	{
		PreparedStatement ps = null;
		ps = dbCon.prepareStatement("insert into employee values(?,?,?,?,?,?,?)");
		ps.setString(1, "cccc");
		ps.setString(2, "Gabby");
		ps.setString(3, "Grey");
		ps.setString(4, "Gabby");
		ps.setString(5, "gabby.grey@standardbank.co.za");
		ps.setString(6, "0834557322");
		ps.setString(7, "1234");
		ps.executeUpdate();
		
		ps = dbCon.prepareStatement("select * from employee where employeeID = 'cccc'");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			assertTrue("cccc".equals(rs.getString("employeeID")));
			assertTrue("Gabby".equals(rs.getString("firstName")));
			assertTrue("Grey".equals(rs.getString("surname")));
			assertTrue("Gabby".equals(rs.getString("alias")));
			assertTrue("gabby.grey@standardbank.co.za".equals(rs.getString("email")));
			assertTrue("0834557322".equals(rs.getString("contactNo")));
			assertTrue("1234".equals(rs.getString("password")));
		}
		
//  delete the record used for testing
		ps = dbCon.prepareStatement("delete from  employee where employeeID = 'cccc'");
		ps.executeUpdate();		
	}

	
	
	
	@Test
	public void testUpdateEmployeeEmployee() throws Exception 
	{
		
//   insert a record onto employee to enable testing  		
		PreparedStatement pst = null;
		pst = dbCon.prepareStatement("insert into employee values(?,?,?,?,?,?,?)");
		pst.setString(1, "dddd");
		pst.setString(2, "Gabby");
		pst.setString(3, "Grey");
		pst.setString(4, "Gabby");
		pst.setString(5, "gabby.grey@standardbank.co.za");
		pst.setString(6, "0834557322");
		pst.setString(7, "1234");
		pst.executeUpdate();
		
		PreparedStatement ps = null;
		ps = dbCon.prepareStatement("update Employee set firstName = ?,Surname = ?,alias = ?,email = ?,contact = ?"
					+ "where employeeID = ?");
		ps.setString(1, "xxxx");
		ps.setString(2, "xxxx");
		ps.setString(3, "xxxx");
		ps.setString(4, "xxxx");
		ps.setString(5, "xxxx");
		ps.setString(6, "xxxx");
		ps.executeUpdate();
		
		
		ps = dbCon.prepareStatement("Select * from employee where employeeID = ?");
		ps.setString(1, "xxxx");
		ResultSet rs = ps.executeQuery();	
		while (rs.next())
		{
			assertTrue("dddd".equals(rs.getString("employeeID")));
			assertTrue("xxxx".equals(rs.getString("firstName")));
			assertTrue("xxxx".equals(rs.getString("surname")));
			assertTrue("xxxx".equals(rs.getString("alias")));
			assertTrue("xxxx".equals(rs.getString("email")));
			assertTrue("xxxx".equals(rs.getString("contactNo")));
		}
		
//  delete the record from employee that was used for testing
		ps = dbCon.prepareStatement("delete from  employee where employeeID = 'dddd'");
		ps.executeUpdate();		
	}


	
	
	
	
	@Test
	public void testSearchEmpHobbyStringShort() throws Exception
	{
		
//   insert records into Hobby and employeeHobby tables to enable testing  
		ps = dbCon.prepareStatement("insert into Hobby values (null,'eeee')");
		ps.executeUpdate();
		ps = dbCon.prepareStatement ("select hobbyID from hobby where hobbyDescription = 'eeee'");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			tempHobbyId = rs.getShort("hobbyID");
		}
		ps = dbCon.prepareStatement("insert into EmployeeHobby values (null, ?, ?) ");
		ps.setString(1, "eeee");
		ps.setShort (2, tempHobbyId);
		ps.executeUpdate();
		
		
		PreparedStatement ps = null;
		ps = dbCon.prepareStatement("select id from employeeHobby where employeeId=? and hobbyId=?");
		ps.setString(1, "eeee");
		ps.setShort(2, tempHobbyId);
		ResultSet rs1 = ps.executeQuery();
		int resultCount = 0;
		if (rs1.next()) 
		{
			resultCount++;
		}
		assertTrue(resultCount>0);
		
//   delete records from Hobby and employeeHobby 		
		ps = dbCon.prepareStatement("delete from  Hobby where (hobbyID = 'eeee')");
		ps.executeUpdate();	
		ps = dbCon.prepareStatement("delete from  employeeHobby where employeeID = 'eeee'");
		ps.executeUpdate();	
	}

	
	

	@Test
	public void testAddEmpHobbyArray() throws Exception
	{

	}

	
	
	
	@Test
	public void testDeleteEmployeeEmployee() throws Exception
	{
//   insert a record onto employee to enable testing  		
		PreparedStatement pst = null;
		pst = dbCon.prepareStatement("insert into employee values(?,?,?,?,?,?,?)");
		pst.setString(1, "ffff");
		pst.setString(2, "Gabby");
		pst.setString(3, "Grey");
		pst.setString(4, "Gabby");
		pst.setString(5, "gabby.grey@standardbank.co.za");
		pst.setString(6, "0834557322");
		pst.setString(7, "1234");
		pst.executeUpdate();
		
		
		ps = dbCon.prepareStatement("delete from Employee where employeeID = ?");
		ps.setString(1,"ffff");
		ps.executeUpdate();
		
		ps = dbCon.prepareStatement("select * from Employee where employeeID = 'ffff'");
		ResultSet rs = ps.executeQuery();
		int rsCount = 0;
		while(rs.next())
		{
			rsCount++;

		}
		assertTrue(rsCount<1);
	}

	
	
	
	
	@Test
	public void testSearchEmployeeString() throws Exception
	{
//  insert employee records to enable testing 
		ps = dbCon.prepareStatement("insert into employee values (?,?,?,?,?,?,?)");
		ps.setString(1, "aaaa");
		ps.setString(2, "1stName");
		ps.setString(3, "surName");
		ps.setString(4, "alias");
		ps.setString(5, "email");
		ps.setString(6, "contact");
		ps.setString(7, "psword");
		ps.executeUpdate();
		ps = dbCon.prepareStatement("insert into employee values (?,?,?,?,?,?,?)");
		ps.setString(1, "bbbb");
		ps.setString(2, "xxxx");
		ps.setString(3, "surName");
		ps.setString(4, "alias");
		ps.setString(5, "email");
		ps.setString(6, "contact");
		ps.setString(7, "psword");
		ps.executeUpdate();
		ps = dbCon.prepareStatement("insert into employee values (?,?,?,?,?,?,?)");
		ps.setString(1, "cccc");
		ps.setString(2, "1stName");
		ps.setString(3, "xxxx");
		ps.setString(4, "alias");
		ps.setString(5, "email");
		ps.setString(6, "contact");
		ps.setString(7, "psword");
		ps.executeUpdate();
		ps = dbCon.prepareStatement("insert into employee values (?,?,?,?,?,?,?)");
		ps.setString(1, "dddd");
		ps.setString(2, "1stName");
		ps.setString(3, "surName");
		ps.setString(4, "xxxx");
		ps.setString(5, "email");
		ps.setString(6, "contact");
		ps.setString(7, "psword");
		ps.executeUpdate();

		
		empList = new ArrayList<Employee>();
		String employeeID = null;
		int chkCount = 1;
		String input = "%" + "x"+ "%";
					
		ps = dbCon.prepareStatement("select employeeID from Employee where employeeID like ? "
				+ "or firstName like ? or surname like ? or alias like ?");

		ps.setString(1, input);
		ps.setString(2, input);
		ps.setString(3, input);
		ps.setString(4, input);
		ResultSet rs = ps.executeQuery();
		
		int rsCount = 0;
		while(rs.next())
		{
			rsCount++;
		}
		assertTrue(rsCount == 4);

	
//   delete the 4 records inserted above
	ps = dbCon.prepareStatement("delete from into employee where employeeID = 'aaaa'");
	ps.executeUpdate();
	ps = dbCon.prepareStatement("delete from into employee where employeeID = 'bbbb'");
	ps.executeUpdate();
	ps = dbCon.prepareStatement("delete from into employee where employeeID = 'cccc'");
	ps.executeUpdate();
	ps = dbCon.prepareStatement("delete from into employee where employeeID = 'dddd'");
	ps.executeUpdate();
	}

	
	
	
	
	
	
	@Test
	public void testGetEmployeeString() throws Exception
	{
		ps = dbCon.prepareStatement("select * from Employee where employeeID = ?");
		ps.setString(1,"xxxx");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			String surname = rs.getString("surname");
			String firstName = rs.getString("firstName");
			employee = new Employee("xxxx", surname, firstName);
			assertTrue("xxxx".equals(rs.getString("employeeID")));
			assertTrue("1stName".equals(rs.getString("firstName")));
			assertTrue("surName".equals(rs.getString("surname")));
			assertTrue("alias".equals(rs.getString("alias")));
			assertTrue("email".equals(rs.getString("email")));
			assertTrue("contact".equals(rs.getString("contactNo")));
		}
//  what do you do here ???? 		this.testGetEmpHobbyShort("xxxx");
		this.errorCode = 0; //Employee found and password matched
			

	
	}
	
	

}
