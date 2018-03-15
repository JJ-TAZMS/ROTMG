package Game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 240;
	public static final int HEIGHT = WIDTH/12 * 9;
	public static final int SCALE = 5;
	public final String NAME = "ROTMG";
	
	
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0;
	
	//private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	//private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private BufferedImage spriteSheet = null;
	
	
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
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/sprite_sheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Used for KeyInput (uses another class to do this)
		requestFocus();
		addKeyListener(new KeyInput(this));
				
				
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		// ss.grabImage(0, 0, 1, 1);
		
		//Initializing any objects here
		
		
		
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
				System.out.println("fps" + frames + " " + ticks);
				frames = 0;
				ticks = 0;
			}
			
		}
	}
	
	public void tick() //Update Game Logic
	{
		/*
		tickCount++;
		
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = (i) +  tickCount;
		}
		*/
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
		
		
		//////////// End of Drawing Stuff to screen
		g.dispose();
		bs.show();
	}
	
	//When a key is preesed down, this is called
		public void keyPressed(KeyEvent e)
		{
			
			//int key = e.getKeyCode();
		}
		
		//When the key it finished being pressed, this is called
		public void keyReleased(KeyEvent e)
		{
			//int key = e.getKeyCode();
		}
	
	public static void main(String[] args)
	{
		new Game().start();
	}
	

	
}
