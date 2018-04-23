package game;

import java.awt.image.BufferedImage;

public class ChildEnemy extends Enemy{

	protected double thetaToParent;
	protected Enemy parentEnemy;
	
	public ChildEnemy(Enemy e, int ID, double X, double Y, BufferedImage img) {
		super(ID, X, Y, img);
		thetaToParent = 0;
		parentEnemy = e;
	}

	public void setTheta(double xIn, double yIn)
	{
		double dX = eX - xIn;
		double dY = eY - yIn;
		
		theta = Math.atan(dY/dX);

		if(xIn < eX)
		{
			theta += Math.PI;
		}
		
		
		double edX = eX - parentEnemy.getX();
		double edY = eY - parentEnemy.getY();

		thetaToParent = Math.atan(edY/edX);

		if (parentEnemy.getX() < eX)
		{
			thetaToParent += Math.PI;
		}
	}
}
