package Game;
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

	
	Projectile(int index, double theta, int x, int y, boolean src, int vel)
	{
		xPos = x;
		yPos = y;
		xVel = vel * Math.cos(theta);
		yVel = vel * Math.sin(theta);
		//range = index
		//damage = index + attack
		//isEnemy = (index > #);
		//img = index;
		
		//TODO add constants for sizes / maxRange based on index
		height = img.getHeight();
		width = img.getWidth();
		maxRange = 100;
		
		dist = 0;
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
			
			//Intersections will be checked in the main Game class
			/*
			if (!isEnemy) //Source is a player, can kill enemies (so check all enemy hits)
			{
					if ((en.getxPos() + en.getWidth() > xPos + width/2 && en.getxPos() < xPos + width/2) && (en.getyPos() - en.getHeight() < yPos - height/2 && en.getyPos() > yPos + height/2))
					{
						en.setHitPoints(en.gethitPoints() - damage);
						enL.set(i,en);
						Map.setEnemies(enL);
					
					}	
				}
			}
			else
			{	
				if ((Player.getxPos() + Player.getWidth() > xPos + width/2 && Player.getxPos() < xPos + width/2) && (Player.getyPos() - Player.getHeight() < yPos - height/2 && Player.getyPos() > yPos + height/2))
				{
						Player.setstats(Player.getstats().sethitPoints(Player.getstats().gethitPoints()-damage));

				}
			}
			*/
	
	private void render(Graphics g)
	{
		g.drawImage(Game.WIDTH/2,Game.HEIGHT/2,img.getHeight(),img.getWidth(), null);
	}
}
