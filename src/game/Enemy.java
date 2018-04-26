package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	
	private int enemyID;
	private int size;
	protected double eX, eY, xVel, yVel;
	protected double theta;
	private BufferedImage img;
	protected double wanderTheta;
	
	public EnemyStats stats;
	
	protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); 
	protected ArrayList<Bomb> bombs = new ArrayList<Bomb> ();
	
	
	public Enemy(int ID, double X, double Y, BufferedImage i){
		
		enemyID = ID;
		eX = X;
		eY = Y;
		xVel = yVel = 0;
		size = Tile.TILESIZE*Game.SCALE;
		stats = new EnemyStats(ID);
		img = i;
		
	}
	
	public void render(Graphics g, double xIn, double yIn) {
		
		double xP = ((-xIn) + (eX))*Tile.TILESIZE;
		double yP = ((-yIn) + (eY))*Tile.TILESIZE;
		
		g.setColor(Color.GREEN);
		g.drawImage(img, (int) (Game.SCALE*(xP + Game.WIDTH/2 - Tile.TILESIZE/2 )), (int) (Game.SCALE*(yP + Game.HEIGHT/2 - Tile.TILESIZE/2)), Game.SCALE*Tile.TILESIZE, Game.SCALE*Tile.TILESIZE, null);
		//g.fillOval((int) (Game.SCALE*(xP + Game.WIDTH/2)) - size/2, (int) (Game.SCALE*(yP + Game.HEIGHT/2)) - size/2, size, size);
		
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
		//System.out.println("Enemy.java - Distance to player is: " + distFromPlayer);
		
		
		if (distFromPlayer < stats.getMoveDist()*1.5)
		{
			stats.setActive(true);
			
		}	else
		{
		
			stats.setActive(false);
			if ((distFromPlayer > Game.WIDTH/Tile.TILESIZE * 3))
			{
				stats.settooFar(true);
				System.out.println("Way too far. setting delete to true");
			}
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
			
		}	else
		{
			//System.out.println("Enemy wander: " + !stats.getActive() + " wanderin");
			boolean wander = (stats.getAtkWait()%30 == 0);
			
			if (wander)
			{
				double speed = stats.getSpeed();
				double changeTheta = Math.PI;
				wanderTheta += (Math.random()*changeTheta - changeTheta/2);
				xVel = (speed*Math.cos(wanderTheta))/2.0;
				yVel = (speed*Math.sin(wanderTheta))/2.0;
			}
			eX += xVel;
			eY += yVel;
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
	
	public double thetaPredict(double xIn, double yIn, double xVel, double yVel, double projectileSpeed)
	{
		double angleToShoot = theta;
		
		
		double playerSpeed = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
		
		
		//Make eTheta and pTheta within [-PI, PI]
		double eTheta = theta;
		double pTheta = Math.atan(yVel/xVel);
		
		if (xVel < 0)
		{
			pTheta += Math.PI;
		}
		
		if (eTheta > Math.PI)
		{
			eTheta -= Math.PI*2;
		}
		if (pTheta > Math.PI)
		{
			pTheta -= Math.PI*2;
		}
		
		//Calculate Player's Angle within triangle (always positive)
		double angleP = Math.abs((Math.PI + (eTheta - pTheta))%Math.PI);
		
		
		//Calculate what angle off of eTheta we must shoot.
		double angleE = 0;
		if (playerSpeed != 0)
		{
			angleE = Math.asin(playerSpeed/projectileSpeed * Math.sin(angleP));
		}
		
		//Figure out which way to add it
		int eDirection = -1;
		if ((eY > yIn && xVel > 0) || (eY < yIn && xVel < 0))
		{
			eDirection = 1;
		}
		
		angleToShoot += eDirection*angleE;
		return angleToShoot;
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
	
	public boolean hurtEnemy(int damage)
	{
		stats.sethp((int)stats.gethp()-damage);
		return (stats.gethp() <= 0);
	}
}