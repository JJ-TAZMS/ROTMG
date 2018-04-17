package game;

import java.io.FileNotFoundException;

public class test {
	public static void main(String[] args) {
		try {
			System.out.println(new LootBag("2",0.0,0.0));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
