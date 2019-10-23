package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChoiceMenu extends GameObject implements KeyListener {
	//these are for the whole menu itself
	protected final int DRAW_MENU_Y = Game.HEIGHT / 12; //all menus are drawn at the same spot
	protected final int DRAW_MENU_SIZE_X = 650;
	protected final int DRAW_MENU_X = (Game.WIDTH - DRAW_MENU_SIZE_X) / 2; //constants that decide where to draw the menu
	protected final int DRAW_MENU_SIZE_Y = 400;
	
	//these are for the player's option to pick from the menu
	//even if they only have one option (See NoChoiceMenu)
	protected final int DRAW_OPTION_X = DRAW_MENU_X + 75;
	protected final int DRAW_OPTION_Y = DRAW_MENU_Y + 200;
	protected final int DRAW_OPTION_SIZE_X = 410;
	protected final int DRAW_OPTION_SIZE_Y = 40;

	//these are the coords to draw the string for each choice at. (should be inside each option's rectangle)
	protected final int OPTSTRX = DRAW_OPTION_X + 5;
	protected final int OPTSTRY = DRAW_OPTION_Y + 30;
	
	protected String prompt; //this is the information the menu gives the player. i.e. "you are in a burger shop, what do?"
	
	protected final int INITIAL_SELECTION = 0; //default selection. this should be 0 for the main menu, and -1 for everything else. 
	protected int reticleY; //the y value to draw the reticle at
	protected final int RETICLEX = DRAW_OPTION_X - 25;
	protected final int RETICLESIZE = 20;
	
	protected final ChoiceProcessorInterface menuFunction;
	//menuFunction is a functional interface (basically a function.) Each menu gets its own function that will decide 
	//what code gets run based on the user's choice. 
	protected Font bigFont = new Font("arial", 1, 25);
	protected Font smallFont = new Font("arial", 1, 14);
	
	protected String[] choiceIdArray;
	
	private final int Y_GAP = 50; //the amount of space between each choice in the menu
	private int numChoices; //number of choices in the menu. goes between 2 - 4. 
	private String[] choiceStrings; //user-facing choice strings, i.e. "Buy a burger"
	private int selectedChoice; //the menu option that the player currently has selected 
	public int getSelectedChoice() {
		return selectedChoice;
	}
	private String finalChoice; //
	
	private int key;
	private MenuDataArchive archive;
	//there are two constructors because the "initial selection" argument is optional.
	//it should only be used for the main menu. on the main menu, we want "play" instantly selected.
	
	public ChoiceMenu(MenuDataArchive inputArchiveArray) {
		super(0, 0, ID.MenuEntity); //GameObject constructor.
		System.out.println("inputarchivearray = " + inputArchiveArray);
		//System.out.println("0 0 " + inputArchiveArray[0][0]);
		
		this.archive = inputArchiveArray;
		System.out.println(archive);
		
		this.prompt = archive.getPromptString();
		this.menuFunction = archive.getArchFunction();
		this.numChoices = archive.getOptionStrings().length;
		
		this.choiceStrings = new String[numChoices];
		this.choiceIdArray = new String[numChoices];
		
		for (int i = 0; i < numChoices; i++) {
			choiceStrings[i] = archive.getOptionStrings()[i][0];
		}
		for (int i = 0; i < numChoices; i++) {
			choiceIdArray[i] = archive.getOptionStrings()[i][1];
		}
		
		setReticleY();
		
		Game.gameState = Game.STATES.Menu;
		//System.out.println("Menu construction time, finalChoice = " + finalChoice);
	}
	
	
	private void setReticleY() {
		reticleY = DRAW_OPTION_Y + selectedChoice * (Y_GAP) + 8;
	}
	protected void drawReticle(Graphics g) {
		g.fillOval(RETICLEX, reticleY, RETICLESIZE, RETICLESIZE);
		//replace this with a picture of an arrow point to the right or something
	}
	
	public void callFunction(String choiceID) {
		menuFunction.processChoice(choiceID);
		//callFunction calls this menu's personal function (menuFunction.) That function calls the functional interface's 
		//only function, processChoice, which is defined for each individual menu. 
	}

	/* public void setUpTheMenuStrings(String[][] setMenuStrings) {
		for (int i = 0; i < setMenuStrings.length; i++) {
			//this.choiceStrings[0] = setMenuStrings[1][0]; 
			System.out.println(setMenuStrings[i][0]);
			System.out.println("i = " + i);
			System.out.println(choiceStrings[2]);
			choiceStrings[i] = setMenuStrings[i][0];
			//System.out.println(choiceStrings[i]);
			//from the i-th choice, set choiceString to the first string in the pair
		}
		for (int i = 0; i < setMenuStrings.length; i++) {
			choiceIdArray[i] = setMenuStrings[i][1];
			//get the choiceIDs from the array passed at construction time
		} 
	} */
	
	public void keyPressed(KeyEvent e) {
		//System.out.println("keyEvent in choicemenu activaitng");
		//lets player press button to change selection, clamps resulting value,sets selectedY and then draws oval
			key = e.getKeyCode(); 
		}
	
	public void tick() {
		modifyChoice(key);
		if (key == KeyEvent.VK_E || key == KeyEvent.VK_ENTER) {
			finalChoice = choiceIdArray[selectedChoice];
			System.out.println("final choice id = " + finalChoice);
			choiceProcessorMaster(finalChoice);
			//finalChoice = "nil";
		}
		//finalChoice = "nil";
		key = KeyEvent.VK_UNDEFINED;
	//System.out.println("Time to clamp!");
	selectedChoice = Game.clamp(selectedChoice, 0, numChoices - 1); //might not work because casting is weird
	//this will keep selection from going to an invalid value
	setReticleY();
	}
	
	private void choiceProcessorMaster(String choiceID) {
		//need to get a choiceID when a menu option is finally chosen. 
		//ChoiceProcessor choiceProcessorInstance;
		//ChoiceProcessorInterface choiceProcessorInstance;
		//System.out.println("choiceProcInst = " + choiceProcessorInstance);
		System.out.println("numChoices =" + numChoices);
		System.out.println("choiceStrings length = " + choiceStrings.length);
		System.out.println("choiceIdArry length = " + choiceIdArray.length);
		this.callFunction(choiceID);

		}
	
	private void modifyChoice(int key) {
		if (key == KeyEvent.VK_W) {
			//System.out.println("W press menu");
			selectedChoice -= 1;
		} else if (key == KeyEvent.VK_S) {
			//System.out.println("S press Menu");
			selectedChoice += 1;
		}
	}
	

	public void render(Graphics g) {
		
		g.setFont(bigFont);
		g.setColor(Color.gray);
		g.fillRect(DRAW_MENU_X, DRAW_MENU_Y, DRAW_MENU_SIZE_X, DRAW_MENU_SIZE_Y); //the menu itself
		g.setColor(Color.black);
		g.drawRect(DRAW_MENU_X, DRAW_MENU_Y, DRAW_MENU_SIZE_X, DRAW_MENU_SIZE_Y); //outline
		
		for (int i = 0; i < numChoices; i++) {
			//System.out.println("choiceMenu render activating");
			g.setColor(Color.white);
			g.drawString(prompt, DRAW_MENU_X + 30, DRAW_MENU_Y + 50); //the main story prompt
			g.drawRect(DRAW_OPTION_X, DRAW_OPTION_Y + i*(Y_GAP), DRAW_OPTION_SIZE_X, DRAW_OPTION_SIZE_Y); //draw each option box
			//System.out.println(choiceStrings[i]);
			g.drawString(choiceStrings[i], OPTSTRX, OPTSTRY + i*(Y_GAP));  //draw each option's string
			if (selectedChoice >= 0) { //if valid selection
				//System.out.println(selectedChoice);
				drawReticle(g);
				//draw reticle
			}
		}
	}


	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	} 

}
