package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Obstacle {

	private BufferedImage image;
	
	public Obstacle()
	{
		image = null;
	}
	
	public Obstacle(BufferedImage img)
	{
		image = img;
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(image, x, y,Game.SCALE*Tile.TILESIZE, Game.SCALE*Tile.TILESIZE, null);
		//g.setColor(Color.PINK);
		//g.fillRect(x, y, Game.SCALE*Tile.TILESIZE, Game.SCALE*Tile.TILESIZE);
	}
	
	public void setImg(BufferedImage img)
	{
		image = img;
	}
}
