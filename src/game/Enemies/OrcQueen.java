package game;

public class OrcQueen extends ChildEnemy {

	public OrcQueen(Enemy e, double x, double y) {
		super(e, 20, x, y);
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
			parentEnemy.getStats().sethp((int)parentEnemy.getStats().gethp() + 75);
		}

}
