package game;

public class Pirate extends Enemy{

	public Pirate(double X, double Y) {
		super(1, X, Y);
	}
	
	//@Overrides Enemy Class
	public void moveBehavior(double xIn, double yIn) 
	{
		double dX = eX - xIn;
		double dY = eY - yIn;
		
		theta = Math.atan(dY/dX);

		if(xIn < eX)
		{
			theta += Math.PI;
		}
		
		
		double speed = stats.getSpeed();
		
		eX += speed*Math.cos(theta);
		eY += speed*Math.sin(theta);
		/*
		if(xIn > eX) {
			eX += (speed*Math.cos(theta));
		}
		if(xIn < eX) {
			eX -= (speed*Math.cos(theta));
		}
		if(yIn > eY) {
			eY += (speed*Math.sin(theta));
		}
		if(yIn < eY) {
			eY -= (speed*Math.sin(theta));
		}
		*/
	}
	
	public void attackBehavior(double xIn, double yIn) {
		
	}

}
