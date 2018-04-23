package game.Enemies;

import game.Enemy;
import game.Projectile;

public class FlyingBrain extends Enemy{
	public FlyingBrain(double X, double Y) {
		super(17, X, Y);
	}
	
	//@Overrides Enemy Class
	public void moveBehavior(double xIn, double yIn) 
	{
		
		
		
		double speed = stats.getSpeed();
		
		eX += speed*Math.cos(theta);
		eY += speed*Math.sin(theta);

	}
	
	public void attackBehavior(double xIn, double yIn) {
		System.out.println("Attacking the player...");
		for (int i = 0;i<5;i++)
		{
			projectiles.add(new Projectile(17, eX, eY, theta + (double)i * Math.PI/2.5, .1));
		}
	}
}
