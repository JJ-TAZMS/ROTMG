package game;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
//import BufferedImageLoader;
public class LootBag extends BufferedImageLoader{
	
	ArrayList<Item> bagItems; //array list of randomized item id's
	String tier;
	
	public LootBag(String enemyTier, double xPos, double yPos) throws FileNotFoundException {
		//create a new image
		//SpriteSheet bag = new SpriteSheet(); //image for the loot bag
		//BufferedImage bag = BufferedImageLoader.loadImage("a");
		
		tier = enemyTier;
		bagItems = new ArrayList<Item>(); 
		randomizeLoot();
		dropBag(xPos, yPos);
	}
	
	//drop the lootBag at the location that the enemy dies. Should be used in the enemy class?
	private void dropBag(double xPos, double yPos) {
		//draw the bag at the death position
	}
	
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
