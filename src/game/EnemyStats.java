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
		}
	}
	
	//Getters
	public double getMoveDist()	{	return moveDist;	}
	public double getAtkDist()	{	return attackDist;	}
	public boolean getActive()	{	return active;	}
	
	//Setters
	public void setActive(boolean b)	{	active = b;	}

}
