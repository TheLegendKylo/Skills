package vzap.phoenix.Server.Employee;

import java.io.Serializable;

public class Hobby implements Serializable
{
	private short hobbyID=0;
	private String hobbyDescription;
	
	public Hobby(short hobbyID, String hobbyDescription)
	{
		this.hobbyID = hobbyID;
		this.hobbyDescription = hobbyDescription;
	}

	public short getHobbyID()
	{
		return hobbyID;
	}

	public void setHobbyID(short hobbyID)
	{
		this.hobbyID = hobbyID;
	}

	public String getHobbyDescription()
	{
		return hobbyDescription;
	}

	public void setHobbyDescription(String hobbyDescription)
	{
		this.hobbyDescription = hobbyDescription;
	}
}
