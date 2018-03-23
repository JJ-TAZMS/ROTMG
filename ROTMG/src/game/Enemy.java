package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	
	public int enemyID, enemyType;
	public double eX, eY;
	public int size;
	ArrayList<Projectile> proj = new ArrayList<Projectile>(); 
	
	boolean active = false;
	
	
	public Enemy(int ID, double X, double Y, int type){
		
		enemyID = ID;
		eX = X;
		eY = Y;
		enemyType = type;
		size = 24;
		
	}
	
	public void render(Graphics g) {
		
		g.drawOval((int)eX - (size/2), (int)eY- (size/2), size, size);
		
	}
	
	public void tick(int mX, int mY, double mag) {
		
		double dist = Math.sqrt((eX - mX)*(eX - mX) + (eY - mY)*(eY - mY));
		if(dist > 400){
			active = false;
		} else {
			active = true;
		}
		
		double dX = mX - eX;
		double dY = mY - eY;
		double theta = 0;
		
		dX = Math.abs(dX);
		dY = Math.abs(dY);
		
		theta = Math.abs(Math.atan(dY/dX));

		if(active) {
			
			
			if(dist<100) {
				mag*=-1;
			}
			if(mX > eX) {
				eX += (mag*Math.cos(theta));
			}
			if(mX < eX) {
				eX -= (mag*Math.cos(theta));
			}
			if(mY > eY) {
				eY += (mag*Math.sin(theta));
			}
			if(mY < eY) {
				eY -= (mag*Math.sin(theta));
			}
		}
		
		
		
		
		
		
	}
	
	public void behavior() {
		
		double theta = 0;
		
		
		
		
		//proj.add(new Projectile());
		
		
		
	}
	

}
