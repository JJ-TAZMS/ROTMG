package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	
	public int enemyID;
	public double eX, eY;
	public int size;
	public double dist;
	public double projTheta;
	public double theta;
	public double speed;
	
	public double count;
	
	ArrayList<Projectile> proj = new ArrayList<Projectile>(); 
	
	boolean active = false;
	
	
	public Enemy(int ID, double X, double Y){
		
		enemyID = ID;
		eX = X;
		eY = Y;
		size = 24;
		speed = .1;
		
	}
	
	public void render(Graphics g, double xIn, double yIn) {
		
		double xP = ((-xIn) + (eX))*Tile.TILESIZE;
		double yP = ((-yIn) + (eY))*Tile.TILESIZE;
		
		//System.out.println(xP + ", " + yP);
		
		g.setColor(Color.GREEN);
		g.fillOval((int) (Game.SCALE*(xP + Game.WIDTH/2)), (int) (Game.SCALE*(yP + Game.HEIGHT/2)), size, size);
		//if(proj.size() >0) {
		//	for(int i = 0; i < proj.size()-1; i++){
		//		proj.get(i).render(g);
		//	}
		//}
		
	}
	
	public void tick(double xIn, double yIn) {
		
		count++;
		
		
		dist = Math.sqrt((eX - xIn)*(eX - xIn) + (eY - yIn)*(eY - yIn));
		if(dist > 10){
			active = false;
		} else {
			active = true;
		}
		
		System.out.println("Active Enemy: " + active);
		double dX = xIn - eX;
		double dY = yIn - eY;

		dX = Math.abs(dX);
		dY = Math.abs(dY);
		
		theta = Math.abs(Math.atan(dY/dX));

		if(active) {
			
			//if(dist<100) {
			//	mag*=-1;
			//}
			
			if(xIn > eX) {
				eX += (speed*Math.cos(theta));
			}
			if(xIn < eX) {
				eX -= (speed*Math.cos(theta));
			}
			if(yIn > eY) {
				eY += (speed*Math.sin(theta));
			}
			if(yIn < eY) {
				eY -= (speed*Math.sin(theta));
			}
		}
		
		
		//Checks which quadrant the player is in
		//if((mX < eX) && (mY < eY)) {
		//	projTheta = Math.PI - theta;
		//}
		//if((mX < eX) && (mY > eY)) {
		//	projTheta = Math.PI + theta;
		//}
		//if((mX > eX) && (mY > eY)) {
		//	projTheta = -theta;
		//}
		
	}
	
	public void behavior() {
		
	}
	

}
