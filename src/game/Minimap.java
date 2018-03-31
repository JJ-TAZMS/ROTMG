package game;

import java.awt.Color;
import java.awt.Graphics;

public class Minimap {
	private Tile[][] m;
	private int dim;
	public Minimap(Tile[][] map)
	{
		//assuming Map is supposed to be Field? or 'map' from Field, aka Chunk[][]
		m = map;
		dim = 10*Game.SCALE;
	}
	public void render(Graphics g, double xIn, double yIn){
		//Only go through like a radius of 30 around player's pos
		int radiusOfTiles = 20;
		int currentXChunk = (int) (xIn);
		int currentYChunk = (int) (yIn);
		
		
		for (int r = currentXChunk-radiusOfTiles; r <= currentXChunk+radiusOfTiles; r++)
		{
			for (int c = currentYChunk-radiusOfTiles; c <= currentYChunk+radiusOfTiles; c++)
			{
				int spread = 5;
				double xPos = GUI.xStart + 10 + ((r - (currentXChunk-radiusOfTiles))*spread);
				double yPos = 10 + ((c - (currentYChunk-radiusOfTiles))*spread);
				//double xPos = GUI.xStart + 10 + (m[r][c].getX()*spread) - m[currentXChunk-radiusOfTiles][currentYChunk-radiusOfTiles].getX()*spread;
				//double yPos = 10 + (m[r][c].getY()*spread) - m[currentXChunk-radiusOfTiles][currentYChunk-radiusOfTiles].getY()*spread;
				//System.out.println("MiniMap: " + xPos + ", " + yPos);
				//System.out.println(GUI.xStart + (m[r][c].getX()*Game.SCALE));
				if (m[r][c].getRendered())
				{
					g.setColor(m[r][c].getColor());
				}	else
				{
					g.setColor(Color.BLACK);
				}
				g.fillRect((int)xPos, (int)yPos, spread, spread);
			}
		}
		
		
		/*
		for (Chunk[] r : m)
		{
			for (Chunk c : r)
			{
				if (c.getRendered()){
					
					g.setColor(c.getColor());
					System.out.println(c.getX());
					System.out.println(GUI.xStart + (c.getX()*Game.SCALE));
					//g.fillRect(Game.WIDTH, Game.HEIGHT, 100, 100);
					double xPos = GUI.xStart + 3 + c.getX();
					double yPos = 3 + c.getY();;
					g.fillRect((int)xPos, (int)yPos, 1, 1);
					//g.fillRect(GUI.xStart + (c.getX()*Game.SCALE), c.getY()+(30*Game.SCALE), 10*Game.SCALE, 10*Game.SCALE);
					
					
				//think Chunk class would need a getColor method so chunk can be shown in minimap
					
				}
			}
		}
		*/
	}
}
