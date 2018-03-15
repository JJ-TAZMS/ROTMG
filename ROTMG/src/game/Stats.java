package game;

public class Stats {
	private double speed, dexterity, attack , defense, vitality;
	private double wisdom, hp, mp, healthPotions, manaPotions;
	
	public Stats(int index)
	{
		if(index == 0)
		{
			speed = .2; 
			dexterity = 5;
			attack = 5;
			defense = 5;
			vitality = 5;
			wisdom = 5;
			hp = 100;
			mp = 100;
			healthPotions = 3;
			manaPotions = 3;
		}
	}
	
	//Setters
	public void setSpeed(int x)
	{
		speed = x;
	}
	public void setDexterity(int x)
	{
		dexterity = x;
	}
	public void setAttack(int x)
	{
		attack = x;
	}
	public void setDefense(int x)
	{
		defense = x;
	}
	public void setVitality(int x)
	{
		vitality = x;
	}
	public void setWisdom(int x)
	{
		wisdom = x;
	}
	public void sethp(int x)
	{
		hp = x;
	}
	public void setmp(int x)
	{
		mp = x;
	}
	public void setHealthPotions(int x)
	{
		healthPotions = x;
	}
	public void setManaPotions(int x)
	{
		manaPotions = x;
	}
	
	//Getters
	public double getSpeed()
	{
		return speed;
	}
	public double getDexterity()
	{
		return dexterity;
	}
	public double getAttack()
	{
		return attack;
	}
	public double getDefense()
	{
		return defense;
	}
	public double getVitality()
	{
		return vitality;
	}
	public double getWisdom()
	{
		return wisdom;
	}
	public double gethp()
	{
		return hp;
	}
	public double getmp()
	{
		return mp;
	}
	public double getHealthPotions()
	{
		return healthPotions;
	}
	public double getManaPotions()
	{
		return manaPotions;
	}
	

}
