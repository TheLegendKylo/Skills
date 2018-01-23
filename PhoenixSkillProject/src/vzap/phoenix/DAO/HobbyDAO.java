package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	public short addHobby(String description)
	{
		short hobbyID=0;
		hobbyID = this.searchHobby(description);
		if(hobbyID!=0)
		{
			return hobbyID;
		}
		PreparedStatement ps;
		try
		{
			ps = dbCon.prepareStatement("insert into hobby values(null,?)");
			ps.setString(1,description);
			ps.executeUpdate();
			
			hobbyID = this.searchHobby(description);
			if(hobbyID!=0)
			{
				System.out.println("hobbyID: "+hobbyID+" - "+description);
				hobbyList.add(new Hobby(hobbyID, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hobbyID;
	}
	public short searchHobby(String description)
	{
		short hobbyID=0;
		PreparedStatement ps;
		try
		{
			ps = dbCon.prepareStatement("select hobbyId from hobby where hobbyDescription = ?");
			ps.setString(1,description);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				hobbyID = rs.getShort("hobbyId");
				System.out.println("Hobby Search Result - hobbyID: "+hobbyID+" - "+description);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hobbyID;
		
	}
	public static Vector<Hobby> getHobbyList()
	{
		return hobbyList;
	}
}
