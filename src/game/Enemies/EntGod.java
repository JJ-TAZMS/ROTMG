package game.Enemies;

import game.Bomb;
import game.Enemy;
import game.Projectile;

public class EntGod extends Enemy{
	private double wanderTheta;

	public EntGod(double X, double Y) {
		super(25, X, Y);
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
			
			double angleToShoot = theta;
			
			
			double playerSpeed = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
			double projectileSpeed = .1;
			
			
			//Make eTheta and pTheta within [-PI, PI]
			double eTheta = theta;
			double pTheta = Math.atan(yVel/xVel);
			
			if (xVel < 0)
			{
				pTheta += Math.PI;
			}
			
			if (eTheta > Math.PI)
			{
				eTheta -= Math.PI*2;
			}
			if (pTheta > Math.PI)
			{
				pTheta -= Math.PI*2;
			}
			
			//Calculate Player's Angle within triangle (always positive)
			double angleP = Math.abs((Math.PI + (eTheta - pTheta))%Math.PI);
			
			
			//Calculate what angle off of eTheta we must shoot.
			double angleE = 0;
			if (playerSpeed != 0)
			{
				angleE = Math.asin(playerSpeed/projectileSpeed * Math.sin(angleP));
			}
			
			//Figure out which way to add it
			int eDirection = -1;
			if ((eY > yIn && xVel > 0) || (eY < yIn && xVel < 0))
			{
				eDirection = 1;
			}
			
			angleToShoot += eDirection*angleE;
			
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
			
			projectiles.add(new Projectile(25, eX, eY, angleToShoot, projectileSpeed));
		}
}