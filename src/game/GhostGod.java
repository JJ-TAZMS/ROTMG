package game;

public class GhostGod extends Enemy	{

	private double wanderTheta;
	private int attackStage;
	
	public GhostGod(double X, double Y) {
		super(24, X, Y);
		wanderTheta = 0;
		attackStage = 0;
	}
	
	public void moveBehavior(double xIn, double yIn)
	{
		//System.out.println("CALLING MOVEBEHAVIOR");
		double speed = stats.getSpeed();
		double playerDist = (Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn))) ;
		//keeps a distance of 5 tiles from the player
		if (playerDist < 5) {
			boolean wander = (stats.getAtkWait()%10 == 0);
			
			if (wander)
			{
				double changeTheta = Math.PI;
				wanderTheta += (Math.random()*changeTheta - changeTheta/2);
				xVel = speed*Math.cos(wanderTheta);
				yVel = speed*Math.sin(wanderTheta);
			}
		}
		else if (playerDist > 5) {
			xVel = speed*Math.cos(theta);
			yVel = speed*Math.sin(theta);
		}
		
		eX += xVel;
		eY += yVel;
	}
	
	public void attackBehavior(double xIn, double yIn)
	{
		//System.out.println("CALLING ATTACKBEHAVIOR");
		if(attackStage < 10) //NORMALLY 60
		{
			attackStage++;
			projectiles.add(new Projectile(24, eX, eY, theta, .1));
		}
		else if(attackStage < 18)
		{
			int numProjectiles = 7;
			double step = 2 * Math.PI / numProjectiles;
				for(int i = 1; i <= 7; i++)
				{
					//System.out.println(2*Math.PI/i);
					projectiles.add(new Projectile(24, eX, eY, (step * i), .1));
				}
				System.out.println("--------------------");
				attackStage++;

		}
		else
		{
			attackStage = 0;
		}
	}
	

}
