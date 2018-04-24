package game.Enemies;

import java.awt.image.BufferedImage;

import game.Enemy;
import game.Projectile;

public class ForestGoblin extends Enemy{
	public ForestGoblin(double X, double Y, BufferedImage img) {
		super(6, X, Y, img);
	}

	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			double speed = stats.getSpeed();
			
				eX += speed*Math.cos(theta);
				eY += speed*Math.sin(theta);
			
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			System.out.println("Attacking the player...");
			projectiles.add(new Projectile(6, eX, eY, theta, .1, stats.getAttack2()));
		}

}
