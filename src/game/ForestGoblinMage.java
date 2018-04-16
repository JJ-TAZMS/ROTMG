package game;

public class ForestGoblinMage extends Enemy{
	public ForestGoblinMage(double X, double Y) {
		super(14, X, Y);
	}

	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			double speed = stats.getSpeed();
			double dist = Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn));
			
			if (dist >=5)
			{
				eX += speed*Math.cos(theta);
				eY += speed*Math.sin(theta);
			}else
			{
				eX -= .6*speed*Math.cos(theta);
				eY -= .6*speed*Math.sin(theta);
			}
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			System.out.println("Attacking the player...");
			projectiles.add(new Projectile(14, eX, eY, theta, .1));
		}

	

}
