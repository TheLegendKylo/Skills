package vzap.phoenix.Server.Employee;

import java.io.Serializable;

public class Capability implements Serializable
{
	private short id;
	private String name = null, description = null;
	
	public Capability(short id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public short getID()
	{
		return id;
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
