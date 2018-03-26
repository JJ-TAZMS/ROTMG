package game;

import java.awt.Color;
import java.awt.Graphics;

public class Minimap {
	private Chunk[][] m;
	private int dim;
	public Minimap(Chunk[][] map)
	{
		//assuming Map is supposed to be Field? or 'map' from Field, aka Chunk[][]
		m = map;
		dim = 10*Game.SCALE;
	}
	public void render(Graphics g, double xIn, double yIn){
		//Only go through like a radius of 30 around player's pos
		int radiusOfChunks = 10;
		int currentXChunk = (int) (xIn/Chunk.CHUNKSIZE);
		int currentYChunk = (int) (yIn/Chunk.CHUNKSIZE);
		
		
		for (int r = currentXChunk-radiusOfChunks; r <= currentXChunk+radiusOfChunks; r++)
		{
			for (int c = currentYChunk-radiusOfChunks; c <= currentYChunk+radiusOfChunks; c++)
			{
				int spread = 10;
				double xPos = GUI.xStart + 10 + (m[r][c].getX()*spread) - m[currentXChunk-radiusOfChunks][currentYChunk-radiusOfChunks].getX()*spread;
				double yPos = 10 + (m[r][c].getY()*spread) - m[currentXChunk-radiusOfChunks][currentYChunk-radiusOfChunks].getY()*spread;
				System.out.println(m[r][c].getX());
				System.out.println(GUI.xStart + (m[r][c].getX()*Game.SCALE));
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
