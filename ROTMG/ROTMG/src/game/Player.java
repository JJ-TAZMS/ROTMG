package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
	private double x, y, xVel, yVel, theta;
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private boolean attacking, canAttack;
	private int index;
	private BufferedImage[] image;
	private BufferedImage img;
	private GUI gui;
	private Stats stats;
	private ArrayList<Projectile> projectiles;
	private int itemID;
	private boolean itemInHand;

	public Player(Chunk[][] map, int index, SpriteSheet ss) // Sets class type with index
	{
		stats = new Stats(index);
		gui = new GUI(map, stats);
		projectiles = new ArrayList<Projectile>();
		x = y = theta = 0;
		
		//TODO Check class index for picture
		image = new BufferedImage[4];
		image[0] = ss.grabImage(8, 29, 1, 1); //Right
		image[1] = ss.grabImage(9, 29, 1, 1); //Down
		image[2] = ss.grabImage(10, 29, 1, 1); //Left
		image[3] = ss.grabImage(11, 29, 1, 1); //Up
		img = image[1];
	}

	public void tick() // Update game logic for player (stats, pos, etc.)
	{
		move();
		projectileTick();
		attack();
	}
	
	public void projectileTick()
	{
		for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).tick();
			if(projectiles.get(i).checkDelete())
			{
				projectiles.remove(i);
				i--;
			}
		}
	}
	
	public void move()
	{
		if(moveUp)
		{
			yVel = stats.getSpeed() * -1;
		}
		if(moveDown)
		{
			yVel = stats.getSpeed();
		}
		if(moveRight)
		{
			xVel = stats.getSpeed();
		}
		if(moveLeft)
		{
			xVel = stats.getSpeed() * -1;
		}
		if(!moveUp && !moveDown)
		{
			yVel = 0;
		}
		if(!moveRight && !moveLeft)
		{
			xVel = 0;
		}
		if((moveUp && moveRight) || (moveUp && moveLeft) || (moveDown && moveRight) || (moveDown && moveLeft))
		{
			xVel = xVel / Math.sqrt(2.0);
			yVel = yVel / Math.sqrt(2.0);
		}
		x += xVel;
		y += yVel;
	}

	public void render(Graphics g) // Update picture for player
	{
		double vel = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
		if (vel != 0)
		{
			if (xVel < 0) //Left has priority in pictures
			{
				img = image[2];
			}	else if (yVel > 0) //Down is next
			{
				img = image[1];
			}	else if (xVel > 0)
			{
				img = image[0]; //Then right
			}	else
			{
				img = image[3]; //And lastly up
			}
		}
		g.drawImage(img, Game.SCALE*(Game.WIDTH-Tile.TILESIZE)/2, Game.SCALE*(Game.HEIGHT-Tile.TILESIZE)/2, Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE, null);
		for(Projectile p : projectiles)
		{
			p.render(g);
		}
		//g.drawRect((int)x, (int)y, 50, 50);
		//gui.render(g);
	}
	
	public void controlPressed(char k) //Takes key input and decides what to do
	{
		System.out.println("Key Pressed: " + k);
		if(k == 'w')
		{
			moveUp = true;
		}
		if(k == 's')
		{
			moveDown = true;
		}
		if(k == 'a')
		{
			moveLeft = true;
		}
		if(k == 'd')
		{
			moveRight = true;
		}
	}
	
	public void controlReleased(char k) //used for deceleration and such
	{
		if(k == 'w')
		{
			moveUp = false;
		}
		if(k == 's')
		{
			moveDown = false;
		}
		if(k == 'a')
		{
			moveLeft = false;
		}
		if(k == 'd')
		{
			moveRight = false;
		}
	}
	
	public void mouseClick(int k)
	{
		if(k == 1)
		{
			if(!attacking)
			{
				
			}
			attacking = true;
		}
	}
	
	public void mouseReleased(int k)
	{
		if(k == 1)
		{
			attacking = false;
		}
	}
	
	public void attack()
	{
		
		if(attacking && canAttack)
		{
			double pTheta = Math.atan((Game.mouseY - Game.HEIGHT / 2.0 * Game.SCALE) / (Game.mouseX - Game.WIDTH / 2.0 * Game.SCALE));
			if(Game.mouseX < Game.WIDTH / 2 * Game.SCALE)
			{
				pTheta += Math.PI;
			}
			
			
			projectiles.add(new Projectile(index,Game.WIDTH / 2.0 * Game.SCALE,Game.HEIGHT / 2.0 * Game.SCALE, pTheta,2));
			System.out.println("Added new projectile");
			
		}
	}

	// Setters
	public void setX(int xc) {
		x = xc;
	}

	public void setY(int yc) {
		y = yc;
	}

	public void setItemInHand(boolean b) {
		itemInHand = b;
	}

	// Getters
	public double getXvel() {
		return xVel;
	}

	public double getYvel() {
		return yVel;
	}
	
	public double getX()	{
		return x;
	}
	
	public double getY()	{
		return y;
	}
	
	public double getTheta() {
		return theta;
	}

	public double getIndex() {
		return index;
	}

	public BufferedImage getImage() {
		return img;
	}

	public boolean getItemInHand() {
		return itemInHand;
	}

	public Stats getStats() {
		return stats;
	}
}
