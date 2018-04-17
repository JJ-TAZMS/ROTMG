package game;

public class EnemyStats extends Stats{

	private double moveDist, attackDist; //In tiles
	private boolean active;
	
	
	public EnemyStats(int index) {
		active = false;
		if (index == 1) //PIRATE
		{
			setSpeed(6); 
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
			moveDist = 20;  //Render distance bc no actual movement ot player
			attackDist = 5;
		}	else if (index == 9) //Giant Crab
		{
			setSpeed(4); 
			setDexterity(4);
			setAttack(9);
			setDefense(2);
			sethp(300);
			setExperience(43);
			moveDist = 10;
			attackDist = 6;
		}	else if (index == 17) //Flying Brain
		{
			setSpeed(8);//Speed needs to be finessed
			setDexterity(4);
			setAttack(50);
			setDefense(12);
			sethp(1000);
			setExperience(100);
			moveDist = 20;
			attackDist = 21.6;
		}
	}
	
	//Getters
	public double getMoveDist()	{	return moveDist;	}
	public double getAtkDist()	{	return attackDist;	}
	public boolean getActive()	{	return active;	}
	
	//Setters
	public void setActive(boolean b)	{	active = b;	}

}
