package game.Enemies;

import game.Enemy;
import game.Projectile;

public class Medusa extends Enemy{

	private double wanderTheta;
	private double attackStage;
	
	public Medusa(double X, double Y) {
		super(23, X, Y);
		wanderTheta = 0;
		attackStage = 0;
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
			attackStage ++;
			//Typically shoot out 5 spread of projectiles
			//System.out.println("Attacking the player...");
			if (attackStage >= 3) //Every three times the spread is fired, throw a bomb
			{
				attackStage = 0;
				//Bomb
			}
			
			projectiles.add(new Projectile(9, eX, eY, theta, .1));
			for (double cT = Math.PI/12; cT <= Math.PI/6; cT += Math.PI/12)
			{
				projectiles.add(new Projectile(9, eX, eY, theta+cT, .1));
				projectiles.add(new Projectile(9, eX, eY, theta-cT, .1));
			}
		}

	
}
