package vzap.phoenix.Server.Employee;

public class EmployeeSkillRating
{
	private short empSkillID = 0, capabilityID=0, rating=0;
	
	public EmployeeSkillRating(short empSkillID, short capabilityID, short rating)
	{
		this.empSkillID = empSkillID;
		this.capabilityID = capabilityID;
		this.rating = rating;
	}

	public short getEmpSkillID()
	{
		return empSkillID;
	}

	public void setEmpSkillID(short empSkillID)
	{
		this.empSkillID = empSkillID;
	}

	public short getCapabilityID()
	{
		return capabilityID;
	}

	public void setCapabilityID(short capabilityID)
	{
		this.capabilityID = capabilityID;
	}

	public short getRating()
	{
		return rating;
	}

	public void setRating(short rating)
	{
		this.rating = rating;
	}
}
