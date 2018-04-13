package game;

public class Pirate extends Enemy{

	public Pirate(double X, double Y) {
		super(1, X, Y);
	}
	
	//@Overrides Enemy Class
	public void moveBehavior(double xIn, double yIn) 
	{
		
		double speed = stats.getSpeed();
		
		eX += speed*Math.cos(theta);
		eY += speed*Math.sin(theta);
	}
	
	public void attackBehavior(double xIn, double yIn) {
		
	}

}
