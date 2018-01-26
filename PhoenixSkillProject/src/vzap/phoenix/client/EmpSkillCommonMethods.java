package vzap.phoenix.client;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.Server.Employee.EmployeeSkill;
import vzap.phoenix.Server.Employee.Skill;

public class EmpSkillCommonMethods
{
	
	public DefaultTableModel getEmpSkillAverage(EmpSkillClientController clientControl, ArrayList<EmployeeSkill> employeeSkillList)
	{
		Object[] skillsHeader = new String[]{"Skill","Your Ave Rating","Nominated Average",
		"Number of ratings"};
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
		System.out.println(">>>Skillcheck: "+skillIDCheck+" empSkill: "+employeeSkillList.get(i).getSkillID()
		+" Counter: "+counter);
		
			// Check whether a new SkillId has been read
			if(!(skillIDCheck==employeeSkillList.get(i).getSkillID()))
			{
				// if this is not the first record found
				// update the previous totals to the relevant variables
				if(i>0)
				{
					if(ratingCount >0)
					{
						System.out.println("---Nominee ID " + " counter: "+counter) ;
						System.out.println("---Rating count = " +nominateeAveRating[counter]+ " ssss" + ratingCount);
						nominateeAveRating[counter]= nominateeAveRating[counter]/ratingCount;
						ratingCount = 0;
					}
					counter++;
				}
				// iterate through the static skill array to obtain the skilldescription
				for (int j = 0; j < skillList.size(); j++)
				{
					System.out.println(">>>SkillList: "+skillList.get(j).getSkillId()+" empSkill: "+employeeSkillList.get(i).getSkillID());
					if(skillList.get(j).getSkillId() == employeeSkillList.get(i).getSkillID())
						{
						skillIDCheck = skillList.get(j).getSkillId();
						System.out.println(">>>set new skillcheck: "+skillIDCheck+" counter: "+counter);
						skillDesc[counter] = skillList.get(j).getSkillDescription();
						System.out.println(">>>set new skillcheck: "+skillIDCheck+" counter: "+counter+" desc: "+skillDesc[counter]);
							break;
						}//comment
				}
			}
			// check whether this record is of the employee rating him/herself
			// only one such record should exist
			if(employeeSkillList.get(i).getEmployeeID().equals(
					employeeSkillList.get(i).getRaterID()))
			{
				yourAveRating[counter] = employeeSkillList.get(i).getOverAllAverageRating();
			}
			else
				// this record if of a nominee rating
			{
				averageRating = employeeSkillList.get(i).getOverAllAverageRating();
				System.out.println("Nominee ID " + employeeSkillList.get(i).getRaterID());
				System.out.println("Skill ID " + employeeSkillList.get(i).getSkillID());
				System.out.println("Average rating = " + averageRating);
				System.out.println("Rating count = " + ratingCount);
				// only include in the average ratings if it is not 0;
				if(averageRating > 0)
				{
					nominateeAveRating[counter] =+ averageRating;
					ratingCount++;
					numberOfRatings[i] = ratingCount;
				}
				
			}
		}
		// ensure that the final nominee ratings have been taken into account
		if(ratingCount >0)
		{
			System.out.println("---Nominee ID " + " counter: "+counter) ;
			nominateeAveRating[counter]= nominateeAveRating[counter]/ratingCount;
			ratingCount = 0;
		}
		
		Object[][] skillsRow = new Object[counter+1][5];
		System.out.println("Before for loop + length of skillDesc " + skillDesc.length);
		System.out.println("Testing the counter " + counter);
		
		for (int i = 0; i < (counter+1); i++)
		{
			
			System.out.println("Index number " + i +" skillDesc "+skillDesc[i]);
				skillsRow[i][0] = skillDesc[i];
				skillsRow[i][1] = yourAveRating[i];
				skillsRow[i][2] = nominateeAveRating[i];
				skillsRow[i][3] = numberOfRatings[i];
		}
		
		DefaultTableModel empSkillModel = new DefaultTableModel(skillsRow, skillsHeader);
		return empSkillModel;
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
		ArrayList<Skill> skillList = clientControl.getSkillList();
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			System.out.println(">>>Skillcheck: "+skillIDCheck+" empSkill: "+employeeSkillList.get(i).getSkillID());
		
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
						skillsRow[2] = skillList.get(j).getSkillId();
						break;
					}//comment
				}
			}
			skillsRow[0] = employee.getEmployeeID();
			skillsRow[1] = employee.getSurname()+", "+employee.getFirstName();
			skillsRow[3] = employeeSkillList.get(i).getCreatedDate();
			empSkillModel.addRow(skillsRow);
		}
		
		return empSkillModel;
		
	}
}
