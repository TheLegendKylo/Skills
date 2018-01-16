package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityLevel;


public class CapabilityLevelDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static ArrayList<CapabilityLevel> capabilityLevelList = null;
	
	public CapabilityLevelDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = myDBCon.getDBCon();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from SkillsDimension");
			while(rs.next())
			{
				short capabilityId = rs.getShort("dimensionId");
				short skillLevel = rs.getShort("skillLevel");
				String description = rs.getString("description");
				capabilityLevelList.add(new CapabilityLevel(capabilityId, skillLevel, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<CapabilityLevel> getCapabilityLevelList()
	{
		return capabilityLevelList;
	}
}
