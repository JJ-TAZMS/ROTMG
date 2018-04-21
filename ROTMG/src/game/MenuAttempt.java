package game;

import java.util.Scanner;
public class MenuAttempt //please work
{
	boolean exit;
	
	public static void main(String[] args)
	{
		MenuAttempt menu = new MenuAttempt();
		menu.runMenu();
	}
	
	public void runMenu()
	{
		printHeader();
		while(!exit)
		{
			printMenu();
			int choice = getInput();
			performAction(choice);
		}
	} 
		
	private void printHeader()
	{
		System.out.println("Realm of the Mad God");
	}
	
	private void printMenu()
	{
		System.out.println("\nPlease make a selection: ");
		System.out.println("1: Play");
		System.out.println("2: Instructions");
		System.out.println("0: Get depressed");
	}//the time is 11:58pm. I want to sleep, but I want to die too. pls send help.
	
	private int getInput()
	{
		Scanner kb = new Scanner(System.in);
		int choice = -1;
		while(choice < 0 || choice > 2)
		{
			try
			{
				System.out.print("\nEnter your choice: ");
				choice = Integer.parseInt(kb.nextLine());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid selection. Please try again.");
			}
		}
		return choice;
	}
	private void performAction(int choice)
	{
		switch(choice)
		{
		case 0:
			exit = true;
			System.out.println("nigga just look at the mirror if you tryna feel emo");
			break;
		case 1:
			playGame();
			break;
		case 2: 
			gameInstructions();
			break;
		default:
			System.out.println("An unknown error has occured.");
		}
	}
	
	private void playGame()
	{
		//starts game here
		
	}
	private void gameInstructions()
	{
		System.out.println("WASD to move. Left click to fire.");
		
	}
}
