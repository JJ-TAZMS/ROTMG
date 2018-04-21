
package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
public class Item 
{
	private int itemStat; //can be damage, defense, agility, etc. 
	private String itemType;
	private int itemTier;
	private ArrayList<String[]> listOfDesiredItems;
	private String itemName;
	

	
	public Item(String type, String tier) throws FileNotFoundException {
		createListOfItems(type, tier);
		String[] itemData = getRandomItemData();
		itemType = itemData[0];
		itemTier = Integer.parseInt(itemData[1]);
		itemName = itemData[2];
		itemStat = Integer.parseInt(itemData[3]);
	}
	
	
	public String getType() {
		return itemType;
	}
	
	public int getTier() {
		return itemTier;
	}
	
	public String getName() {
		return itemName;
	}
	
	public int getStat() {
		return itemStat;
	}
	
	//This will return a random item from the arraylist of desired items
	private String[] getRandomItemData() {
		Random rand = new Random();
		int index = rand.nextInt(listOfDesiredItems.size());
		return listOfDesiredItems.get(index);
	}
	
	//This will parse a csv file and put items of desired type and tier in an arraylist
	private void createListOfItems(String type, String tier) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new File("res/items_wiz.csv"));
        //scanner.useDelimiter(",");
        String itemType = type;
        String itemTier = tier;
        ArrayList<String[]> listOfData = new ArrayList<String[]>();
        while(scanner.hasNextLine()){
        		String data = scanner.nextLine();
     
        		String[] sepData = data.split(",");
        		
        		if (sepData[0].equals(itemType) && sepData[1].equals(itemTier)) {
        			listOfData.add(sepData);
        		}
        }
        
        scanner.close();
		listOfDesiredItems = listOfData;
		
		//testing this to see if merge  can work
		//please work for the love of god
		
	}
	
	public String toString() {
		return (itemType + "," + itemTier + "," + itemName + "," + itemStat);
	}
	
}
