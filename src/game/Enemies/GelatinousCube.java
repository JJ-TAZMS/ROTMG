package game.Enemies;

import game.Enemy;
import game.Projectile;

public class GelatinousCube extends Enemy{

	private double wanderTheta;
	
	public GelatinousCube(double X, double Y) {
		//Add randomization for type of gelatinous cube here
		super(2, X, Y);
		wanderTheta = 0;
	}

	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			
			boolean wander = (stats.getAtkWait()%10 == 0);
			
			if (wander)
			{
				double changeTheta = Math.PI;
				wanderTheta += (Math.random()*changeTheta - changeTheta/2);
				xVel = speed*Math.cos(wanderTheta);
				yVel = speed*Math.sin(wanderTheta);
			}
			
			eX += xVel;
			eY += yVel;
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			System.out.println("Attacking the player...");
			projectiles.add(new Projectile(2, eX, eY, theta, .1));
		}

	
}
