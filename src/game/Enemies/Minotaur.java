package game.Enemies;

import game.Enemy;
import game.Projectile;

public class Minotaur extends Enemy{

	public Minotaur(double X, double Y) {
		super(11, X, Y);
	}
	
	private boolean isAdvancing = true;
	
	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			double playerDist = (Math.sqrt((eX - xIn)*(eX - xIn) + 
					(eY - yIn)*(eY - xIn))) ;
			
			//charge player at faster than normal speed
			if (playerDist>10 && isAdvancing) {
				eX += speed*Math.cos(theta)*3;
				eY += speed*Math.sin(theta)*3;
			}
			if (playerDist<=10 && isAdvancing) { isAdvancing = false; }
			
			//retreat at slower than normal speed
			if (playerDist<40 && !isAdvancing){
				eX -= speed*Math.cos(theta)/2;
				eY -= speed*Math.sin(theta)/2;
			}
			if (playerDist>=40 && !isAdvancing) { isAdvancing = true; }
			
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			//System.out.println("Attacking the player...");
			projectiles.add(new Projectile(11, eX, eY, theta, .1));
		}

	
}
