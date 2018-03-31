package game;

import java.awt.Color;
import java.awt.Graphics;

public class Inventory {
	private Slots[][] inv; 
	private int xPos,yPos;
		
	
	public Inventory(){
		/*
		inv = new Slots[8];
		for (int i = 0;i<inv.length;i++){
			inv[i] = null;
		}
		*/
		inv = new Slots[4][2];
		
		for (int i = 0; i<4; i++){
			for (int j = 0; j<2; j++){
				inv[i][j] = new Slots();
			}
		}
	}
	public void addItem(int x, int y, Item it){
		inv[x][y].addItem(it);
	}
	public void render(Graphics g){
		
		g.setColor(Color.black);
		for (int i = 0; i<4; i++){
			for (int j = 0; j<2; j++){
				if (inv[i][j].getEmpty()){
					g.drawRect((GUI.xStart + 15)+(50*i), GUI.yStartInv+(50*(j+1)), 50, 50);
				} else {
					//when there's items, gotta add something that would render
					//the item icon, need to use isEmpty in Slots
				}
			}
			//g.drawRect((GUI.xStart + 15)+(50*i), GUI.yStartInv+50, 50, 50);
			//g.drawRect((GUI.xStart + 15)+(50*i), GUI.yStartInv+100, 50, 50);
		}
	}
}
