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
			essThread = new EmpSkillServerThread(clientSocket, serverSocket);
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
		private ArrayList<SkillStage> skillStageList = null;
		private ArrayList<EmployeeSkill> empSkillList = null;
		private ArrayList<Capability> skillDimensionList = null;
		private ArrayList<CapabilityLevel> skillDimensionLevelList = null;

		public EmpSkillServerThread(Socket clientSocket, ServerSocket serverSocket)
		{
			this.clientSocket = clientSocket;
			this.serverSocket = serverSocket;
			
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
					System.out.println(inMessage);
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
						case "getSkillList":
						{
							this.getSkillList();
							break;
						}
						case "getSkillStageList":
						{
							this.getSkillStageList();
							break;
						}
						case "getEmpSkillList":
						{
							this.getEmpSkillList();
							break;
						}
						case "getSkillDimensionList":
						{
							this.getSkillDimensionList();
							break;
						}
						case "getSkillDimensionLevelList":
						{
							this.getSkillDimensionLevelList();
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
					}
				} catch (IOException e)
				{
					e.printStackTrace();
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
			System.out.println(employee.getSurname());
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
		public void getSkillStageList()
		{
			skillStageList = EmployeeController.getSkillStageList();
			System.out.println("Number of Skill Stages: "+skillStageList.size());
			try
			{
				oos.writeObject(skillStageList);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getEmpSkillList()
		{
			empSkillList = empControl.getEmpSkillList();
System.out.println("Number of Skill Stages: "+skillStageList.size());
			try
			{
				oos.writeObject(empSkillList);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getSkillDimensionList()
		{
			skillDimensionList = EmployeeController.getSkillDimensionList();
			System.out.println("Number of Skill Dimensions: "+skillDimensionList.size());
			try
			{
				oos.writeObject(skillDimensionList);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void getSkillDimensionLevelList()
		{
			skillDimensionLevelList = EmployeeController.getSkillDimensionLevelList();
			System.out.println("Number of Skill Dimension Levels: "+skillDimensionLevelList.size());
			try
			{
				oos.writeObject(skillDimensionLevelList);
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
