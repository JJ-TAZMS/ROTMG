package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	
	private int enemyID;
	private int size;
	protected double eX, eY, xVel, yVel;
	protected double theta;
	
	public EnemyStats stats;
	
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); 
	
	
	public Enemy(int ID, double X, double Y){
		
		enemyID = ID;
		eX = X;
		eY = Y;
		xVel = yVel = 0;
		size = Tile.TILESIZE*Game.SCALE;
		stats = new EnemyStats(ID);
		
	}
	
	public void render(Graphics g, double xIn, double yIn) {
		
		double xP = ((-xIn) + (eX))*Tile.TILESIZE;
		double yP = ((-yIn) + (eY))*Tile.TILESIZE;
		
		g.setColor(Color.GREEN);
		g.fillOval((int) (Game.SCALE*(xP + Game.WIDTH/2)) - size/2, (int) (Game.SCALE*(yP + Game.HEIGHT/2)) - size/2, size, size);
		
		
	}
	
	public void tick(double xIn, double yIn) {

		
		double distFromPlayer = Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn));
		stats.setAtkWait(stats.getAtkWait()-1);
		//System.out.println("distFromPlayer " + distFromPlayer + " == " + stats.getMoveDist());

		if (distFromPlayer < Game.WIDTH/Tile.TILESIZE * 3)
		{
			stats.setActive(true);
			
		}	else
		{
			stats.setActive(false);
		}
		
		
		if (stats.getActive())
		{
			if(distFromPlayer < stats.getMoveDist()) {
				setTheta(xIn, yIn);
				
				moveBehavior(xIn, yIn);
				
				if (distFromPlayer < stats.getAtkDist()) {
					if((stats.getAtkWait() <= 0))
					{
						attackBehavior(xIn, yIn);
						
						stats.setAtkWait( (int) (360/stats.getDexterity()));
					}
					
				}
			}
			
			
			//Worst comes to worst take this out of the active if statement
			for (int i = 0; i < projectiles.size(); i++)
			{
				projectiles.get(i).tick();
				if (projectiles.get(i).getDist() > projectiles.get(i).getRange())
				{
					projectiles.remove(i);
					i--;
					continue;
				}
				
					
				if (((Math.sqrt((xIn - projectiles.get(i).getX())*(xIn- projectiles.get(i).getX()) + (yIn - projectiles.get(i).getY())*(yIn - projectiles.get(i).getY()))))<=0.5){
					System.out.println("Enemy has done " + projectiles.get(i).getDamage() + " damage to the player!");
					projectiles.remove(i);
					i--;
				}	
			}
		}
		
		
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
	}
	
	public void moveBehavior(double xIn, double yIn) {
		
	}
	
	public void attackBehavior(double xIn, double yIn) {
		
	}

	public EnemyStats getStats()	{	return stats;	}
	public ArrayList<Projectile> getProj()	{	return projectiles;	}
	public double getX()	{	return eX;	}
	public double getY()	{	return eY;	}
}
