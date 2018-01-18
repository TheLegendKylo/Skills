package vzap.phoenix.Server.Employee;

import java.io.Serializable;
import java.util.Date;

public class EmployeeSkill implements Serializable
{
	private String employeeID = null, skill = null, raterID = null;
	private short empSkillID=0, status = 0;
	private Date ratedDate, createdDate;
	private String coachingAvailability;
	private String comment;
	private short[] capabilityID, rating;	
	
	public EmployeeSkill(String employeeID, String skill, 
			String raterID, Date createdDate)
	{
		this.employeeID = employeeID;
		this.skill = skill;
		this.raterID = raterID;
		this.createdDate = createdDate;
	}

	public short getEmpSkillID()
	{
		return this.empSkillID;
	}
	public String getEmployeeID()
	{
		return this.employeeID;
	}
	public String getSkill()
	{
		return skill;
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

	public void setSkill(String skill)
	{
		this.skill = skill;
	}

	public void setRater(String rateeID)
	{
		this.raterID = rateeID;
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
