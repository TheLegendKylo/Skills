package vzap.phoenix.DAO;

import java.sql.Connection;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.EmployeeSkillRating;

public class EmployeeSkillDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	private ArrayList<EmployeeSkill> empSkillList = null;
	private ArrayList<EmployeeSkillRating> empSkillRatingList = null;

	private short errorCode;	
	private String errorMsg;
	
	public EmployeeSkillDAO(String employeeID)
	{
		dbCon = MyDBCon.getDBCon();
		empSkillList = new ArrayList<EmployeeSkill>();
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select * from employeeskills where employeeId=?");
			ps.setString(1, employeeID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				short empSkillID = rs.getShort("empSkillId");
				int skillID = rs.getInt("skillId");
				String raterID = rs.getString("raterID");
				String coachingAvailability = rs.getString("coachingAvailability");
				String comment = rs.getString("comment");
				Date ratedDate = rs.getDate("ratedDate");
				Date createDate = rs.getDate("createdDate");
				empSkillList.add(new EmployeeSkill(employeeID, skillID, 
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
			ps = dbCon.prepareStatement("select * from employeeSkillsRating where empSkillID=? order by capabilityId");
			ps.setShort(1, empSkillID);
			ResultSet rs = ps.executeQuery();
			int resultCount = 0;
			if (rs.last()) 
			{
				resultCount = rs.getRow();
				  rs.beforeFirst(); 
			}
			short capabilityID[] = new short[resultCount];
			short rating[] = new short[resultCount];
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
		this.errorMsg = "Employee Skill: Select statement failed for EmpSkillID: "+empSkillID;
		}
	}
	public ArrayList<EmployeeSkill> getEmpSkillList()
	{
		return empSkillList;
	}
	public short rateEmployeeSkill(EmployeeSkill rateEmployeeSkill)
	{
		int updateCount=0, ratingsCount=0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("update employeeSkills set ratedDate=?,status=?"
					+"where empSkillID=?");
			ps.setDate(1, (java.sql.Date) rateEmployeeSkill.getRatedDate());
			ps.setShort(2, rateEmployeeSkill.getStatus());
			ps.setShort(3, rateEmployeeSkill.getEmpSkillID());
			updateCount = ps.executeUpdate();
			ratingsCount = this.addEmployeeSkillRating(rateEmployeeSkill);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorMsg = "Employee Skill: Rating failed for Employee SkillID: "+rateEmployeeSkill.getEmpSkillID();
		}
		errorCode = 0;
		return errorCode;
	}
	public int addEmployeeSkillRating(EmployeeSkill rateEmployeeSkill)
	{
		this.errorCode = 0;
		int insertCount=0;
		try
		{
			dbCon.setAutoCommit(false);
			PreparedStatement ps = dbCon.prepareStatement("insert into EmployeeSkillsRating values(?,?,?)");
			  
			for(int i=0; i<= 6;i++){
				ps.setShort(2, rateEmployeeSkill.getCapabilityID()[i]);
				ps.setShort(3, rateEmployeeSkill.getRating()[i]);
				ps.executeUpdate();
				insertCount++; 
			}
			System.out.println("The number of rows inserted: "+ insertCount);
			if(insertCount==rateEmployeeSkill.getCapabilityID().length)
			{
			    dbCon.commit();
			    dbCon.setAutoCommit(true);
			}
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
				this.errorCode = 9;
				return errorCode;
		}
		return this.errorCode;
	}
	public int insertEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		// first check whether a duplicate record already exists
		this.errorCode = 0;
		int insertCount = 0, ratingsCount=0;
		short empSkillID = this.searchEmployeeSkill(addEmployeeSkill);
		if(empSkillID!=0)
		{
			this.errorCode = 1;
			this.errorMsg = "Record already exists";
			System.out.println(this.errorMsg);
			return this.errorCode; //record already exists
		}
		
		PreparedStatement ps = null, ps_select=null;
		try
		{
			ps = dbCon.prepareStatement("insert into EmployeeSkills values(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, null); // auto_generated empSkillId
			ps.setString(2, addEmployeeSkill.getEmployeeID());
			ps.setInt(3, addEmployeeSkill.getSkillID());
			ps.setString(4, addEmployeeSkill.getRaterID());
			ps.setString(5, addEmployeeSkill.getCoachingAvailability());
			ps.setShort(6, addEmployeeSkill.getStatus());
			ps.setString(7, addEmployeeSkill.getComment());
			java.sql.Date ratedDate = new java.sql.Date(addEmployeeSkill.getCreatedDate().getTime());
			ps.setDate(8,  ratedDate);
			java.sql.Date createdDate = new java.sql.Date(addEmployeeSkill.getCreatedDate().getTime());
			ps.setDate(9,  createdDate );
			insertCount = ps.executeUpdate();

			empSkillID = this.searchEmployeeSkill(addEmployeeSkill);
			addEmployeeSkill.setEmpSkillID(empSkillID);

			if(!(addEmployeeSkill.getCapabilityID()==null))
			{
				ratingsCount = this.addEmployeeSkillRating(addEmployeeSkill);				
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorMsg = "Employee Skill: Insert statement failed for EmployeeID: "+addEmployeeSkill.getEmployeeID();
			this.errorCode = 9;
			return this.errorCode; //SQL Error
		}
		return this.errorCode;
	}
	public short searchEmployeeSkill(EmployeeSkill searchEmployeeSkill)
	{
		short empSkillID = 0;
		PreparedStatement ps_select=null;
		try
		{
			ps_select = dbCon.prepareStatement("select empSkillId from EmployeeSkills where employeeId=? and skillId=? and raterId=?");
			ps_select.setString(1, searchEmployeeSkill.getEmployeeID());
			ps_select.setInt(2, searchEmployeeSkill.getSkillID());
			ps_select.setString(3, searchEmployeeSkill.getRaterID());
			ResultSet rs = ps_select.executeQuery();
			while(rs.next())
			{
				empSkillID = rs.getShort("empSkillId");
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkillID;
	}
	public short deleteEmployeeSkill(EmployeeSkill deleteEmployeeSkill)
	{
		this.errorCode = 0;
		int deleteCount=0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement
					("delete from EmployeeSkills where empSkillID = ?");
			ps.setShort(1, deleteEmployeeSkill.getEmpSkillID());
			deleteCount = ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorCode = 9;
			this.errorMsg = "Employee Skill: Delete statement failed for EmpSkillID: "+deleteEmployeeSkill.getEmpSkillID();
			return this.errorCode;
		}
		return this.errorCode;
	}
	public short getErrorCode()
	{
		return this.errorCode;
	}
	public String getErrorMsg()
	{
		return this.errorMsg;
	}
}
