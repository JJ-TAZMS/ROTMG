package game;

public class GelatinousCube extends Enemy{

	public GelatinousCube(double X, double Y) {
		//Add randomization for type of gelatinous cube here
		super(2, X, Y);
	}

	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			
			boolean wander = (stats.getAtkWait()%10 == 0);
			
			if (wander)
			{
				double rndTheta = (Math.random()*Math.PI*2);
				xVel = ((Math.random()>=.5)? 1: -1)*speed*Math.cos(rndTheta);
				yVel = ((Math.random()>=.5)? 1: -1)*speed*Math.sin(rndTheta);
			}
			
			eX += xVel;
			eY += yVel;
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			System.out.println("Attacking the player...");
			projectiles.add(new Projectile(2, eX, eY, theta, .1));
		}

	
}
