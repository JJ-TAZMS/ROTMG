package game;

public class GelatinousCube extends Enemy{

	public GelatinousCube(double X, double Y) {
		super(2, X, Y);
	}

	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			System.out.println("Attacking the player...");
			projectiles.add(new Projectile(2, eX, eY, theta, .1));
		}

	
}