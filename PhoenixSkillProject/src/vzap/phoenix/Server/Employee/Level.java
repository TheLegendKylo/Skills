package vzap.phoenix.Server.Employee;

import java.io.Serializable;

public class Level implements Serializable
{
	private short rate = 0;
	private String name = null, description = null;
	
	public Level(short rate, String name, String description)
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
