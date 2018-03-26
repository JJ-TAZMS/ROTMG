package game;
import java.util.*;
public class Items_Robes 
{
	ArrayList<String> robes = new ArrayList<String>();
	public Items_Robes()
	{
		robes.add("T1 Robe of the Neophyte");
		robes.add("T2 Robe of the Apprentice ");
		robes.add("T3 Robe of the Acolyte");
		robes.add("T4 Robe of the Student");
		robes.add("T5 Robe of the Conjurer");
		robes.add("T6 Robe of the Adept");
		robes.add("T7 Robe of the Invoker");
		robes.add("T8 Robe of the Illusionist");
		robes.add("T9 Robe of the Master");
		robes.add("T10 Robe of the Shadow Magus");
		robes.add("T11 Robe of the Moon Wizard");
		robes.add("T12 Robe of the Elder Warlock");
		robes.add("T13 Robe of the Grand Sorcerer");
		robes.add("T14 Robe of the Star Mother");
		
		System.out.println("Wizard robes are: " + robes);
	}
	public  String getItem(int itemID)
	{
		return robes.get(itemID);
	}
}
