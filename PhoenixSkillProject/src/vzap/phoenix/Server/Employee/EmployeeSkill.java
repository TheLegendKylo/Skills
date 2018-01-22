package vzap.phoenix.Server.Employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EmployeeSkill implements Serializable
{
	private String employeeID = null, raterID = null;
	private int skillID = 0;
	private short empSkillID=0, status = 0;
	private Date ratedDate, createdDate;
	private String coachingAvailability;
	private String comment;
	private ArrayList<Short>capabilityIDList=null, ratingList=null;	
	
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
	public ArrayList<Short> getCapabilityList()
	{
		return capabilityIDList;
	}
	public ArrayList<Short> getRatingList()
	{
		return ratingList;
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
	public void setCapabilityList(ArrayList<Short> capabilityIDList)
	{
		this.capabilityIDList = capabilityIDList;
	}
	public void setRatingList(ArrayList<Short> ratingList)
	{
		this.ratingList = ratingList;
	}
	@Override
	public String toString()
	{
		return "EmployeeSkill [employeeID=" + employeeID + ", raterID=" + raterID + ", skillID=" + skillID
				+ ", empSkillID=" + empSkillID + ", status=" + status + ", ratedDate=" + ratedDate + ", createdDate="
				+ createdDate + ", coachingAvailability=" + coachingAvailability + ", comment=" + comment
				+ ", capabilityIDList=" + capabilityIDList + ", ratingList=" + ratingList+ "]";
	}
}
