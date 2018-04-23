package game.Enemies;

import java.awt.image.BufferedImage;

import game.Enemy;
import game.Projectile;

public class BanditLeader extends Enemy	{
	
	private boolean grenade;
	private int delay = 1000;
	
	public BanditLeader(double x, double y, BufferedImage img)
	{
		super(16,x,y, img);
		grenade = true;
	}
	
	public void moveBehavior(double xIn, double yIn)
	{
		double speed = stats.getSpeed();
		
		eX += speed*Math.cos(theta);
		eY += speed*Math.sin(theta);
	}
	
	public void attackBehavior(double xIn, double yIn)
	{
		if(grenade)
		{
			delay = 1000;
			grenade = false;
			projectiles.add(new Projectile(1, eX, eY, theta, .3));
		}
		else
		{
			projectiles.add(new Projectile(1, eX, eY, theta, .1));
			grenade = delayAttack();
		}
	}
	
	public boolean delayAttack()
	{
		if(delay > 0)
		{
			delay--;
			return false;
		}
		
		return true;
	}

}
