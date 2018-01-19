package vzap.phoenix.Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
		private ServerSocket serverSocket;
		private Socket clientSocket;
		private BufferedReader br;
		private FileOutputStream fos;
		private ObjectOutputStream oos;
		private PrintWriter pw;
		private FileInputStream fis;
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
				pw = new PrintWriter(clientSocket.getOutputStream(),true);
				br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
			while(true)
			{
				try
				{
					System.out.println("Try to read new message");
					inMessage=br.readLine();
					System.out.println("Server: In Thread-inMessage: "+inMessage);
					switch (inMessage)
					{
						case "Quit":
						{
							this.closeConnections();
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
						case "getHobbyList":
						{
							this.getHobbyList();
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
				} catch (IOException e)
				{
					e.printStackTrace();
					break;
				}
			}
		}
		public void loginEmployee()
		{
			String employeeID = null;
			String password = null;
			try
			{
				employeeID = br.readLine();
				System.out.println(employee);
				password = br.readLine();
				System.out.println(password);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			empControl = new EmployeeController(employeeID, password);
			employee = empControl.getLogonEmployee();
			System.out.println("Employee login errorCode: "+empControl.getErrorCode());
			try
			{
				oos.writeShort(empControl.getErrorCode());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

System.out.println(employee.getSurname());
		}
		public void getLogonEmployee()
		{
			try
			{
				oos.writeObject(employee);
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
				oos.writeBoolean(registerSuccess);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public void updateEmployee()
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
			
			boolean updateSuccess = empControl.updateEmployee(employee);
			try
			{
				oos.writeBoolean(updateSuccess);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public void searchEmployee()
		{
			String searchCriteria = null;
			try
			{
				searchCriteria = br.readLine();
				System.out.println(employee);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		public boolean addEmployeeSkill()
		{
			EmployeeSkill addEmployeeSkill = null;
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
				oos.writeBoolean(success);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return success;
		}
		public boolean nominateRater()
		{
			EmployeeSkill addNominee = null;
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
				oos.writeBoolean(success);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return success;
		}
		public boolean rateEmployeeSkill()
		{
			EmployeeSkill rateEmployeeSkill = null;
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
			
			boolean success = empControl.rateEmployeeSkill(rateEmployeeSkill);
			try
			{
				oos.writeBoolean(success);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return success;
		}
		public void getErrorCode()
		{
			try
			{
				oos.writeShort(empControl.getErrorCode());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getErrorMsg()
		{
			pw.println(empControl.getErrorMsg());
			pw.flush();
		}
		public void closeConnections()
		{
			try
			{
				clientSocket.close();
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
	public static void main(String [] args)
	{
		new EmpSkillServer();
	}
	
}
