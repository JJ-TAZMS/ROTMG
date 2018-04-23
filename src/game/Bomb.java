package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bomb {

	
	private double xPos, yPos;
	private double xVel, yVel;
	private double dist;
	private double maxRange;
	private double damage;
	private int index;
	private BufferedImage img;
	private ArrayList<Projectile> bombSpread;
	
	public Bomb(int ind, double x, double y, double theta)
	{
		xPos = x;
		yPos = y;
		index = ind;
		
		double vel = .2/4.0;
		xVel = vel*Math.cos(theta);
		yVel = vel*Math.sin(theta);
		
		maxRange = new EnemyStats(index).getAtkDist2();
		damage = new EnemyStats(index).getAttack2();
		bombSpread = new ArrayList<Projectile> ();
		
	}
	
	public void tick()
	{	
		
			//System.out.println("Bomb Dist: " + dist);
			if (dist == -1)//Move projectiles
			{
				for (int i = 0; i < bombSpread.size(); i++)
				{
					bombSpread.get(i).tick();
					if (bombSpread.get(i).getDist() > bombSpread.get(i).getRange())
					{
						//System.out.println("Removing a bomb projectile");
						bombSpread.remove(i);
						i--;
					}
				}
			}	else if (dist < maxRange) //If the bomb should be moving, move it
			{
				//Update position
				//System.out.println("Updating Bomb Pos");
				xPos += xVel;
				yPos += yVel;
				
				//add distance to range max distance
				dist += Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel,  2));
			}	else //Create projectiles
			{
				//System.out.println("Adding a bunch of porjectiles for the bomb");
				xVel = yVel = 0;
				dist = -1;
				for (double bombTheta = 0; bombTheta < 2*Math.PI; bombTheta+= Math.PI/4)
				{
					bombSpread.add(new Projectile(index, xPos, yPos, bombTheta, .2));
				}
			}
			
			
			//System.out.println(xPos + ", " + yPos);
	}

	public void render(Graphics g, double xIn, double yIn) 
	{
		double xP = ((-xIn) + (xPos))*Tile.TILESIZE;
		double yP = ((-yIn) + (yPos))*Tile.TILESIZE;
		
		g.setColor(Color.RED);
		
		//g.fillRect((int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);

		
		
		//System.out.println(xP + ", " + yP);
		if (dist == -1) //Display projectiles
		{
			for (Projectile p : bombSpread)
			{
				p.render(g,  xIn,  yIn);
			}
		}	else
		{
			g.drawOval((int) (Game.SCALE*(xP + Game.WIDTH/2)) - 5, (int) (Game.SCALE*(yP + Game.HEIGHT/2)) - 5, 10, 10);

		}
		
		
		//g.drawImage(img, Game.WIDTH / 2, Game.HEIGHT / 2, img.getHeight(), img.getWidth(), null);
	}
	
	
	// Getters
	
public boolean shouldDelete()	{	return bombSpread.size() == 0 && dist == -1;	}
	public double getDist(){		return dist;	}
	
	public double getRange()	{		return maxRange;	}
}
