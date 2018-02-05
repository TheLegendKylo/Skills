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
		dbCon = MyDBCon.getDBCon();
		capabilityList = new ArrayList<Capability>();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from capability order by capabilityId");
			while(rs.next())
			{
				short id = rs.getShort("capabilityId");
				String name = rs.getString("capabilityName");
				String description = rs.getString("capabilityDescription");
				capabilityList.add(new Capability(id, name, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
System.out.println("Capability DAO- Build: "+capabilityList.size());
	}
	public static ArrayList<Capability> getCapabilityList()
	{
		System.out.println("Capability DAO- Return: "+capabilityList.size());
		return capabilityList;
	}
}
