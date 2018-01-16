package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Skill;


public class SkillDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static ArrayList<Skill> skillList = null;
	
	public SkillDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = myDBCon.getDBCon();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from Skills");
			while(rs.next())
			{
				String name = rs.getString("name");
				String description = rs.getString("description");
				skillList.add(new Skill(name, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<Skill> getSkillList()
	{
		return skillList;
	}
}
