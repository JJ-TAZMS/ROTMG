package game.Enemies;

import java.awt.image.BufferedImage;

import game.Bomb;
import game.Enemy;
import game.Projectile;

public class EntGod extends Enemy{
	private double wanderTheta;

	public EntGod(double X, double Y, BufferedImage img) {
		super(25, X, Y, img);
		wanderTheta = 0;
	}

	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			double playerDist = (Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn))) ;
			//keeps a distance of 5 tiles from the player
			if (playerDist < 2) {
				boolean wander = (stats.getAtkWait()%10 == 0);
				
				if (wander)
				{
					double changeTheta = Math.PI;
					wanderTheta += (Math.random()*changeTheta - changeTheta/2);
					xVel = speed*Math.cos(wanderTheta);
					yVel = speed*Math.sin(wanderTheta);
				}
			}
			else if (playerDist > 2) {
				xVel = speed*Math.cos(theta);
				yVel = speed*Math.sin(theta);
			}
			
			eX += xVel;
			eY += yVel;
		}
		
		public void attackBehavior(double xIn, double yIn, double xVel, double yVel) {
			
			//Calculate where the player WILL BE and shoot there
			//double playerDist = (Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn))) ;
			
			
			/*
			 * double dX = eX - xIn;
		double dY = eY - yIn;
		
		theta = Math.atan(dY/dX);

		if(xIn < eX)
		{
			theta += Math.PI;
		}
			 */
		
			
			//System.out.println("Player Direction Theta: " + pTheta);
			//System.out.println("Enemy to Player Theta: " + eTheta);
			//System.out.println("Angle P: " + angleP);
			double projectileSpeed = .1;
			projectiles.add(new Projectile(25, eX, eY, thetaPredict(xIn, yIn, xVel, yVel, projectileSpeed), projectileSpeed, stats.getAttack2()));
		}
}