package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class BuildingKeyListener extends GameObject implements KeyListener {

	//The Building object that this listener belongs to.
	public ImportantBuilding parentBuilding;
	private MenuDataArchive parentArchive;
	//Boolean on whether or not player is close enough to interact.
	private boolean inRadius = false;
	//Interaction radius.
	private final int RADIUS = 175;
	//Coordinates for drawing visible interaction radius.
	private int drawX;
	private int drawY; 
	//Stores user key input
	private int key;
	
	private static ChoiceMenu topMenu; //i hope this being static isnt a problem.
	public ChoiceMenu getTopMenu() {
		return topMenu;
	}
	//i hope this being static is not an issue
	
	BuildingKeyListener(float setX, float setY, ImportantBuilding setParent) {
		super(setX, setY, ID.BuildingListener);
		this.parentBuilding = setParent;
		this.parentArchive = parentBuilding.getMenuDataInBuilding();
		//System.out.println("buildTime parentBuilding = " + parentBuilding);
		//System.out.println("buildTime parentArchive = " + parentArchive);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		key = e.getKeyCode();
		System.out.println(key);
		System.out.println("inRadius");
		if (inRadius && key == KeyEvent.VK_F && Game.gameState == Game.STATES.Playing) {
			key = 0;
			//System.out.println("parentArchive = " + parentArchive);
			
			
			topMenu = new ChoiceMenu(parentArchive);
			
			
			
			//System.out.println(Game.handler.object.size() + " = size");
			Game.handler.addObject(topMenu);
			//System.out.println(Game.handler.object.size() + " = after size");
			Game.thisGame.addKeyListener(topMenu);
		}
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tick() {
		//Update the coordinates the radius is drawn at each tick. Remove this in final build, this is for development.
		drawX = (int) x - (int) Player.getPlayerX() - RADIUS + parentBuilding.getBuildingSizeX() / 2;
		drawY = (int) y - (int) Player.getPlayerY() - RADIUS + parentBuilding.getBuildingSizeY() / 2;

		//Update coordinates used to calculate distance from this building every tick.
		int uX = (int) x - (int) Player.getPlayerX() + parentBuilding.getBuildingSizeX() /2;
		int uY = (int) y - (int) Player.getPlayerY() + parentBuilding.getBuildingSizeY() /2;
		if (Game.getPlayerDist(uX, uY) < RADIUS) {
			inRadius = true;
		} else {
			inRadius = false;
		}
		if (inRadius) {
			//System.out.print("in Radius");
			
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.drawOval(drawX, drawY, 2*RADIUS, 2*RADIUS); //2*radius = diameter of circle.
		//g.drawOval(drawX, drawY, 35, 35); debug for "where is the oval origin?"
		if (inRadius) {
			Font font = new Font("arial", 1, 28);
			g.setFont(font);
			g.setColor(Color.black);
			g.drawString("F", (int) (Player.getDRAW_PLAYER_X()) - 34, (int) (Player.getDRAW_PLAYER_Y()) - 48); //interaction prompt

			
		}
	}


	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
