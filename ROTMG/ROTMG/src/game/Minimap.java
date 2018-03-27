package game;

import java.awt.Color;
import java.awt.Graphics;

public class Minimap {
	private Chunk[][] m;
	public Minimap(Chunk[][] map)
	{
		//assuming Map is supposed to be Field? or 'map' from Field, aka Chunk[][]
		m = map;
	}
	public void render(Graphics g){
		for (Chunk[] r : m)
		{
			for (Chunk c : r)
			{
				if (c.getRendered()){
					
					g.setColor(c.getColor());
					g.fillRect( (c.getX() + ((int)(Game.WIDTH/2)))*Game.SCALE, (c.getY()+((int)(Game.HEIGHT/2)))*Game.SCALE, 1*Game.SCALE, 1*Game.SCALE);

				//think Chunk class would need a getColor method so chunk can be shown in minimap
					
				}
			}
		}
	}
}
