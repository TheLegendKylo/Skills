package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.Server.Employee.Capability;


public class CapabilityDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static ArrayList<Capability> capabilityList = null;
	
	public CapabilityDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = myDBCon.getDBCon();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from SkillsDimension");
			while(rs.next())
			{
				short id = rs.getShort("skillDimensionID");
				String name = rs.getString("dimensionName");
				String description = rs.getString("dimensionDescription");
				capabilityList.add(new Capability(id, name, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<Capability> getCapabilityList()
	{
		return capabilityList;
	}
}
