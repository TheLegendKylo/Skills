package vzap.phoenix.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Skill;
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
			ResultSet rs = stat.executeQuery("select * from Skills order by skillId");
			System.out.println("ResultSet Size: "+rs.getFetchSize());
			while(rs.next())
			{
				int skillId = rs.getInt("skillId");
				String skillDescription = rs.getString("skillDescription");
				skillList.add(new Skill(skillId, skillDescription));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public short addSkill(String description)
	{
		short skillID=0;
		skillID = this.searchSkill(description);
		if(skillID!=0)
		{
			return skillID;
		}
		PreparedStatement ps;
		try
		{
			ps = dbCon.prepareStatement("insert into skills values(null,?)");
			ps.setString(1,description);
			ps.executeUpdate();

			skillID = this.searchSkill(description);
			if(skillID!=0)
			{
				System.out.println("Added skillID: "+skillID+" - "+description);
				skillList.add(new Skill(skillID, description));	
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Returning skillID: "+skillID+" - "+description);
		return skillID;
	}
	public short searchSkill(String description)
	{
		short skillID=0;
		PreparedStatement ps;
		try
		{
			ps = dbCon.prepareStatement("select skillId from skills where skillDescription = ?");
			ps.setString(1,description);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				skillID = rs.getShort("skillId");
				System.out.println("Skill Search Result - skillID: "+skillID+" - "+description);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return skillID;
		
	}
	public static ArrayList<Skill> getSkillList()
	{
		return skillList;
	}
}
