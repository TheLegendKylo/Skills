package vzap.phoenix.Server.Employee;

public class CapabilityLevel
{
	private short capabilityID = 0, skillLevel = 0;
	private String description = null;
	
	public CapabilityLevel(short dimensionID, short level, String description)
	{
		this.capabilityID = dimensionID;
		this.skillLevel = skillLevel;
		this.description = description;
	}

	public short getDimensionID()
	{
		return capabilityID;
	}
	public short getSkillLevel()
	{
		return skillLevel;
	}
	
	public String getDescription()
	{
		return description;
	}
	
}
