package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import game.Enemies.EntGod;
import game.Enemies.GiantCrab;
import game.Enemies.Medusa;
import game.Enemies.Pirate;
import game.Enemies.Urgle;

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
	private SpriteSheet spriteSheet;
	private int itemID;
	private boolean itemInHand;
	private boolean itemSelected;
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
		
		spriteSheet = ss;
		//TODO Check class index for picture
		image = new BufferedImage[4];
		image[0] = ss.grabImage(8, 29, 1, 1); //Right
		image[1] = ss.grabImage(9, 29, 1, 1); //Down
		image[2] = ss.grabImage(10, 29, 1, 1); //Left
		image[3] = ss.grabImage(11, 29, 1, 1); //Up
		img = image[1];
		
		bag = null;
		nearBag = itemInHand = itemSelected = false;
	}

	public void tick() // Update game logic for player (stats, pos, etc.)
	{
		move();
		rotateMap();
		projectileTick();
		checkPlayerHit();
		attack();
		checkBags();
	}
	
	
	public void projectileTick()
	{
		//Display each of the player's projectiles
		for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).tick();
			//(Math.sqrt((xPos - playerX)*(xPos - playerX) + (yPos - playerY)*(yPos - playerY)))
			
			
			if(projectiles.get(i).checkDelete())
			{
				projectiles.remove(i);
				i--;
				continue; //ends current iteration of for loop
			}
			
			//Iterate through all enemies to see if a collision was detected 
			for (int cL = -1; cL <= 1; cL++)
			{ //In surrounding lands only
				int toRender = cL + map.getMap()[(int) x][(int) y].getDif();
				if (toRender < 1)
				{
					cL++;
				}
				if (toRender > 5)
				{
					break;
				}
				for (Enemy en : map.getEnemies(toRender)) //For every enemies in surrounding lands
				{
				
					//Your projectile hitting them
					if (((Math.sqrt((en.getX() - projectiles.get(i).getX())*(en.getX()- projectiles.get(i).getX()) + (en.getY() - projectiles.get(i).getY())*(en.getY() - projectiles.get(i).getY()))))<=.50){
						
						//Make enemy lose health
						if (en.hurtEnemy((int)projectiles.get(i).getDamage()))
						{
							//Add loot bag to field
							if (Math.random() < .5)
							{
								System.out.println("Adding LootBag! Player.java");
								map.getBags().add(new LootBag(en.getStats().getTier(), en.getX(), en.getY()));
							}
							
							//Get rid of the enemy
							map.getEnemies(toRender).remove(en);
							
						}
						System.out.println("Player has done " + projectiles.get(i).getDamage() + " damage to enemy ( " + en.getStats().gethp() + ")");
						
						projectiles.remove(i);
						i--;
						break;
					}
				}
				if (i < 0)
				{
					break;
				}
			}
			
		}
	}
	
	public void checkPlayerHit()
	{
		//Iterate through all enemies to see if a collision was detected 
		for (int cL = -1; cL <= 1; cL++)
		{ //In surrounding lands only
			int toRender = cL + map.getMap()[(int) x][(int) y].getDif();
			if (toRender < 1)
			{
				cL++;
			}
			if (toRender > 5)
			{
				break;
			}
			for (Enemy en : map.getEnemies(toRender)) //For every enemies in surrounding lands
			{
				//The enemy projectiles hitting you
				for (int j = 0; j < en.getProj().size(); j++)
				{
					if (((Math.sqrt((x - en.getProj().get(j).getX())*(x- en.getProj().get(j).getX()) + (y - en.getProj().get(j).getY())*(y - en.getProj().get(j).getY()))))<=0.5){
						System.out.println("Enemy has done " + en.getProj().get(j).getDamage() + " damage to the player (" + stats.gethp() + ")");
						stats.sethp((int)(stats.gethp()-en.getProj().get(j).getDamage()));
						
						
						en.getProj().remove(j);
						j--;
					}
				}
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
		
		if ((xVel != 0) && (yVel != 0))
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
		
		if(xVel != 0 || yVel != 0)
		{
			spawnEnemies();
		}
	}
	
	public void spawnEnemies()
	{
		
		double playerTheta = Math.atan(yVel/xVel);

		if(xVel < 0)
		{
			playerTheta += Math.PI;
		}
		
		for(double cT = 0; cT < Math.PI/4; cT += Math.PI/30)
		{
			for(int negAndPos = -1; negAndPos < 2; negAndPos += 2)
			{
				double spawnTheta = playerTheta + cT * negAndPos;
				
				//Goes this far because Field sets the rendered tiles.
				int radiusOfTiles = Game.WIDTH/Tile.TILESIZE + 12;
				
				int xSpawn = (int) (x + (Math.cos(spawnTheta) * radiusOfTiles));
				int ySpawn = (int) (y + (Math.sin(spawnTheta) * radiusOfTiles));
				
				//Spawning of enemies
				
				if (!map.getMap()[xSpawn][ySpawn].getRendered())
				{
					if(map.getMap()[xSpawn][ySpawn].getDif() == 1 && ( map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()) == null || map.getEnemies(map.getMap()[(int) xSpawn][(int) ySpawn].getDif()).size() <= 40))
					{
						//the player is in the shoreline and there are less than 40 enemies
						if(Math.random() < 0.005)
						{
							System.out.println("Pirate spawned");
							//System.out.println(map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()).size());
							map.addEnemy(new Pirate(xSpawn, ySpawn, spriteSheet.grabImage(11, 1, 1, 1)), 1);
						}
					}
					if(map.getMap()[xSpawn][ySpawn].getDif() == 2 && ( map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()) == null || map.getEnemies(map.getMap()[(int) xSpawn][(int) ySpawn].getDif()).size() <= 40))
					{
						//the player is in the shoreline and there are less than 40 enemies
						if(Math.random() < 0.005)
						{
							System.out.println("Giant Crab spawned");
							//System.out.println(map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()).size());
							map.addEnemy(new GiantCrab(xSpawn, ySpawn, spriteSheet.grabImage(9, 12, 1, 1)), 2);
						}
					}
					if(map.getMap()[xSpawn][ySpawn].getDif() == 3 && ( map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()) == null || map.getEnemies(map.getMap()[(int) xSpawn][(int) ySpawn].getDif()).size() <= 40))
					{
						//the player is in the shoreline and there are less than 40 enemies
						if(Math.random() < 0.005)
						{
							System.out.println("Pirate spawned");
							//System.out.println(map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()).size());
							map.addEnemy(new Pirate(xSpawn, ySpawn, spriteSheet.grabImage(11, 1, 1, 1)), 3);
						}
					}
					if(map.getMap()[xSpawn][ySpawn].getDif() == 4 && ( map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()) == null || map.getEnemies(map.getMap()[(int) xSpawn][(int) ySpawn].getDif()).size() <= 40))
					{
						//the player is in the shoreline and there are less than 40 enemies
						if(Math.random() < 0.005)
						{
							System.out.println("Urcle spawned");
							//System.out.println(map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()).size());
							map.addEnemy(new Urgle(xSpawn, ySpawn, spriteSheet.grabImage(6,  16,  2,  2)), 4);
						}
					}
					if(map.getMap()[xSpawn][ySpawn].getDif() == 5 && ( map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()) == null || map.getEnemies(map.getMap()[(int) xSpawn][(int) ySpawn].getDif()).size() <= 40))
					{
						//the player is in the shoreline and there are less than 40 enemies
						if(Math.random() < 0.005)
						{
							if (Math.random() < .5)
							{
								System.out.println("Medusa spawned");
								//System.out.println(map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()).size());
								map.addEnemy(new Medusa(xSpawn, ySpawn, spriteSheet.grabImage(4, 18, 2, 2)), 5);

							}	else
							{
								System.out.println("EntGod spawned");
								//System.out.println(map.getEnemies(map.getMap()[(int)xSpawn][(int)ySpawn].getDif()).size());
								map.addEnemy(new EntGod(xSpawn, ySpawn, spriteSheet.grabImage(12, 20, 2, 2)), 5);

							}
						}
					}
				}
				
			}
		}
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
		
		/*
		//TODO only render close enemies
		for (Enemy en : map.getEnemies()){
			if (en.getStats().getActive())
			{
				en.render(g, x, y);
				
				for (Projectile p : en.getProj()) 
				{
					p.render(g, x, y);
				}
				for (Bomb b : en.getBombs())
				{
					b.render(g,  x,  y);
				}
			}
		}
		*/
		
		for (int cL = -1; cL <= 1; cL++)
		{ //In surrounding lands only
			int toRender = cL + map.getMap()[(int) x][(int) y].getDif();
			if (toRender < 1)
			{
				cL++;
			}
			if (toRender > 5)
			{
				break;
			}
			for (Enemy en : map.getEnemies(toRender)) //For every enemies in surrounding lands
			{
				if (en.getStats().getActive())
				{
					//System.out.println("Rendering enemy from player.java");
					en.render(g, x, y);
					
				}
			}
		}

		//g.setColor(Color.CYAN);
		//g.fillRect((int) (Game.SCALE*(Game.WIDTH/2 - 5)), (int) (Game.SCALE*(Game.HEIGHT/2 - 5)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);
		//g.fillRect(Game.SCALE*(Game.WIDTH-Tile.TILESIZE)/2 , Game.SCALE*(Game.HEIGHT-Tile.TILESIZE)/2, Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);
		gui.render(g, x, y);
	}
	
	public void updateStats(Item it)
	{
		if (it.getType().equals("W")) 
		{ 
			stats.setAttack(12+it.getStat());
		}else if (it.getType().equals("A")) {
		 
			stats.setDefense(it.getStat()); 
		} 
	}
	
	public void controlPressed(int k)
	{
		//if(k == 'w')
		if(k == 87)
		{
			moveUp = true;
		}
		//if(k == 's')
		if(k == 83)
		{
			moveDown = true;
		}
		//if(k == 'a')
		if(k == 65)
		{
			moveLeft = true;
		}
		//if(k == 'd')
		if(k == 68)
		{
			moveRight = true;
		}
		//if (k == 'q')
		if (k == 81)
		{
			rotateUp = true;
		}
		//if (k == 'e')
		if (k == 69)
		{
			rotateDown = true;
		}
		/*if (k == '.')
		{
			try {
				map.addLootBag("1", x, y);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		if (bag!=null) {
			for (int i=1; i<=bag.bagItems.size();i++)
			{
				if (k == 48+i && nearBag)
				{
					itemInHand = true;
					bagIndex = i-1;		
					itemHeld = bag.bagItems.get(i-1);
					gui.setIndex(i);
					bag.bagItems.remove(i-1);
				}
			}
		}
		
		if (k == KeyEvent.VK_LEFT)
		{
			if (itemSelected)
			{
				gui.getHot().setX((gui.getHot().getX()-1)%4);
			} else {
				gui.getInv().setX((gui.getInv().getX()-1)%4);
			}			
		}
		if (k == KeyEvent.VK_RIGHT)
		{
			if (itemSelected)
			{
				gui.getHot().setX((gui.getHot().getX()+1)%4);
			} else {
				gui.getInv().setX((gui.getInv().getX()+1)%4);
			}
		}
		if (k == KeyEvent.VK_UP)
		{
			if (!itemSelected){ gui.getInv().setY((gui.getInv().getY()-1)%2); }
		}
		if (k == KeyEvent.VK_DOWN)
		{
			if (!itemSelected) { gui.getInv().setY((gui.getInv().getY() +1)%2); }
		}
		if (k == KeyEvent.VK_ENTER)
		{
			if (itemInHand){
				gui.getInv().addItem(itemHeld);
				itemInHand = false; 
			} 
			if(itemSelected){
				gui.getHot().addItem(itemHeld);
				itemSelected = false;
				updateStats(itemHeld);
			}
			if (!itemInHand && !itemSelected)
			{
				itemHeld = gui.getInv().getInv()[gui.getInv().getX()][gui.getInv().getY()];
			}
			gui.getInv().setItemBool(itemSelected);
			gui.getHot().setItemBool(itemSelected);
		}


		
		
		
	}
	
	//public void controlReleased(char k) //used for deceleration and such
	public void controlReleased(int k)
	{
		//if(k == 'w')
		if (k == 87)
		{
			moveUp = false;
		}
		//if(k == 's')
		if (k == 83)
		{
			moveDown = false;
		}
		//if(k == 'a')
		if (k == 65)
		{
			moveLeft = false;
		}
		//if(k == 'd')
		if (k == 68)
		{
			moveRight = false;
		}
		//if (k == 'q')
		if (k == 81)
		{
			rotateUp = false;
		}
		//if (k == 'e')
		if (k == 69)
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
				nearBag = (Math.abs(playerDist)<1);
				if (nearBag)
				{
					bag = map.getBags().get(i);	
					i = map.getBags().size()+1;
				}else{
					bag = null;		
				}
				gui.setBag(bag);
				if (playerDist > Game.WIDTH/Tile.TILESIZE*3)
				{
					map.getBags().remove(i);
				}
			}
		}
	}
	
	public LootBag getBag() { return bag; }
	
	public boolean getNear() { return nearBag;  }
	
	public int getBagIndex() { return bagIndex; }


}
