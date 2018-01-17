package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Skill;


public class HobbyDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static Vector<Hobby> hobbyList = null;
	
	public HobbyDAO()
	{
		dbCon = MyDBCon.getDBCon();
		try
		{
			hobbyList = new Vector<Hobby>();
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from hobby");
			System.out.println("ResultSet Size: "+rs.getFetchSize());
			while(rs.next())
			{
				short hobbyId = rs.getShort("hobbyId");
				String hobbyDescription = rs.getString("hobbyDescription");
				System.out.println("hobbyID: "+hobbyId+" - "+hobbyDescription);
				hobbyList.add(new Hobby(hobbyId, hobbyDescription));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Vector<Hobby> getHobbyList()
	{
		return hobbyList;
	}
}
