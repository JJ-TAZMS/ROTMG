package game;

public class Urgle extends Enemy{

	public Urgle(double X, double Y) {
		super(1, X, Y);
	}
	
	private boolean isAdvancing = true;
	
	//@Overrides Enemy Class
		public void moveBehavior(double xIn, double yIn) 
		{
			
			double speed = stats.getSpeed();
			double playerDist = (Math.sqrt((eX - xIn)*(eX - xIn) + 
					(eY - yIn)*(eY - xIn))) ;
			
			//get within certain tile distance of player then retreat back to farther tile distance. Repeat
			if (playerDist>30 && isAdvancing) {
				eX += speed*Math.cos(theta);
				eY += speed*Math.sin(theta);
			}
			if (playerDist<=30 && isAdvancing) { isAdvancing = false; }
			
			if (playerDist<50 && !isAdvancing){
				eX -= speed*Math.cos(theta);
				eY -= speed*Math.sin(theta);
			}
			if (playerDist>=50 && !isAdvancing) { isAdvancing = true; }
			
			
		}
		
		public void attackBehavior(double xIn, double yIn) {
			
			//TODO give in weapon firing speed
			//System.out.println("Attacking the player...");
			projectiles.add(new Projectile(1, eX, eY, theta, .1));
		}

	
}