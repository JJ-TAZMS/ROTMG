package game;

import java.awt.Color;
import java.awt.Graphics;

public class GUI {
	private Minimap mini;
	private Stats stat;
	private Inventory inv;
	private Hotbar hot;
	private boolean loot;
	public static final int width = Game.WIDTH/5;
	public static int xStart = (Game.WIDTH-width+2)*Game.SCALE;
	public static int yStartInv = (Game.HEIGHT/2) * Game.SCALE;
	public static int yStartStat = (Game.HEIGHT/3)*Game.SCALE;
	
	//TODO add width or GUI (as ratio of total JFrame width)
	
	public GUI(Chunk[][] map, Stats stats)
	{
		mini = new Minimap(map);
		stat = stats;
		inv = new Inventory();
		hot = new Hotbar();
		//possibly put player name above the stats, just another JLabel
		Game.attack.setSize(75, 35);
		Game.defense.setSize(75, 35);
		Game.speed.setSize(75, 35);
		Game.dexterity.setSize(75, 35);
		Game.vitality.setSize(75, 35);
		Game.wisdom.setSize(75, 35);
		Game.attack.setOpaque(true);
		Game.defense.setOpaque(true);
		Game.speed.setOpaque(true);
		Game.dexterity.setOpaque(true);
		Game.vitality.setOpaque(true);
		Game.wisdom.setOpaque(true);
		Game.attack.setForeground(Color.orange);
		Game.defense.setForeground(Color.orange);
		Game.speed.setForeground(Color.orange);
		Game.dexterity.setForeground(Color.orange);
		Game.vitality.setForeground(Color.orange);
		Game.wisdom.setForeground(Color.orange);
		Game.attack.setBackground(Color.darkGray);
		Game.defense.setBackground(Color.darkGray);
		Game.speed.setBackground(Color.darkGray);
		Game.dexterity.setBackground(Color.darkGray);
		Game.vitality.setBackground(Color.darkGray);
		Game.wisdom.setBackground(Color.darkGray);
		Game.attack.setLocation(xStart + 30, yStartStat);
		Game.defense.setLocation(xStart + 145, yStartStat);
		Game.speed.setLocation(xStart+30, yStartStat+35);
		Game.dexterity.setLocation(xStart+145, yStartStat+35);
		Game.vitality.setLocation(xStart+30, yStartStat+70);
		Game.wisdom.setLocation(xStart+145, yStartStat+70);
	}
	//in Player, determine if lootbag is close by, which should then call a method here
	public void setLootBag(boolean b){
		loot = b;
	}
	public void render(Graphics g, double xIn, double yIn){
		
		g.setColor(Color.darkGray);
		g.fillRect((Game.WIDTH-width+2)*Game.SCALE, 0, width*Game.SCALE, Game.HEIGHT*Game.SCALE);
		mini.render(g, xIn, yIn);
		inv.render(g);
		hot.render(g);
		Game.attack.setText("ATT - "+stat.getAttack());
		Game.defense.setText("DEF - "+stat.getDefense());
		Game.speed.setText("SPD - "+ stat.getSpeed());
		Game.dexterity.setText("DEX - "+ stat.getDexterity());
		Game.vitality.setText("VIT - "+stat.getVitality());
		Game.wisdom.setText("WIS - "+stat.getWisdom());
		//health bar
		//add inventory of lootbags when player is near one
		
		
	}
}

