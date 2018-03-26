package game;
import java.util.*;
public class Item_Wizard
{
	ArrayList<String> staves = new ArrayList<String>();
	public Item_Wizard()
	{
		staves.add("T0 Energy Staff");
		staves.add("T1 Fireband Staff");
		staves.add("T2 Comet Staff");
		staves.add("T3 Serpentine Staff");
		staves.add("T4 Meteor Staff");
		staves.add("T5 Slayer Staff");
		staves.add("T6 Avenger Staff");
		staves.add("T7 Staff of Destruction");
		staves.add("T8 Staff of Horror");
		staves.add("T9 Staff of Necrotic Arcana");
		staves.add("T10 Staff of Diabolical Secrets");
		staves.add("T11 Staff of Astral Knowledge");
		staves.add("T12 Staff of the Cosmic Whole");
		staves.add("T13 Staff of the Vital Unity");
			
		System.out.println("Wizard staves are: " + staves);
		} 	
	public  String getItem(int itemID)
	{
		return staves.get(itemID);
		
	}
	}

