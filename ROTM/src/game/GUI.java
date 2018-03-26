package game;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

public class GUI {
	private Minimap mini;
	private Stats stat;
	private Inventory inv;
	private Hotbar hot;
	public static final int width = Game.WIDTH/5;
	public static int xStart = (Game.WIDTH-width+2)*Game.SCALE;
	public static int yStart = (Game.HEIGHT/2) * Game.SCALE;
	private JLabel attack;
	private JLabel defense;
	private JLabel speed; 
	private JLabel dexterity; 
	private JLabel vitality;
	private JLabel wisdom; 
	
	//TODO add width or GUI (as ratio of total JFrame width)
	
	public GUI(Chunk[][] map, Stats stats)
	{
		mini = new Minimap(map);
		stat = stats;
		inv = new Inventory();
		hot = new Hotbar();
		attack = new JLabel("ATT - ");
		defense = new JLabel("DEF - ");
		speed = new JLabel("SPD - ");
		dexterity = new JLabel("DEX - ");
		vitality = new JLabel("VIT - ");
		wisdom = new JLabel("WIS - ");
	}
	public void render(Graphics g, double xIn, double yIn){
		xStart = (Game.WIDTH-width+2)*Game.SCALE;
		
		g.setColor(Color.darkGray);
		g.fillRect((Game.WIDTH-width+2)*Game.SCALE, 0, width*Game.SCALE, Game.HEIGHT*Game.SCALE);
		mini.render(g, xIn, yIn);
		inv.render(g);
		hot.render(g);
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
		
		*/
	}
}

