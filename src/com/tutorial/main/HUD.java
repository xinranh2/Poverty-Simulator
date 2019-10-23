package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HUD {
	
	public static long rawTimeElapsed = 0;
	public static double health = 100;
	public static double money = 10.00;
	public static double hunger = 100.00;
	private static double moneyPast = 10.00;
	private static double moneyDelta;
	private static String moneyDeltaString;
	private static int moneyDeltaY = 10;
	
	private final int BARX = 150; //x value of start of hunger/hp barsr
	private final int HEALTHY = 5; //y value to draw health bar at 
	
	private final int BARSIZEY = 18; //y size of hunger/hp bars
	private final int HUNGERY = 27; //y value to draw hunger rectangles at
	
	private final int BARLENGTH = 200; //length of hp and health bas
	
	private double hungerPercent; 
	private double healthPercent; //used for math for healthFullnessX
	
	private int hungerFullnessX; //used for drawing hunger/hp bars based on current value
	private int healthFullnessX;
	//this measures time passed since game start in ticks. this is public, so it will be used for any game events tied to time.
	//everything else is just formatting for the player's sake. 
	//60 ticks = one real second. 3600 ticks = one real minute.
	//currently, the game runs at one in-game minute = 1 tick. this is just for now, later on it will run slower.
	private double day;
	private double hour;
	private double min;

	
	public void tick() {
		rawTimeElapsed++; //keeps track of actual time since inception of universe
		day = (rawTimeElapsed / (1440 * 15)) + 1; //for player, goes to infniite
		hour = (rawTimeElapsed / 900) % 24; //for math, loops 0 - 23
		min = (rawTimeElapsed / 15) % 60; //for player, loops 0 - 59
		hunger -= .1;
		hunger = Game.clamp( (float) hunger, (float) 0.0, (float) 100.0);
		if (hunger < 50) {
			health -= .1;
		}
		health = Game.clamp( (float) health, (float) 0.0, (float) 100);
		healthPercent = health / 100;
		hungerPercent = hunger / 100;
		
		healthFullnessX = (int) (BARLENGTH * healthPercent);
		hungerFullnessX = (int) (BARLENGTH * hungerPercent);
		
		//put in a function updateMoney();
		if (money != moneyPast) { //if money has changed
			moneyDelta = money - moneyPast;
			moneyDeltaY = 10;
		}
		moneyPast = money;
	}
	
	//this converts the rawtimeElapsed to a a datetime output for hours/minutes display.
	private String hourPretty(double hour, double min) {
	String dateString = Double.toString(hour) + Double.toString(min);
	SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
	try {
		Date date3 = sdf.parse(dateString);
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh.mm aa");
		String dotVer = sdf2.format(date3);
		return dotVer.replace(".", ":");
	}catch(ParseException e) {
		e.printStackTrace();
	}
	return "this is bad";
}
	
	public void render(Graphics g) {
		if (!Game.mainMenuOpen) {
			Font hudFont = new Font("arial", 1, 16);
			g.setFont(hudFont);
			String moneyString = String.format("%.2f", money);
			
			g.setColor(Color.black);
			g.fillRect(0,0, 425, 50);
			
			g.setColor(Color.white);
			g.drawString("Day " + (int) day + " " + hourPretty(hour, min), 16, 22);
			g.drawString("$" + moneyString, 16, 42); //MOVE THSI
			
			g.drawString("Health", BARLENGTH + BARX + 4, HEALTHY + BARSIZEY - 3);
			g.drawString("Fullness", BARLENGTH + BARX + 4, HUNGERY + BARSIZEY - 2);
			

			
			g.setColor(Color.white); //draw outlines 
			g.drawRect(BARX, HEALTHY, BARLENGTH, BARSIZEY); //health bar outline
			g.drawRect(BARX - 1, HEALTHY - 1, BARLENGTH, BARSIZEY); //health bar outline
			g.drawRect(BARX, HUNGERY, BARLENGTH, BARSIZEY); //hunger bar outline
			g.drawRect(BARX - 1, HUNGERY - 1, BARLENGTH, BARSIZEY); //hunger bar outline
			
			g.setColor(Color.black);
			g.fillRect(BARX, HEALTHY, 200, BARSIZEY); //health bar background
			g.fillRect(BARX, HUNGERY, 200, BARSIZEY); //hunger bar background
			g.setColor(Color.red);
			g.fillRect(BARX, HEALTHY, healthFullnessX, BARSIZEY); //health bar
			g.setColor(Color.orange); //hunger bar
			g.fillRect(BARX, HUNGERY, hungerFullnessX, BARSIZEY);
			g.setColor(Color.black);
			
			//these lines show the player when their stuff is at 25%, 50%, and 75%
			//there are two lines to increase thickness
			g.drawLine(BARX + BARLENGTH/4, HUNGERY, BARX + BARLENGTH/4, HUNGERY + BARSIZEY - 1);
			g.drawLine(BARX + BARLENGTH/4 + 1, HUNGERY, BARX + BARLENGTH/4 + 1, HUNGERY + BARSIZEY - 1);
			//the minus one is there to stop the line from going over the border and looking bad
			g.drawLine(BARX + BARLENGTH/2, HUNGERY, BARX + BARLENGTH/2, HUNGERY + BARSIZEY - 1);
			g.drawLine(BARX + BARLENGTH/2 + 1, HUNGERY, BARX + BARLENGTH/2 + 1, HUNGERY + BARSIZEY - 1);
			
			g.drawLine(BARX + (3 * BARLENGTH) /4, HUNGERY, BARX + (3 * BARLENGTH) /4, HUNGERY + BARSIZEY - 1);
			g.drawLine(BARX + (3 * BARLENGTH) /4 + 1, HUNGERY, BARX + (3 * BARLENGTH)/4 + 1, HUNGERY + BARSIZEY - 1);
			
			//same thing but for hunger
			g.drawLine(BARX + BARLENGTH/4, HEALTHY, BARX + BARLENGTH/4, HEALTHY + BARSIZEY - 1);
			g.drawLine(BARX + BARLENGTH/4 + 1, HEALTHY, BARX + BARLENGTH/4 + 1, HEALTHY + BARSIZEY - 1);
			//the minus one is there to stop the line from going over the border and looking bad
			g.drawLine(BARX + BARLENGTH/2, HEALTHY, BARX + BARLENGTH/2, HEALTHY + BARSIZEY - 1);
			g.drawLine(BARX + BARLENGTH/2 + 1, HEALTHY, BARX + BARLENGTH/2 + 1, HEALTHY + BARSIZEY - 1);
			
			g.drawLine(BARX + (3 * BARLENGTH) /4, HEALTHY, BARX + (3 * BARLENGTH) /4, HEALTHY + BARSIZEY - 1);
			g.drawLine(BARX + (3 * BARLENGTH) /4 + 1, HEALTHY, BARX + (3 * BARLENGTH)/4 + 1, HEALTHY + BARSIZEY - 1);

			
			if (moneyDelta != 0) {
				if (moneyDelta > 0) {
					g.setColor(Color.green);
				} else {
					g.setColor(Color.red);
				}
				moneyDeltaString = String.format("%.2f", moneyDelta);
				g.drawString(moneyDeltaString, 10, moneyDeltaY);
				moneyDeltaY++;
			}
			//g.drawString("PlayerX " + Player.getPlayerX(), 32, 64);
			//g.drawString("PlayerY " + Player.getPlayerY(), 32, 96);
		}
	}
}
