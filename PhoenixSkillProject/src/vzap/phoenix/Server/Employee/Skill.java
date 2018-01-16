package vzap.phoenix.Server.Employee;

public class Skill
{
	private String name = null, description = null;
	
	public Skill(String name, String description)
	{
		this.name = name;
		this.description = description;
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
