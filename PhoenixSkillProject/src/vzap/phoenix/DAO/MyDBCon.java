package vzap.phoenix.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyDBCon
{
	private File inputFile = null;
	private Properties dbProperties;
	static Connection dbCon;
	
	String driver, url, user, passwd;
	
	public MyDBCon()
	{
		if(dbCon==null)
		{
			dbProperties = new Properties();
			InputStream is = null;
			
			try
			{
				inputFile = new File("Resources/Properties");
				is = new FileInputStream(inputFile);
				dbProperties.load(is);
				is.close();
	
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			driver = dbProperties.getProperty("DB_Driver");
			url = dbProperties.getProperty("url");
            user = dbProperties.getProperty("user");
            passwd = dbProperties.getProperty("password");
            
			this.addConnection();
		}
	}
	public void addConnection()
	{
		try
		{
			Class.forName(driver);
			dbCon = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connected to DB");
		} catch (ClassNotFoundException | SQLException e){
			System.out.println("Eish, no driver found:");
			e.printStackTrace();
		}
	
	}
	static Connection getDBCon()
	{
		return dbCon;
	}
	public void closeDBCon()
	{
		try
		{
			dbCon.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
