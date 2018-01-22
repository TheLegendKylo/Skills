package vzap.phoenix.Server.Employee;

import java.io.Serializable;
import java.util.Date;

public class EmployeeSkill implements Serializable
{
	private String employeeID = null, raterID = null;
	private int skillID = 0;
	private short empSkillID=0, status = 0;
	private Date ratedDate, createdDate;
	private String coachingAvailability;
	private String comment;
	private short[] capabilityID, rating;	
	
	public EmployeeSkill(String employeeID, int skillID, 
			String raterID, Date createdDate)
	{
		this.employeeID = employeeID;
		this.skillID = skillID;
		this.raterID = raterID;
		this.createdDate = createdDate;
	}

	public EmployeeSkill()
	{
		
	}
	public short getEmpSkillID()
	{
		return this.empSkillID;
	}
	public String getEmployeeID()
	{
		return this.employeeID;
	}
	public int getSkillID()
	{
		return skillID;
	}
	public String getRaterID()
	{
		return this.raterID;
	}
	public short[] getCapabilityID()
	{
		return capabilityID;
	}
	public short[] getRating()
	{
		return rating;
	}

	public Date getRatedDate()
	{
		return this.ratedDate;
	}
	public short getStatus()
	{
		return this.status;
	}

	public Date getCreatedDate()
	{
		return this.createdDate;
	}
	public String getComment()
	{
		return this.comment;
	}
	public String getCoachingAvailability()
	{
		return this.coachingAvailability;
	}

	public void setEmpSkillID(short empSkillID)
	{
		this.empSkillID = empSkillID;
	}
	public void setEmployeeID(String employeeID)
	{
		this.employeeID = employeeID;
	}

	public void setSkillID(int skillID)
	{
		this.skillID = skillID;
	}

	public void setRaterID(String raterID)
	{
		this.raterID = raterID;
	}
	public void setRatedDate(Date ratingDate)
	{
		this.ratedDate = ratingDate;
	}
	public void setStatus(short status)
	{
		this.status = status;
	}
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public void setCoachingAvailability(String coachingAvailability)
	{
		this.coachingAvailability = coachingAvailability;
	}
	public void setCapability(short[] capabilityID)
	{
		this.capabilityID = capabilityID;
	}
	public void setRating(short[] rating)
	{
		this.rating = rating;
	}
	
}
