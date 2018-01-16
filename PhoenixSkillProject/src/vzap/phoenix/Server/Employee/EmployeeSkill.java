package vzap.phoenix.Server.Employee;

import java.util.Date;

public class EmployeeSkill
{
	private String employeeID = null, skill = null, rateeID = null;
	private short capabilityID = 0, status = 0, rating = 0;
	private Date ratingDate;
	
	public EmployeeSkill(String employeeID, String skill, short capabilityID,
			String rateeID, short rating)
	{
		this.employeeID = employeeID;
		this.skill = skill;
		this.capabilityID = capabilityID;
		this.skill = skill;
		this.rateeID = rateeID;
		this.rating = rating;
	}

	public String getemployeeID()
	{
		return this.employeeID;
	}
	public String getSkill()
	{
		return skill;
	}
	public short getCapabilityID()
	{
		return capabilityID;
	}
	public String getRateeID()
	{
		return this.rateeID;
	}
	public short getRating()
	{
		return rating;
	}

	public String getEmployeeID()
	{
		return employeeID;
	}
	public Date getRatingDate()
	{
		return this.ratingDate;
	}
	public short getStatus()
	{
		return this.status;
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
		this.rateeID = rateeID;
	}
	public void setRatingDate(Date ratingDate)
	{
		this.ratingDate = ratingDate;
	}
	public void setStatus(short status)
	{
		this.status = status;
	}
	public void setCapabilityID(short capabilityID)
	{
		this.capabilityID = capabilityID;
	}
	public void setRating(short rating)
	{
		this.rating = rating;
	}
	
}
