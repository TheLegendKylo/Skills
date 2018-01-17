package vzap.phoenix.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.SkillStage;


public class SkillStageDAO
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static ArrayList<SkillStage> skillStageList = null;
	
	public SkillStageDAO()
	{
		myDBCon = new MyDBCon();
		dbCon = myDBCon.getDBCon();
		try
		{
			Statement stat=dbCon.createStatement();
			ResultSet rs = stat.executeQuery("select * from Skills");
			while(rs.next())
			{
				short rate = rs.getShort("rate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				skillStageList.add(new SkillStage(rate, name, description));					
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<SkillStage> getSkillStageList()
	{
		return skillStageList;
	}
}
