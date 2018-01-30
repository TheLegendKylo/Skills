package vzap.JunitTests;

import static org.junit.Assert.*;
import org.junit.Test;

import vzap.phoenix.DAO.MyDBCon;
import vzap.phoenix.Server.Employee.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Skill;


public class SkillDAO_TTest
{
	private MyDBCon myDBCon;
	private Connection dbCon;
	static ArrayList<Skill> skillList = null;

	@Test
	public void testSkillDAO() throws Exception
	{
		dbCon = MyDBCon.getDBCon();
		
//  insert some skill records into the database to enable testing 
		
		PreparedStatement ps = dbCon.prepareStatement("insert into Skills values(null,?)");
		ps.setString(1,"zzzz");
		ps.executeUpdate();
		ps = dbCon.prepareStatement("insert into Skills values(null,?)");
		ps.setString(1,"yyyy");
		ps.executeUpdate();
		ps = dbCon.prepareStatement("insert into Skills values(null,?)");
		ps.setString(1,"wwww");
		ps.executeUpdate();

		skillList = new ArrayList<Skill>();
		Statement stat=dbCon.createStatement();
		ResultSet rs = stat.executeQuery("select * from Skills order by skillId");
		System.out.println("ResultSet Size: "+rs.getFetchSize());
		int rsCount = 0;
		while(rs.next())
		{
			int skillId = rs.getInt("skillId");
			String skillDescription = rs.getString("skillDescription");
			skillList.add(new Skill(skillId, skillDescription));					
		}
		assertTrue(rsCount == 3);
		
//   delete the three records just added
		ps = dbCon.prepareStatement("delete from Skills where skillId = 'zzzz'");
		ps.executeUpdate();
		ps = dbCon.prepareStatement("delete from Skills where skillId = 'yyyy'");
		ps.executeUpdate();
		ps = dbCon.prepareStatement("delete from Skills where skillId = 'wwww'");
		ps.executeUpdate();		

	}

	@Test
	public void testAddSkillString()  throws Exception
	{
		PreparedStatement ps;

		ps = dbCon.prepareStatement("insert into skills values(null,?)");
		ps.setString(1,"xxxx");
		ps.executeUpdate();

		ps = dbCon.prepareStatement("select skillId from skills where skillDescription = ?");
		ps.setString(1,"xxxx");
		ResultSet rs = ps.executeQuery();
		short skillID;
		String skillDescription = null;
		while(rs.next())
		{
			skillID = rs.getShort("skillId");
			skillDescription = rs.getString("skillDescription");
			skillList.add(new Skill(skillID, "xxxx"));	
		}
		assertTrue("xxxx".equals(skillDescription));
		
//   delete the record jsut added
		ps = dbCon.prepareStatement("delete from skills where skillDescription = ?)");
		ps.setString(1,"xxxx");
		ps.executeUpdate();
	}

	@Test
	public void testSearchSkillString() throws Exception
	{
//   insert records into database to enable testing
		PreparedStatement ps = dbCon.prepareStatement("insert into Skills values(null,?)");
		ps.setString(1,"zzzz");
		ps.executeUpdate();
		
		short skillID=0;
		String skillDescription = null;
		ps = dbCon.prepareStatement("select skillId from skills where skillDescription = ?");
		ps.setString(1,"zzzz");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			skillDescription = rs.getString("skillDescription");
		}
		assertTrue("zzzz".equals(skillDescription));
		
//  delete record jsut added 
		ps = dbCon.prepareStatement("delete Skills where skillDescription = ?");
		ps.setString(1,"zzzz");
		ps.executeUpdate();		
		
	}

	@Test
	public void testGetSkillList() throws Exception
	{

	}

}
