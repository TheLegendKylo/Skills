package vzap.phoenix.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Level;


public class LevelDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static ArrayList<Level> levelList = null;
	
	public LevelDAO()
	{
		dbCon = MyDBCon.getDBCon();
		levelList = new ArrayList<Level>();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from level");
			while(rs.next())
			{
				short rate = rs.getShort("levelNumber");
				String name = rs.getString("DreyfusTitle");
				String description = rs.getString("detailedDescription");
				levelList.add(new Level(rate, name, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<Level> getLevelList()
	{
		return levelList;
	}
}
