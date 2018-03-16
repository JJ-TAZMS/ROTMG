package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
	Projectile(int index, int x, int y, double theta, int vel) {
		xPos = x;
		yPos = y;
		xVel = vel * Math.cos(theta);
		yVel = vel * Math.sin(theta);
		dist = 0;

		// TODO add constants for sizes / maxRange based on index
		height = img.getHeight();
		width = img.getWidth();
		maxRange = 100;
		damage = 10;
		isEnemy = true;
		
	}

	private void tick()
	{	
			//Update position
			xPos += xVel;
			yPos += yVel;
			
			//add distance to range max distance
			dist += Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel,  2));
			if (dist >= maxRange)
			{
				xVel = 0;
				yVel = 0;
			}
	}

	private void render(Graphics g) 
	{
		g.drawImage(img, Game.WIDTH / 2, Game.HEIGHT / 2, img.getHeight(), img.getWidth(), null);
	}
}
