package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Field {
	private ArrayList<Chunk> field; //Used for map creation
	private Chunk[][] map; //Where the actual map is stored
	private int chunks;
	private int mapDist;
	
	
	//Construct a new Field, where til is the amount of steps that must be taken each time the
	//generation is tried.
	public Field(int til)
	{
		field = new ArrayList<Chunk> ();
		chunks = til;
		mapDist = 70;
		
		createMap(); //Creates the Map and stores it into 'map'
		
	}
	
	//Create the map through an arraylist and convert it to an array. Then using the largest godlands area
	//Set the various difficulty areas in the map.
	public void createMap()
	{	
		int largestAlt = 0;
		
		//Rather than run the seed creation a certain amount of times, keep running it until you reach a certain height
		//Technically want a certain amount of area of certain altitude or higher
		boolean shouldReStep = true;
		while (largestAlt < 18 || shouldReStep)
		{
			field.add(new Chunk(0, 0));
			int dir = 0;
			
			
			String seed = initFieldNewSeed(chunks-1, dir, 0, 0);
			System.out.println(compactSeed(seed));
			
			for (Chunk c : field)
			{
				if (c.getCount() > largestAlt) {	largestAlt = c.getCount();	}
			}
			shouldReStep = (hasGodLandsArea(70, 11) == null);
		}
		
		
		//Convert the ok map
		map = convertToArray(field);
		
		System.out.println("Thickenning Map...");
		map = thickenMap(map);
		
		//System.out.println("Smoothing Altitudes...");
		//map = smoothAltitudes(map);
		
		//Send in a thickened version of the largest godLands area to create
		//The various difficulties.
		Chunk[][] godLands = (thickenMap(convertToArray(hasGodLandsArea(70, 11))));
		map = setLands(godLands);
		
		//TODO add biomes like Forests, Plains, and Deserts.
		
		
		setColors(); //Set the minimap colors and the ingame Tile colors.
		
		//System.out.println("Highest Altitude: " + largestAltitude(map));
	}
	

	//Sets a random beach start location in the map. 
	public void setPositionInMap(Player player)
	{
		//Randomly pick beach chunk...
		boolean chosen = false;
		
		while (!chosen)
		{
			int rndX = (int) (Math.random()*(map.length));
			int rndY = (int) (Math.random()*(map[0].length));
			
			if (map[rndX][rndY].getCount() == 1)
			{
				player.setX(map[rndX][rndY].getX()*Chunk.CHUNKSIZE);
				player.setY(map[rndX][rndY].getY()*Chunk.CHUNKSIZE);
				chosen = true;
			}
		}
		
	}
	
	
	//Used for map generation, returns the largest altitude in the map
	public int largestAltitude(Chunk[][] maptoCheck)
	{
		int count = 0;
		for (Chunk[] r : maptoCheck)
		{
			for (Chunk c : r)
			{
				if (c.getCount() > count) {	count = c.getCount();	}
			}
		}
		return count;
	}
	
	//Used for map generation, converts the arraylist of chunks into an array of chunks
	public Chunk[][] convertToArray(ArrayList<Chunk> toBeConverted)
	{
		int highX = 0;
		int highY = 0;
		int lowX = 100000;
		int lowY = 100000;
		for (Chunk t : toBeConverted)
		{
			if (highX < t.getX())
			{
				highX = t.getX();
			}
			if (highY < t.getY())
			{
				highY = t.getY();
			}
			
			if (lowX > t.getX())
			{
				lowX = t.getX();
			}
			if (lowY > t.getY())
			{
				lowY = t.getY();
			}
		}
		System.out.println("High and Lows found.");
		System.out.println("X : (" + lowX + ", " + highX + ")");
		System.out.println("Y : (" + lowY + ", " + highY + ")");
		System.out.println("X range: " + (highX - lowX));
		System.out.println("Y range: " + (highY - lowY));
		
		int outerborder = 10;
		
		Chunk[][] newMap = new Chunk[(highX-lowX) + outerborder*2][(highY-lowY) + outerborder*2];
		
		//System.out.println("X range: " + newMap.length);
		//System.out.println("Y range: " + newMap[0].length)
		
		
		//The array should be able to fit the whole area of the field as well
		//as a bit around for thickening.
		
		
		//Go through every Chunk in field and set its spot in the array
		for (Chunk t : toBeConverted)
		{
			//Set the position to the center (plus a bit change for outer border)
			newMap[t.getX() - lowX + (outerborder)][t.getY() - lowY + (outerborder)] = t;
		}
		
		//Set the water if not yet established as a Chunk
		for (int r = 0; r < newMap.length; r++)
		{
			for (int c = 0; c < newMap[r].length; c++)
			{
				if (newMap[r][c] == null)
				{
					//Set as water if not yet set
					newMap[r][c] = new Chunk(r + lowX - outerborder, c + lowY - outerborder, 0);
				}
			}
		}
		
		return newMap;
				
	}
	
	
	//The basic generated map uses a step function and leaves lots of spaces throughout the map
	//This aims to fill in any holes around the map and make it seem like an actual island.
	
	//Accomplishes this by going through from the highest altitude to the lowest, creating a circle
	//Around the tile, never overriding larger tiles.
	public Chunk[][] thickenMap(Chunk[][] barrenMap)
	{
		Chunk[][] newMap = new Chunk[barrenMap.length][barrenMap[0].length];
		
		int largest = largestAltitude(barrenMap);
		
		
		System.out.println("Largest array altitude: " + largest);
		
		int outerborder = 10;
		//System.out.println("r l = " + newMap.length + " and c l " + newMap[0].length);
		
		//From largest altitude to 1 so that the larger ones override
		for(int altitude = largest; altitude > 0; altitude--)
		{
			//System.out.println("Altitude " + altitude);
			for (int r = outerborder; r < barrenMap.length - (outerborder-1); r++)
			{
				for (int c = outerborder; c < barrenMap[r].length-(outerborder-1); c++)
				{
					//newMap[r][c].setCount(10);
					//System.out.println("at (" + r + ", " + c + ")");
					//When at the proper altitude
					if (barrenMap[r][c].getCount() == altitude)
					{
						//System.out.println("Successfully found altitude at (" + r + ", " + c + ") +" + map[r][c].getCount() );
						
						int rad = 2; //The usual size of a given sphere..
						if (altitude == 1)//for the outside edge, make it spread out more
						{
							rad = 5;
						}
						//System.out.println("Sphere of size " + rad + " at (" + r + ", " + c + ")");

						
						//Go all the way around and create the sphere
						for (double theta = 0; theta < Math.PI*2; theta += Math.PI/30)
						{
							for (int radius = rad; radius > 0; radius --)
							{
								int xPos = (int) (Math.cos(theta)*radius) + r;
								int yPos = (int) (Math.sin(theta)*radius) + c;
								
								//System.out.println("At (" + xPos + ", " + yPos + ") Altitude: " + toMap[xPos][yPos].getCount());
								if (barrenMap[xPos][yPos].getCount() < altitude)
								{
									newMap[xPos][yPos] = new Chunk(xPos-r + barrenMap[r][c].getX(), yPos-c + barrenMap[r][c].getY(), barrenMap[r][c].getCount()); //map[x][y].getCount()
								}
							}
						}
					}
				}
			}
		}
		
		//If a spot in the new map has not been set (is null) set it to barrenMap's value.
		for (int r = 0; r < barrenMap.length; r++)
		{
			for (int c = 0; c < barrenMap[r].length; c++)
			{
				if (newMap[r][c] == null)
				{
					newMap[r][c] = barrenMap[r][c];
				}
			}
		}
		
		
		return newMap;
	}
		
	
	//Find and return the largest godLands area.
	//Return null if the size is not at least 'area'
	public ArrayList<Chunk> hasGodLandsArea(int area, int reqAlt)
	{
		ArrayList<Chunk> godLands = new ArrayList<Chunk> ();
		ArrayList<Chunk> highAltitudes = new ArrayList<Chunk> ();
		
		//Add all possible chunks as possible godLands
		for (Chunk c : field)
		{
			if (c.getCount() >= reqAlt)
			{
				highAltitudes.add(c);
				//System.out.println(c.getX() + ", " + c.getY());
			}
		}
		
		int largestArea = 0;
		//Make a check for each Area
		//System.out.println("Entering size check loop...");
		while(highAltitudes.size() > 0) //Keep running through until you've tried all areas
		{ 
			//Take the first chunk as the starting point
			ArrayList<Chunk> areaCheck = new ArrayList<Chunk> ();
			//System.out.println("Checking new Area starting at " + godLands.get(0).getX() + ", " + godLands.get(0).getY());
			areaCheck.add(highAltitudes.remove(0));
			
			//System.out.println("godLands array size: " + godLands.size());
			
			
			boolean continueSearching = true;
			
			//Keep looking for more area to add to the areaCheck
			while (continueSearching)
			{
				continueSearching = false;
				
				//Check each possible being apart of the area being checked
				for (int n = 0; n < highAltitudes.size(); n++)
				{
					//System.out.println("  godLands array size: " + godLands.size());
					//System.out.println("  Checking from main " + godLands.get(n).getX() + ", " + godLands.get(n).getY() + " with...");
					for (int i = 0; i < areaCheck.size(); i++)
					{
						//System.out.println("      areaCheck " + areaCheck.get(i).getX() + ", " + areaCheck.get(i).getY());
						//Note they should never actually have the same tile 
						//Because whenever they are added to check they are deleted from godLands
						//Can literally just use distance to check it
						double xPort = Math.pow(areaCheck.get(i).getX() - highAltitudes.get(n).getX(), 2);
						double yPort = Math.pow(areaCheck.get(i).getY() - highAltitudes.get(n).getY(), 2);
						
						double dist = Math.sqrt(xPort + yPort);
						if (dist <= 1.0)
						{
							areaCheck.add(highAltitudes.remove(n));
							continueSearching = true;
							n = highAltitudes.size();
							i = areaCheck.size();
						}
					}
				}
			}
			//Once no more pieces of the area being checked are in godLands, check the size
			if (areaCheck.size() > largestArea)
			{
				largestArea = areaCheck.size();
				godLands = areaCheck;
			}
			
		}
		
		System.out.println("Largest Godlands Area: " + largestArea);
		if (largestArea < area)
		{
			godLands = null;
		}
		return godLands;
	}
	
	//Sets the differing difficulty lands (Godlands, Highlands, Midlands, Lowlands, Beach, Water
	//Takes in the position of the Godlands and does everything else based on the shoreline
	//And that's position.
	public Chunk[][] setLands(Chunk[][] godLands)
	{
		Chunk[][] newMap = map;
		
		//Sets the godland chunks and shorline chunks simultaneously, setting everything else to Midlands.
		//By setting everything else to midlands, only lowlands, highlands, and water have to be set.
		for (int r = 0; r < newMap.length; r++)
		{
			for (int c = 0; c < newMap[r].length; c++)
			{	
				//Set everything to MidLands (lvl 3), GodLands (lvl 5), and Beach (lvl 1)
				if (newMap[r][c].getCount() > 0)
				{
					
					//(Initially sets everything (thats not water) to midlands
					newMap[r][c].setCount(3);
					for (Chunk[] j : godLands)
					{
						for (Chunk k : j)
						{
							//the 'godlands' array is only composed of godlands and water.
							//Takes all the godland parts and puts it into this newMap
							if (k.getCount() > 0)
							{
								if (newMap[r][c].getX() == k.getX() && newMap[r][c].getY() == k.getY())
								{
									newMap[r][c].setCount(5);
								}
							}
						}
					}
					
					
					//Set the shore to lvl 1
					//Need to make sure the distance between the tile and the nearest shore is good

					int shoreradius = 4;
					
					//If there's water nearby, you are a shore.
					for (double theta = 0; theta < Math.PI*2; theta += Math.PI/60)
					{
						for (int radius = 0; radius < shoreradius; radius ++)
						{
							int xPos = (int) (Math.cos(theta)*radius) + r;
							int yPos = (int) (Math.sin(theta)*radius) + c;
							
							if (newMap[xPos][yPos].getCount() == 0)
							{
								newMap[r][c].setCount(1);
								//Don't keep checking if you're already a shore...
								theta = Math.PI*2;
								radius = shoreradius;
							}
						}
					}
					
				}
			}
		}
		
		
		//This part sets the lowlands(lvl 2)
		for (int r = 0; r < newMap.length; r++)
		{
			for (int c = 0; c < newMap[r].length; c++)
			{	
				
				//Checks the distance for the low and highlands
				if (newMap[r][c].getCount() > 1)
				{
					int lowLandRadius = 13;
					
					//If theres a shore close by, you are now a lowLand.
					for (double theta = 0; theta < Math.PI*2; theta += Math.PI/10)
					{
						for (int radius = 0; radius < lowLandRadius; radius ++)
						{
							int xPos = (int) (Math.cos(theta)*radius) + r;
							int yPos = (int) (Math.sin(theta)*radius) + c;
							
							if (newMap[xPos][yPos].getCount() == 1)
							{
								newMap[r][c].setCount(2);
								theta = Math.PI*2;
								radius = lowLandRadius;
							}
						}
					}
					
				}
			}
		}
		
		//The starting point which will be the center of a circular check to set the highlands.
		int centerX = godLands[godLands.length/2][godLands.length/2].getX() - newMap[0][0].getX();
		int centerY = godLands[godLands.length/2][godLands.length/2].getY() - newMap[0][0].getY();
		
		int edgeRad = 0; //Used for setting where the highlands start (don't overlap the godlands)
		
		//Finds the distance until the lowLands, and the distance to the edge of the Godlands
		for (double theta = 0; theta < Math.PI*2; theta += Math.PI/400)
		{
			int radius = 1;
			int xPos = (int) (Math.cos(theta)*radius) + centerX;
			int yPos = (int) (Math.sin(theta)*radius) + centerY;
			while(newMap[xPos][yPos].getCount() != 2)
			{
				radius++;
				if (newMap[xPos][yPos].getCount() >= 5)
				{
					edgeRad = radius;
				}
				xPos = (int) (Math.cos(theta)*radius) + centerX;
				yPos = (int) (Math.sin(theta)*radius) + centerY;
			}
			
			//Make sure anything within the godlands area is actually godlands.
			for (int dist = 1; dist < edgeRad; dist ++)
			{
				//System.out.println(dist);
				xPos = (int) (Math.cos(theta)*(dist)) + centerX;
				yPos = (int) (Math.sin(theta)*(dist)) + centerY;
				if (newMap[xPos][yPos].getCount() < 5)
				{
					newMap[xPos][yPos].setCount(5);
				}
			}
			
			//Set the highlands to be from the edge of the godlands to 1/3 the distance to the lowlands
			//This makes the midlands 2/3 of the distance
			for (int dist = edgeRad; dist <= ((radius-edgeRad)/3) + edgeRad; dist ++)
			{
				System.out.println(dist);
				xPos = (int) (Math.cos(theta)*(dist)) + centerX;
				yPos = (int) (Math.sin(theta)*(dist)) + centerY;
				newMap[xPos][yPos].setCount(4);
			}
			
		}
		return newMap;
	}
	
	
	//Recursive methods for map generation in the arraylist. A stepfunction is used
	//That goes either forward, left, or right. When overlapping a chunk, its
	//'altitude' increased, allowing for the different areas in the map to be made.
	//Returns String as the seed
	//TODO remove seed from this method and add it to a new seed system
	public String initFieldNewSeed(int left, int direction, int xInd, int yInd)
	{	
		
		//Set new direction (based on turning or going straight
		int newDir = direction +  ((int) ( (Math.random()*4) -2 ) );
		if (newDir < 0)
		{
			newDir += 4;
		}
		if (newDir > 3)
		{
			newDir -= 4;
		}
		//System.out.println(newDir);
		
		//Set change in x and y based on direction
		int cX = 0;
		int cY = 0;
		if (newDir == 0 || newDir == 2)
		{
			cY = newDir == 0? -1:1;
		}
		if (newDir == 1 || newDir == 3)
		{
			cX = newDir == 3? -1:1;
		}
		
		int newX = xInd + cX;
		int newY = yInd + cY;
		
		//Try to keep it within a certain range...
		double dist = Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2));
		if (dist > mapDist)
		{
			newX = 0;
			newY = 0;
			//System.out.println("Dist: " + dist);
		}
		//System.out.println("Dist: " + dist);
		
		
		if (left > 0)
		{
			//Add Chunk
			addChunk(newX, newY, true);
			return newDir + "" + initFieldNewSeed(left-1, newDir, newX, newY);
		}
		return "";
	}
	
	//Used for map generation. Adds a chunks to the arraylist
	public boolean addChunk(int x, int y, boolean addCount)
	{
		//go through each Chunk
		for (Chunk t : field)
		{
			if (t.getX() == x && t.getY() == y)
			{
				//add to the count if you are generating the map
				if (addCount)
				{
					t.addCount();
				}
				//System.out.println("HEY -- " + t.getColor());
				return false;
			}
		}
		//System.out.println("Making new Chunk!");
		if (addCount)
		{
			
			field.add(new Chunk(x, y));
		}
		return true;
	}
	
	public void tick()
	{
		
	}
	
	//Displays the map or minimap depending on what 'showMini' is
	public void render(Graphics g, Player player)
	{
		//Display the rendering chunks only
		
		int radiusOfChunks = 3;
		int currentXChunk = (int) (player.getX()/Chunk.CHUNKSIZE) + Math.abs(map[0][0].getX());
		int currentYChunk = (int) (player.getY()/Chunk.CHUNKSIZE) +  Math.abs(map[0][0].getY());
		//Add the distance to the 0,0 coordinate
		
		//System.out.println("Showing from " + (currentXChunk-radiusOfChunks) + " to " + (currentXChunk+radiusOfChunks));
		
		for (int r = currentXChunk-radiusOfChunks; r <= currentXChunk+radiusOfChunks; r++)
		{
			for (int c = currentYChunk-radiusOfChunks; c <= currentYChunk+radiusOfChunks; c++)
			{
				map[r][c].render(g, player.getX(), player.getY());
				if (!map[r][c].getRendered())
				{
					map[r][c].setRendered(true);
				}
			}
		}
	}
	
	//Used for getting the seed of map.
	//TODO make better seeds using the final map array
	public String compactSeed(String seed)
	{
		if (seed.length() > 2)
		{
			String newSeed = seed.substring(seed.length()-2);
			String restOfSeed = seed.substring(0, seed.length()-2);
			int deciSeed = Integer.valueOf(newSeed, 4);
			String hexSeed = Integer.toString(deciSeed, 16);
			return compactSeed(restOfSeed) + hexSeed;
		}	else
		{
			int deciSeed = Integer.valueOf(seed, 4);
			String hexSeed = Integer.toString(deciSeed, 16);
			return hexSeed;
		}
	}
	
	
	//For viewing the map and the minimap. 
	//Done here to prevent an overflow of loaded images and spritesheets, etc.
	public void setColors() //For viewing map
	{
		BufferedImage spriteSheet = null;
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/spritesheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpriteSheet ss = new SpriteSheet(spriteSheet, 100);
		//hooks into the spritesheet and BufferedImageLoader class
		
		for (Chunk[] r : map)
		{
			for (Chunk t : r)
			{
			//System.out.println(t.getCount());
				
				if (t.getCount() <= 0) //WATER, set color to blue, get water image
				{
					t.setColor(new Color(0, 191, 255), ss.grabImage(0, 0, 1, 1));
					
					
				}	else if (t.getCount() <= 1) //BEACH, set color to white, get beach image
				{
					t.setColor(new Color(255, 255, 255), ss.grabImage(1, 0, 1, 1));
					
					
				}	else if (t.getCount() <= 2) //LOWLANDS, color to white-grey, lowlands image
				{
					t.setColor(new Color(200, 200, 200), ss.grabImage(2, 0, 1, 1));
					
					
				}	else if (t.getCount() <= 3)  //MIDLANDS, color to gray, midlands image
				{
					t.setColor(new Color(140, 140, 140), ss.grabImage(3, 0, 1, 1));
					
					
				}	else if (t.getCount() <= 4)  //HIGHLANDS, color to dark-gray, highlands image
				{
					t.setColor(new Color(80, 80, 80), ss.grabImage(4, 0, 1, 1));
					
					
				}	else                         //GODLANDS, color to black, godlands image
				{
					t.setColor(new Color(0, 0, 0), ss.grabImage(5, 0, 1, 1));
				}
			}
		}
	}

	
	public Chunk[][] getMap()	{	return map;	}
	
	//Used to be used to smooth the look of the map. Has possible future allocations
		//Can be possibly used to better generate the map
		/*
		public Chunk[][] smoothAltitudes(Chunk[][] roughMap)
		{
			Chunk[][] newMap = roughMap;
			int increment = 2;
			int outerborder = 10;
			
			//From largest altitude to 2 , that way the beaches don't "smooth" into the ocean
			for(int altitude = largestAltitude(roughMap); altitude > 1; altitude--)
			{
				for (int r = outerborder-5; r < roughMap.length - (outerborder-1+5); r++)
				{
					for (int c = outerborder-5; c < roughMap[r].length-(outerborder-1+5); c++)
					{
						//When at the proper altitude
						if (newMap[r][c].getCount() == altitude)
						{
							//Make a circle around it
							for (double theta = 0; theta < Math.PI*2; theta += Math.PI/2)
							{
								int xPos = (int) (Math.cos(theta)) + r;
								int yPos = (int) (Math.sin(theta)) + c;
								
								//If the altitude of the surrounding one is (2 or more) less
								
								if (newMap[xPos][yPos].getCount() < newMap[r][c].getCount() - increment)
								{
									//Set it's altitude accordingly
									newMap[xPos][yPos].setCount(altitude-increment);
								}
							}
						}
					}
				}
			}
			
			return newMap;
		}
		*/
	
	
	/*
	 * Was used for seed loading, system needs to be reworked
	public String expandSeed(String seed)
	{
		if (seed.length() > 1)
		{
			String newSeed = seed.substring(seed.length()-1);
			String restOfSeed = seed.substring(0, seed.length()-1);
			int deciSeed = Integer.valueOf(newSeed, 16);
			//System.out.println("Expanding... " + deciSeed);
			String base4Seed = Integer.toString(deciSeed, 4);
			if (base4Seed.length() < 2)	{ base4Seed = 0 + base4Seed;	}
			return expandSeed(restOfSeed) + base4Seed;
		}	else
		{
			int deciSeed = Integer.valueOf(seed, 16);
			String base4Seed = Integer.toString(deciSeed, 4);
			return base4Seed;
		}
	}

	
	public void loadSeed(String seed)
	{
		
		field.add(new Chunk(0, 0));
		int dir = 0;
		
		initFieldWithSeed(dir, 0, 0, expandSeed(seed));
		
	}
	
	public void initFieldWithSeed(int direction, int xInd, int yInd, String seed)
	{	
		
		//Set new direction (based on turning or going straight
		String strDir = seed.substring(0, 1);
		int newDir = Integer.parseInt(strDir);
		
		if (newDir < 0)
		{
			newDir += 4;
		}
		if (newDir > 3)
		{
			newDir -= 4;
		}
		//System.out.println(newDir);
		
		//Set change in x and y based on direction
		int cX = 0;
		int cY = 0;
		if (newDir == 0 || newDir == 2)
		{
			cY = newDir == 0? -1:1;
		}
		if (newDir == 1 || newDir == 3)
		{
			cX = newDir == 3? -1:1;
		}
		
		int newX = xInd + cX;
		int newY = yInd + cY;
		
		//Try to keep it within a certain range...
		double dist = Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2));
		if (dist > mapDist)
		{
			newX = 0;
			newY = 0;
			System.out.println("Dist: " + dist);
		}
		//System.out.println("Dist: " + dist);
		
		
		String newSeed = seed.substring(1);
		//System.out.println(newSeed);
		if (seed.length() > 1)
		{
			//Add Chunk
			addChunk(newX, newY, true);
			initFieldWithSeed(newDir, newX, newY, newSeed);
		}
	}
	*/
}
