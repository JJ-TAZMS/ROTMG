package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	public static int WIDTH = 240;
	public static int HEIGHT = (int) (WIDTH * .75);
	public static final int SCALE = 5;
	
	public static final int DELRADIUS = Game.WIDTH * Game.SCALE * 20;
	public final String NAME = "ROTMG";
	
	
	private JFrame frame;
	public static JLabel attack;
	public static JLabel defense;
	public static JLabel speed; 
	public static JLabel dexterity; 
	public static JLabel vitality;
	public static JLabel wisdom;
	
	public boolean running = false;
	public int tickCount = 0;
	
	public static double mouseX;
	public static double mouseY;

	
	//private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	//private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private BufferedImage spriteSheet = null;
	
	private Field map;
	private Player player1;
	
	public Game()
	{
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(NAME);
		
		attack = new JLabel("ATT - ");
		defense = new JLabel("DEF - ");
		speed = new JLabel("SPD - ");
		dexterity = new JLabel("DEX - ");
		vitality = new JLabel("VIT - ");
		wisdom = new JLabel("WIS - ");
		frame.add(attack);
		frame.add(defense);
		frame.add(speed);
		frame.add(dexterity);
		frame.add(vitality);
		frame.add(wisdom);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/character_sheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Used for KeyInput (uses another class to do this)
		requestFocus();
		addKeyListener(new KeyInput(this));
				
				
		SpriteSheet ss = new SpriteSheet(spriteSheet, 8);
		// ss.grabImage(0, 0, 1, 1);
		
		//Initializing any objects here
		map = new Field(3000);
		
		int index = 0;
		player1 = new Player(map, index, ss);
		player1.getMap().setPositionInMap(player1);
		
		MouseListeners listeners = new MouseListeners();   
		addMouseListener(listeners);
		addMouseMotionListener(listeners);

		
		
	}
	

	
	
	

	
	
	public synchronized void start()
	{
		running = true;
		new Thread(this).start();
		
	}
	
	public synchronized void stop()
	{
		running = false;
	}
	
	
	public void run() 
	{
		init();
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while (delta >= 1)
			{
				ticks++;
				tick();
				delta --;
				shouldRender = true;
			}
			
			//Can possibly get rid of shouldRender, the Thread.sleep(2), and the if statement around the
			//frames++ and render() (but not those lines themselves)
			//Seems like its more of just for testing?
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shouldRender)
			{
				frames++;
				render();
			}
			
			if (System.currentTimeMillis() - lastTimer >= 1000)
			{
				lastTimer += 1000;
				//System.out.println("fps" + frames + " " + ticks);
				//System.out.println(player1.getX() + ", " + player1.getY());
				frame.setTitle(NAME + "  Pos: " + (int)player1.getX() + ", " + (int)player1.getY() + "  MapSize: " + player1.getMap().getMap().length + ", " + player1.getMap().getMap()[0].length + " Size: " + this.getWidth() + "/" + WIDTH + ", " + this.getHeight() + "/" + HEIGHT);
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick() //Update Game Logic
	{
		
		player1.tick();
		
		//TODO when we have an arraylist of players, we need to send in the player positions to only their nearby enemies.
		for (Enemy en : map.getEnemies())
		{
			en.tick(player1.getX(), player1.getY());
		}
		WIDTH = this.getWidth()/Game.SCALE;
		HEIGHT = this.getHeight()/Game.SCALE;
	}

	public void render() //Update Game Display
	{
		BufferStrategy bs = getBufferStrategy(); 
		if (bs == null)
		{
			createBufferStrategy(3); //three buffers (usually 'doubleBuffer, this is 'TripleBuffer'
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		//////////////  Start of Drawing Stuff to Screen
		
		
		
		//g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 6*SCALE));
		//g.drawString(getWidth() + " -- " + getHeight(), 1*SCALE, 6*SCALE);;
		Graphics2D g2d = (Graphics2D)g;
		g2d.rotate(player1.getTheta(), Game.WIDTH/2 * Game.SCALE, Game.HEIGHT/2 * Game.SCALE);
		
		player1.getMap().render(g2d, player1);
		
		
		g2d.rotate(-player1.getTheta(), Game.WIDTH/2 * Game.SCALE, Game.HEIGHT/2 * Game.SCALE);
		//player1.getMap().renderNoRot(g2d, player1);
		player1.render(g2d);
		
		//////////// End of Drawing Stuff to screen
		g.dispose();
		bs.show();
	}
	
	//When a key is preesed down, this is called
	public void keyPressed(KeyEvent e)
	{
		char key = e.getKeyChar();
		player1.controlPressed(key);
		
	}
	
	//When the key it finished being pressed, this is called
	public void keyReleased(KeyEvent e)
	{
		char key = e.getKeyChar();
		player1.controlReleased(key);
	}
	
	
	public static void main(String[] args)
	{
		new Game().start();
	}
	

	class MouseListeners extends MouseAdapter {

	    public void mousePressed(MouseEvent e) {
	    	//System.out.println("YO");
	    	player1.mouseClick(e.getButton());
	    	
	    }

	    public void mouseDragged(MouseEvent e) {
	    	mouseX = e.getX();
	    	mouseY = e.getY();
	    }
	    
	    public void mouseReleased(MouseEvent e) {
	    	player1.mouseReleased(e.getButton());
	    }
	    
	    public void mouseMoved(MouseEvent e) {
	    	mouseX = e.getX();
	    	mouseY = e.getY();
	    }
	    
	}

	
}
