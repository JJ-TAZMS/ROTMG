package game.Enemies;

import java.awt.image.BufferedImage;

import game.Enemy;
import game.Projectile;

public class GiantCrab extends Enemy{

	private double wanderTheta;
	public GiantCrab(double X, double Y, BufferedImage img) {
		super(9, X, Y, img);
		wanderTheta = 0;
	}
	
	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
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
		
		public void attackBehavior(double xIn, double yIn) {
			
			System.out.println("Attacking the player...");
			projectiles.add(new Projectile(9, eX, eY, theta, .1));
		}

	
}