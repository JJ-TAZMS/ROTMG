package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EnemyStats extends Stats{

	private double moveDist, attackDist, attackDist2; //In tiles
	private boolean active;
	
	
	public EnemyStats(int index) {
		active = true;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("res/enemies.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Enemies.csv not found!");
		}
		
		if (scanner != null) {
			scanner.nextLine(); //skip the first line of the csv file which is the categories
	        while(scanner.hasNextLine()){
	        		String data = scanner.nextLine();
	     
	        		String[] sepData = data.split(",");
	        		
	        		//if the index matches the index we are looking for then store the data 
	        		if (sepData[1].equals("" + index)) {
	        			setSpeed(Integer.valueOf(sepData[5])/4);
	        			setDexterity(Integer.valueOf(sepData[6]));
	        			setDefense(Integer.valueOf(sepData[3]));
	        			sethp(Integer.valueOf(sepData[2]));
	        			setExperience(Integer.valueOf(sepData[4]));
	        			moveDist = Integer.valueOf(sepData[7]);
	        			setAttack(Integer.valueOf(sepData[8]));
	        			attackDist = Integer.valueOf(sepData[9]);
	        			setAttack2(Integer.valueOf(sepData[10]));
	        			attackDist2 = Integer.valueOf(sepData[11]);
	        			break; //break out of loop because we are finished
	        		}
	        		
	        }
	        
	        scanner.close();
		}
		
	}
	
	//print out information of the enemy. Just for testing
	public String toString() {
		return ("ENEMY STATS: " + String.valueOf(this.gethp()) + ", " + String.valueOf(this.getDefense()) + ", " 
				+ String.valueOf(this.getExperience())+ ", " + String.valueOf(this.getSpeed()) + ", " 
				+ String.valueOf(this.getDexterity()) + ", " + String.valueOf(this.getAttack()) 
				+ ", " + String.valueOf(this.getMoveDist()) + ", " + String.valueOf(this.getAtkDist())) ;
	}
	
	//Getters
	public double getMoveDist()	{	return moveDist;	}
	public double getAtkDist()	{	return attackDist;	}
	public double getAtkDist2()	{	return attackDist2;	}
	public boolean getActive()	{	return active;	}
	public double getSpeed()	{		return speed/100.0;	}
	
	//Setters
	public void setActive(boolean b)	{	active = b;	}

}
