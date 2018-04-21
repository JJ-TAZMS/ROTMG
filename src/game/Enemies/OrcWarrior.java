package game;

public class OrcWarrior extends ChildEnemy {

	public OrcWarrior(Enemy e, double x, double y) {
		super(e, 21, x, y);
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
			xVel = speed*Math.cos(theta);
			yVel = speed*Math.sin(theta);
		}
		
		eX += xVel;
		eY += yVel;
		}
		
		public void attackBehavior(double xIn, double yIn) {
			projectiles.add(new Projectile(21, eX, eY, theta, .1));
		}

}
