package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Inventory {
	//private Slots[][] inv; 
	private Item[][] inv;
	private int xPos,yPos;
	private int invX;
	private int invY;		
	private boolean itemSelected;
	public Inventory(){
		//inv = new Slots[4][2];
		/*
		for (int m = 0; m < inv.length; m++){
			for (int n = 0; n < inv[m].length; n++){
				inv[m][n] = new Slots();
			}
		} */
		inv = new Item[4][2];
		invX = 0;
		invY = 0;
		itemSelected = false;
		
	}
	public Item[][] getInv() { return inv; }
	public void setX(int x) 
	{ 
		if (x < 0)
		{
			x += 4;
		}
		invX = x; 
	}
	public void setY(int y) 
	{ 
		if (y < 0)
		{
			y += 4;
		}
		invY = y; 
	}
	public void setItemBool(boolean b) { itemSelected = b; }
	public int getX() { return invX; }
	public int getY() { return invY; }
	public void addItem(Item it){
		inv[invX][invY] = it;
	}
	public void render(Graphics g){
		
		
		//g.setColor(Color.black);
		for (int i = 0; i<inv.length; i++){
			for (int j = 0; j<inv[i].length; j++){
				if (i == (invX%4) && j == (invY%2) && !itemSelected){
					g.setColor(Color.YELLOW);
				} else {
					g.setColor(Color.BLACK);
				}
				
				g.drawRect((GUI.xStart + 15)+(51*i), (int)(GUI.yStartInv+(51*(j+1.2))), 50, 50);
				if (inv[i][j] != null)
				{
					g.drawImage(inv[i][j].getImage(), (GUI.xStart + 15)+(51*i), (int)(GUI.yStartInv+(51*(j+1.2))), null);

				}
			}
			
		}
	}
}

