package game;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI {
	private Minimap mini;
	private Stats stat;
	private Inventory inv;
	private Hotbar hot;
	public static final int width = Game.WIDTH/5;
	public static int xStart = (Game.WIDTH-width+2)*Game.SCALE;
	public static int yStartInv = (Game.HEIGHT/2) * Game.SCALE;
	public static int yStartStat = (3*Game.HEIGHT/4)*Game.SCALE;
	private JLabel attack;
	private JLabel defense;
	private JLabel speed; 
	private JLabel dexterity; 
	private JLabel vitality;
	private JLabel wisdom; 
	private JPanel statPan;
	
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
		statPan = new JPanel();
		statPan.setLocation(xStart+3, yStartStat);
		statPan.setSize(Game.WIDTH-(Game.WIDTH-xStart), 100);
		statPan.add(attack);
		statPan.add(defense);
		statPan.add(speed);
		statPan.add(dexterity);
		statPan.add(vitality);
		statPan.add(wisdom);
		attack.setSize(50, 20);
		defense.setSize(50, 20);
		speed.setSize(50, 20);
		dexterity.setSize(50, 20);
		vitality.setSize(50, 20);
		wisdom.setSize(50, 20);
		attack.setLocation(xStart + 15, yStartStat+3);
		defense.setLocation(xStart + 70, yStartStat+3);
		speed.setLocation(xStart+15, yStartStat+28);
		dexterity.setLocation(xStart+70, yStartStat+28);
		vitality.setLocation(xStart+15, yStartStat+53);
		wisdom.setLocation(xStart+70, yStartStat+53);		
	}
	public void render(Graphics g, double xIn, double yIn){
		//xStart = (Game.WIDTH-width+2)*Game.SCALE;
		
		g.setColor(Color.darkGray);
		g.fillRect((Game.WIDTH-width+2)*Game.SCALE, 0, width*Game.SCALE, Game.HEIGHT*Game.SCALE);
		mini.render(g, xIn, yIn);
		inv.render(g);
		hot.render(g);
		attack.setText("ATT - "+stat.getAttack());
		defense.setText("DEF - "+stat.getDefense());
		speed.setText("SPD - "+ stat.getSpeed());
		dexterity.setText("DEX - "+ stat.getDexterity());
		vitality.setText("VIT - "+stat.getVitality());
		wisdom.setText("WIS - "+stat.getWisdom());
		//health bar
		//add inventory of lootbags when player is near one
		
		
	}
}

