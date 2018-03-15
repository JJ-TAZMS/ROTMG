package game;

import java.awt.Graphics;

public class GUI {
	private Minimap mini;
	private Stats stat;
	private Inventory inv;
	private Hotbar hot;
	
	public GUI(Chunk[][] map, Stats stats)
	{
		mini = new Minimap(map);
		stat = stats;
	}
	public void render(Graphics g){
		System.out.println(stat.getAttack());
		System.out.println(stat.getDefense());
		System.out.println(stat.getDexterity());
		System.out.println(stat.gethp());
		System.out.println(stat.getmp());
		System.out.println(stat.getHealthPotions());
		System.out.println(stat.getManaPotions());
		System.out.println(stat.getSpeed());
		System.out.println(stat.getVitality());
		System.out.println(stat.getWisdom());
		//dont think all these stats need to be displayed, just hp and mp, which will be bars
		//but printed them out anyway
		
		
		
	}
}

