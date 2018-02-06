package vzap.phoenix.client;
//
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
		Object[] skillsHeader = new String[]{"Skill","Coaching Role","Employee Self Rating","Nominated Average",
		"Number of \nNominee ratings"};
		int counter = 0;	

		String [] skillDesc = new String[99];
		String [] coachingInd = new String[99];
		ArrayList<Skill> skillList = clientControl.getSkillList();
		double yourAveRating[] = new double[99];
		double nominateeAveRating[] = new double[99];
		int numberOfRatings[] = new int[99];
		int ratingCount = 0;
		double averageRating = 0;
		int skillIDCheck = 0;
		boolean recordFound = false;

		if(employeeSkillList!=null)
		{
			for (int i = 0; i < employeeSkillList.size(); i++)
			{
				recordFound = true;
				// Check whether a new SkillId has been read
				if(!(skillIDCheck==employeeSkillList.get(i).getSkillID()))
				{
					// if this is not the first record found
					// update the previous totals to the relevant variables
					if(i>0)
					{
						if(ratingCount >0)
						{
							nominateeAveRating[counter]= Math.round(nominateeAveRating[counter]/ratingCount *100.0)/100.0;
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
							//	System.out.println("Skill: "+skillDesc[counter]);
							break;
						}//comment
					}
				}
				// check whether this record is of the employee rating him/herself
				// only one such record should exist
				if(employeeSkillList.get(i).getEmployeeID().equals(
						employeeSkillList.get(i).getRaterID()))
				{
					coachingInd[counter] = employeeSkillList.get(i).getCoachingAvailability();
					yourAveRating[counter] = Math.round(employeeSkillList.get(i).getOverAllAverageRating()*100.0)/100.0;
				}
				else
					// this record if of a nominee rating
				{
					//	System.out.println("Nominee Rating: "+ratingCount);
					averageRating = employeeSkillList.get(i).getOverAllAverageRating();
					// only include in the average ratings if it is not 0;
					if(averageRating > 0)
					{
						nominateeAveRating[counter] += averageRating;
						ratingCount++;
					}
				}
			}
		}
		// ensure that the final nominee ratings have been taken into account
		if(ratingCount >0)
		{
			//			System.out.println("Write Row: "+counter+ "No of raters: "+ratingCount);
			nominateeAveRating[counter]= Math.round(nominateeAveRating[counter]/ratingCount*100.0)/100.0;
			numberOfRatings[counter] = ratingCount;
			ratingCount = 0;
		}

		Object[][] skillsRow = new Object[counter+1][6];
		if(recordFound)
		{
			counter++;
		}
		for (int i = 0; i < counter; i++)
		{
			skillsRow[i][0] = skillDesc[i];
			skillsRow[i][1] = coachingInd[i];
			skillsRow[i][2] = yourAveRating[i];
			skillsRow[i][3] = nominateeAveRating[i];
			skillsRow[i][4] = numberOfRatings[i];
		}
		DefaultTableModel empSkillModel = new DefaultTableModel(skillsRow, skillsHeader){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		return empSkillModel;
	}

	public DefaultTableModel getEmpSkillDetail(EmpSkillClientController clientControl, ArrayList<EmployeeSkill> employeeSkillList, 
			ArrayList<Capability> capabilityList)
	{
		//		System.out.println("******************Summary Detail Model************************");
		Object[] capabilityHeader = new String[11];
		capabilityHeader[0] = "Employee";
		capabilityHeader[1] = "Skill";
		capabilityHeader[9] = "Overall Rating";
		capabilityHeader[10] = "No of Ratings";
		ArrayList<Skill> skillList = clientControl.getSkillList();

		// iterate through the static capability array to obtain the capabilityName
		for (int j = 0; j < capabilityList.size(); j++)
		{
			capabilityHeader[j+2] =  capabilityList.get(j).getName();
		}
		Object[] capabilityRating = new Object[11];
		int counter = 0;

		String skillDesc = null;
		double averageRating[] = new double[7];
		double ratingCount = 0;
		double overAllRating = 0;
		String empIDCheck = null;
		short skillIDCheck = 0;
		ArrayList<Short> capList = null;
		ArrayList<Short> ratingList = null;

		DefaultTableModel empCapModel = new DefaultTableModel(){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		empCapModel.setColumnIdentifiers(capabilityHeader);
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			//			System.out.println(employeeSkillList.get(i).getEmployeeID()+" "+employeeSkillList.get(i).getSkillID());
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
						capabilityRating[0] = employee.getFirstName()+" "+employee.getSurname(); 
						capabilityRating[1] = ""+skillDesc; 
						int ratingIdx = 2;
						for (int j = 0; j < averageRating.length; j++)
						{
							//							System.out.println("Write Totals: "+j+" "+averageRating[j]+" Rating Count: "+ratingCount);
							capabilityRating[ratingIdx] = Math.round(averageRating[j]/ratingCount*100.0)/100.0;
							overAllRating += (double)capabilityRating[ratingIdx];
							averageRating[j]=0;
							ratingIdx++;
						}
						//System.out.println("commonmethods - capability Rating = " + capabilityRating.length);
						capabilityRating[9] = Math.round(overAllRating*100.0/7)/100.0;
						capabilityRating[10] = ratingCount;
						empCapModel.addRow(capabilityRating);;
						ratingCount = 0;
						overAllRating = 0;
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

			if(ratingList!=null && ratingList.size()!=0)
			{

				for (int j = 0; j < ratingList.size(); j++)
				{
					//					System.out.println("Write Lines Before: "+j+" "+averageRating[j]+" "+ratingList.get(j)+" Rating Count: "+ratingCount);
					averageRating [j] += Math.round(ratingList.get(j)*100.0)/100.0;
					//					System.out.println("Write Lines After : "+j+" "+averageRating[j]+" "+ratingList.get(j)+" Rating Count: "+ratingCount);
				}
				ratingCount++;
			}		
		}
		// ensure that the final nominee ratings have been taken into account
		if(ratingCount >0)
		{
			Employee employee = new Employee();
			employee = clientControl.searchEmployee(empIDCheck).get(0);
			capabilityRating[0] = employee.getFirstName()+" "+employee.getSurname(); 
			capabilityRating[1] = ""+skillDesc; 
			int ratingIdx = 2;

			for (int j = 0; j < averageRating.length; j++)
			{
				//				System.out.println("Write Totals: "+j+" "+averageRating[j]+" Rating Count: "+ratingCount);
				capabilityRating[ratingIdx] = Math.round(averageRating[j]/ratingCount *100.0)/100.0;
				overAllRating += (double)capabilityRating[ratingIdx];
				averageRating[j]=0;
				ratingIdx++;
			}
			capabilityRating[9] = Math.round(overAllRating*100.0/7)/100.0;
			capabilityRating[10] = ratingCount;
			empCapModel.addRow(capabilityRating);;
			ratingCount = 0;
		}

		return empCapModel;
	}
	public DefaultTableModel getEmpCapabilityDetail(EmpSkillClientController clientControl, ArrayList<EmployeeSkill> employeeSkillList, 
			ArrayList<Capability> capabilityList)
	{
		//		System.out.println("******************Capability Detail Model************************");
		Object[] capabilityHeader = new String[10];
		capabilityHeader[0] = "Rater";
		capabilityHeader[1] = "Skill";
		capabilityHeader[9] = "Overall Rating";
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
		String empIDCheck = null;
		short skillIDCheck = 0;
		ArrayList<Short> capList = null;
		ArrayList<Short> ratingList = null;

		DefaultTableModel empCapModel = new DefaultTableModel(){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		empCapModel.setColumnIdentifiers(capabilityHeader);
		for (int i = 0; i < employeeSkillList.size(); i++)
		{
			//			System.out.println(employeeSkillList.get(i).getRaterID()+" "+employeeSkillList.get(i).getSkillID());
			empIDCheck = employeeSkillList.get(i).getRaterID();
			Employee employee = clientControl.searchEmployee(empIDCheck).get(0);
			capabilityRating[0] = employee.getFirstName()+" "+employee.getSurname(); 
			if(!(skillIDCheck == (short)employeeSkillList.get(i).getSkillID()))
			{
				skillIDCheck = (short)employeeSkillList.get(i).getSkillID();
				// iterate through the static skill array to obtain the skilldescription
				for (int j = 0; j < skillList.size(); j++)
				{
					if(skillList.get(j).getSkillId() == employeeSkillList.get(i).getSkillID())
					{
						capabilityRating[1] = skillList.get(j).getSkillDescription();
						break;
					}
				}
			}
			// get the Capability Ratings for each EmpSkillRating done
			ratingList = employeeSkillList.get(i).getRatingList();
			if(ratingList!=null && ratingList.size()!=0)
			{

				//				System.out.println("EmpSkillID = " +employeeSkillList.get(i).getEmpSkillID() +" ratingList.size: "+ratingList.size());
				int ratingIdx = 2;
				double overAllRating = 0;
				for (int j = 0; j < ratingList.size(); j++)
				{
					capabilityRating[ratingIdx] = Math.round(ratingList.get(j)*100.0)/100.0;
					overAllRating += (double)capabilityRating[ratingIdx];
					//					System.out.println("Write Lines: "+j+" "+ratingList.get(j)+
					//					"overAllRating: "+j+" "+overAllRating);
					ratingIdx++;
				}	
				capabilityRating[9] = Math.round(overAllRating*100.0/7)/100.0;
				empCapModel.addRow(capabilityRating);;
				//				System.out.println("commonMethods - capability Rating = " + capabilityRating.length);
				counter++;
			}
		}

		return empCapModel;
	}

	public DefaultTableModel getEmpSkillList(EmpSkillClientController clientControl, 
			ArrayList<EmployeeSkill> employeeSkillList, Employee employee)
	{
		Object[] skillsHeader = new String[]{"EmployeeID","Name","Skill","Date of Request"};
		Object[] skillsRow = new Object[4];
		DefaultTableModel empSkillModel = new DefaultTableModel(){

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
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
				//
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
				skillsRow[1] = employee.getFirstName()+" "+employee.getSurname();
				empIDCheck = employeeSkillList.get(i).getEmployeeID();
			}
			skillsRow[3] = employeeSkillList.get(i).getCreatedDate();
			empSkillModel.addRow(skillsRow);
		}		
		return empSkillModel;
	}

}
