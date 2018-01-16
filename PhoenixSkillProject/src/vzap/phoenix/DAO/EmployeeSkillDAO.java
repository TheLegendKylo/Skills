package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.EmployeeSkill;

public class EmployeeSkillDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	private ArrayList<EmployeeSkill> empSkillList = null;
	
	public EmployeeSkillDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = myDBCon.getDBCon();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from Skills");
			while(rs.next())
			{
				String employeeID = rs.getString("employeeID");
				String skill = rs.getString("skill");
				short capabilityID = rs.getShort("capabilityID");
				String rateeID = rs.getString("rateeID");
				short rating = rs.getShort("rating");
				empSkillList.add(new EmployeeSkill(employeeID, skill, capabilityID,
						rateeID, rating));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<EmployeeSkill> getEmpSkillList()
	{
		return empSkillList;
	}
	public int updateUserRecord(EmployeeSkill editEmployeeSkill)
	{
		int updateCount=0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("update employeeSkill set "
					+"rating=?,ratingDate=?,status=?"
					+"where employeeID=?,skill=?,rateeID=?");
			ps.setShort(1, editEmployeeSkill.getRating());
			ps.setDate(2, (Date) editEmployeeSkill.getRatingDate());
			ps.setShort(3, editEmployeeSkill.getStatus());
			ps.setString(4, editEmployeeSkill.getemployeeID());
			ps.setString(5, editEmployeeSkill.getSkill());
			ps.setShort(6, editEmployeeSkill.getCapabilityID());
			ps.setString(7, editEmployeeSkill.getRateeID());
			updateCount = ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateCount;
	}
	public int insertEmployeeSkill(EmployeeSkill foundEmployeeSkill)
	{
		int insertCount = 0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("insert into EmployeeSkill values(?,?,?,?,?,?)");
			ps.setString(1, foundEmployeeSkill.getemployeeID());
			ps.setString(2, foundEmployeeSkill.getSkill());
			ps.setString(3, foundEmployeeSkill.getRateeID());
			ps.setShort(4, foundEmployeeSkill.getCapabilityID());
			ps.setShort(5, foundEmployeeSkill.getRating());
			ps.setDate(6, (Date) foundEmployeeSkill.getRatingDate());
			ps.setShort(7, foundEmployeeSkill.getStatus());
			insertCount = ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertCount;
	}
	public int deleteEmployeeSkill(EmployeeSkill foundEmployeeSkill)
	{
		int deleteCount=0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement
					("delete from EmployeeSkill where employeeID = ? and skill = ? "
							+ "and capabilityID = ? and rateeID = ?");
			ps.setString(1, foundEmployeeSkill.getemployeeID());
			ps.setString(2, foundEmployeeSkill.getSkill());
			ps.setShort(3, foundEmployeeSkill.getCapabilityID());
			ps.setString(4, foundEmployeeSkill.getRateeID());
			deleteCount = ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteCount;
	}

}
