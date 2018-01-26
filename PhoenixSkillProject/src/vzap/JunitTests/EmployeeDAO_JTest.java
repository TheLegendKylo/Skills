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
import vzap.phoenix.DAO.MyDBCon;
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
	
	
	public EmployeeDAO_JTest()
	{
		myDBCon = new MyDBCon();
		dbCon = MyDBCon.getDBCon();
		empList = new ArrayList<Employee>();
	}

	@Test
	public void testEmployeeDAO()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testLoginEmployee() throws Exception
	{
		ps = dbCon.prepareStatement("select password from Employee where employeeID = ?");
		ps.setString(1, "A043410");
		ResultSet rs = ps.executeQuery();
		rs.next();
		String psw = rs.getString(1);
		assertTrue("1234".equals(psw));
	}
	

	@Test
	public void testGetEmpHobby() throws Exception
	{
		PreparedStatement ps = null;
	
		ps = dbCon.prepareStatement("select * from employeeHobby where employeeId=?");
		ps.setString(1, "A043410");
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
		
		assertTrue(empHobbyList.size() > 0);
	}

	@Test
	public void testRegisterEmployee() throws Exception
	{
		PreparedStatement ps = null;
		ps = dbCon.prepareStatement("insert into employee values(?,?,?,?,?,?,?)");
		ps.setString(1, "A12345");
		ps.setString(2, "Abby");
		ps.setString(3, "Grey");
		ps.setString(4, "Abby");
		ps.setString(5, "abby.grey@standardbank.co.za");
		ps.setString(6, "0834557322");
		ps.setString(7, "1234");
		ps.executeUpdate();
		
		ps = dbCon.prepareStatement("select * from employee where employeeID = 'A12345'");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			assertTrue("A12345".equals(rs.getString("employeeID")));
			assertTrue("Abby".equals(rs.getString("firstName")));
			assertTrue("Grey".equals(rs.getString("surname")));
			assertTrue("Abby".equals(rs.getString("alias")));
			assertTrue("abby.grey@standardbank.co.za".equals(rs.getString("email")));
			assertTrue("0834557322".equals(rs.getString("contactNo")));
			assertTrue("1234".equals(rs.getString("password")));
		}
	}

	@Test
	public void testUpdateEmployee() throws Exception 
	{
		PreparedStatement ps = null;

			ps = dbCon.prepareStatement("update Employee set firstName = ?,Surname = ?,alias = ?,email = ?,contact = ?"
						+ "where employeeID = ?");
			
			ps.setString(1, "Betty");
			ps.setString(2, "Boobs");
			ps.setString(3, "Bee");
			ps.setString(4, "bee.boobs@standardbank.co.za");
			ps.setString(5, "0832292786");
			ps.setString(6, "B00000");
			ps.executeUpdate();
			
			
			ps = dbCon.prepareStatement("Select * from employee where employeeID = ?");
			ResultSet rs = ps.executeQuery();	
			while (rs.next())
			{
				assertTrue("B00000".equals(rs.getString("employeeID")));
				assertTrue("Betty".equals(rs.getString("firstName")));
				assertTrue("Boobs".equals(rs.getString("surname")));
				assertTrue("Bee".equals(rs.getString("alias")));
				assertTrue("bee.boobs@standardbank.co.za".equals(rs.getString("email")));
				assertTrue("0832292786".equals(rs.getString("contactNo")));
			}
	}

	@Test
	public void testAddEmpHobby()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteEmployee()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSearchEmployee()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSearchEmpHobbyStringShort()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSearchEmpHobbyShort()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetEmployee()
	{
		fail("Not yet implemented");
	}

}
