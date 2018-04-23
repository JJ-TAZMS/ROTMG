package game.Enemies;

import java.awt.image.BufferedImage;

import game.ChildEnemy;
import game.Enemy;
import game.Projectile;

public class Bandit extends ChildEnemy {

	public Bandit(Enemy e, double x, double y, BufferedImage img) {
		super(e, 15, x, y, img);
	}
	
	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			double toLeaderDist = (Math.sqrt((eX - parentEnemy.getX())*(eX - parentEnemy.getX()) + (eY - parentEnemy.getY())*(eY - parentEnemy.getY()))) ;
			
			
			
			
			//keeps a distance of 1 tiles from the player
			if (toLeaderDist < 5)
			{
					boolean wander = (stats.getAtkWait()%15 == 0);
					
					if (wander)
					{
						double rndTheta = (Math.random()*Math.PI*2);
						xVel = (speed*Math.cos(rndTheta))/2.0;
						yVel = (speed*Math.sin(rndTheta))/2.0;
					}
					
					
			}	else {
				xVel = speed*Math.cos(thetaToParent);
				yVel = speed*Math.sin(thetaToParent);
			}
			
			eX += xVel;
			eY += yVel;
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			projectiles.add(new Projectile(1, eX, eY, theta, .1));
		}

}
