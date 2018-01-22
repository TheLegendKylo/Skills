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
System.out.println("Into rateEmployeeSkill");
		int updateCount=0, ratingsCount=0;
		if(!(rateEmployeeSkill.getCapabilityID()==null))
		{
			this.errorCode = 1; //No rating data provided
			this.errorMsg = "Rate Employee: Rating information is required for: "+rateEmployeeSkill.getEmpSkillID();
		}
		PreparedStatement ps = null;	
		try
		{
			dbCon.setAutoCommit(false);
			ps = dbCon.prepareStatement("update employeeSkills set ratedDate=?,status=? "
					+"where empSkillID=?");
			java.sql.Date ratedDate = new java.sql.Date(rateEmployeeSkill.getRatedDate().getTime());
			ps.setDate(1,  ratedDate);
			ps.setShort(2, rateEmployeeSkill.getStatus());
			ps.setShort(3, rateEmployeeSkill.getEmpSkillID());
			updateCount = ps.executeUpdate();
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
System.out.println("Line:"+i+" "+rateEmployeeSkill.getEmpSkillID()+" "+rateEmployeeSkill.getCapabilityID()[i]+" "+rateEmployeeSkill.getRating()[i]);
				ps.setShort(1, rateEmployeeSkill.getEmpSkillID());
				ps.setShort(2, rateEmployeeSkill.getCapabilityID()[i]);
				ps.setShort(3, rateEmployeeSkill.getRating()[i]);
				ps.executeUpdate();
				insertCount++; 
			}
			System.out.println("The number of rows inserted: "+ insertCount+" vs object lines"+rateEmployeeSkill.getCapabilityID().length);
			if(insertCount==rateEmployeeSkill.getCapabilityID().length)
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
	public int insertEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		// first check whether a duplicate record already exists
		this.errorCode = 0;
		int insertCount = 0, ratingsCount=0;
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
	public int updateEmployeeSkill(EmployeeSkill updEmployeeSkill)
	{
		// first check whether a duplicate record already exists
		this.errorCode = 0;
		int updateCount = 0, ratingsCount=0;
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
			ps = dbCon.prepareStatement("update EmployeeSkills set status=?,coachingAvailability=?,comment=? where empSkillId=?)");
			ps.setString(1, updEmployeeSkill.getCoachingAvailability());
			ps.setShort(2, updEmployeeSkill.getStatus());
			ps.setString(3, updEmployeeSkill.getComment());
			ps.setShort(4, updEmployeeSkill.getEmpSkillID());
			updateCount = ps.executeUpdate();
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
	public short searchEmployeeSkill(String employeeID, int skillID, String raterID)
	{
		short empSkillID = 0;
		PreparedStatement ps_select=null;
		try
		{
			ps_select = dbCon.prepareStatement("select empSkillId from EmployeeSkills where employeeId=? and skillId=? and raterId=?");
			ps_select.setString(1, employeeID);
			ps_select.setInt(2, skillID);
			ps_select.setString(3, raterID);
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
	public ArrayList<EmployeeSkill> searchEmployeeSkillByID(String enteredQuery)
	{
		String employeeID;
		PreparedStatement ps_select;
		
		try
		{
			String input = "%" + enteredQuery + "%";
			
			ps_select = dbCon.prepareStatement("select * from EmployeeSkills where skillId like ? ");
			
			ps_select.setString(1, input);
			
			ResultSet rs = ps_select.executeQuery();
			
			while(rs.next())
			{
				
				employeeID = rs.getString("employeeID");
				empSkillList.addAll(this.getEmployeeSkill(employeeID));
			}
			
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
