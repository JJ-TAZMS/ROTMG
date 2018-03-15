package game;
import java.util.*;
public class Items
{
	public static void main(String[] args)
	{
		ArrayList<String> staves = new ArrayList<String>() {{
			add("T0 Energy Staff");
			add("T1 Fireband Staff");
			add("T2 Comet Staff");
			add("T3 Serpentine Staff");
			add("T4 Meteor Staff");
			add("T5 Slayer Staff");
			add("T6 Avenger Staff");
			add("T7 Staff of Destruction");
			add("T8 Staff of Horror");
			add("T9 Staff of Necrotic Arcana");
			add("T10 Staff of Diabolical Secrets");
			add("T11 Staff of Astral Knowledge");
			add("T12 Staff of the Cosmic Whole");
			add("T13 Staff of the Vital Unity");
			}};
			
		ArrayList<String> spells = new ArrayList<String>() {{
			add("T0 Fire Spray Spell");
			add("T1 Flame Burst Spell");
			add("T2 Fire Nova Spell");
			add("T3 Scorching Blast Spell");
			add("T4 Destruction Sphere Spell");
			add("T5 Magic Nova Spell");
			add("T6 Elemental Detonation Spell");
			}};
		
		ArrayList<String> robes = new ArrayList<String>() {{
			add("T1 Robe of the Neophyte");
			add("T2 Robe of the Apprentice ");
			add("T3 Robe of the Acolyte");
			add("T4 Robe of the Student");
			add("T5 Robe of the Conjurer");
			add("T6 Robe of the Adept");
			add("T7 Robe of the Invoker");
			add("T8 Robe of the Illusionist");
			add("T9 Robe of the Master");
			add("T10 Robe of the Shadow Magus");
			add("T11 Robe of the Moon Wizard");
			add("T12 Robe of the Elder Warlock");
			add("T13 Robe of the Grand Sorcerer");
			add("T14 Robe of the Star Mother");
			}};
		
			System.out.println("Wizard staves are: " + staves);
			System.out.println("Wizard spells are: " + spells);
			System.out.println("Wizard robes are: " + robes);	
		} 	
	}

