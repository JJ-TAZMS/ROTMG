package game;

import java.awt.Color;
import java.awt.Graphics;

public class Inventory {
	//private Slots[] inv; 
	private int xPos,yPos;
		
	
	public Inventory(){
		/*
		inv = new Slots[8];
		for (int i = 0;i<inv.length;i++){
			inv[i] = null;
		}
		*/
	}
	//public void addItem(int index, Item it){
	//	inv[index].addItem(it);
	//}
	public void render(Graphics g){
		
		g.setColor(Color.black);
		for (int i = 0; i<4; i++){
			//when there's items, gotta add something that would render
			//the item icon, need to use isEmpty in Slots
			g.drawRect((GUI.xStart + 15)+(50*i), GUI.yStart+50, 50, 50);
			g.drawRect((GUI.xStart + 15)+(50*i), GUI.yStart+100, 50, 50);
		}
	}
}
