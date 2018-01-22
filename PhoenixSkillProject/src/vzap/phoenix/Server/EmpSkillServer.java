package vzap.phoenix.Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
		private DataOutputStream dos;
		private PrintWriter pw;
		private FileInputStream fis;
		private ObjectInputStream ois;
		private DataInputStream dis;
		
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
			boolean exitSession = false;
			while(!exitSession)
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
				System.out.println("Server: read EmployeeID: "+employeeID);
				password = br.readLine();
				System.out.println("Server: read password: "+password);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			empControl = new EmployeeController(employeeID, password);
			employee = empControl.getLogonEmployee();
			System.out.println("Employee login errorCode: "+empControl.getErrorCode());
			outMessage = empControl.getErrorMsg();
			System.out.println("Employee login writing errorMsg: "+outMessage);
			pw.println(outMessage);
			pw.flush();
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

System.out.println(employee.getSurname());
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
			String description = null;
			try
			{
				description = br.readLine();
				System.out.println("Server: read Skill Description: "+description);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			skillID = empControl.addSkill(description);
			System.out.println("Hobby Added: "+skillID);
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
			String description = null;
			try
			{
				description = br.readLine();
				System.out.println("Server: read Hobby Description: "+description);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				oos.writeBoolean(registerSuccess);
				oos.flush();
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
				oos.flush();
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
				oos.writeBoolean(success);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return success;
		}
		public boolean nominateRater()
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
				oos.writeBoolean(success);
				oos.flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return success;
		}
		public boolean rateEmployeeSkill()
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
			
			boolean success = empControl.rateEmployeeSkill(rateEmployeeSkill);
			try
			{
				oos.writeBoolean(success);
				oos.flush();
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
				oos.flush();
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
				pw.println("Closing Server Connections");
				pw.flush();
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
