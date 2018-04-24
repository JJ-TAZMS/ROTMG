package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	public static int WIDTH = 240;
	public static int HEIGHT = (int) (WIDTH * .75);
	public static final int SCALE = 5;
	
	
	public final String NAME = "ROTMG";
	
	
	private JFrame frame, mainScreen;
	private JPanel contentPane;
	JButton btnPlay;
	JLabel lblLoading, lblTitle, lblDesc;
	
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
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);
		
		
		//Main menu
		mainScreen = new JFrame(NAME);
		mainScreen.setBackground(Color.BLACK);
		mainScreen.setForeground(Color.BLACK);
		
		mainScreen.setEnabled(true);
		mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainScreen.setBounds(100, 100, 1200, 920 );
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainScreen.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnPlay = new JButton("Play");
		btnPlay.setForeground(Color.RED);
		btnPlay.setBackground(Color.BLACK);
		
		btnPlay.setFont(new Font("Modern No. 20", Font.PLAIN, 60));
		btnPlay.setBounds(470, 580, 253, 86);
		contentPane.add(btnPlay);
		btnPlay.setVisible(false);
		
		lblTitle = new JLabel("Realm of the Mad God");
		lblTitle.setForeground(Color.RED);
		lblTitle.setBackground(Color.RED);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 60));
		lblTitle.setBounds(318, 21, 643, 111);
		contentPane.add(lblTitle);
		
		lblDesc = new JLabel("Welcome to the Realm! Do you have what it takes to fight the monsters?");
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblDesc.setForeground(Color.RED);
		lblDesc.setBounds(54, 123, 1099, 175);
		contentPane.add(lblDesc);
		
		JLabel lblControlsHeader, lblControls1, lblControls2, lblControls3, lblControls4;
		
		lblControlsHeader = new JLabel("Movement                              Attack                              Magic");
		lblControlsHeader.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblControlsHeader.setForeground(Color.RED);
		lblControlsHeader.setBounds(150, 305, 993, 39);
		contentPane.add(lblControlsHeader);
		

		lblControls1 = new JLabel("  W                          Aim with your mouse        Aim with your mouse");
		lblControls1.setForeground(Color.RED);
		lblControls1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblControls1.setBounds(179, 408, 1000, 39);
		contentPane.add(lblControls1);
		lblControls2 = new JLabel("A S D                           Left Mouse Click                    SpaceBar");
		lblControls2.setForeground(Color.RED);
		lblControls2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblControls2.setBounds(175, 440, 1000, 39);
		contentPane.add(lblControls2);
		lblControls3 = new JLabel("Get Items From Lootbags: Numpad > Arrows > Enter");
		lblControls3.setForeground(Color.RED);
		lblControls3.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblControls3.setBounds(200, 750, 1000, 39);
		contentPane.add(lblControls3);
		lblControls4 = new JLabel("Equip Items From Inventory: Enter > Arrows > Enter");
		lblControls4.setForeground(Color.RED);
		lblControls4.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblControls4.setBounds(200, 790, 1000, 39);
		contentPane.add(lblControls4);
		
		lblLoading = new JLabel("Generating Realm...");
		lblLoading.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblLoading.setForeground(Color.RED);
		lblLoading.setBounds(460, 580, 467, 39);
		contentPane.add(lblLoading);
		mainScreen.setVisible(true);
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

		btnPlay.setVisible(true);
		lblLoading.setVisible(false);
		
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
		
		if (btnPlay.getModel().isPressed()) {
			mainScreen.setVisible(false);
			frame.setVisible(true);
		}
		
		player1.tick();
		
		//TODO when we have an arraylist of players, we need to send in the player positions to only their nearby enemies.
		for (int toTick = 1; toTick <= 5; toTick++)
		{ //In surrounding lands only
			for (Enemy en : map.getEnemies(toTick)) //For every enemies in the game
			{
				if (en.getStats().getActive())
				{
					//System.out.println("Ticking enemy from Game.java");
					en.tick(player1.getX(), player1.getY(), player1.getXvel(), player1.getYvel());
					
				}
			}
		}
		
		if (player1.getStats().gethp() <= 0)
		{
			System.exit(0);
		}
		
		WIDTH = this.getWidth()/Game.SCALE;
		HEIGHT = this.getHeight()/Game.SCALE;
		
		//System.out.println("Game Tiles Width: " + Game.WIDTH/Tile.TILESIZE);
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
		int key = e.getKeyCode();
		player1.controlPressed(key);
		
	}
	
	//When the key it finished being pressed, this is called
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
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
