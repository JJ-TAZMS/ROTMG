package game;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
//import BufferedImageLoader;
public class LootBag extends BufferedImageLoader{
	
	ArrayList<String> bagItems; //array list of randomized item id's
	String tier;
	Item_Wizard itemWiz = new Item_Wizard();
	//create new lootbag item
	//to be used in the enemy class when an enemy dies
	public LootBag(String enemyTier, double xPos, double yPos) {
		//create a new image
		//SpriteSheet bag = new SpriteSheet(); //image for the loot bag
		//BufferedImage bag = BufferedImageLoader.loadImage("a");
		
		tier = enemyTier;
		bagItems = new ArrayList<String>(); 
		randomizeLoot();
		dropBag(xPos, yPos);
	}
	
	//drop the lootBag at the location that the enemy dies. Should be used in the enemy class?
	private void dropBag(double xPos, double yPos) {
		//draw the bag at the death position
	}
	
	private void randomizeLoot() {
		
		Random rand = new Random();
		
		int randItemId = rand.nextInt(14);
		bagItems.add(itemWiz.getRobe(randItemId));
		randItemId = rand.nextInt(14)+1;
		bagItems.add(itemWiz.getSpells(randItemId));
		randItemId = rand.nextInt(7);
		bagItems.add(itemWiz.getSpells(randItemId));
		
		
		int numOfItems = rand.nextInt(4) + 1; //loot bag can spawn anywhere from 1 to 4 items
		/*
		for (int i=0; i<numOfItems; i++) {
			randItemId = rand.nextInt(36);
			//bagItems.add(random item from item class)
			bagItems.add(Item.getItem(rand));
		}
		*/
		
	}
}
