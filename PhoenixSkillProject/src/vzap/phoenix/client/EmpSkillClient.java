package vzap.phoenix.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Hobby;
import vzap.phoenix.Server.Employee.Skill;
import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.CapabilityRating;
import vzap.phoenix.Server.Employee.Level;
/*
 * EmpSkillClient used to create connection with server and facilitate all communication with EmpSkillServer class
 */
//*
public class EmpSkillClient
{
	static Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private String outMessage = null;
	private String inMessage = null;
	
	private Employee employee = null;
	private ArrayList<Skill> skillList = null;
	private Vector<Hobby> hobbyList = null;
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
		if(socket==null) //ensure that only one instance of socket is created
		{
			try
			{
				socket = new Socket("localhost", 10002);
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
	}
	/*
	 * loginEmployee method will accept String employeeId and String password
	 * if login is successful, String message == "Login Successful" will be returned by server.  call method getLoginEmployee to get employee object
	 * if login is unsuccessful, String message with detail of error message will be returned
	 * 
	 * Once login is successful the following methods must immediately be called to obtain static system information:
	 * 
	 */
	public short loginEmployee(String employeeID, String password)
	{
		this.writeOutMessage("loginEmployee");
		this.writeOutMessage(employeeID);
		this.writeOutMessage(password);
		try
		{
			inMessage = (String)ois.readObject();
		} catch (IOException | ClassNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		short errorCode=0;
		try
		{
			errorCode = (Short)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorCode;
	}
	/*
	 * returns object of the logged-in employee (Class Employee)
	 */
	public Employee getLogonEmployee()
	{
		this.writeOutMessage("getLogonEmployee");
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
		this.writeOutMessage("getSkillList");
		try
		{
			skillList = (ArrayList<Skill>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client-: Number of Skills: "+skillList.size());
		return skillList;
	}
	/*
	 * Will Add a new Skill to the SkillTable
	 * Will return the skillID generated
	 */
	public short addSkill(String description)
	{
		this.writeOutMessage("addSkill");
		this.writeOutMessage(description);
		short skillID=0;
		try
		{
			skillID = (Short)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Skill Created: "+skillID);
		return skillID;
	}
	/*
	 * Should be called as soon as login is successful
	 * Will return Vector of all Hobbies on the system
	 */
	@SuppressWarnings("unchecked")
	public Vector<Hobby> getHobbyList()
	{
		this.writeOutMessage("getHobbyList");
		try
		{
			hobbyList = (Vector<Hobby>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Skills: "+hobbyList.size());
		return hobbyList;
	}
	/*
	 * Will Add a new Hobby to the HobbyTable
	 * Will return the hobbyID generated
	 */
	public short addHobby(String description)
	{
		this.writeOutMessage("addHobby");
		this.writeOutMessage(description);

		short hobbyID=0;
		try
		{
			hobbyID = (Short)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Hobby Created: "+hobbyID);
		return hobbyID;
	}
	/*
	 * Should be called as soon as login is successful
	 * Will return ArrayList of all Skill Levels on the system
	 */
	public ArrayList<Level> getLevelList()
	{
		this.writeOutMessage("getLevelList");
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
		this.writeOutMessage("getEmpSkillList");
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
		this.writeOutMessage("getCapabilityList");
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
		this.writeOutMessage("getCapabilityRatingList");
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
		this.writeOutMessage("registerEmployee");
		try
		{
			oos.writeObject(newEmployee);
			oos.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean registerSuccessfull = false;
		try
		{
			registerSuccessfull = (boolean)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return registerSuccessfull;
	}
	/*
	 * Creates a new Skill against an Employee i.e. EmployeeSkill
	 * Takes in an object of EmployeeSkill and returns a boolean
	 * true=success ... false = failed
	 */
	public boolean updateEmployee(Employee updateEmployee)
	{
		this.writeOutMessage("updateEmployee");
		System.out.println("Client: updateEmployee - No of Hobbies: "+updateEmployee.getEmpHobbies().size());
		try
		{
			oos.writeObject(updateEmployee);
			oos.writeObject(updateEmployee.getEmpHobbies().size());
			for (int i = 0; i < updateEmployee.getEmpHobbies().size(); i++)
			{
				oos.writeObject(updateEmployee.getEmpHobbies().get(i));
			}
			oos.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean updateSuccessfull = false;
System.out.println("Client: waiting for server");
		try
		{
			updateSuccessfull = (boolean)ois.readObject();
System.out.println("Client: after read from server: "+updateSuccessfull);
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateSuccessfull;
	}
	/*
	 * Searches the database for Employees that meet the criteria of parameter passed
	 * Will return ArrayList of all Employees that meet the criteria of parameter passed
	 * The ArrayList will contain objects of the class Employee
	 */
	public ArrayList<Employee> searchEmployee(String searchCriteria)
	{
		if(searchCriteria==null)
		{
			return null;
		}
		
		this.writeOutMessage("searchEmployee");
		this.writeOutMessage(searchCriteria);
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		try
		{
			employeeList = (ArrayList<Employee>)ois.readObject();
			
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Employees: "+employeeList.size());
		return employeeList;
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkill(String searchCriteria)
	{
		if(searchCriteria==null)
		{
			return null;
		}
		
		this.writeOutMessage("searchEmployeeSkill");
		this.writeOutMessage(searchCriteria);
		ArrayList<EmployeeSkill> employeeSkillList = new ArrayList<EmployeeSkill>();
		try
		{
			employeeSkillList = (ArrayList<EmployeeSkill>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Employee Skills: "+employeeSkillList.size());
		return employeeSkillList;
	}	
	public ArrayList<EmployeeSkill> searchEmployeeSkillByRaterID(String raterID)
	{
		if(raterID==null)
		{
			return null;
		}
		
		this.writeOutMessage("searchEmployeeSkillByRaterID");
		this.writeOutMessage(raterID);
		ArrayList<EmployeeSkill> employeeSkillList = new ArrayList<EmployeeSkill>();
		try
		{
			employeeSkillList = (ArrayList<EmployeeSkill>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Employee Skills: "+employeeSkillList.size());
		return employeeSkillList;
	}	

	public boolean addEmployeeSkill(EmployeeSkill addEmployeeSkill)
	{
		this.writeOutMessage("addEmployeeSkill");
		try
		{
			oos.writeObject(addEmployeeSkill);
			oos.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean addSuccessfull = false;
		try
		{
			addSuccessfull = (boolean)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addSuccessfull;
	}
	public boolean nominateRater(EmployeeSkill nominateRater)
	{
		this.writeOutMessage("nominateRater");
		try
		{
			oos.writeObject(nominateRater);
			oos.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean nominateSuccessfull = false;
		try
		{
			nominateSuccessfull = (boolean)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nominateSuccessfull;
	}
	public boolean rateEmployeeSkill(EmployeeSkill rateEmployeeSkill)
	{
		this.writeOutMessage("rateEmployeeSkill");
		try
		{
			oos.writeObject(rateEmployeeSkill);
			oos.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
System.out.println("rateArray: "+rateEmployeeSkill.getCapabilityList().size());		
		boolean ratingSuccessfull = false;
		try
		{
			ratingSuccessfull = (boolean)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("EmpSkillClient printing ratingSuccessful..." + ratingSuccessfull);
		return ratingSuccessfull;
	}
	public EmployeeSkill searchEmployeeSkill(String employeeID, int skillID, String raterID)
	{
		this.writeOutMessage("searchEmployeeSkill");
		this.writeOutMessage(employeeID);
		this.writeOutMessage(raterID);
		try
		{
			oos.writeObject(new Integer(skillID));
			oos.flush();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EmployeeSkill empSkill = new EmployeeSkill();
		try
		{
			empSkill = (EmployeeSkill)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkill;
	}
	public ArrayList<EmployeeSkill> searchEmployeeSkill(int skillID)
	{
		this.writeOutMessage("searchEmployeeSkillBySkillID");
		try
		{
			oos.writeObject(new Integer(skillID));
			oos.flush();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<EmployeeSkill> empSkillList = new ArrayList<EmployeeSkill>();
		try
		{
			empSkillList = (ArrayList<EmployeeSkill>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkillList;
	}
	public ArrayList<EmployeeSkill> getEmpSkillByEmpID(String employeeID)
	{
		this.writeOutMessage("getEmpSkillByEmpID");
		this.writeOutMessage(employeeID);
		ArrayList<EmployeeSkill> empSkillList = new ArrayList<EmployeeSkill>();
		try
		{
			empSkillList = (ArrayList<EmployeeSkill>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empSkillList;
	}
	public boolean updateRatingStatus(int skillID, String raterID)
	{
		this.writeOutMessage("updateRating");
		this.writeOutMessage(raterID);
		try
		{
			oos.writeObject(new Integer(skillID));
			oos.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean updateSuccessfull = false;
System.out.println("Client: waiting for server");
		try
		{
			updateSuccessfull = (boolean)ois.readObject();
System.out.println("Client: after read from server: "+updateSuccessfull);
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateSuccessfull;
	}
	public ArrayList<Employee> searchEmployeeHobby(short hobbyID)
	{
		if(hobbyID==0)
		{
			return null;
		}
		
		this.writeOutMessage("searchEmployeeHobby");
		try
		{
			oos.writeObject(new Short(hobbyID));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		try
		{
			employeeList = (ArrayList<Employee>)ois.readObject();
			
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of Employees: "+employeeList.size());
		return employeeList;
	}
	public short getErrorCode()
	{
		this.writeOutMessage("getErrorCode");
		
		short errorCode =0;
		try
		{
			errorCode = (short)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorCode;
	}
	public String getErrorMsg()
	{
		this.writeOutMessage("getErrorMsg");
		
		String errorMsg = this.readObject();
		return errorMsg;
	}
	public void writeOutMessage(String outMessage)
	{
System.out.println("XXXXClient: sending message:"+outMessage);
		try
		{
			oos.writeObject(outMessage);
			oos.flush();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public String readObject()
	{
		try
		{
			inMessage=(String)ois.readObject();
		} catch (ClassNotFoundException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inMessage;
	}
	public void closeConnections()
	{
		try
		{
			this.writeOutMessage("Quit");
			socket.close();
			oos.close();
			ois.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
