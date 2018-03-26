package game;
import java.util.*;
public class Items_Spells 
{
	ArrayList<String> spells = new ArrayList<String>();
	public Items_Spells()
	{
		spells.add("T0 Fire Spray Spell");
		spells.add("T1 Flame Burst Spell");
		spells.add("T2 Fire Nova Spell");
		spells.add("T3 Scorching Blast Spell");
		spells.add("T4 Destruction Sphere Spell");
		spells.add("T5 Magic Nova Spell");
		spells.add("T6 Elemental Detonation Spell");
		
		System.out.println("Wizard spells are: " + spells);
	}
	public  String getItem(int itemID)
	{
		return spells.get(itemID);
	}
}
