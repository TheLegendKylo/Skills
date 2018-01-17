package vzap.phoenix.Server.Employee;

import java.io.Serializable;

public class Skill implements Serializable
{
	private int skillId = 0;
	private String skillDescription = null;
	
	public Skill(int skillId, String skillDescription)
	{
		this.skillId = skillId;
		this.skillDescription = skillDescription;
	}

	public int getSkillId()
	{
		return skillId;
	}

	public String getSkillDescription()
	{
		return skillDescription;
	}
	
}
