package vzap.phoenix.DAO;

import java.sql.Connection;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.EmployeeSkill;

public class EmployeeSkillDAO
{
	private Connection dbCon;
	private ArrayList<EmployeeSkill> empSkillList = null;

	private short errorCode;	
	private String errorMsg;
	
	public EmployeeSkillDAO(String employeeID)
	{
		empSkillList = getEmployeeSkill(employeeID);
	}
	public ArrayList<EmployeeSkill> getEmployeeSkill(String employeeID)
	{
		dbCon = MyDBCon.getDBCon();
		empSkillList = new ArrayList<EmployeeSkill>();
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select * from employeeskills where employeeId=?"
					+ " order by skillId, raterId");
			ps.setString(1, employeeID);
			ResultSet rs = ps.executeQuery();
			empSkillList = this.populateEmpSkillList(rs);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorMsg = "Employee Skill: Search failed for EmployeeID: "+employeeID;
		}
		return empSkillList;
	}

	public void getEmpSkillRating(short empSkillID, int idx)
	{
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select * from employeeSkillsRating where empSkillID=? order by capabilityId");
			ps.setShort(1, empSkillID);
			ResultSet rs = ps.executeQuery();
			ArrayList<Short> capabilityIDList = new ArrayList<Short>();
			ArrayList<Short> ratingList = new ArrayList<Short>();
			while(rs.next())
			{
				capabilityIDList.add(rs.getShort("capabilityId"));
				ratingList.add(rs.getShort("rating"));
			}
			empSkillList.get(idx).setCapabilityList(capabilityIDList);
			empSkillList.get(idx).setRatingList(ratingList);
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
System.out.println("Into rateEmployeeSkill for: "+rateEmployeeSkill.getEmployeeID()+" "+rateEmployeeSkill.getEmpSkillID());
		int ratingsCount=0;
		int existingRatings = checkEmpSkillRatingDuplicates(rateEmployeeSkill.getEmpSkillID());
		if(existingRatings>0)
		{
			System.out.println("Rating already exists data provided");
			this.errorCode = 2; //Rating already exists data provided
			this.errorMsg = "Rate Employee: Rating already exists for: "+rateEmployeeSkill.getEmpSkillID();
			return this.errorCode;
		}
		if(rateEmployeeSkill.getCapabilityList()==null)
		{
			System.out.println("No rating data provided");
			this.errorCode = 1; //No rating data provided
			this.errorMsg = "Rate Employee: Rating information is required for: "+rateEmployeeSkill.getEmpSkillID();
			return this.errorCode;
		}
		PreparedStatement ps = null;	
		try
		{
			dbCon.setAutoCommit(false);
			ps = dbCon.prepareStatement("update employeeSkills set ratedDate=?,status=?,coachingAvailability=? "
					+"where empSkillID=?");
			java.sql.Date ratedDate = new java.sql.Date(rateEmployeeSkill.getRatedDate().getTime());
			ps.setDate(1,  ratedDate);
			ps.setShort(2, (short)1);
			ps.setString(3, rateEmployeeSkill.getCoachingAvailability());
			ps.setShort(4, rateEmployeeSkill.getEmpSkillID());
			ps.executeUpdate();
			System.out.println("calling this.addEmployeeSkillRating");
			ratingsCount = this.addEmployeeSkillRating(rateEmployeeSkill);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorMsg = "Employee Skill: Rating failed for Employee SkillID: "+rateEmployeeSkill.getEmpSkillID();
		}
		this.errorCode = 0;
		return this.errorCode;
	}
	public int addEmployeeSkillRating(EmployeeSkill rateEmployeeSkill)
	{
		System.out.println("Into addEmployeeSkillRating");
		this.errorCode = 0;
		int insertCount=0;
		try
		{
			PreparedStatement ps = dbCon.prepareStatement("insert into EmployeeSkillsRating values(null,?,?,?)");
			  
			for(int i=0; i<= 6;i++){
System.out.println("Line:"+i+" "+rateEmployeeSkill.getEmpSkillID()+" "
			+rateEmployeeSkill.getCapabilityList().get(i)+" "+rateEmployeeSkill.getRatingList().get(i));

				ps.setShort(1, rateEmployeeSkill.getEmpSkillID());
				ps.setShort(2, rateEmployeeSkill.getCapabilityList().get(i));
				ps.setShort(3, rateEmployeeSkill.getRatingList().get(i));
				ps.executeUpdate();
				insertCount++; 
			}
			System.out.println("The number of rows inserted: "+ insertCount+" vs object lines"
					+rateEmployeeSkill.getCapabilityList().size());
			if(insertCount==rateEmployeeSkill.getCapabilityList().size())
			{
				System.out.println("Committing");
			    dbCon.commit();
			    dbCon.setAutoCommit(true);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			try
			{
System.out.println("Rolling Back");
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
	public int checkEmpSkillRatingDuplicates(short empSkillID)
	{
		int counter = 0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("select * from employeeSkillsRating where empSkillID=? and status <> 9 order by capabilityId");
			ps.setShort(1, empSkillID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				counter++;
			}
		} catch (SQLException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		this.errorMsg = "Employee Skill: Select statement failed for EmpSkillID: "+empSkillID;
		}
		return counter;
	}
	public int insertEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		// first check whether a duplicate record already exists
		this.errorCode = 0;
		int ratingsCount=0;
		short empSkillID = this.searchEmployeeSkill(addEmployeeSkill);
		if(empSkillID!=0)
		{
			this.errorCode = 1;
			this.errorMsg = "Employee Skill Record already exists";
			System.out.println(this.errorMsg);
			return this.errorCode; //record already exists
		}
		
		PreparedStatement ps = null;
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
			ps.setDate(8,  (java.sql.Date)addEmployeeSkill.getRatedDate());
			java.sql.Date createdDate = new java.sql.Date(addEmployeeSkill.getCreatedDate().getTime());
			ps.setDate(9,  createdDate );
			ps.executeUpdate();

			empSkillID = this.searchEmployeeSkill(addEmployeeSkill);
			addEmployeeSkill.setEmpSkillID(empSkillID);

			if(!(addEmployeeSkill.getCapabilityList()==null))
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
	public int updateEmployeeSkill(EmployeeSkill updEmployeeSkill)
	{
		// first check whether a duplicate record already exists
		this.errorCode = 0;
		int ratingsCount=0;
System.out.println(updEmployeeSkill.getEmployeeID());
		short empSkillID = this.searchEmployeeSkill(updEmployeeSkill);
		if(empSkillID==0)
		{
			this.errorCode = 1;
			this.errorMsg = "Employee Skill Record not found";
			System.out.println(this.errorMsg);
			return this.errorCode; 
		}
		
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement("update EmployeeSkills set status=?,coachingAvailability=?,comment=? where empSkillId=?");
			ps.setShort(1, updEmployeeSkill.getStatus());
			ps.setString(2, updEmployeeSkill.getCoachingAvailability());
			ps.setString(3, updEmployeeSkill.getComment());
			ps.setShort(4, updEmployeeSkill.getEmpSkillID());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.errorMsg = "Employee Skill: Update statement failed for: "+updEmployeeSkill.getEmpSkillID();
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
			ps_select = dbCon.prepareStatement("select empSkillId,status from EmployeeSkills where employeeId=? and status<>9 and skillId=? and raterId=?");
			ps_select.setString(1, searchEmployeeSkill.getEmployeeID());
			ps_select.setInt(2, searchEmployeeSkill.getSkillID());
			ps_select.setString(3, searchEmployeeSkill.getRaterID());
			ResultSet rs = ps_select.executeQuery();
			while(rs.next())
			{
				short status = rs.getShort("status");
				empSkillID = rs.getShort("empSkillId");
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkillID;
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkill(String employeeID, int skillID, String raterID)
	{
		empSkillList = new ArrayList<EmployeeSkill>();
		PreparedStatement ps_select=null;
		try
		{
			if(raterID==null || raterID=="")
			{
				ps_select = dbCon.prepareStatement("select * from EmployeeSkills where employeeId=?"
						+ "and skillId=? and status<>9 order by skillId, raterId");
			} else {
				ps_select = dbCon.prepareStatement("select * from EmployeeSkills where employeeId=?"
						+ "and skillId=? and status<>9 and raterId = ? order by skillId, raterId");
				ps_select.setString(3, raterID);
			}
			ps_select.setString(1, employeeID);
			ps_select.setInt(2, skillID);
			ResultSet rs = ps_select.executeQuery();
			empSkillList = this.populateEmpSkillList(rs);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkillList;
	}
	public ArrayList<EmployeeSkill> populateEmpSkillList (ResultSet rs) throws SQLException
	{
		while(rs.next())
		{
			String employeeID = rs.getString("employeeId");
			short empSkillID = rs.getShort("empSkillId");
			int skillID = rs.getInt("skillId");
			String raterID = rs.getString("raterID");
			String coachingAvailability = rs.getString("coachingAvailability");
			short status = rs.getShort("status");
			if(status==9)
			{
				continue;
			}
			String comment = rs.getString("comment");
			Date ratedDate = rs.getDate("ratedDate");
			Date createDate = rs.getDate("createdDate");
			empSkillList.add(new EmployeeSkill(employeeID, skillID, 
					raterID, createDate));
			int idx = empSkillList.size()-1;
			empSkillList.get(idx).setEmpSkillID(empSkillID);
			empSkillList.get(idx).setCoachingAvailability(coachingAvailability);
			empSkillList.get(idx).setStatus(status);
			empSkillList.get(idx).setComment(comment);
			empSkillList.get(idx).setRatedDate(ratedDate);
			this.getEmpSkillRating(empSkillID, idx);
		}
		return empSkillList;
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkillByID(int skillID)
	{
		PreparedStatement ps_select;
		
		try
		{
			ps_select = dbCon.prepareStatement("select * from EmployeeSkills where skillId = ? and status<>9");
			
			ps_select.setInt(1, skillID);
			
			ResultSet rs = ps_select.executeQuery();
			empSkillList = new ArrayList<EmployeeSkill>(); // gerald made change 
			empSkillList = this.populateEmpSkillList(rs);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkillList;
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkillByRaterID(String raterID)
	{
		PreparedStatement ps_select;
		
		try
		{
			ps_select = dbCon.prepareStatement("select * from EmployeeSkills where raterId = ? and status<>9");
			
			ps_select.setString(1, raterID);
			
			ResultSet rs = ps_select.executeQuery();
			empSkillList = new ArrayList<EmployeeSkill>(); 
			empSkillList = this.populateEmpSkillList(rs);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkillList;
	}
	public short deleteEmployeeSkill(EmployeeSkill deleteEmployeeSkill)
	{
		this.errorCode = 0;
		PreparedStatement ps = null;
		try
		{
			ps = dbCon.prepareStatement
					("delete from EmployeeSkills where empSkillID = ?");
			ps.setShort(1, deleteEmployeeSkill.getEmpSkillID());
			ps.executeUpdate();
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
