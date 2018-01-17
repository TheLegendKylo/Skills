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
		dbCon = MyDBCon.getDBCon();
		try
		{
			skillList = new ArrayList<Skill>();
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from Skills");
			System.out.println("ResultSet Size: "+rs.getFetchSize());
			while(rs.next())
			{
				int skillId = rs.getInt("skillId");
				String skillDescription = rs.getString("skillDescription");
				System.out.println("SkillID: "+skillId+" - "+skillDescription);
				skillList.add(new Skill(skillId, skillDescription));					
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
