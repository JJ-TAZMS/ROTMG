package game;

public class GiantCrab extends Enemy{

	public GiantCrab(double X, double Y) {
		super(9, X, Y);
	}
	
	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			double playerDist = (Math.sqrt((eX - xIn)*(eX - xIn) + 
					(eY - yIn)*(eY - xIn))) ;
			//keeps a distance from the player
			//Ask Sean about stats.getMoveDist();
			if (playerDist > 40) {
				eX += speed*Math.cos(theta);
				eY += speed*Math.sin(theta);
			}
			else if (playerDist < 40) {
				eX -= speed*Math.cos(theta);
				eY -= speed*Math.sin(theta);
			}
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			//System.out.println("Attacking the player...");
			projectiles.add(new Projectile(9, eX, eY, theta, .1));
		}

	
}
