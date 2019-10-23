package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.event.KeyAdapter;
import java.awt.image.BufferStrategy;
//import java.util.Random;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//
public class Game extends Canvas implements Runnable{
	 
	private static final long serialVersionUID = 1550691097823471818L;
	public MenuLibrary library;
	public static Game thisGame;
	public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		thisGame = new Game();
	}
	public static final int WIDTH = 800, HEIGHT = WIDTH /12 * 9; //resolution setup
	
	private Thread threadName; //threadName is instance of Thread
	private boolean running = false; //this is a single thread game; that could be relevant.
	//(is your game running? you'd better go catch it!)
	//private Random r;
	public static Handler handler;
	//this being static shouldn't be an issue; there's only one handler anyway, we pass it to everything.
	private HUD hud;
	public static ChoiceMenu mainMenu;
	public static boolean mainMenuOpen = true;
	
	PlayerMovement playerMovementThing;
	public static BuildingKeyListener activeListener;
	public enum STATES { //determines if game is in a menu or not
		Menu, //in a menu
		Playing, //not in a menu
	};
	

	
	
	public static void checkGameState() {
		if (Game.gameState == Game.STATES.Menu) {
			System.out.println("Menu.");
		} else {
			System.out.println("Playing.");
		}
	}
	
	public static STATES gameState = STATES.Menu; //initial state of game, should be menu at first to bring up main menu
	//this being static shoul not be a problem; if we have more than one "game" at a time, something is wrong.
	
	public Game() throws UnsupportedAudioFileException, IOException, LineUnavailableException { //Game class constructor
		handler = new Handler();
		hud = new HUD();
		library = new MenuLibrary();
		

		new Window(WIDTH, HEIGHT, "Poverty Simulator", this); //creates an instance of Window class for the name, sets size and name
		setUpMainMenu(); //set up main menu
		System.out.println("DOES THIS HAPPEN BEFORE YOU LEAVE THE MENU?");

		playerMovementThing = new PlayerMovement(handler);
		this.addKeyListener(playerMovementThing); //allow game to respond to inputs for player movement
		
		
		//r = new Random();
		//if (gameState == STATES.Playing) {
			//this area doesn't get run right now. it only runs at start time, but the game is in main menu at that time.
			//will remove later.
		} 
	
	public void gameMapSetup() {
		//HARDCODE THE POSITION/SIZE/STUFF OF ALL MAB OBJECTS RIGHT HERE
		Game.handler.addObject(new Player((Game.WIDTH/2) - 32, (Game.HEIGHT/2) - 32, ID.Player, Game.handler));
		Game.handler.addObject(new Building(400, 200, ID.Building, 121, 88, "building3.png"));
		Game.handler.addObject(new ImportantBuilding(50, 50, ID.Building, 117, 127, library.burgerTopArchive, "building2.png"));
		//Game.handler.addObject(new ImportantBuilding(300, 300, ID.Building, 150, 150, BuildingKeyListener.walmartArchive));
		Game.handler.addObject(new ImportantBuilding(700, 300, ID.Building, 152, 178, library.apartmentArchive, "building1.png"));
		Game.handler.addObject(new Building(700, 100, ID.Building, 1221, 88, "building3.png"));
		Game.handler.addObject(new ImportantBuilding(200, 200, ID.Building, 154, 225, library.collegeArchive, "building4.png"));
	}
	
	//this is used to keep a float variable between a max and minimum.
	public static float clamp(float variable, float min, float max) {
		if (variable >= max) {
			return max;
		} else if (variable <= min) {
			return min;
		}
		return variable; //if within range already, do nothing
	}
	
	//same thing as the first clamp but for integers
	public static int clamp(int variable, int min, int max) {
		//System.out.println("Clamping in progress...");
		if (variable >= max) {
			return max;
		} else if (variable <= min) {
			return min;
		}
		return variable; //if within range already, do nothing
	}
	public synchronized void start() {
		threadName = new Thread(this); //this = this instance of our Game class
		threadName.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			threadName.join(); //stops the thread, basically
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() { //the game loop, try not to mess with this
		this.requestFocus(); //added to make the game focus (take input) whenever it's selected/alt tabbed to. 
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		//int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			//frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				//prints FPS to console each seconds
				//frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		//checkGameState(); for debugging
		//System.out.println("outside tick call.");
		handler.tick(); //when the handler ticks, everyone ticks.
		if (playerMovementThing != null) {
			playerMovementThing.tick();
		}
		if (gameState == STATES.Playing) {
			hud.tick();
		} else if (gameState == STATES.Menu) {
			//mainMenu.tick();
		};
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) { //weird stuff, don't worry about it, idk what it does.
			this.createBufferStrategy(3);
			return;	
		} 
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.gray);
		g.fillRect(0,0, WIDTH, HEIGHT);
		//THIS FILLS IN THE BACKGROUND COLOR
		
		handler.render(g); //when the handler renders, everyone does
		hud.render(g);
		if (mainMenuOpen) {
			Font okFont = new Font("monospaced", 1, 20);
			g.setFont(okFont);
			g.drawString("Use W and S keys to navigate menu options.", 70, 480);
			g.drawString("Press E to choose an option. Press F when prompted to interact.", 35, 510);
		}
		g.dispose();
		bs.show();
	}
	
	public static float getPlayerDist(float a, float b) { //returns distance from player to point x,y
		float xDist = (((Game.WIDTH) / 2) - 32 - a);
		float yDist = (((Game.HEIGHT) / 2) - 32 - b);
		xDist = xDist * xDist; //square it
		yDist = yDist * yDist;
		float distance = (float) Math.sqrt(xDist + yDist);
		return distance;
	}
	public void setUpMainMenu() {
		mainMenu = new ChoiceMenu(library.mainMenuArchive); //make mainMenu a new instance of Menu class
		Game.handler.addObject(mainMenu);
		this.addKeyListener(mainMenu); //this has to be here for main menu to take player input
	}
	


	
	
}
