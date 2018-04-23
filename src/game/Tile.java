package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Tile {

	private int xPos;
	private int yPos;
	private int difficulty;
	private Obstacle obstacle;
	private BufferedImage image;
	private Color color; //Shown on the minimap
	private boolean beenRendered; //For the minimap (what to be shown)
	//private Color color;	//Would be bufferedImage for actual printing
	
	public static final int TILESIZE = 10;
	
	public Tile(int x, int y)
	{
		xPos = x;
		yPos = y;
		difficulty = 1;
		image = null;
		color = null;
		beenRendered = false;
		obstacle = null;
	}
	
	public Tile(int x, int y, int dif)
	{
		xPos = x;
		yPos = y;
		difficulty = dif;
		image = null;
		color = null;
		beenRendered = false;
		
		/*
		if (dif > 1 && dif < 5 && Math.random() <= .05)
		{
			obstacle = new Obstacle();
		}	else
		{
			obstacle = null;
		}
		*/
	}
	
	public Tile(int x, int y, Tile t)
	{
		xPos = x;
		yPos = y;
		difficulty = t.getDif();
		image = t.getImg();
		color = t.getColor();
		beenRendered = t.getRendered();
	}
	

	public void tick()
	{
		
	}
	
	//Display the Tile to the screen
	public void render(Graphics g, double xIn, double yIn)
	{
		//System.out.println();
		//System.out.println("Tile " + xPos + ", " + yPos + " near " + (int)(xIn*TILESIZE) + ", " + (int)(yIn*TILESIZE));

		//Where you are in the map + (where the tile is located in the map)
		//xIn and yIn are based on the tile you are on in the map

		double xP = -(xIn*TILESIZE) + (xPos);
		double yP = -(yIn*TILESIZE) + (yPos);
		
		
		g.drawImage(image, (int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), TILESIZE*Game.SCALE, TILESIZE*Game.SCALE, null);
		if (obstacle != null)
		{
			obstacle.render(g, (int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)));
		}
		//drawRotated(g, newX, newY, theta);
		//System.out.println("Wha");
	}
	
	
	public void renderNoRot(Graphics g, double xIn, double yIn, double theta)
	{
			double normaltheta = Math.atan((yPos - yIn*TILESIZE)/(xPos - xIn*TILESIZE));
			
			
			//this normal theta is only a reference angle
			
			if (xPos < xIn*TILESIZE) //If X is negative for reference angle
			{
				//System.out.println("Gotta move em");
				normaltheta = Math.PI + normaltheta;
			}
			
			
			double normalR = Math.sqrt(Math.pow(yPos - yIn*TILESIZE, 2) + Math.pow(xPos - xIn*TILESIZE, 2));
			
			double xP = (Math.cos(normaltheta+theta)*normalR);
			double yP = (Math.sin(normaltheta+theta)*normalR);
			
			obstacle.render(g, (int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)));
	}
	

	//public Color getColor()	{	return color;	}
	public void setImg(Color clr, BufferedImage tileImage)
	{	
		color = clr;
		image = tileImage;
	}
	
	public void setColor(Color clr)	{	color = clr;	}
	
	public void addDif()	{	difficulty++;	}
	public void setDif(int n)	{	difficulty = n;	}
	public int getDif() {	return difficulty;	}
	
	public boolean getRendered()	{	return beenRendered;	}
	public void setRendered(boolean b) {	beenRendered = b;	}
	
	public void setPos(int x, int y)	{	xPos = x;	yPos = y;	}
	public int getX()	{	return xPos;	}
	public int getY()	{	return yPos;	}
	
	public Obstacle getObst()	{	return obstacle;	}
	public void setObst(Obstacle obst)	{	obstacle = obst;	}
	public Color getColor()	{	return color;	}
	public BufferedImage getImg()	{	return image;	}
}
