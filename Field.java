package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

import game.Enemies.Medusa;


public class Field {
	private ArrayList<Tile> field; //Used for map creation
	private Tile[][] map; //Where the actual map is stored
	private int chunks;
	private int mapDist;
	
	private ArrayList<LootBag> lootBags;
	private ArrayList<Enemy> shoreE, lowE, midE, highE, godE;
	
	
	//Construct a new Field, where til is the amount of steps that must be taken each time the
	//generation is tried.
	public Field(int til)
	{
		field = new ArrayList<Tile> ();
		lootBags = new ArrayList<LootBag> ();
		shoreE = lowE = midE = highE = godE = new ArrayList<Enemy>();
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
			field.add(new Tile(0, 0));
			int dir = 0;
			
			
			String seed = initFieldNewSeed(chunks-1, dir, 0, 0);
			System.out.println(compactSeed(seed));
			
			for (Tile c : field)
			{
				if (c.getDif() > largestAlt) {	largestAlt = c.getDif();	}
			}
			shouldReStep = (hasGodLandsArea(70, 11) == null);
		}
		
		
		//Convert the ok map to an Array
		map = convertToArray(field);
		
		
		
		System.out.println("Thickenning Map...");
		map = thickenMap(map);
		
		//System.out.println("Smoothing Altitudes...");
		//map = smoothAltitudes(map);
		
		//Send in a thickened version of the largest godLands area to create
		//The various difficulties.
		Tile[][] godLands = (thickenMap(convertToArray(hasGodLandsArea(70, 11))));
		field = null;
		
		System.out.println("Setting Lands...");
		map = setLands(godLands);
		
		System.out.println("Shifting Positions...");
		//ConvertToAllPositives. Doing this earlier would mess with generation
		//Aka generation starts at some arbitrary point (0,0) , and that is used
		//All the way until the lands are set.
		map = shiftChunks(map);
		
		//Make the map the actual size
		
		
		//TODO add biomes like Forests, Plains, and Deserts.
		
		System.out.println("Setting Pictures...");
		setPictures(); //Set the minimap colors and the ingame Tile colors.
		
		System.out.println("Expanding Map...");
		map = expandMap(map);
		
		map = addObstacles(map);
		//map = thickenMap(map);
		
		//System.out.println("Highest Altitude: " + largestAltitude(map));
	}
	
	public void render(Graphics g, Player player)
	{
		//Display the rendering chunks only
		
		//Add the distance to the 0,0 coordinate
		//The current chunk you are is the distance you are from the [0][0] chunk (In chunks)
		//Plus the position of that first chunk
		
		int radiusOfTiles = Game.WIDTH/10 + 10;
		int currentX = (int) player.getX();
		int currentY = (int) player.getY();
		
		
		for (int r = currentX-radiusOfTiles; r <= currentX+radiusOfTiles; r++)
		{
			for (int c = currentY-radiusOfTiles; c <= currentY+radiusOfTiles; c++)
			{
				map[r][c].render(g, player.getX(), player.getY());
				if (!map[r][c].getRendered())
				{
					map[r][c].setRendered(true);
				}
			}
		}
		for (LootBag lb : lootBags)
		{
			lb.render(g, player.getX(), player.getY());
		}
		
		
	}
	/*
	public void renderNoRot(Graphics g, Player player)
	{
		//Display the rendering chunks only
		
		//Add the distance to the 0,0 coordinate
		//The current chunk you are is the distance you are from the [0][0] chunk (In chunks)
		//Plus the position of that first chunk
		
		int radiusOfChunks = (Game.WIDTH/100) + 1;
		int currentXChunk = (int) (player.getX()/Chunk.CHUNKSIZE);
		int currentYChunk = (int) (player.getY()/Chunk.CHUNKSIZE);
		
		
		for (int r = currentXChunk-radiusOfChunks; r <= currentXChunk+radiusOfChunks; r++)
		{
			for (int c = currentYChunk-radiusOfChunks; c <= currentYChunk+radiusOfChunks; c++)
			{
				map[r][c].renderNoRot(g, player.getX(), player.getY(), player.getTheta());
			}
		}
	}
	*/

	//Sets a random beach start location in the map. 
	public void setPositionInMap(Player player)
	{
		//Randomly pick beach chunk...
		boolean chosen = false;
		
		while (!chosen)
		{
			int rndX = (int) (Math.random()*(map.length));
			int rndY = (int) (Math.random()*(map[0].length));
			
			if (map[rndX][rndY].getDif() == 1)
			{
				player.setX(map[rndX][rndY].getX()/Tile.TILESIZE);
				player.setY(map[rndX][rndY].getY()/Tile.TILESIZE);
				
				
				//TODO get rid of this and add real spawning
				//Enemy e1 = new Pirate(player.getX() + 5, player.getY() + 10);
				
				
				//Enemy e2 = new GelatinousCube(player.getX() + 5, player.getY() + 10);
				
				
				
				
				//enemies.add(e1);
				//enemies.add(e2);
				
				chosen = true;
			}
		}
		
	}
	
	
	//Used for map generation, returns the largest altitude in the map
	public int largestAltitude(Tile[][] maptoCheck)
	{
		int count = 0;
		for (Tile[] r : maptoCheck)
		{
			for (Tile c : r)
			{
				if (c.getDif() > count) {	count = c.getDif();	}
			}
		}
		return count;
	}
	
	//Used for map generation, converts the arraylist of chunks into an array of chunks
	public Tile[][] convertToArray(ArrayList<Tile> toBeConverted)
	{
		int highX = 0;
		int highY = 0;
		int lowX = 100000;
		int lowY = 100000;
		for (Tile t : toBeConverted)
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
		
		int outerborder = 20;
		
		Tile[][] newMap = new Tile[(highX-lowX) + outerborder*2][(highY-lowY) + outerborder*2];
		
		//System.out.println("X range: " + newMap.length);
		//System.out.println("Y range: " + newMap[0].length)
		
		
		//The array should be able to fit the whole area of the field as well
		//as a bit around for thickening.
		
		
		//Go through every Chunk in field and set its spot in the array
		for (Tile t : toBeConverted)
		{
			//Set the position to the center (plus a bit change for outer border)
			//Substracting the low (which is a negative num) will get you to 0. adding the outerborder
			//Keeps some room for the water.
			int newX = t.getX() - lowX + (outerborder);
			int newY = t.getY() - lowY + (outerborder);
			newMap[newX][newY] = t;
			
		}
		
		//Set the water if not yet established as a Chunk
		//Also this makes all the chunks POSITIV
		for (int r = 0; r < newMap.length; r++)
		{
			for (int c = 0; c < newMap[r].length; c++)
			{
				if (newMap[r][c] == null)
				{
					//Set as water if not yet set
					newMap[r][c] = new Tile(r + lowX - outerborder, c + lowY - outerborder, -1);
					
				}
				//System.out.print(newMap[r][c].getDif() + " ");
			}
			//System.out.println("");
		}
		
		return newMap;
				
	}
	
	public Tile[][] expandMap(Tile[][] chunkBasedMap)
	{
		//Previous to this point, everything was in 'Chunk' form (but still called 'Tile'). This aims
		//to expand everyting to tiles.
		Tile[][] expandedMap = new Tile[chunkBasedMap.length*10][chunkBasedMap[0].length*10];
		
		System.out.println("New: " + expandedMap.length + ", " + expandedMap[0].length);
		for (int r = 0; r < chunkBasedMap.length; r++)
		{
			for (int c = 0; c < chunkBasedMap[r].length; c++)
			{
				int chunksize = 10;
				for (int j = 0; j < chunksize; j++)
				{
					for (int k = 0; k < chunksize; k++)
					{
						
						expandedMap[r*chunksize + j][c*chunksize + k] = new Tile(r*chunksize*Tile.TILESIZE + j*(Tile.TILESIZE), c*chunksize*Tile.TILESIZE + k*(Tile.TILESIZE), chunkBasedMap[r][c]);
					}
				}
			}
		}
		return expandedMap;
				
	}
	
	public Tile[][] addObstacles(Tile[][] oldMap)
	{
		Tile[][] newMap = oldMap;
		BufferedImage sspriteSheet = null;
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			sspriteSheet = loader.loadImage("/structure_sheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpriteSheet sss = new SpriteSheet(sspriteSheet, 8);
		//hooks into the spritesheet and BufferedImageLoader class
		
		for (Tile[] r : oldMap)
		{
			for (Tile c : r)
			{
				if (c.getDif() == 2) //Low lands
				{
					if (Math.random() <= .02)
					{
						c.setObst(new Obstacle(sss.grabImage(13, 4, 1, 1))); //Forest Tree
						c.setColor(new Color(20, 53, 26));
					}
				}	else if (c.getDif() == 3) //Midlands
				{
					if (Math.random() <= .05)
					{
						if (Math.random() >= .5)
						{
							c.setObst(new Obstacle(sss.grabImage(9, 4, 1, 1))); //Green Tree
							c.setColor(new Color(10, 67, 28));
						}	else
						{
							c.setObst(new Obstacle(sss.grabImage(10, 4, 1, 1)));  //Yellow Tree
							c.setColor(new Color(179, 146, 42));
						}
					}
				}	else if (c.getDif() == 4) //Highlands
				{
					if (Math.random() <= .03)
					{
						if (Math.random() >= (2.0/3.0))
						{
							c.setObst(new Obstacle(sss.grabImage(9, 4, 1, 1))); //Green Tree
							c.setColor(new Color(10, 67, 28));
						}	else
						{
							if (Math.random() >= .5)
							{
								c.setObst(new Obstacle(sss.grabImage(10, 4, 1, 1))); //Yellow Tree
								c.setColor(new Color(179, 146, 42));
							}	else
							{
								c.setObst(new Obstacle(sss.grabImage(11, 4, 1, 1))); //Dead tree
								c.setColor(new Color(83,53,10));
							}
						}
					}
				}	else if (c.getDif() == 5) //Godlands
				{
					if (Math.random() <= .005)
					{
						c.setObst(new Obstacle(sss.grabImage(9, 5, 1, 1))); //Rock
						c.setColor(new Color(169,169,169));
					}
				}
			}
		}
		
		return newMap;
	}
	
	
	//The basic generated map uses a step function and leaves lots of spaces throughout the map
	//This aims to fill in any holes around the map and make it seem like an actual island.
	
	//Accomplishes this by going through from the highest altitude to the lowest, creating a circle
	//Around the tile, never overriding larger tiles.
	public Tile[][] thickenMap(Tile[][] barrenMap)
	{
		Tile[][] newMap = new Tile[barrenMap.length][barrenMap[0].length];
		
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
					if (barrenMap[r][c].getDif() == altitude)
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
								if (barrenMap[xPos][yPos].getDif() < altitude)
								{
									newMap[xPos][yPos] = new Tile(xPos-r + barrenMap[r][c].getX(), yPos-c + barrenMap[r][c].getY(), barrenMap[r][c]);
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
	public ArrayList<Tile> hasGodLandsArea(int area, int reqAlt)
	{
		ArrayList<Tile> godLands = new ArrayList<Tile> ();
		ArrayList<Tile> highAltitudes = new ArrayList<Tile> ();
		
		//Add all possible chunks as possible godLands
		for (Tile c : field)
		{
			if (c.getDif() >= reqAlt)
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
			ArrayList<Tile> areaCheck = new ArrayList<Tile> ();
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
	public Tile[][] setLands(Tile[][] godLands)
	{
		Tile[][] newMap = map;
		
		//Sets the godland chunks and shorline chunks simultaneously, setting everything else to Midlands.
		//By setting everything else to midlands, only lowlands, highlands, and water have to be set.
		for (int r = 0; r < newMap.length; r++)
		{
			for (int c = 0; c < newMap[r].length; c++)
			{	
				//Set everything to MidLands (lvl 3), GodLands (lvl 5), and Beach (lvl 1)
				if (newMap[r][c].getDif() > 0)
				{
					
					//(Initially sets everything (thats not water) to midlands
					newMap[r][c].setDif(3);
					for (Tile[] j : godLands)
					{
						for (Tile k : j)
						{
							//the 'godlands' array is only composed of godlands and water.
							//Takes all the godland parts and puts it into this newMap
							if (k.getDif() > 0)
							{
								if (newMap[r][c].getX() == k.getX() && newMap[r][c].getY() == k.getY())
								{
									newMap[r][c].setDif(5);
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
							
							if (newMap[xPos][yPos].getDif() <= 0)
							{
								newMap[r][c].setDif(1);
								//Don't keep checking if you're already a shore...
								theta = Math.PI*2;
								radius = shoreradius;
							}
						}
					}
					
				}
			}
		}
		
		
		//This part sets the lowlands(lvl 2) and the shallow water (lvl 0)
		for (int r = 0; r < newMap.length; r++)
		{
			for (int c = 0; c < newMap[r].length; c++)
			{	
				
				//Checks the distance for the lowlands
				if (newMap[r][c].getDif() > 1)
				{
					int lowLandRadius = 13;
					
					//If theres a shore close by, you are now a lowLand.
					for (double theta = 0; theta < Math.PI*2; theta += Math.PI/10)
					{
						for (int radius = 0; radius < lowLandRadius; radius ++)
						{
							int xPos = (int) (Math.cos(theta)*radius) + r;
							int yPos = (int) (Math.sin(theta)*radius) + c;
							
							if (newMap[xPos][yPos].getDif() == 1)
							{
								newMap[r][c].setDif(2);
								theta = Math.PI*2;
								radius = lowLandRadius;
							}
						}
					}
				}
				
				
				//Checks the distance for the shallow water
				if (newMap[r][c].getDif() < 1)
				{
					int shallowRadius = 2  +1;
					
					//If theres a shore close by, you are now a lowLand.
					for (double theta = 0; theta < Math.PI*2; theta += Math.PI/10)
					{
						for (int radius = 0; radius < shallowRadius; radius ++)
						{
							int xPos = (int) (Math.cos(theta)*radius) + r;
							int yPos = (int) (Math.sin(theta)*radius) + c;
							
							if (xPos > 0 && xPos < newMap.length && yPos > 0 && yPos < newMap[0].length)
							{
								if (newMap[xPos][yPos].getDif() == 1)
								{
									newMap[r][c].setDif(0);
									theta = Math.PI*2;
									radius = shallowRadius;
								}
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
			while(newMap[xPos][yPos].getDif() != 2)
			{
				radius++;
				if (newMap[xPos][yPos].getDif() >= 5)
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
				if (newMap[xPos][yPos].getDif() < 5)
				{
					newMap[xPos][yPos].setDif(5);
				}
			}
			
			//Set the highlands to be from the edge of the godlands to 1/3 the distance to the lowlands
			//This makes the midlands 2/3 of the distance
			for (int dist = edgeRad; dist <= ((radius-edgeRad)/3) + edgeRad; dist ++)
			{
				//System.out.println(dist);
				xPos = (int) (Math.cos(theta)*(dist)) + centerX;
				yPos = (int) (Math.sin(theta)*(dist)) + centerY;
				newMap[xPos][yPos].setDif(4);
			}
		}
		return newMap;
	}
	
	
	public Tile[][] shiftChunks(Tile[][] toBeShifted)
	{
		Tile[][] newMap = new Tile[toBeShifted.length][toBeShifted[0].length];
		
		for (int r = 0; r < newMap.length; r++)
		{
			for (int c = 0; c < newMap[r].length; c++)
			{	
				newMap[r][c] = new Tile(r, c, toBeShifted[r][c].getDif());
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
		for (Tile t : field)
		{
			if (t.getX() == x && t.getY() == y)
			{
				//add to the count if you are generating the map
				if (addCount)
				{
					t.addDif();
				}
				//System.out.println("HEY -- " + t.getColor());
				return false;
			}
		}
		//System.out.println("Making new Chunk!");
		if (addCount)
		{
			
			field.add(new Tile(x, y));
		}
		return true;
	}
	
	public void tick()
	{
		//Spawning of enemies
		
	}
	
	//Displays the map
	
	
	
	/*
	public void rotateChunks(double theta, Player player)
	{
		int radiusOfChunks = 0;
		int currentXChunk = (int) (player.getX()/Chunk.CHUNKSIZE) + Math.abs(map[0][0].getX());
		int currentYChunk = (int) (player.getY()/Chunk.CHUNKSIZE) +  Math.abs(map[0][0].getY());
		
		for (int r = currentXChunk-radiusOfChunks; r <= currentXChunk+radiusOfChunks; r++)
		{
			for (int c = currentYChunk-radiusOfChunks; c <= currentYChunk+radiusOfChunks; c++)
			{
				map[r][c].setRotatedTiles(theta);
			}
		}
	}
*/	
	
	
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
	public void setPictures() //For viewing map
	{
		BufferedImage cspriteSheet = null;
		//BufferedImage sspriteSheet = null;
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			cspriteSheet = loader.loadImage("/spritesheet.png");
			//sspriteSheet = loader.loadImage("/structure_sheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpriteSheet css = new SpriteSheet(cspriteSheet, 100);
		//SpriteSheet sss = new SpriteSheet(sspriteSheet, 8);
		//hooks into the spritesheet and BufferedImageLoader class
		
		
		
		for (Tile[] r : map)
		{
			for (Tile t : r)
			{	
				Color clr = null;
				if (t.getDif() == -1)
				{ //Deep Water - dark blue
					clr = new Color(0, 0, 255);
				}	else if (t.getDif() == 0)
				{ //Shallow water - light blue
					clr = new Color(0, 191, 255);
				}	else if (t.getDif() == 1)
				{ //Beach - Sand Color
					clr = new Color(194, 178, 128);
				}	else if (t.getDif() == 2)
				{ //Lowlands - Light Grass Green
					clr = new Color(56, 173, 76);
				}	else if (t.getDif() == 3)
				{ //Midlands - brown/green grass
					clr = new Color(77, 89, 39);
				}	else if (t.getDif() == 4)
				{ //Highlands - golden grass
					clr = new Color(162, 163, 3);
				}	else if (t.getDif() == 5)
				{ //Godlands - dark
					clr = new Color(80, 80, 80);
				}
				//System.out.println("Field SetColors... " + t.getDif());
				
				t.setImg(clr, css.grabImage(t.getDif()+1, 0, 1, 1));
			}
		}
	}

	
	public Tile[][] getMap()	{	return map;	}
	
	public void addLootBag(String enemyTier, double x, double y) throws FileNotFoundException
	{
		lootBags.add(new LootBag(enemyTier, x, y));
	}
	
	public void addEnemy(Enemy e, int diff)
	{
		if(diff == 1)
		{
			shoreE.add(e);
		}
		else if(diff == 2)
		{
			lowE.add(e);
		}
		else if (diff == 3)
		{
			midE.add(e);
		}
		else if(diff == 4)
		{
			highE.add(e);
		}
		else if(diff == 5)
		{
			godE.add(e);
		}
	}
	
	public ArrayList<Enemy> getEnemies(int diff)	{	
		if(diff == 1)
		{
			return shoreE;
		}
		else if(diff == 2)
		{
			return lowE;
		}
		else if (diff == 3)
		{
			return midE;
		}
		else if(diff == 4)
		{
			return highE;
		}
		else if(diff == 5)
		{
			return godE;
		}
		return shoreE;
	}
	
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
