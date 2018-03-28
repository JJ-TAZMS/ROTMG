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
	
	/*
	public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("res/items_wiz.csv"));
        //scanner.useDelimiter(",");
        String itemType = "A";
        String itemTier = "3";
        ArrayList<String[]> listOfData = new ArrayList<String[]>();
        while(scanner.hasNextLine()){
        		String data = scanner.nextLine();
        		//System.out.println(data);
        		String[] sepData = data.split(",");
        		//System.out.println(Arrays.toString(sepData));
        		//System.out.println(sepData[0] + "  " + sepData[1]);
        		if (sepData[0].equals(itemType) && sepData[1].equals(itemTier)) {
        			listOfData.add(sepData);
        		}
        }
        
        //print out all items of the desired type and tier
        for (String[] a: listOfData) {
        		System.out.println(Arrays.toString(a));
        }
        scanner.close();
    }
    */
	
	public Item(String type, String tier) throws FileNotFoundException {
		createListOfItems(type, tier);
		String[] itemData = getRandomItemData();
		itemType = itemData[0];
		itemTier = Integer.parseInt(itemData[1]);
		itemName = itemData[2];
		itemStat = Integer.parseInt(itemData[3]);
	}
	
	//SHOULD WRITE A CONSTRUCTOR THAT CAN GET A SPECIFIC ITEM, NOT JUST A RANDOM ONE
	
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
	}
	
}