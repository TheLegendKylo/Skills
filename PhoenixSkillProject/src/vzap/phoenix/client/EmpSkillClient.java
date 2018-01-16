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
import vzap.phoenix.Server.Employee.CapabilityLevel;
import vzap.phoenix.Server.Employee.SkillStage;

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
	private ArrayList<SkillStage> skillStageList = null;
	private ArrayList<EmployeeSkill> empSkillList = null;
	private ArrayList<Capability> skillDimensionList = null;
	private ArrayList<CapabilityLevel> skillDimensionLevelList = null;
	
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
	public Employee loginEmployee(String employeeID, String password)
	{
		outMessage = "loginEmployee";
		pw.println(outMessage);
		pw.flush();
		outMessage = employeeID;
System.out.println(outMessage);
		pw.println(outMessage);
		pw.flush();
		outMessage = password;
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
	public ArrayList<SkillStage> getSkillStageList()
	{
		outMessage = "getSkillStageList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			skillStageList = (ArrayList<SkillStage>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of SkillStages: "+skillStageList.size());
		return skillStageList;
	}
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
	public ArrayList<Capability> getSkillDimensionList()
	{
		outMessage = "getSkillDimensionList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			skillDimensionList = (ArrayList<Capability>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of SkillDimensions: "+skillDimensionList.size());
		return skillDimensionList;
	}
	public ArrayList<CapabilityLevel> getSkillDimensionLevelList()
	{
		outMessage = "getSkillDimensionLevelList";
		pw.println(outMessage);
		pw.flush();
		try
		{
			skillDimensionLevelList = (ArrayList<CapabilityLevel>)ois.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client: Number of SkillDimensionLevels: "+skillDimensionLevelList.size());
		return skillDimensionLevelList;
	}
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
