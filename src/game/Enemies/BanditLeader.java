package game;

public class BanditLeader extends Enemy	{
	
	private int attackStage;
	
	public BanditLeader(double x, double y)
	{
		super(16,x,y);
		attackStage = 0;
	}
	
	public void moveBehavior(double xIn, double yIn)
	{
		double speed = stats.getSpeed();
		
		eX += speed*Math.cos(theta);
		eY += speed*Math.sin(theta);
	}
	
	public void attackBehavior(double xIn, double yIn)
	{
		if(attackStage < 30)
		{
			projectiles.add(new Projectile(1, eX, eY, theta, .2));
			attackStage++;
		}
		else //Throw grenade
		{
			projectiles.add(new Projectile(1, eX, eY, theta, .1));
			attackStage = 0;
		}
	}
	

}
