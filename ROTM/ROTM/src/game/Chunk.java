package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Chunk {
	private int xPos; //Position of the chunk
	private int yPos;
	private int count; //represents difficulty 
	/* 0 - Sea
	 * 1 - Beach
	 * 2 - LowLands
	 * 3 - MidLands
	 * 4 - Highlands
	 * 5 - Godlands
	 */
	
	private Color color; //Shown on the minimap
	private boolean beenRendered; //For the minimap (what to be shown)
	
	public static final int CHUNKSIZE = 10;
	private Tile[][] tiles = new Tile[CHUNKSIZE][CHUNKSIZE];
	
	
	//A Chunk contains a 10x10 of Tiles
	public Chunk(int x, int y)
	{
		xPos = x;
		yPos = y;
		count = 1;
		color = null;
		beenRendered = false;
		setTiles(x, y);
	}
	
	public Chunk(int x, int y, int cou)
	{
		xPos = x;
		yPos = y;
		count = cou;
		color = null;
		beenRendered = false;
		setTiles(x, y);
	}
	
	//This sets where all the tiles should be layed out in the map
	public void setTiles(int x, int y)
	{
		for (int r = 0; r < CHUNKSIZE; r++)
		{
			for (int c = 0; c < CHUNKSIZE; c++)
			{
				tiles[r][c] = new Tile(x*CHUNKSIZE*Tile.TILESIZE + r*(Tile.TILESIZE), y*CHUNKSIZE*Tile.TILESIZE + c*(Tile.TILESIZE));
			}
		}
	}
	
	//What does the map itself have to do?
	public void tick()
	{
		
	}
	
	//Prints out the map
	public void render(Graphics g, double xIn, double yIn, double theta)
	{
		//tiles[0][0].render(g, xIn, yIn, theta);
		//tiles[0][5].render(g, xIn, yIn, theta);
		//tiles[0][9].render(g, xIn, yIn, theta);
		//tiles[9][9].render(g, xIn, yIn, theta);
		//tiles[0][0].render(g, xIn, yIn, theta);
		//tiles[0][0].render(g, xIn, yIn, theta);
		
		//g.fillRect((int)xPos, (int)yPos, 10, 10);
		
			//Display all the tiles in the chunk if you are displaying the actual map
			for (int r = 0; r < CHUNKSIZE; r++)
			{
				for (int c = 0; c < CHUNKSIZE; c++)
				{
					tiles[r][c].render(g, xIn, yIn, theta);
					
				}
			}
			
		
			
			/*
			String cpos = "(" + xPos + ", " + yPos + ")";
			g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			
			
			g.drawString(cpos, (int)(xIn+xPos)*CHUNKSIZE*Game.SCALE + Game.SCALE*Game.WIDTH/2, (int)(yIn+yPos)*CHUNKSIZE*Game.SCALE + Game.SCALE*Game.HEIGHT/2);
			*/
		
	}
	
	/*
	public void setRotatedTiles(double theta)
	{
		for (int r = 0; r < CHUNKSIZE; r++)
		{
			for (int c = 0; c < CHUNKSIZE; c++)
			{
				tiles[r][c].setRotated(theta);
			}
		}
	}
	*/
	
	
	//Passing an image to each tile and sets the color of the chunk (for the minimap)
	public void setColor(Color col, BufferedImage img)	
	{
		color = col;
		for (int r = 0; r < CHUNKSIZE; r++)
		{
			for (int c = 0; c < CHUNKSIZE; c++)
			{
				tiles[r][c].setImg(img);
			}
		}
		
	}
	
	public Color getColor()	{	return color;	}
	
	public void addCount()	{	count++;	}
	public void setCount(int n)	{	count = n;	}
	public int getCount() {	return count;	}
	public boolean getRendered()	{	return beenRendered;	}
	public void setRendered(boolean b) {	beenRendered = b;	}
	
	public void setPos(int x, int y)	{	xPos = x;	yPos = y;	}
	public int getX()	{	return xPos;	}
	public int getY()	{	return yPos;	}
	public Tile[][] getTiles()	{	return tiles;	}
}