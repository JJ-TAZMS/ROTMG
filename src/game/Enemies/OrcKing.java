package game.Enemies;

import java.awt.image.BufferedImage;

import game.Enemy;
import game.Projectile;

public class OrcKing extends Enemy{

	public OrcKing(double X, double Y, BufferedImage img) {
		//Add randomization for Pirate vs Piratess here
		super(19, X, Y, img);
	}
	
	//@Overrides Enemy Class
	public void moveBehavior(double xIn, double yIn) 
	{
		
		double speed = stats.getSpeed();
		double playerDist = (Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn))) ;
		//keeps a distance of 1 tiles from the player
		if (playerDist < .5)
		{
				boolean wander = (stats.getAtkWait()%15 == 0);
				
				if (wander)
				{
					double rndTheta = (Math.random()*Math.PI*2);
					xVel = (speed*Math.cos(rndTheta))/2.0;
					yVel = (speed*Math.sin(rndTheta))/2.0;
				}
				
				
		}	else {
			xVel = speed*Math.cos(theta);
			yVel = speed*Math.sin(theta);
		}
		
		eX += xVel;
		eY += yVel;
		
	}
	
	public void attackBehavior(double xIn, double yIn) {
		projectiles.add(new Projectile(19, eX, eY, theta, .1, stats.getAttack()));
	}

}
