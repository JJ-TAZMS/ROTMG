package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile {
	private double xPos, yPos;
	private double xVel, yVel;
	private double dist;
	private double maxRange;
	private double damage;
	private boolean isEnemy;
	private double height;
	private double width;
	private BufferedImage img;

	//Index that represents the projectile, its position, and its angle and speed
	public Projectile(int index, double x, double y, double theta, double vel, double dam) {
		xPos = x;
		yPos = y;
		
		dist = 0;

		// TODO add constants for sizes / maxRange based on index
		//height = img.getHeight();
		//width = img.getWidth();
		if (index == 0)
		{
			maxRange = 10;
			damage = dam;
			isEnemy = false;
			/*
			try {
				img = ImageIO.read(new File("/.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}	else
		{
			maxRange = new EnemyStats(index).getAtkDist()*2;
			damage = dam;
			isEnemy = true;
			vel /= 4;
		}
		
		xVel = vel * Math.cos(theta);
		yVel = vel * Math.sin(theta);
		
		
	}

	public void tick()
	{	
			//Update position
			xPos += xVel;
			yPos += yVel;
			
			//add distance to range max distance
			dist += Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel,  2));
			
			//System.out.println(xPos + ", " + yPos);
	}

	public void render(Graphics g, double xIn, double yIn) 
	{
		double xP = ((-xIn) + (xPos))*Tile.TILESIZE;
		double yP = ((-yIn) + (yPos))*Tile.TILESIZE;
		
		g.setColor(Color.BLACK);
		if (!isEnemy)
		{
			g.setColor(Color.GREEN);
		}
		
		//g.fillRect((int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);

		
		
		//System.out.println(xP + ", " + yP);
		
		g.drawOval((int) (Game.SCALE*(xP + Game.WIDTH/2)) - 25, (int) (Game.SCALE*(yP + Game.HEIGHT/2)) - 25, 50, 50);
		//g.drawImage(img, Game.WIDTH / 2, Game.HEIGHT / 2, img.getHeight(), img.getWidth(), null);
	}
	
	public boolean checkDelete()
	{
		return dist >= maxRange;
	}
	
	// Getters
	
	public double getDist()	{		return dist;	}
	public double getRange()	{		return maxRange;	}
	public double getX()	{	return xPos;	}
	public double getY()	{	return yPos;	}
	public double getDamage()	{	return damage;	}
}