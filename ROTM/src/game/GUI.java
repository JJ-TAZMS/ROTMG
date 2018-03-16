package game;

import java.awt.Color;
import java.awt.Graphics;

public class GUI {
	private Minimap mini;
	private Stats stat;
	private Inventory inv;
	private Hotbar hot;
	public static final int width = Game.WIDTH/5;
	//TODO add width or GUI (as ratio of total JFrame width)
	
	public GUI(Chunk[][] map, Stats stats)
	{
		mini = new Minimap(map);
		stat = stats;
	}
	public void render(Graphics g){
		g.setColor(Color.black);
		g.fillRect((Game.WIDTH-width+2)*Game.SCALE, 0, width*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
		/* System.out.println("Attack: " + stat.getAttack());
		System.out.println("Defense: " + stat.getDefense());
		System.out.println("Dexterity: " + stat.getDexterity());
		System.out.println("HP: " + stat.gethp());
		System.out.println("MP: " + stat.getmp());
		System.out.println("H Pots: " + stat.getHealthPotions());
		System.out.println("M Pots: " + stat.getManaPotions());
		System.out.println("Speed: " + stat.getSpeed());
		System.out.println("Vitality: " + stat.getVitality());
		System.out.println("Wisdom: " + stat.getWisdom());
		System.out.println();
		//dont think all these stats need to be displayed, just hp and mp, which will be bars
		//but printed them out anyway
		*/
	}
}

