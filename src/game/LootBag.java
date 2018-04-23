package game;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
//import BufferedImageLoader;
public class LootBag extends BufferedImageLoader{
	
	ArrayList<Item> bagItems; //array list of randomized item id's
	String tier;
	private double xPos, yPos;
	
	public LootBag(String enemyTier, double x, double y) throws FileNotFoundException {
		//create a new image
		//SpriteSheet bag = new SpriteSheet(); //image for the loot bag
		//BufferedImage bag = BufferedImageLoader.loadImage("a");
		
		tier = enemyTier;
		bagItems = new ArrayList<Item>(); 
		randomizeLoot();
		dropBag(x, y);
		xPos = x;
		yPos = y;
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, double xIn, double yIn)
	{
		double xP = ((-xIn) + (xPos))*Tile.TILESIZE;
		double yP = (-(yIn) + (yPos))*Tile.TILESIZE;
		
		
		g.fillRect((int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);
		//g.fillRect((int) (Game.SCALE*(Game.WIDTH/2)), (int) (Game.SCALE*(Game.HEIGHT/2)), Tile.TILESIZE*Game.SCALE, Tile.TILESIZE*Game.SCALE);

		System.out.println("LOOT BAG " + xP + ", " + yP + " " + toString());
	}
	
	//drop the lootBag at the location that the enemy dies. Should be used in the enemy class?
	private void dropBag(double xPos, double yPos) {
		//draw the bag at the death position
	}
	
	
	//TODO make getting potions a lot more common, and make fewer items within a bag more common
	private void randomizeLoot() throws FileNotFoundException {
		
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
	
	public String toString() {
		String s = "Items in bag: " + "\n";
		for (Item i : bagItems) {
			s += i + "\n";
		}
		return s;
	}
}
