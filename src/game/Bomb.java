package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bomb {

	
	private double xPos, yPos;
	private double xVel, yVel;
	private double dist;
	private double maxRange;
	private double damage;
	private double height;
	private double width;
	private BufferedImage img;
	
	public Bomb(int index, double x, double y, double theta)
	{
		xPos = x;
		yPos = y;
		
		
		double vel = .2;
		xVel = vel*Math.cos(theta);
		yVel = vel*Math.sin(theta);
		
		maxRange = new EnemyStats(index).getAtkDist2();
		damage = new EnemyStats(index).getAttack2();
		
	}
	
	public void tick()
	{	
			//Update position
			xPos += xVel;
			yPos += yVel;
			
			//add distance to range max distance
			dist += Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel,  2));
			
			System.out.println(xPos + ", " + yPos);
	}

	public void render(Graphics g, double xIn, double yIn) 
	{
		double xP = ((-xIn) + (xPos))*Tile.TILESIZE;
		double yP = ((-yIn) + (yPos))*Tile.TILESIZE;
		
		g.setColor(Color.RED);
		
		//g.fillRect((int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);

		
		
		System.out.println(xP + ", " + yP);
		
		g.drawOval((int) (Game.SCALE*(xP + Game.WIDTH/2)) - 5, (int) (Game.SCALE*(yP + Game.HEIGHT/2)) - 5, 10, 10);
		//g.drawImage(img, Game.WIDTH / 2, Game.HEIGHT / 2, img.getHeight(), img.getWidth(), null);
	}
	
	public boolean checkDelete()	{		return dist >= maxRange;	}
	
	// Getters
	
	public double getDist(){		return dist;	}
	
	public double getRange()	{		return maxRange;	}
}
