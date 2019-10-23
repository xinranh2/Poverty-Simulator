package com.tutorial.main;

//import java.awt.Font;

//These buildings get KeyHandlers, special art, and menus. 
public class ImportantBuilding extends Building {
	
	private BuildingKeyListener listener;
	//private boolean inRange = false;
	
	private ChoiceProcessorInterface function;
	public ChoiceProcessorInterface getFunction() {
		return function; //this is a getter.
	}
	
	protected MenuDataArchive menuDataInBuilding;
	public MenuDataArchive getMenuDataInBuilding() {
		return this.menuDataInBuilding;
	}
	
	
	public ImportantBuilding(int x, int y, ID id, int sizeX, int sizeY, MenuDataArchive setMenuDataInBuilding, String imagepath) { //constructor
		super(x, y, id, sizeX, sizeY, imagepath);
		this.menuDataInBuilding = setMenuDataInBuilding;
		BuildingKeyListener aListener = new BuildingKeyListener(x, y, this);
		this.listener = aListener;
		Game.thisGame.addKeyListener(aListener);
		Game.handler.addObject(listener);
	}

	
	public void tick() {
		
	}
	
	/*public void render(Graphics g) {
		//g.setColor(Color.yellow);
		g.drawImage(img,(int) x - (int) Player.getPlayerX(), (int) y - (int) Player.getPlayerY(), null);
		g.fillRect((int) x - (int) Player.getPlayerX(), (int) y - (int) Player.getPlayerY(),
				getBuildingSizeX(), getBuildingSizeY());
	}
	*/

}
