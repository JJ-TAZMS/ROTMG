package game;
import java.util.*;
public class Item_Wizard
{
	ArrayList<String> staves = new ArrayList<String>();
	ArrayList<String> robes = new ArrayList<String>();
	ArrayList<String> spells = new ArrayList<String>();
	public Item_Wizard()
	{
		//depression
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
		
		spells.add("T0 Fire Spray Spell");
		spells.add("T1 Flame Burst Spell");
		spells.add("T2 Fire Nova Spell");
		spells.add("T3 Scorching Blast Spell");
		spells.add("T4 Destruction Sphere Spell");
		spells.add("T5 Magic Nova Spell");
		spells.add("T6 Elemental Detonation Spell");
		} 	
	public  String getStaff(int itemID)
	{
		return staves.get(itemID);
		
	}
	public  String getRobe(int itemID)
	{
		return robes.get(itemID);
		
	}
	public  String getSpells(int itemID)
	{
		return spells.get(itemID);
		
	}
	}

