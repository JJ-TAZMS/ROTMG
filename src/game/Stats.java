package game;

public class Stats {
	protected double speed;
	private double dexterity;
	private double attack;
	private double defense;
	private double vitality;
	private double attack2;
	private double wisdom, hp, mp, healthPotions, manaPotions, experience;
	private int atkWait;
	
	public Stats()
	{
		vitality = 0; //Enemies don't regenerate
		mp = 0;       //Enemies don't use spells
		wisdom = 0;   //Don't use magic
		healthPotions = 0;
		manaPotions = 0;
		atkWait = 0;
		attack2 = 0;
	}
	
	
	public Stats(int index)
	{
		atkWait = 0;
		if(index == 0) //Two digit numbers are player stats only
		{
			speed = 10; 
			dexterity = 15;
			attack = 12;
			attack2 = 10;
			defense = 0;
			vitality = 12;
			wisdom = 12;
			hp = 100;
			mp = 100;
			healthPotions = 3;
			manaPotions = 3;
			experience = 0;
		}
	}
	
	//Setters
	public void setSpeed(int x)	{		speed = x;	}
	public void setDexterity(int x)	{		dexterity = x;	}
	public void setAttack(int x)	{		attack = x;	}
	public void setDefense(int x)	{		defense = x;	}
	public void setVitality(int x)	{		vitality = x;	}
	public void setWisdom(int x)	{		wisdom = x;	}
	public void sethp(int x)	{		hp = x;	}
	public void setmp(int x)	{		mp = x;	}
	public void setHealthPotions(int x)	{		healthPotions = x;	}
	public void setManaPotions(int x)	{		manaPotions = x;	}
	public void setExperience(int x)	{		experience = x;	}
	public void setAtkWait(int x)	{		atkWait = x;	}
	public void setAttack2(int x)	{	attack2 = x;	}
	
	//Getters
	public double getDispSpeed()	{		return speed;	}
	public double getSpeed()	{		return speed/100.0;	}
	public double getDexterity()	{		return dexterity;	}
	public double getAttack()	{		return attack;	}
	public double getDefense()	{		return defense;	}
	public double getVitality()	{		return vitality;	}
	public double getWisdom()	{		return wisdom;	}
	public double gethp()	{		return hp;	}
	public double getmp()	{		return mp;	}
	public double getHealthPotions()	{		return healthPotions;	}
	public double getManaPotions()	{		return manaPotions;	}
	public double getExperience() 	{		return experience;	}
	public int getAtkWait()	{		return atkWait;	}
	public double getAttack2() {	return attack2;	}

}
