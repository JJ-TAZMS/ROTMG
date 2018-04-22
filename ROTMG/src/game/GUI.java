package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GUI {
	private Minimap mini;
	private Stats stat;
	private Inventory inv;
	private Hotbar hot;
	private LootBag loot;
	private int index;
	private int invIndex;
	public static int width = Game.WIDTH/5;
	public static int xStart = (Game.WIDTH-width+2)*Game.SCALE;
	public static int yStartInv = (Game.HEIGHT/2) * Game.SCALE;
	public static int yStartStat = (Game.HEIGHT/3)*Game.SCALE;
	
	//TODO add width or GUI (as ratio of total JFrame width)
	
	public GUI(Tile[][] map, Stats stats)
	{
		mini = new Minimap(map);
		stat = stats;
		inv = new Inventory();
		hot = new Hotbar();
	}
	public void setBag(LootBag l)
	{
		loot = l;
	}
	public void setIndex(int i){ index = i; }
	public void setInvIndex(int i) { invIndex = i; }
	
	public void render(Graphics g, double xIn, double yIn){
		
		width = Game.WIDTH/5;
		xStart = (Game.WIDTH-width+2)*Game.SCALE;
		yStartInv = (Game.HEIGHT/2) * Game.SCALE;
		yStartStat = (Game.HEIGHT/3)*Game.SCALE;
		
		
		g.setColor(Color.darkGray);
		g.fillRect((Game.WIDTH-width+2)*Game.SCALE, 0, width*Game.SCALE, Game.HEIGHT*Game.SCALE);

		
		if (loot!=null) {
			
			for (int i = 0; i<loot.bagItems.size(); i++)
			{
				if (i == index)
				{
					g.setColor(Color.YELLOW);
				} else {
					g.setColor(Color.BLACK);
				}
				g.drawRect((xStart + 15)+(50*i), Game.HEIGHT*Game.SCALE - 150, 50, 50); //item icons
			}
		}
		mini.render(g, xIn, yIn);
		inv.render(g);
		hot.render(g);
		
		//possibly put player name above the stats
		g.setColor(Color.YELLOW);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		g.drawString("ATT - "+stat.getAttack(), xStart + 10, yStartStat);
		g.drawString("DEF - "+stat.getDefense(), xStart + 125, yStartStat);
		g.drawString("SPD - "+ stat.getDispSpeed(), xStart+10, yStartStat+35);
		g.drawString("DEX - "+ stat.getDexterity(), xStart+125, yStartStat+35);
		g.drawString("VIT - "+stat.getVitality(), xStart+10, yStartStat+70);
		g.drawString("WIS - "+stat.getWisdom(), xStart+125, yStartStat+70);
		g.drawString("GUISIZE - " + width + ", " + Game.HEIGHT , xStart+10, yStartStat+105);
		//health bar
		//add inventory of lootbags when player is near one
		
		
	}
}