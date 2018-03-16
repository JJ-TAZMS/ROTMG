package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

	private int xPos;
	private int yPos;
	private BufferedImage image;
	//private Color color;	//Would be bufferedImage for actual printing
	
	public static final int TILESIZE = 10;
	
	public Tile(int x, int y)
	{
		xPos = x;
		yPos = y;
		image = null;
	}
	

	public void tick()
	{
		
	}
	
	//Display the Tile to the screen
	public void render(Graphics g, double xIn, double yIn)
	{
		//g.setColor(color);
		//g.fillRect( (xPos+((int)(Game.HEIGHT/2)))*Game.SCALE, (yPos+((int)(Game.WIDTH/2)))*Game.SCALE, TILESIZE*Game.SCALE, TILESIZE*Game.SCALE);
		
		//Where you are in the map + (where the tile is located in the map)
		//xIn and yIn are based on the tile you are on in the map
		int xP = (int)-(xIn*TILESIZE) + (xPos);
		int yP = (int)-(yIn*TILESIZE) + (yPos);
		
		g.drawImage(image, Game.SCALE*(xP + Game.WIDTH/2), Game.SCALE*(yP+ Game.HEIGHT/2), TILESIZE*Game.SCALE, TILESIZE*Game.SCALE, null);

		/*
		int numx = (int)(xIn/10);
		int numy = (int)(yIn/10);
		String cpos = "(" + numx + ", " + numy + ")";
		String pos = "(" + xP + ", " + yP + ")";
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		
		
		g.drawString(cpos, xP*Game.SCALE, yP*Game.SCALE);
		g.drawString(pos, xP*Game.SCALE, yP*Game.SCALE+15);
		*/
	}
	
	//public Color getColor()	{	return color;	}
	public void setImg(BufferedImage tileImage)
	{	
		image = tileImage;
	}
	
	public int getX()	{	return xPos;	}
	public int getY()	{	return yPos;	}
}
