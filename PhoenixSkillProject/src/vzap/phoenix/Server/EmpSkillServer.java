package vzap.phoenix.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import vzap.phoenix.Server.Employee.*;

public class EmpSkillServer
{
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private EmpSkillServerThread essThread;
	
	
	public EmpSkillServer()
	{
		try
		{
			serverSocket = new ServerSocket(10002);

		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true)
		{
			try
			{
				System.out.println("Waiting for connection");
				clientSocket = serverSocket.accept();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			essThread = new EmpSkillServerThread(clientSocket);
		}
		
	}
	class EmpSkillServerThread implements Runnable
	{
		private Socket clientSocket;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		private String outMessage = null;
		private String inMessage = null;
		
		private Employee employee = null;
		private EmployeeController empControl;
		private Thread thread = null;
		
		private ArrayList<Skill> skillList = null;
		private Vector<Hobby> hobbyList = null;
		private ArrayList<Level> levelList = null;
		private ArrayList<EmployeeSkill> empSkillList = null;
		private ArrayList<Capability> capabilityList = null;
		private ArrayList<CapabilityRating> capabilityRatingList = null;

		public EmpSkillServerThread(Socket clientSocket)
		{
			this.clientSocket = clientSocket;
			
			try
			{
				oos = new ObjectOutputStream(clientSocket.getOutputStream());
				ois = new ObjectInputStream(clientSocket.getInputStream());
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			thread = new Thread(this);
			thread.start();
			
		}
		public void run()
		{
			boolean exitSession = false;
			while(!exitSession)
			{
				if(!(empControl==null))
				{
					empControl.resetErrorMsg();;					
				}
				System.out.println("Try to read new message");
				
				inMessage= this.readObject();
				System.out.println("Server: In Thread-inMessage: "+inMessage);
				switch (inMessage)
				{
					case "Quit":
					{
						this.closeConnections();
						exitSession = true;
						break;
					}
					case "loginEmployee":
					{
						this.loginEmployee();
						break;
					}
					case "getLogonEmployee":
					{
						this.getLogonEmployee();
						break;
					}
					case "getSkillList":
					{
						this.getSkillList();
						break;
					}
					case "addSkill":
					{
						this.addSkill();
						break;
					}
					case "getHobbyList":
					{
						this.getHobbyList();
						break;
					}
					case "addHobby":
					{
						this.addHobby();
						break;
					}
					case "getLevelList":
					{
						this.getLevelList();
						break;
					}
					case "getEmpSkillList":
					{
						this.getEmpSkillList();
						break;
					}
					case "getEmpSkillByEmpID":
					{
						this.getEmpSkillByEmpID();
						break;
					}
					case "getCapabilityList":
					{
						this.getCapabilityList();
						break;
					}
					case "getCapabilityRatingList":
					{
						this.getCapabilityRatingList();
						break;
					}
					case "searchEmployee":
					{
						this.searchEmployee();
						break;
					}
					case "registerEmployee":
					{
						this.registerEmployee();
						break;
					}
					case "updateEmployee":
					{
						this.updateEmployee();
						break;
					}
					case "addEmployeeSkill":
					{
						this.addEmployeeSkill();
						break;
					}
					case "nominateRater":
					{
						this.nominateRater();
						break;
					}
					case "rateEmployeeSkill":
					{
						this.rateEmployeeSkill();
						break;
					}
					case "searchEmployeeSkill":
					{
						this.searchEmployeeSkill();
						break;
					}
					case "searchEmployeeSkillBySkillID":
					{
						this.searchEmployeeSkillBySkillID();
						break;
					}
					case "searchEmployeeSkillByRaterID":
					{
						this.searchEmployeeSkillByRaterID();
						break;
					}
					case "searchEmployeeHobby":
					{
						this.searchEmployeeHobby();
						break;
					}
					case "getErrorCode":
					{
						this.getErrorCode();
						break;
					}
					case "getErrorMsg":
					{
						this.getErrorMsg();
						break;
					}
				}
			}
		}
		public void loginEmployee()
		{
			String employeeID = null;
			String password = null;
			employeeID= this.readObject();
			password= this.readObject();
			System.out.println("Server: read EmployeeID: "+employeeID);
			empControl = new EmployeeController(employeeID, password);
			employee = empControl.getLogonEmployee();
			System.out.println("Employee login errorCode: "+empControl.getErrorCode());
			outMessage = empControl.getErrorMsg();
			System.out.println("Employee login writing errorMsg: "+outMessage);
			this.writeOutMessage(outMessage);
			try
			{
				short errorCode = empControl.getErrorCode();
				System.out.println("Employee login writing errorCode: "+errorCode);
				oos.writeObject(new Short(errorCode));
//				dos.writeShort(1234);//errorCode);
				System.out.println("Employee login AFTER writing errorCode: "+errorCode);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public void getLogonEmployee()
		{
			try
			{
				oos.writeObject(employee);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getSkillList()
		{
			skillList = EmployeeController.getSkillList();
			System.out.println("Number of Skills: "+skillList.size());
			try
			{
				oos.writeObject(skillList);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void addSkill()
		{
			short skillID = 0;
			String description = this.readObject();
			System.out.println("Server: read Skill Description: "+description);
			skillID = empControl.addSkill(description);
			System.out.println("Server Skill Added: "+skillID);
			try
			{
				oos.writeObject(new Short(skillID));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void getHobbyList()
		{
			hobbyList = EmployeeController.getHobbyList();
			System.out.println("Number of Hobbies: "+hobbyList.size());
			try
			{
				oos.writeObject(hobbyList);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void addHobby()
		{
			short hobbyID = 0;
			String description = this.readObject();
			hobbyID = empControl.addHobby(description);
			System.out.println("Hobby Added: "+hobbyID);
			try
			{
				oos.writeObject(new Short(hobbyID));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void getLevelList()
		{
			levelList = EmployeeController.getLevelList();
System.out.println("Number of Levels: "+levelList.size());
			try
			{
				oos.writeObject(levelList);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getEmpSkillList()
		{
			empSkillList = empControl.getEmpSkillList();
System.out.println("Number of Employee SkillS: "+empSkillList.size());
			try
			{
				oos.writeObject(empSkillList);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getEmpSkillByEmpID()
		{
			this.readObject();
			empSkillList = empControl.getEmpSkillByEmpID(inMessage);
System.out.println("Number of Employee SkillS: "+empSkillList.size());
			try
			{
				oos.writeObject(empSkillList);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getCapabilityList()
		{
			capabilityList = EmployeeController.getCapabilityList();
System.out.println("Number of Capabilities: "+capabilityList.size());
			try
			{
				oos.writeObject(capabilityList);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getCapabilityRatingList()
		{
			capabilityRatingList = EmployeeController.getCapabilityRatingList();
System.out.println("Number of Capability Levels: "+capabilityRatingList.size());
			try
			{
				oos.writeObject(capabilityRatingList);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void registerEmployee()
		{
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
			
			boolean registerSuccess = empControl.registerEmployee(employee);
			try
			{
				oos.writeObject(new Boolean(registerSuccess));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public void updateEmployee()
		{
			employee = new Employee();
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
			
			boolean updateSuccess = empControl.updateEmployee(employee);
System.out.println("Server: after empControl call");
			try
			{
				oos.writeObject(new Boolean(updateSuccess));
				oos.flush();
System.out.println("Server: after flush");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public void searchEmployee()
		{
			String searchCriteria = this.readObject();
System.out.println("calling Controller: "+searchCriteria);			
			ArrayList <Employee> employeeSearchResults = empControl.searchEmployee(searchCriteria);
			try
			{
System.out.println("Number of employee records returned: "+employeeSearchResults.size());
				oos.writeObject(employeeSearchResults);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void addEmployeeSkill()
		{
			EmployeeSkill addEmployeeSkill = new EmployeeSkill();
			try
			{
				addEmployeeSkill = (EmployeeSkill)ois.readObject();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boolean success = empControl.addEmployeeSkill(addEmployeeSkill);
			try
			{
				oos.writeObject(new Boolean(success));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void nominateRater()
		{
			EmployeeSkill addNominee = new EmployeeSkill();
			try
			{
				addNominee = (EmployeeSkill)ois.readObject();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boolean success = empControl.addNominee(addNominee);
			try
			{
				oos.writeObject(new Boolean(success));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void rateEmployeeSkill()
		{
			EmployeeSkill rateEmployeeSkill = new EmployeeSkill();
			try
			{
				rateEmployeeSkill = (EmployeeSkill)ois.readObject();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("rateArray: "+rateEmployeeSkill.getCapabilityList().size());		
			boolean success = empControl.rateEmployeeSkill(rateEmployeeSkill);
			try
			{
				oos.writeObject(new Boolean(success));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void searchEmployeeSkill()
		{
			String employeeID = this.readObject();
			String raterID = this.readObject();
			int skillID = 0;
			try
			{
				skillID = (Integer)ois.readObject();
			} catch (IOException | ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Server: read searchEmployeeSkills: "+employeeID+" "+raterID+" "+skillID);
			try
			{
				oos.writeObject(empControl.searchEmployeeSkill(employeeID, skillID, raterID));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void searchEmployeeSkillBySkillID()
		{
			int skillID = 0;
			try
			{
				skillID = (Integer)ois.readObject();
			} catch (IOException | ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Server: read searchEmployeeSkills: "+skillID);
			try
			{
				oos.writeObject(empControl.searchEmployeeSkill(skillID));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void searchEmployeeSkillByRaterID()
		{
			String raterID = null;
			this.readObject();
			raterID = inMessage;
			System.out.println("Server: read searchEmployeeSkills: "+raterID);
			try
			{
				oos.writeObject(empControl.searchEmployeeSkillByRaterID(raterID));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void searchEmployeeHobby()
		{
			short hobbyID = 0;
			try
			{
				hobbyID = (short)ois.readObject();
			} catch (IOException | ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Server: read searchEmployeeHobby: "+hobbyID);
			try
			{
				oos.writeObject(empControl.searchEmployeeHobby(hobbyID));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getErrorCode()
		{
			try
			{
				oos.writeObject(new Short(empControl.getErrorCode()));
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getErrorMsg()
		{
			this.writeOutMessage(empControl.getErrorMsg());
		}
		public String readObject()
		{
System.out.println("Server: in readObject()");
			try
			{
				inMessage=(String)ois.readObject();
			} catch (ClassNotFoundException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Server: after read-inMessage- "+inMessage);
			return inMessage;
		}
		public void writeOutMessage(String outMessage)
		{
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
		public void closeConnections()
		{
			try
			{
				clientSocket.close();
				oos.close();
				ois.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String [] args)
	{
		new EmpSkillServer();
	}
	
}
