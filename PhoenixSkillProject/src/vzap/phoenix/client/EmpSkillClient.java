package vzap.phoenix.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Level;
/*
 * EmpSkillClient used to create connection with server and facilitate all communication with EmpSkillServer class
 */
public class EmpSkillClient
{
	private Socket socket;
	private BufferedReader br;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private PrintWriter pw;
	private FileInputStream fis;
	private ObjectInputStream ois;
	
	private String outMessage = null;
	private String inMessage = null;
	
	private Employee employee = null;
	private ArrayList<Skill> skillList = null;
	private ArrayList<Level> levelList = null;
	private ArrayList<EmployeeSkill> empSkillList = null;
	private ArrayList<Capability> capabilityList = null;
	private ArrayList<CapabilityRating> capabilityRatingList = null;

	/*
	 * Constructor will setup socket connection to the EmpSkillServer
	 * and initiate Writer/Reader/Stream classes
	 */
	public EmpSkillClient()
	{
		try
		{
			socket = new Socket("localhost", 10002);
			pw = new PrintWriter(socket.getOutputStream(),true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * loginEmployee method will accept String employeeId and String password
	 * if login is successful, String message == "Login Successful" will be returned by server.  call method getLoginEmployee to get employee object
	 * if login is unsuccessful, String message with detail of error message will be returned
	 * 
	 * Once login is successfull the following methods must immediately be called to obtain static system information:
	 * 
	 */
	public String loginEmployee(String employeeID, String password)
	{
		outMessage = "loginEmployee";
		pw.println(outMessage);
		pw.flush();
		outMessage = employeeID;
System.out.println("OutMessage: "+outMessage);
		pw.println(outMessage);
		pw.flush();
		outMessage = password;
		pw.println(outMessage);
		pw.flush();
		
		try
		{
			inMessage = br.readLine();
			System.out.println("EmpSkillClient.inMessage: "+inMessage);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inMessage;
		
	}
	/*
	 * returns object of the logged-in employee (Class Employee)
	 */
	public Employee getLogonEmployee()
	{
		outMessage = "getLogonEmployee";
		pw.println(outMessage);
		pw.flush();
		try
		{
			employee = (Employee)ois.readObject();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}
	/*
	 * Should be called as soon as login is successful
	 * Will return ArrayList of all Skills on the system
	 */
	public ArrayList<Skill> getSkillList()
	{
		outMessage = "getSkillList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			skillList = (ArrayList<Skill>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Skills: "+skillList.size());
		return skillList;
	}
	/*
	 * Should be called as soon as login is successful
	 * Will return ArrayList of all Skill Levels on the system
	 */
	public ArrayList<Level> getLevelList()
	{
		outMessage = "getLevelList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			levelList = (ArrayList<Level>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Skill Levels: "+levelList.size());
		return levelList;
	}
	/*
	 * Should be called as soon as login is successful
	 * Will return ArrayList of all Skills for the Logged-In Employee on the system
	 * The ArrayList will contain objects of the class EmployeeSkill
	 */
	public ArrayList<EmployeeSkill> getEmpSkillList()
	{
		outMessage = "getEmpSkillList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			empSkillList = (ArrayList<EmployeeSkill>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of EmployeeSkills: "+empSkillList.size());
		return empSkillList;
	}
	/*
	 * Should be called as soon as login is successful
	 * Will return ArrayList of all static Capabilities (eg Knowledge, Autonomy etc) on the system
	 * The ArrayList will contain objects of the class Capability
	 */
	public ArrayList<Capability> getCapabilityList()
	{
		outMessage = "getCapabilityList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			capabilityList = (ArrayList<Capability>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Capabilities: "+capabilityList.size());
		return capabilityList;
	}
	/*
	 * Should be called as soon as login is successful
	 * Will return ArrayList of all static CapabilityRatings (eg Novice, Expert etc per Capability) on the system
	 * The ArrayList will contain objects of the class CapabilityRating
	 */
	public ArrayList<CapabilityRating> getCapabilityRatingList()
	{
		outMessage = "getCapabilityRatingList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			capabilityRatingList = (ArrayList<CapabilityRating>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of capabilityRatings: "+capabilityRatingList.size());
		return capabilityRatingList;
	}
	/*
	 * This method will register a new employee on the system add add the employee to the database
	 * Will return a boolean message of "true" if the registration was successful
	 */
	public boolean registerEmployee(Employee newEmployee)
	{
		outMessage = "registerEmployee";
		pw.println(outMessage);
		pw.flush();
		try
		{
			oos.writeObject(newEmployee);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean registerSuccessfull = false;
		try
		{
			registerSuccessfull = ois.readBoolean();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return registerSuccessfull;
	}
	public boolean updateEmployee(Employee updateEmployee)
	{
		outMessage = "updateEmployee";
		pw.println(outMessage);
		pw.flush();
		try
		{
			oos.writeObject(updateEmployee);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean updateSuccessfull = false;
		try
		{
			updateSuccessfull = ois.readBoolean();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateSuccessfull;
	}
	public boolean nominateRater(EmployeeSkill nominateRater)
	{
		outMessage = "nominateRater";
		pw.println(outMessage);
		pw.flush();
		try
		{
			oos.writeObject(nominateRater);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean nominateSuccessfull = false;
		try
		{
			nominateSuccessfull = ois.readBoolean();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nominateSuccessfull;
	}
	public boolean rateEmployeeSkill(EmployeeSkill rateEmployeeSkill)
	{
		outMessage = "rateEmployeeSkill";
		pw.println(outMessage);
		pw.flush();
		try
		{
			oos.writeObject(rateEmployeeSkill);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean ratingSuccessfull = false;
		try
		{
			ratingSuccessfull = ois.readBoolean();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ratingSuccessfull;
	}
	public void closeConnections()
	{
		try
		{
			outMessage = "Quit";
			pw.println(outMessage);
			pw.flush();
			socket.close();
			br.close();
			oos.close();
			ois.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
