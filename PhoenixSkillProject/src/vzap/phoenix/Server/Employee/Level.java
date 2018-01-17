package vzap.phoenix.Server.Employee;

public class SkillStage
{
	private short rate = 0;
	private String name = null, description = null;
	
	public SkillStage(short rate, String name, String description)
	{
		this.rate = rate;
		this.name = name;
		this.description = description;
	}

	public short getRate()
	{
		return rate;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}
	
}
