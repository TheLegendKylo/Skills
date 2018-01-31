package vzap.phoenix.client;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import vzap.phoenix.Server.Employee.Capability;
import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;

public class EmpSkillCommonMethods
{
	
	public DefaultTableModel getEmpSkillAverage(EmpSkillClientController clientControl, ArrayList<EmployeeSkill> employeeSkillList)
	{
		Object[] skillsHeader = new String[]{"Skill","Your Ave Rating","Nominated Average",
		"Number of \nNominee ratings"};
		int counter = 0;	
		
		String [] skillDesc = new String[99];
		ArrayList<Skill> skillList = clientControl.getSkillList();
		double yourAveRating[] = new double[99];
		double nominateeAveRating[] = new double[99];
		int numberOfRatings[] = new int[99];
		int ratingCount = 0;
		double averageRating = 0;
		int skillIDCheck = 0;
		
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			// Check whether a new SkillId has been read
			if(!(skillIDCheck==employeeSkillList.get(i).getSkillID()))
			{
				// if this is not the first record found
				// update the previous totals to the relevant variables
				if(i>0)
				{
					if(ratingCount >0)
					{
						nominateeAveRating[counter]= ( (nominateeAveRating[counter]/ratingCount *100.0)/100.0);
						numberOfRatings[counter] = ratingCount;
						ratingCount = 0;
					}
					counter++;
				}
				// iterate through the static skill array to obtain the skilldescription
				for (int j = 0; j < skillList.size(); j++)
				{
					if(skillList.get(j).getSkillId() == employeeSkillList.get(i).getSkillID())
						{
						skillIDCheck = skillList.get(j).getSkillId();
						skillDesc[counter] = skillList.get(j).getSkillDescription();
						break;
						}//comment
				}
			}
			// check whether this record is of the employee rating him/herself
			// only one such record should exist
			if(employeeSkillList.get(i).getEmployeeID().equals(
					employeeSkillList.get(i).getRaterID()))
			{
				yourAveRating[counter] = Math.round(employeeSkillList.get(i).getOverAllAverageRating()*100.0)/100.0;
			}
			else
				// this record if of a nominee rating
			{
				averageRating = employeeSkillList.get(i).getOverAllAverageRating();
				// only include in the average ratings if it is not 0;
				if(averageRating > 0)
				{
					nominateeAveRating[counter] =+ averageRating;
					ratingCount++;
				}
			}
		}
		// ensure that the final nominee ratings have been taken into account
		if(ratingCount >0)
		{
			nominateeAveRating[counter]= Math.round(nominateeAveRating[counter]/ratingCount*100.0)/100.0;
			numberOfRatings[counter] = ratingCount;
			ratingCount = 0;
		}
		
		Object[][] skillsRow = new Object[counter+1][5];
		for (int i = 0; i < (counter+1); i++)
		{
			skillsRow[i][0] = skillDesc[i];
			skillsRow[i][1] = yourAveRating[i];
			skillsRow[i][2] = nominateeAveRating[i];
			skillsRow[i][3] = numberOfRatings[i];
		}
		DefaultTableModel empSkillModel = new DefaultTableModel(skillsRow, skillsHeader);
		return empSkillModel;
	}

	public DefaultTableModel getEmpSkillDetail(EmpSkillClientController clientControl, ArrayList<EmployeeSkill> employeeSkillList, 
			ArrayList<Capability> capabilityList)
	{
		System.out.println("******************Detail Model************************");
		Object[] capabilityHeader = new String[10];
		capabilityHeader[0] = "Employee";
		capabilityHeader[1] = "Skill";
		capabilityHeader[9] = "No of Ratings";
		ArrayList<Skill> skillList = clientControl.getSkillList();

		// iterate through the static capability array to obtain the capabilityName
		for (int j = 0; j < capabilityList.size(); j++)
		{
			capabilityHeader[j+2] =  capabilityList.get(j).getName();
		}
		Object[] capabilityRating = new Object[10];
		int counter = 0;
		
		String skillDesc = null;
		double averageRating[] = new double[7];
		double ratingCount = 0;
		String empIDCheck = null;
		short skillIDCheck = 0;
		ArrayList<Short> capList = null;
		ArrayList<Short> ratingList = null;
		
		DefaultTableModel empCapModel = new DefaultTableModel();
		empCapModel.setColumnIdentifiers(capabilityHeader);
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			System.out.println(employeeSkillList.get(i).getEmployeeID()+" "+employeeSkillList.get(i).getSkillID());
			// Check whether a new SkillId has been read
			if(((empIDCheck==null) ||
					!(empIDCheck.equals(employeeSkillList.get(i).getEmployeeID()))) ||
					!(skillIDCheck==employeeSkillList.get(i).getSkillID()))
			{
				// if this is not the first record found
				// update the previous totals to the relevant variables
				if(i>0)
				{
					if(ratingCount >0)
					{
						Employee employee = clientControl.searchEmployee(empIDCheck).get(0);
						capabilityRating[0] = ""+employee.getSurname()+", "+employee.getFirstName(); 
						capabilityRating[1] = ""+skillDesc; 
						int ratingIdx = 2;
						for (int j = 0; j < averageRating.length; j++)
						{
							System.out.println("Write Totals: "+j+" "+averageRating[j]+" Rating Count: "+ratingCount);
							capabilityRating[ratingIdx] = Math.round(averageRating[j]/ratingCount*100.0)/100.0;
							averageRating[j]=0;
							ratingIdx++;
						}
System.out.println("commonmethoeds - capability Rating = " + capabilityRating.length);
						capabilityRating[9] = ratingCount;
						empCapModel.addRow(capabilityRating);;
						ratingCount = 0;
					}
					counter++;
				}
				empIDCheck = employeeSkillList.get(i).getEmployeeID();
				if(!(skillIDCheck==(short)employeeSkillList.get(i).getSkillID()))
				{
					skillIDCheck = (short)employeeSkillList.get(i).getSkillID();
					// iterate through the static skill array to obtain the skilldescription
					for (int j = 0; j < skillList.size(); j++)
					{
						if(skillList.get(j).getSkillId() == employeeSkillList.get(i).getSkillID())
							{
								skillDesc = skillList.get(j).getSkillDescription();
								break;
							}
					}
				}
			}
			
			// get the Capability Ratings for each EmpSkillRating done
			ratingList = employeeSkillList.get(i).getRatingList();
			
			if(ratingList!=null)
			{
				System.out.println("ratingList.size: "+ratingList.size());
				for (int j = 0; j < ratingList.size(); j++)
				{
					System.out.println("Write Lines: "+j+" "+averageRating[j]+" Rating Count: "+ratingCount);
					averageRating [j]=+ Math.round(ratingList.get(j)*100.0)/100.0;
				}
				ratingCount++;
			}		
		}
		// ensure that the final nominee ratings have been taken into account
		if(ratingCount >0)
		{
			Employee employee = new Employee();
			employee = clientControl.searchEmployee(empIDCheck).get(0);
			capabilityRating[0] = ""+employee.getSurname()+", "+employee.getFirstName(); 
			capabilityRating[1] = ""+skillDesc; 
			int ratingIdx = 2;
			for (int j = 0; j < averageRating.length; j++)
			{
				System.out.println("Write Totals: "+j+" "+averageRating[j]+" Rating Count: "+ratingCount);
				capabilityRating[ratingIdx] = Math.round(averageRating[j]/ratingCount *100.0)/100.0;
				averageRating[j]=0;
				ratingIdx++;
			}
			capabilityRating[9] = ratingCount;
			empCapModel.addRow(capabilityRating);;
			ratingCount = 0;
		}
		return empCapModel;
	}

	public DefaultTableModel getEmpSkillList(EmpSkillClientController clientControl, 
			ArrayList<EmployeeSkill> employeeSkillList, Employee employee)
	{
		Object[] skillsHeader = new String[]{"EmployeeID","Name","Skill","Date of Request"};
		Object[] skillsRow = new Object[4];
		DefaultTableModel empSkillModel = new DefaultTableModel();
		empSkillModel.setColumnIdentifiers(skillsHeader);
		String [] skillDesc = new String[99];
		int skillIDCheck = 0;
		String empIDCheck = null;
		ArrayList<Skill> skillList = clientControl.getSkillList();
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			// Check whether a new SkillId has been read
			if(!(skillIDCheck==employeeSkillList.get(i).getSkillID()))
			{
				// if this is not the first record found
				// update the previous totals to the relevant variables
				// iterate through the static skill array to obtain the skilldescription
				for (int j = 0; j < skillList.size(); j++)
				{
					if(skillList.get(j).getSkillId() == employeeSkillList.get(i).getSkillID())
					{
						skillsRow[2] = skillList.get(j).getSkillDescription();
						break;
					}//comment
				}
				skillIDCheck = employeeSkillList.get(i).getSkillID();
			}
			if((empIDCheck==null)||
			!(empIDCheck.equals(employeeSkillList.get(i).getEmployeeID())))
			{
				ArrayList<Employee>empList = clientControl.searchEmployee(employeeSkillList.get(i).getEmployeeID());
				employee = empList.get(0);
//				employee = clientControl.searchEmployee(employeeSkillList.get(i).getEmployeeID()).get(i);
				skillsRow[0] = employee.getEmployeeID();
				skillsRow[1] = employee.getSurname()+", "+employee.getFirstName();
				empIDCheck = employeeSkillList.get(i).getEmployeeID();
			}
			skillsRow[3] = employeeSkillList.get(i).getCreatedDate();
			empSkillModel.addRow(skillsRow);
		}
		
		return empSkillModel;
		
	}
	
}
