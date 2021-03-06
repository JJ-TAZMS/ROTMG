package game;

import java.awt.Color;
import java.awt.Font;
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
	public void removeSpells()
	{
		for (int i=0;i<4;i++)
		{
			if (hotbar[i] != null && hotbar[i].getType().equals("M")) { hotbar[i] = null; System.out.println("ayyyyy");}
		}
	}
	public void render(Graphics g){
		for (int i = 0; i<4; i++){	
			if (i == hotX && itemSelected)
			{
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawRect((GUI.xStart + 15)+(51*i), GUI.yStartInv, 50, 50);
			if (hotbar[i] != null){
				g.drawImage(hotbar[i].getImage(),(GUI.xStart + 15)+(51*i), GUI.yStartInv, null);
				g.setColor(Color.RED);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
				g.drawString(Integer.toString(hotbar[i].getTier()), (GUI.xStart + 15)+(51*i)+40, GUI.yStartInv+47);
			}
		}
	}	
	public int getX() { return hotX; }
	public void setX(int x) 
	{ 
		if (x < 0)
		{
			x += 4;
		}
		hotX = x; 
	}
	public Item[] getHot() {  return hotbar; }
	public void addItem(Item it)
	{
		hotbar[hotX] = it;
	}
}


