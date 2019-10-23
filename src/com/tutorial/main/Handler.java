package com.tutorial.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	//Handler is the thing that takes "code objects" and turns them into objects that matter to the player.
	//It makes them visible and makes them do stuff.
	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	//object is actually a list of GameObjects
	//Makes all game objects do stuff by calling their tick method.
	public void tick() {
		boolean foundListener = false;
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject.getId().equals(ID.BuildingListener)) {
				BuildingKeyListener smepObject = (BuildingKeyListener) tempObject;
				int uX = (int) tempObject.getX() - (int) Player.getPlayerX() + smepObject.parentBuilding.getBuildingSizeX() /2;
				int uY = (int) tempObject.getY() - (int) Player.getPlayerY() + smepObject.parentBuilding.getBuildingSizeY() /2;
				if (Game.getPlayerDist(uX, uY) < 175) { //if player within range of that BL
					Game.activeListener = (BuildingKeyListener) tempObject;
					foundListener = true;
					//System.out.println("we've got one = " + Game.priorityBKL);
				}
			}
			if (Game.gameState == Game.STATES.Playing && tempObject.getId() != ID.MenuEntity) {
				tempObject.tick(); //make each "regular" object tick when not in menu
			} else if (Game.gameState == Game.STATES.Menu && tempObject.getId() == ID.MenuEntity) {
				//System.out.println("Menu zone");
				tempObject.tick(); //make each menu tick when in menu
			}

		}
		if (foundListener == false) {
			Game.activeListener = null;
		}
	}
	
	//Makes all game objects visible by calling their render method.
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (Game.gameState == Game.STATES.Playing && tempObject.getId() != ID.MenuEntity) {
				tempObject.render(g); //tick all non-menus in playtime.
				if (tempObject.getId() == ID.BuildingListener) {
					//System.out.println(tempObject.getX() + "x");
					//System.out.println(tempObject.getY() + "y");
				}
			} else if (Game.gameState == Game.STATES.Menu) {
				tempObject.render(g);
			}
		}
	}
	
	//Adds an object to the handler, and makes it "real" so that it can be visible and do stuff.
	public void addObject(GameObject object) {
		this.object.add(object);
		
	}
	
	//Removes an object from the handler, so it will no longer be visible or do stuff.
	public void removeObject(GameObject object) {
		 this.object.remove(object);
	}
	
}
