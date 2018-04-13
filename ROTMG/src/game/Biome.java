package game;

public class Biome {
	private int index;
	private String biomeName;
	
	/*
	 * 0 -> Water
	 * 10 -> GodLands
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	//Depending on the radius, a Biome type will be picked
	public Biome(double radius)
	{
		if (radius < 10)
		{
			index = 10;
		}
	}
	
	/*
	public Field[][] generate()
	{
		
	}
	*/
	
}
