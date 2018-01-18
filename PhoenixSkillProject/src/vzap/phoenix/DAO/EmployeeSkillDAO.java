package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.EmployeeSkillRating;

public class EmployeeSkillDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	private ArrayList<EmployeeSkill> empSkillList = null;
	private ArrayList<EmployeeSkillRating> empSkillRatingList = null;
	
	private String errorMsg;
	
	public EmployeeSkillDAO(String employeeID)
	{
		dbCon = MyDBCon.getDBCon();
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select * employeeskills where employeeId=?");
			ps.setString(1, employeeID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				short empSkillID = rs.getShort("empSkillId");
				String skill = rs.getString("skillId");
				String raterID = rs.getString("raterID");
				String coachingAvailability = rs.getString("coachingAvailability");
				String comment = rs.getString("comment");
				Date ratedDate = rs.getDate("ratedDate");
				Date createDate = rs.getDate("createdDate");
				empSkillList.add(new EmployeeSkill(employeeID, skill, 
						raterID, createDate));
				int idx = empSkillList.size()-1;
				empSkillList.get(idx).setEmpSkillID(empSkillID);
				empSkillList.get(idx).setCoachingAvailability(coachingAvailability);
				empSkillList.get(idx).setComment(comment);
				empSkillList.get(idx).setRatedDate(ratedDate);
				this.getEmpSkillRating(empSkillID, idx);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorMsg = "Employee Skill: Search failed for EmployeeID: "+employeeID;
		}
	}
	public void getEmpSkillRating(short empSkillID, int idx)
	{
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select * employeeSkillsRating where empSkillID=? order by capabilityId");
			ps.setShort(1, empSkillID);
			ResultSet rs = ps.executeQuery();
			short capabilityID[] = new short[rs.getFetchSize()];
			short rating[] = new short[rs.getFetchSize()];
			while(rs.next())
			{
				int j=0;
				capabilityID[j] = rs.getShort("capabilityId");
				rating[j] = rs.getShort("rating");
				j++;
			}
			empSkillList.get(idx).setCapability(capabilityID);
			empSkillList.get(idx).setCapability(rating);
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
	public int rateEmployeeSkill(EmployeeSkill rateEmployeeSkill)
	{
		int updateCount=0, ratingsCount=0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("update employeeSkill set ratedDate=?,status=?"
					+"where empSkillID=?");
			ps.setDate(1, (Date) rateEmployeeSkill.getRatedDate());
			ps.setShort(2, rateEmployeeSkill.getStatus());
			ps.setShort(3, rateEmployeeSkill.getEmpSkillID());
			updateCount = ps.executeUpdate();
			ratingsCount = this.addEmployeeSkillRating(rateEmployeeSkill);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateCount;
	}
	public int addEmployeeSkillRating(EmployeeSkill rateEmployeeSkill)
	{
		int insertCount=0;
		try
		{
		      Statement stmt = dbCon.createStatement();
		      dbCon.setAutoCommit(false);
		      PreparedStatement ps = dbCon.prepareStatement("insert into EmployeeSkillsRating values(?,?,?)");
		      for(int i=0; i<= 6;i++){
					ps.setShort(2, rateEmployeeSkill.getCapabilityID()[i]);
					ps.setShort(3, rateEmployeeSkill.getRating()[i]);
					ps.executeUpdate();
					insertCount++; 
		      }
		      System.out.println("The number of rows inserted: "+ insertCount);
		      dbCon.commit();
		      dbCon.setAutoCommit(true);
		}catch(Exception e)
		{
		      e.printStackTrace();
		      try
		      {
			      dbCon.rollback();
			      dbCon.setAutoCommit(true);
				} catch (SQLException r)
				{
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
		return insertCount;
	}
	public int insertEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		// first check whether a duplicate record already exists
		int insertCount = 0, ratingsCount=0;
		int recordCount = this.checkDuplicate(addEmployeeSkill);
		if(recordCount>0)
		{
			this.errorMsg = "Record already exists";
			return insertCount;
		}
		
		
		PreparedStatement ps = null, ps_select=null;
		try
		{
			ps = dbCon.prepareStatement("insert into EmployeeSkill values(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, null); // auto_generated empSkillId
			ps.setString(2, addEmployeeSkill.getEmployeeID());
			ps.setString(3, addEmployeeSkill.getSkill());
			ps.setString(4, addEmployeeSkill.getRaterID());
			ps.setString(5, addEmployeeSkill.getCoachingAvailability());
			ps.setShort(6, addEmployeeSkill.getStatus());
			ps.setString(7, addEmployeeSkill.getComment());
			ps.setDate(8,  (Date) addEmployeeSkill.getRatedDate());
			ps.setDate(9,  (Date) addEmployeeSkill.getCreatedDate());
			insertCount = ps.executeUpdate();

			ps_select = dbCon.prepareStatement("select empSkillId from EmployeeSkill where employeeId=? and skillID=? and raterId=?");
			ps_select.setString(1, addEmployeeSkill.getEmployeeID());
			ps_select.setString(2, addEmployeeSkill.getSkill());
			ps_select.setString(3, addEmployeeSkill.getRaterID());
			ResultSet rs = ps_select.executeQuery();
			while(rs.next())
			{
				addEmployeeSkill.setEmpSkillID(rs.getShort("empSkillId"));
			}
			if(addEmployeeSkill.getCapabilityID().length>0)
			{
				ratingsCount = this.addEmployeeSkillRating(addEmployeeSkill);				
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertCount;
	}
	public int checkDuplicate(EmployeeSkill addEmployeeSkill)
	{
		int recordCount = 0;
		PreparedStatement ps_select=null;
		try
		{
			ps_select = dbCon.prepareStatement("select empSkillId from EmployeeSkill where employeeId=? and skillID=? and raterId=?");
			ps_select.setString(1, addEmployeeSkill.getEmployeeID());
			ps_select.setString(2, addEmployeeSkill.getSkill());
			ps_select.setString(3, addEmployeeSkill.getRaterID());
			ResultSet rs = ps_select.executeQuery();
			while(rs.next())
			{
				recordCount++;
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recordCount;
	}
	public int deleteEmployeeSkill(EmployeeSkill foundEmployeeSkill)
	{
		int deleteCount=0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement
					("delete from EmployeeSkill where empSkillID = ?");
			ps.setShort(1, foundEmployeeSkill.getEmpSkillID());
			deleteCount = ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteCount;
	}
	public String getErrorMsg()
	{
		return this.errorMsg;
	}
}
