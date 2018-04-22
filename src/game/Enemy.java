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
	
	protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); 
	protected ArrayList<Bomb> bombs = new ArrayList<Bomb> ();
	
	
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
		
		for (Projectile p : projectiles) 
		{
			p.render(g, xIn, yIn);
		}
		for (Bomb b : bombs)
		{
			b.render(g,  xIn,  yIn);
		}
		
	}
	
	public void tick(double xIn, double yIn, double pxVel, double pyVel) {

		
		double distFromPlayer = Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn));
		stats.setAtkWait(stats.getAtkWait()-1);
		//System.out.println("distFromPlayer " + distFromPlayer + " == " + stats.getMoveDist());
		System.out.println("Enemy.java - Distance to player is: " + distFromPlayer);
		
		
		if (distFromPlayer < Game.WIDTH/Tile.TILESIZE * 3)
		{
			stats.setActive(true);
			
		}	else
		{
			System.out.println("Enemy.java - Distance to player is: " + distFromPlayer + " tiles. Setting Active to False");
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
						if (enemyID < 25)
						{
							
							attackBehavior(xIn, yIn);
						}	else
						{
							attackBehavior(xIn, yIn, pxVel, pyVel);
						}
						
						
						stats.setAtkWait( (int) (360*4/stats.getDexterity()));
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
				}
			}
			for (int i = 0; i < bombs.size(); i++)
			{
				//System.out.println("TICKING BOMB");
				bombs.get(i).tick();
				if (bombs.get(i).shouldDelete()) {
					bombs.remove(i);
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
			//Its TAN inverse, when left and right, it was going directly away from the player.
			//Tan inverse can't tell the difference between two positives and two negatives
			//and which one is negative for the fraction inside
		}
		//System.out.println("Enemy Theta: " + theta);
	}
	
	public void moveBehavior(double xIn, double yIn) {
		
	}
	
	public void attackBehavior(double xIn, double yIn) {
		
	}
	
	public void attackBehavior(double xIn, double yIn, double xVel, double yVel) {
		
	}

	public EnemyStats getStats()	{	return stats;	}
	public ArrayList<Projectile> getProj()	{	return projectiles;	}
	public ArrayList<Bomb> getBombs()	{	return bombs;	}
	public double getX()	{	return eX;	}
	public double getY()	{	return eY;	}
}
