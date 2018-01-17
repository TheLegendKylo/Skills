package vzap.phoenix.Server.Employee;

import java.io.Serializable;

public class CapabilityRating implements Serializable
{
	private short capabilityID = 0, rating = 0;
	private String description = null;
	
	public CapabilityRating(short dimensionID, short rating, String description)
	{
		this.capabilityID = dimensionID;
		this.rating = rating;
		this.description = description;
	}

	public short getCapabilityID()
	{
		return capabilityID;
	}
	public short getRating()
	{
		return rating;
	}
	
	public String getDescription()
	{
		return description;
	}
	
}
