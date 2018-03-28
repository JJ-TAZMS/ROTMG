package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
	private double x, y, xVel, yVel, theta;
	private boolean moveUp, moveDown, moveLeft, moveRight, rotateUp, rotateDown;
	private int index;
	private BufferedImage[] image;
	private BufferedImage img;
	private GUI gui;
	private Stats stats;
	private ArrayList<Projectile> projectiles;
	private Field map;
	private int itemID;
	private boolean itemInHand;

	public Player(Field m, int index, SpriteSheet ss) // Sets class type with index
	{
		map = m;
		stats = new Stats(index);
		gui = new GUI(map.getMap(), stats);
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
		rotateMap();
	}
	
	
	public void rotateMap()
	{
		if (rotateUp || rotateDown)
		{
			double cT = 0;
			if (rotateUp)
			{
				cT += Math.toRadians(1);
			}
			if (rotateDown)
			{
				cT += Math.toRadians(-1);
			}
			theta += cT;
		}
	}
	public void move()
	{
		xVel = yVel = 0;
		
		
		if(moveUp)
		{
			yVel += -stats.getSpeed()*Math.sin(theta+Math.PI/2);
			xVel += stats.getSpeed()*Math.cos(theta+Math.PI/2);
		}

		
		if(moveDown)
		{
			yVel += stats.getSpeed()*Math.sin(theta+Math.PI/2);
			xVel += -stats.getSpeed()*Math.cos(theta+Math.PI/2);
		}
		
		if(moveRight)
		{
			yVel += -stats.getSpeed()*Math.sin(theta);
			xVel += stats.getSpeed()*Math.cos(theta);
		}
		if(moveLeft)
		{
			yVel += stats.getSpeed()*Math.sin(theta);
			xVel += -stats.getSpeed()*Math.cos(theta);
		}
		
		
		
		if ((moveUp || moveDown) && (moveLeft || moveRight))
		{
			xVel = xVel / Math.sqrt(2.0);
			yVel = yVel / Math.sqrt(2.0);
		}
		
		//System.out.println("Speed: " + Math.sqrt(xVel*xVel + yVel*yVel));
		
		x += xVel;
		y += yVel;
	}

	public void render(Graphics g) // Update picture for player
	{
		double vel = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
		if (vel > .02)
		{
			if (moveLeft) //Left has priority in pictures
			{
				img = image[2];
			}	else if (moveUp) //Down is next
			{
				img = image[3];
			}	else if (moveRight)
			{
				img = image[0]; //Then right
			}	else
			{
				img = image[1]; //And lastly up
			}
		}
		
		/*
		if (rotateUp || rotateDown)
		{
			Graphics2D g2d = (Graphics2D) g;
			//Make a backup so that we can reset our graphics object after using it.
		    AffineTransform backup = g2d.getTransform();
		    //rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
		    //is the angle to rotate the image. If you want to rotate around the center of an image,
		    //use the image's center x and y coordinates for rx and ry.
		    AffineTransform a = AffineTransform.getRotateInstance(theta, Game.WIDTH/2, Game.HEIGHT/2);
		    //Set our Graphics2D object to the transform
		    g2d.setTransform(a);
		    //Draw our image like normal
		    //g2d.drawImage(image, (int) (Game.SCALE*(x + Game.WIDTH/2)), (int) (Game.SCALE*(y+ Game.HEIGHT/2)), (int) (TILESIZE*Game.SCALE), (int) (TILESIZE*Game.SCALE), null);
		    //Reset our graphics object so we can draw with it again.
		    ((Graphics2D) g).setTransform(backup);
		}
		*/
		
		g.drawImage(img, Game.SCALE*(Game.WIDTH-Tile.TILESIZE)/2, Game.SCALE*(Game.HEIGHT-Tile.TILESIZE)/2, Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE, null);
		//g.drawRect((int)x, (int)y, 50, 50);
		gui.render(g, x, y);
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
		if (k == 'q')
		{
			rotateUp = true;
		}
		if (k == 'e')
		{
			rotateDown = true;
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
		if (k == 'q')
		{
			rotateUp = false;
		}
		if (k == 'e')
		{
			rotateDown = false;
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
	
	public Field getMap()	{
		return map;
	}
}
