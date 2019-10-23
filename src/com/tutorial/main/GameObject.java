package com.tutorial.main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject { //all game entities are this object
	//they will extend it, probably

	protected float x, y; //everything needs a coordinate
	//protected -> can only be accessed by it and its descendants
	protected ID id; //everything is a player or enemy
	protected float velX, velY;
	
	public GameObject(float x, float y, ID id) { //THE PARENT CONSTRCTOR,called by super
		this.x = x; //give each object these from input
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick(); //these methods exist for GameObject's
	public abstract void render(Graphics g); 
	public abstract Rectangle getBounds();
	
	public void setX(float x) { //not abstract -> in player class but hidden
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setId(ID id) {
		this.id = id;
	}
	
	public ID getId() {
		return id;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

}
