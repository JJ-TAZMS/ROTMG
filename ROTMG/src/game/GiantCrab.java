package game;

public class GiantCrab extends Enemy{

	public GiantCrab(double X, double Y) {
		super(1, X, Y);
	}
	
	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			double playerDist = (Math.sqrt((eX - Game.player1.getX())*(eX - Game.player1.getX()) + 
					(eY - Game.player1.getY())*(eY - Game.player1.getX()))) ;
			//keeps a distance of 5 from the player
			if (playerDist > 50) {
				eX += speed*Math.cos(theta);
				eY += speed*Math.sin(theta);
			}
			else if (playerDist < 50) {
				eX -= speed*Math.cos(theta);
				eY -= speed*Math.sin(theta);
			}
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			System.out.println("Attacking the player...");
			projectiles.add(new Projectile(1, eX, eY, theta, .1));
		}

	
}
