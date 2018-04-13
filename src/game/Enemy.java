package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	
	public int enemyID;
	public double eX, eY;
	public int size;
	public double theta;
	
	public EnemyStats stats;
	
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); 
	
	
	public Enemy(int ID, double X, double Y){
		
		enemyID = ID;
		eX = X;
		eY = Y;
		size = Tile.TILESIZE*Game.SCALE;
		stats = new EnemyStats(ID);
		
	}
	
	public void render(Graphics g, double xIn, double yIn) {
		
		double xP = ((-xIn) + (eX))*Tile.TILESIZE;
		double yP = ((-yIn) + (eY))*Tile.TILESIZE;
		
		g.setColor(Color.GREEN);
		g.fillOval((int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), size, size);
		
		
	}
	
	public void tick(double xIn, double yIn) {

		
		double distFromPlayer = Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn));
		stats.setAtkWait(stats.getAtkWait()-1);
		//System.out.println("distFromPlayer " + distFromPlayer + " == " + stats.getMoveDist());

		if(distFromPlayer < stats.getMoveDist()) {
			double dX = eX - xIn;
			double dY = eY - yIn;
			
			theta = Math.atan(dY/dX);

			if(xIn < eX)
			{
				theta += Math.PI;
			}
			
			moveBehavior(xIn, yIn);
			
			if (distFromPlayer < stats.getAtkDist()) {
				if((stats.getAtkWait() <= 0))
				{
					attackBehavior(xIn, yIn);
					
					stats.setAtkWait( (int) (360/stats.getDexterity()));
				}
				
			}
		}
		
		for (int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).tick();
			if (projectiles.get(i).getDist() > projectiles.get(i).getRange())
			{
				projectiles.remove(i);
				i--;
			}
		}
		
	}
	
	public void moveBehavior(double xIn, double yIn) {
		
	}
	
	public void attackBehavior(double xIn, double yIn) {
		
	}

	public Stats getStats()	{	return stats;	}
	public ArrayList<Projectile> getProj()	{	return projectiles;	}
}
