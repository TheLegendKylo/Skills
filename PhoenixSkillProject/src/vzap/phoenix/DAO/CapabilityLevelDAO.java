package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;


public class CapabilityLevelDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static ArrayList<CapabilityRating> capabilityLevelList = null;
	
	public CapabilityLevelDAO()
	{
		dbCon = MyDBCon.getDBCon();
		capabilityLevelList = new ArrayList<CapabilityRating>();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from capabilityrating");
			while(rs.next())
			{
				short capabilityId = rs.getShort("capabilityId");
				short rating = rs.getShort("rating");
				String description = rs.getString("description");
				capabilityLevelList.add(new CapabilityRating(capabilityId, rating, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<CapabilityRating> getCapabilityLevelList()
	{
		return capabilityLevelList;
	}
}
