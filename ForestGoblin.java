package game;

public class ForestGoblin extends Enemy{
	public ForestGoblin(double X, double Y) {
		super(1, X, Y);
	}
	
	//@Overrides Enemy Class
	public void moveBehavior(double xIn, double yIn) 
	{
		double dX = eX - xIn;
		double dY = eY - yIn;
		
		theta = Math.atan(dY/dX);

		if(xIn < eX)
		{
			theta += Math.PI;
		}
		
		
		double speed = stats.getSpeed();
		
		eX += speed*Math.cos(theta);
		eY += speed*Math.sin(theta);

	}
	
	public void attackBehavior(double xIn, double yIn) {
		System.out.println("Attacking the player...");
		projectiles.add(new Projectile(2, eX, eY, theta, .1));
	}

}
