package vzap.JunitTests;

import static org.junit.Assert.*;

import vzap.exceptions.ExceptionTest;
import vzap.phoenix.Server.Employee.Hobby;
import org.junit.Test;

public class HobbyUnitTest
{

	private short setHobbyID = 1;
	
	@Test
	public void TestHobbyShortString()
	{
		Hobby hobby;
		hobby = new Hobby(setHobbyID,"gaming");
		hobby.setHobbyID(setHobbyID);
		hobby.setHobbyDescription("gaming");
		
		assertEquals(setHobbyID,hobby.getHobbyID());
		assertEquals("gaming",hobby.getHobbyDescription());

	}
	
	
	@Test
	public void TestGetHobbyID()
	{
		Hobby hobby;
		hobby = new Hobby(setHobbyID,"gaming");
		hobby.setHobbyID(setHobbyID);
		
		assertEquals(setHobbyID,hobby.getHobbyID());
	}
	

	@Test
	public void TestSetHobbyIDShort()
	{
		Hobby hobby;
		hobby = new Hobby(setHobbyID,"gaming");
		hobby.setHobbyID(setHobbyID);
		
		assertEquals(setHobbyID,hobby.getHobbyID());
	}
	
	@Test
	public void TestGetHobbyDescription()
	{
		Hobby hobby;
		hobby = new Hobby(setHobbyID,"gaming");
		hobby.setHobbyDescription("gaming");
		
		assertEquals("gaming",hobby.getHobbyDescription());
	}
	
	@Test
	public void TestSetHobbyDescriptionString()
	{
		Hobby hobby;
		hobby = new Hobby(setHobbyID,"gaming");
		hobby.setHobbyDescription("gaming");
		
		assertEquals("gaming",hobby.getHobbyDescription());
	}

}
