package game;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
//import BufferedImageLoader;
public class LootBag {
	
	ArrayList<Item> bagItems; //array list of randomized item id's
	String tier;
	private double xPos, yPos;
	private BufferedImage bagImage;
	public LootBag(String enemyTier, double x, double y) {
		//create a new image
		//SpriteSheet bag = new SpriteSheet(); //image for the loot bag
		//BufferedImage bag = BufferedImageLoader.loadImage("a");
		
		tier = enemyTier;
		bagItems = new ArrayList<Item>(); 
		randomizeLoot();
		xPos = x;
		yPos = y;
		
		try {
			switch (tier) {
			
				case "1": bagImage = ImageIO.read(new File("res/lb_pics/lb_brown.png"));
						  break;
				case "2": bagImage = ImageIO.read(new File("res/lb_pics/lb_pink.png"));
						  break;
				case "3": bagImage = ImageIO.read(new File("res/lb_pics/lb_purple.png"));
						  break;
				case "4": bagImage = ImageIO.read(new File("res/lb_pics/lb_white.png"));
					      break;
				default: bagImage = ImageIO.read(new File("res/lb_pics/lb_brown.png"));
				//System.out.println("BAGIMAGE LOADED SUCCESSFULLY");
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("LOOT BAG " + xP + ", " + yP + " " + toString());
		
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, double xIn, double yIn)
	{
		double xP = ((-xIn) + (xPos))*Tile.TILESIZE;
		double yP = (-(yIn) + (yPos))*Tile.TILESIZE;
		
		
		g.drawImage( (Image) bagImage, (int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), null);
		//g.fillRect((int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);
		//g.fillRect((int) (Game.SCALE*(Game.WIDTH/2)), (int) (Game.SCALE*(Game.HEIGHT/2)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);

		//System.out.println("LOOT BAG " + xP + ", " + yP + " " + toString());
	}
	
	
	//TODO make getting potions a lot more common, and make fewer items within a bag more common
	private void randomizeLoot() {
		
		Random rand = new Random();
		int numOfItems = rand.nextInt(4) + 1; //loot bag can spawn anywhere from 1 to 4 items
		int randType;
		String itemType;
		for (int i=0; i<numOfItems; i++) {
			//random item type (weapon, armor, misc.)
			randType = rand.nextInt(3);
			if (randType==0)
				itemType = "W";
			else if (randType==1)
				itemType = "A";
			else
				itemType = "M";
			bagItems.add(new Item(itemType, tier));
		}
	}
	
	public double getX() {
		return xPos;
	}
	
	public double getY() {
		return yPos;
	}
	
	public String toString() {
		String s = "Items in bag: " + "\n";
		for (Item i : bagItems) {
			s += i + "\n";
		}
		return s;
	}
}
