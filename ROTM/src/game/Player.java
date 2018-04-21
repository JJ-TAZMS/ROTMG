package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Player {
	private double x, y, xVel, yVel, theta;
	private boolean moveUp, moveDown, moveLeft, moveRight, rotateUp, rotateDown;
	private boolean attacking;
	private int index;
	private BufferedImage[] image;
	private BufferedImage img;
	private GUI gui;
	private Stats stats;
	private ArrayList<Projectile> projectiles;
	private Field map;
	private int itemID;
	private boolean itemInHand;
	private LootBag bag;
	private boolean nearBag;
	private Item itemHeld;
	private int bagIndex;

	public Player(Field m, int index, SpriteSheet ss) // Sets class type with index
	{
		map = m;
		stats = new Stats(index);
		gui = new GUI(map.getMap(), stats);
		projectiles = new ArrayList<Projectile>();
		x = y = 0;
		theta = 0;
		
		//TODO Check class index for picture
		image = new BufferedImage[4];
		image[0] = ss.grabImage(8, 29, 1, 1); //Right
		image[1] = ss.grabImage(9, 29, 1, 1); //Down
		image[2] = ss.grabImage(10, 29, 1, 1); //Left
		image[3] = ss.grabImage(11, 29, 1, 1); //Up
		img = image[1];
		
		bag = null;
		nearBag = false;
	}

	public void tick() // Update game logic for player (stats, pos, etc.)
	{
		move();
		rotateMap();
		projectileTick();
		attack();
		checkBags();
	}
	
	
	public void projectileTick()
	{
		//Display each of the player's projectiles
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
		
		double speed = stats.getSpeed();
		
		if(moveUp)
		{
			yVel += -speed*Math.sin(theta+Math.PI/2);
			xVel += speed*Math.cos(theta+Math.PI/2);
		}

		
		if(moveDown)
		{
			yVel += speed*Math.sin(theta+Math.PI/2);
			xVel += -speed*Math.cos(theta+Math.PI/2);
		}
		
		if(moveRight)
		{
			yVel += -speed*Math.sin(theta);
			xVel += speed*Math.cos(theta);
		}
		if(moveLeft)
		{
			yVel += speed*Math.sin(theta);
			xVel += -speed*Math.cos(theta);
		}
		
		if ((moveUp || moveDown) && (moveLeft || moveRight))
		{
			xVel = xVel / Math.sqrt(2.0);
			yVel = yVel / Math.sqrt(2.0);
		}
		
		//Adjust for error in sin/cos
		if (Math.abs(xVel) < .000001)
		{
			xVel = 0;
		}
		if (Math.abs(yVel) < .000001)
		{
			yVel = 0;
		}
		
		//System.out.println("Before... Xvel: " + xVel + ", yVel: " + yVel);
		
		//So far, the above sets the default values for the movement based on no interference.
		
		checkObstacle(xVel, yVel);
		
		
	}
	
	private void checkObstacle(double xVel, double yVel)
	{
		/*
		 * dX  dY
		 * -1  0   cos(pi)      sin
		 * 1  0		cos(0)		sin
		 * 0  -1	cos(3pi/2)	sin
		 * 0  1		cos(pi/2)	sin
		 */
		
		
		//Check water intersection
		//The X and Y position are the direct center of the displayed player. This is fine for water intersection
		//System.out.println(" + x + ", " + y);
		Tile t = map.getMap()[(int) (x)][(int) (y)];
		if (t.getDif() == 0)
		{
			xVel *= .5;
			yVel *= .5;
			//System.out.println("In water!");
		}
		
		
		
		
		//Check obstacle intersection
		for (double cX = x-.5; cX <= x+.5; cX+=.2)
		{
			if (cX == x-.1)	{	cX = x+.1;	}
			for (double cY = y-.5; cY <= y+.5; cY+=.2)
			{
				if (cY == y-.1) {	cY = y+.1;	}
				Tile cT = map.getMap()[(int) (cX)][(int) (cY)];
				//double xDist = Math.abs((int)cX - (x));
				//double yDist = Math.abs((int)cY - (y));
				if (cT.getDif() < 0 || cT.getObst() != null) //Just add a condition of the tile having an obstacle here.
				{
					//Check for where a barrier is...
					if (cY > y-.3 && cY < y+.3)
					{
						if (cX < x && xVel < 0) //If left and trying left
						{
							xVel = 0;
						}
						if (cX > x && xVel > 0) //If right and trying right
						{
							xVel = 0;
						}
					}
					if (cX > x-.3 && cX < x+.3)
					{
						if (cY < y && yVel < 0) //If up and trying up
						{
							yVel = 0;
						}
						if (cY > y && yVel > 0) //If down and trying down
						{
							yVel = 0;
						}
					}
					
					/*
					if (cX < x && cY == y) //If checking left
					{
						if (xVel < 0)
						{
							xVel = 0;
						}
					}
					if (cX > x && cY == y) //If checking right
					{
						if (xVel > 0)
						{
							xVel = 0;
						}
					}
					if (cY < y && cX == x) //If checking up
					{
						if (yVel < 0)
						{
							yVel = 0;
						}
					}
					if (cY > y && cX == x) //If checking down
					{
						if (yVel > 0)
						{
							yVel = 0;
						}
					}
					*/
					
					/*
					if ((cC.getTiles()[(int) ((x+.5)%Chunk.CHUNKSIZE)][(int) (y%Chunk.CHUNKSIZE)].getDif() < 0 && xVel > 0) || (cC.getTiles()[(int) ((x-.5)%Chunk.CHUNKSIZE)][(int) (y%Chunk.CHUNKSIZE)].getDif() < 0 && xVel < 0))
					{
						xVel = 0;
					}
					if ((cC.getTiles()[(int) (x%Chunk.CHUNKSIZE)][(int) ((y+.5)%Chunk.CHUNKSIZE)].getDif() < 0 && yVel > 0) || (cC.getTiles()[(int) (x%Chunk.CHUNKSIZE)][(int) ((y-.5)%Chunk.CHUNKSIZE)].getDif() < 0 && yVel < 0))
					{
						yVel = 0;
					}
					*/
				}
			}
		}
		
			
		//System.out.println("         After Xvel: " + xVel + ", yVel: " + yVel);
		//System.out.println();
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
		//g.setColor(Color.CYAN);
		//g.fillRect(Game.SCALE*(Game.WIDTH-Tile.TILESIZE)/2, Game.SCALE*(Game.HEIGHT-Tile.TILESIZE)/2, Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);

		
		g.drawImage(img, Game.SCALE*(Game.WIDTH-Tile.TILESIZE)/2, Game.SCALE*(Game.HEIGHT-Tile.TILESIZE)/2, Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE, null);
		
		for(Projectile p : projectiles)
		{
			p.render(g, x, y);
		}
		
		for (Enemy en : map.getEnemies()){
			en.render(g, x, y);
			
			for (Projectile p : en.getProj()) 
			{
				p.render(g, x, y);
			}
		}

		//g.setColor(Color.CYAN);
		//g.fillRect((int) (Game.SCALE*(Game.WIDTH/2 - 5)), (int) (Game.SCALE*(Game.HEIGHT/2 - 5)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);
		//g.fillRect(Game.SCALE*(Game.WIDTH-Tile.TILESIZE)/2 , Game.SCALE*(Game.HEIGHT-Tile.TILESIZE)/2, Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);
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
		if (k == '.')
		{
			try {
				map.addLootBag("1", x, y);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i=1; i<=bag.bagItems.size();i++)
		{
			if (k == (char)(i) && nearBag)
			{
				itemInHand = true;
				bagIndex = i-1;			
			}
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
	
	public void mouseClick(int k)
	{
		if(k == 1)
		{
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
		stats.setAtkWait(stats.getAtkWait()-1);;
		
		if(attacking && (stats.getAtkWait() <= 0))
		{
			double pTheta = Math.atan((Game.mouseY - Game.HEIGHT / 2.0 * Game.SCALE) / (Game.mouseX - Game.WIDTH / 2.0 * Game.SCALE));
			if(Game.mouseX < Game.WIDTH / 2 * Game.SCALE)
			{
				pTheta += Math.PI;
			}
			
			
			//TODO give in weapon firing speed
			projectiles.add(new Projectile(index, x, y, pTheta, .2));
			System.out.println("Added new projectile");
			
			stats.setAtkWait( (int) (360/stats.getDexterity()));
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
	public void checkBags(){
		if (map.getBags().size()>0)
		{
			for (int i=0; i<map.getBags().size(); i++)
			{
				double bagX = map.getBags().get(i).getX();
				double bagY = map.getBags().get(i).getY();
				double playerDist = (Math.sqrt(bagX - x)*(bagX - x) + (bagY - y)*(bagY - y));
				nearBag = (playerDist<3);
				if (nearBag)
				{
					bag = map.getBags().get(i);
					gui.setBag(bag);
				}else{
					bag = null;
				}
				if (playerDist > Game.DELRADIUS)
				{
					map.getBags().remove(i);
				}
					
				/*if (playerDist<3)
				{
					nearBag = true;
					bag = map.getBags().get(i);
					gui.renderLoot(bag);
				} else if (playerDist> Game.DELRADIUS)
				{
					map.getBags().remove(i);
				} else {
					nearBag = false;  //jk dont do this
					bag = null;
				} */
			}
		}
	}
	
	public LootBag getBag() { return bag; }
	
	public boolean getNear() { return nearBag;  }
}