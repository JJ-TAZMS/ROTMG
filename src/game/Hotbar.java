package game;

import java.awt.Color;
import java.awt.Graphics;

public class Hotbar {
	//private int xPos, yPos;
	//private Slots[] hotbar;
	private Item[] hotbar;
	private int hotX;
	private boolean itemSelected;
	
	public Hotbar(){
		//hotbar = new Slots[3];
		hotbar = new Item[4];
		/*for (int i = 0; i<3; i++){	
			hotbar[i] = new Slots();
		}*/
		itemSelected = false; 
	}
	public void setItemBool(boolean b) { itemSelected = b; }
	public void render(Graphics g){
		for (int i = 0; i<4; i++){	
			if (i == hotX && itemSelected)
			{
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawRect((GUI.xStart + 15)+(51*i), GUI.yStartInv, 50, 50);
		}
	}	
	public int getX() { return hotX; }
	public void setX(int x) { hotX = x; }
	public Item[] getHot() {  return hotbar; }
	public void addItem(Item it)
	{
		hotbar[hotX] = it;
	}
}


