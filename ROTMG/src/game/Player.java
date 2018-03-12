package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
	private double x, y, xVel, yVel, theta;
	private double speed;
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private int index;
	private BufferedImage image;
	private GUI gui;
	private Stats stats;
	private ArrayList<Projectile> projectiles;
	private int itemID;
	private boolean itemInHand;

	public Player(Map map, int index) // Sets class type with index
	{
		stats = new Stats(index);
		gui = new GUI(map, stats);
		projectiles = new ArrayList<Projectile>();
		x = y = xVel = yVel = theta = 0;
		speed = 5;
	}

	public void tick() // Update game logic for player (stats, pos, etc.)
	{
		move();
	}
	
	public void move()
	{
		if(moveUp)
		{
			yVel = speed * -1;
		}
		if(moveDown)
		{
			yVel = speed;
		}
		if(moveRight)
		{
			xVel = speed;
		}
		if(moveLeft)
		{
			xVel = speed * -1;
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
		g.drawImage(image, (int)x, (int)y, null);
		//g.drawRect((int)x, (int)y, 50, 50);
	}
	
	public void controlPressed(char k) //Takes key input and decides what to do
	{
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

	public double getTheta() {
		return theta;
	}

	public double getIndex() {
		return index;
	}

	public BufferedImage getImage() {
		return image;
	}

	public boolean getItemInHand() {
		return itemInHand;
	}

	public Stats getStats() {
		return stats;
	}
}
