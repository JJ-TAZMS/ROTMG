package game;
import java.util.ArrayList;
import java.util.Random;
public class LootBag {
	
	ArrayList<String> bagItems; //array list of randomized item id's
	
	//create new lootbag item
	//to be used in the enemy class when an enemy dies
	public LootBag(String enemyTier) {
		//create a new image
		//SpriteSheet bag = new SpriteSheet();
		
		bagItems = new ArrayList<String>(); 
		
	}
	
	//drop the lootBag at the location that the enemy dies. Should be used in the enemy class?
	public void dropBag(int xPos, int yPos) {
		
	}
	
	public void randomize() {
		Random rand = new Random();

		int numOfItems = rand.nextInt(4) + 1; //loot bag can spawn anywhere from 1 to 4 items
		
		for (int i=0; i<numOfItems; i++) {
			//bagItems.add(random item from item class)
		}
	}
}
