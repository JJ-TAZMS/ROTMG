package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Tile {

	private int xPos;
	private int yPos;
	private BufferedImage image;
	//private Color color;	//Would be bufferedImage for actual printing
	
	public static final int TILESIZE = 10;
	
	public Tile(int x, int y)
	{
		xPos = x;
		yPos = y;
		image = null;
	}
	

	public void tick()
	{
		
	}
	
	//Display the Tile to the screen
	public void render(Graphics g, double xIn, double yIn, double theta)
	{
		//System.out.println();
		//System.out.println("Tile " + xPos + ", " + yPos + " near " + (int)(xIn*TILESIZE) + ", " + (int)(yIn*TILESIZE));
		//g.setColor(color);
		//g.fillRect( (xPos+((int)(Game.HEIGHT/2)))*Game.SCALE, (yPos+((int)(Game.WIDTH/2)))*Game.SCALE, TILESIZE*Game.SCALE, TILESIZE*Game.SCALE);
		
		//Where you are in the map + (where the tile is located in the map)
		//xIn and yIn are based on the tile you are on in the map
		//double normaltheta = Math.atan((yPos - yIn*TILESIZE)/(xPos - xIn*TILESIZE));
		
		/*
		 * 	
		 * |-----------------|
		 * |	3+		4	 |
		 * |		C		 |++++
		 * |	2		1+	 |
		 * |_________________|
		 * 		+++++++++
		 */
		
		
		//this normal theta is only a reference angle
		
		//if (xPos < xIn*TILESIZE) //If X is negative for reference angle
		//{
			//System.out.println("Gotta move em");
		//	normaltheta = Math.PI + normaltheta;
		//}
		
		
		//double normalR = Math.sqrt(Math.pow(yPos - yIn*TILESIZE, 2) + Math.pow(xPos - xIn*TILESIZE, 2));
		
		//double xP = (Math.cos(normaltheta+theta)*normalR);
		//double yP = (Math.sin(normaltheta+theta)*normalR);
		
		//int xP = (int)-(Math.cos(normaltheta+Math.toRadians(theta))*dist*TILESIZE) + (xPos);
		//int yP = (int)-(Math.sin(normaltheta+Math.toRadians(theta))*dist*TILESIZE) + (yPos);

		double xP = -(xIn*TILESIZE) + (xPos);
		double yP = -(yIn*TILESIZE) + (yPos);
		
		//System.out.println("Position in map: (" + xPos + ", " + yPos + ") Relative to player");
		//System.out.println("A change of (" + (int)-(xIn*TILESIZE) + ", " + (int)-(yIn*TILESIZE) + ")");
		//System.out.println("New trying at (" + newX + ", " + newY + ")");
		
		//System.out.println("Showing at " + xP + ", " + yP + " with normalTheta " + normaltheta + " and r " + normalR + " and newTheta " + theta);
		
		
		g.drawImage(image, (int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), TILESIZE*Game.SCALE, TILESIZE*Game.SCALE, null);
		//drawRotated(g, newX, newY, theta);
		//System.out.println("Wha");
	}
	
	
	
	/*
	public void drawRotated(Graphics g, double x, double y, double theta)
	{

	    
		// Rotation information
		
		//Gets the center X and center Y
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		//System.out.println(");
		
		
		//the Magic
		AffineTransform tx = AffineTransform.getRotateInstance(theta, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		
		//The size is smaller at the angles (and needs to be adjusted for scaling), uses a sin function
		//that has a frequency of 2, and this coefficient makes sure it doesn't overaccount
		double coef = .2;
		double xSize = TILESIZE* (1 + coef*(Math.abs(Math.sin(2*theta))) );
		double ySize = TILESIZE* (1 + coef*(Math.abs(Math.sin(2*theta))) );
		//System.out.println("xS: " + xSize + ", yS: " + ySize + ", ImgW: " + image.getWidth() + ", ImgH: " + image.getHeight());
		//System.out.println( (1 + coef*(Math.abs(Math.sin(2*rotationRequired))) ));
		//System.out.println("Rot: " + (rotation));

		//image = op.filter(image, null);
		Graphics2D g2d = (Graphics2D) g;
		
		// Drawing the rotated image at the required drawing location
		g2d.drawImage(op.filter(image, null), (int) (Game.SCALE*(x + Game.WIDTH/2)), (int) (Game.SCALE*(y+ Game.HEIGHT/2)), (int) (xSize*Game.SCALE), (int) (ySize*Game.SCALE), null);
		
		//Unrotated Image
		//g.drawImage(image, (int)xPos, (int)yPos, image.getWidth()*Game.SCALE, image.getHeight()*Game.SCALE, null);
	
		
		/*
		Graphics2D g2d = (Graphics2D) g;
		//Make a backup so that we can reset our graphics object after using it.
	    AffineTransform backup = g2d.getTransform();
	    //rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
	    //is the angle to rotate the image. If you want to rotate around the center of an image,
	    //use the image's center x and y coordinates for rx and ry.
	    AffineTransform a = AffineTransform.getRotateInstance(theta, Game.WIDTH/2, Game.HEIGHT/2);
	    //Set our Graphics2D object to the transform
	    g2d.setTransform(a);
	    //Draw our image like normal
	    g2d.drawImage(image, (int) (Game.SCALE*(x + Game.WIDTH/2)), (int) (Game.SCALE*(y+ Game.HEIGHT/2)), (int) (TILESIZE*Game.SCALE), (int) (TILESIZE*Game.SCALE), null);
	    //Reset our graphics object so we can draw with it again.
	    ((Graphics2D) g).setTransform(backup);
		
	}
	*/
	//public Color getColor()	{	return color;	}
	public void setImg(BufferedImage tileImage)
	{	
		image = tileImage;
	}
	
	public int getX()	{	return xPos;	}
	public int getY()	{	return yPos;	}
}
