package game;

import java.awt.Graphics;

public class Hotbar {
	private int xPos, yPos;
	private Slots[] hotbar;
	
	public Hotbar(){
		hotbar = new Slots[4];
		for (int i = 0; i<4; i++){	
			hotbar[i] = new Slots();
		}
	}
	
	public void render(Graphics g){
		for (int i = 0; i<4; i++){		
			if (hotbar[i].getEmpty()){
				g.drawRect((GUI.xStart + 15)+(50*i), GUI.yStartInv, 50, 50);
			} else {
				//when there's items, gotta add something that would render
				//the item icon, need to use isEmpty in Slots
			}
		}	
	}
}
