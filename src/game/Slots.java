package game;

import java.awt.Graphics;

public class Slots {
	private boolean isEmpty;
	private Item item;
	
	public Slots() {
		isEmpty = true;
		item = null;
	}
	
	public Slots(Item it){
		isEmpty = false;
		item = it;
	}
	
	public void addItem(Item it){
		item = it;
		isEmpty = false;
	}
	public void removeItem(){
		isEmpty = true;
	}
	public void render(Graphics g){
		//if empty then put an empty rectangle, if has an item, show item
	}
	public boolean getEmpty(){
		return isEmpty;
	}
}

