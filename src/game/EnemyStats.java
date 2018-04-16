package game;

public class EnemyStats extends Stats{

	private double moveDist, attackDist; //In tiles
	private boolean active;
	
	public EnemyStats(int index) {
		active = false;
		if (index == 1) //PIRATE
		{
			setSpeed(8); 
			setDexterity(3);
			setAttack(4);
			setDefense(0);
			sethp(5);
			setExperience(1);
			moveDist = 10;
			attackDist = 3;
		}	else if (index == 2) //Gelatinous Cube
		{
			setSpeed(4); 
			setDexterity(4);
			setAttack(9);
			setDefense(0);
			sethp(70);
			setExperience(4);
			moveDist = 20;
			attackDist = 5;
		}	else if (index == 6) //Forest Goblin
		{
			setSpeed(6); 
			setDexterity(8);
			setAttack(20);
			setDefense(0);
			sethp(60);
			setExperience(6);
			moveDist = 20;
			attackDist = 4.8;
		}	else if (index == 14) //Forest Goblin Mage
		{
			setSpeed(7); 
			setDexterity(4);
			setAttack(8);
			setDefense(0);
			sethp(35);
			setExperience(4);
			moveDist = 20;
			attackDist = 8.4;
		}

	}
	
	//Getters
	public double getMoveDist()	{	return moveDist;	}
	public double getAtkDist()	{	return attackDist;	}
	public boolean getActive()	{	return active;	}
	
	//Setters
	public void setActive(boolean b)	{	active = b;	}

}
