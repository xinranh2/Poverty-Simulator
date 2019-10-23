package com.tutorial.main;
//One instance of this class will be made. That class will have as variables...every piece of menu data, for easy,
//object oriented retrieval. Yes.
public class MenuLibrary {
	public ResultMenu resultMenu;
	
	private String mainMenuPrompt = "Poverty Simulator";
	private String[][] mainMenuOptions = {
			{"Begin", "mainMenuBegin"},
			{"Instructions", "mainMenuInstructions"},
			{"Quit", "mainMenuQuit"} };
	
	private ChoiceProcessorInterface mainMenuProcessor;
	
	public MenuDataArchive mainMenuArchive;
	
	private String burgerPrompt = "Welcome to macdonarls";
	private String[][] burgerOptions = { {"Buy a Meal", "buyBurgerFood"},
			{"Apply for a Job", "applyBurgerJob"},
			{"Leave", "leaveBurger"} };
	
	private ChoiceProcessorInterface burgerProcessor;
	public MenuDataArchive burgerTopArchive;
	
	private String burger0Prompt = "You buy one borger.";
	private String[][] burger0Options = { {"Okay", "burger0Leave"} };
	private ChoiceProcessorInterface burger0Processor;
	public MenuDataArchive burger0Archive;
	
	private String burger1Prompt =  "You acquire burger job.";
	private String[][] burger1Options = { {"Happy to McServe you, sir.", "endBurgerJob"} };
	private ChoiceProcessorInterface burger1Processor;
	public MenuDataArchive burger1Archive;
	
	private String apartmentPrompt = "It's your apartment.";
	private String[][] apartmentOptions = { {"Sleep", "sleepApartment"}, {"Leave", "leave"} };
	private ChoiceProcessorInterface apartmentProcessor;
	public MenuDataArchive apartmentTopArchive;
	
	private String apartment0Prompt = "good nap good nap good nap good nap good nap good nap good nap";
	private String[][] apartment0Options = { {"Leave apartment", "leave"} };
	private ChoiceProcessorInterface apartment0Processor;
	public MenuDataArchive apartment0Archive;
	
	public MenuDataArchive apartmentArchive;
	
	private String collegePrompt = "College Admissions Office";
	private String[][] collegeOptions = { {"Pay Tuition($1000", "payTuition"}, {"Leave", "leave"} };
	private ChoiceProcessorInterface collegeProcessor;
	
	public MenuDataArchive collegeArchive;
	
	private String college0Prompt = "Paid tuition";
	private String[][] college0Options = { {"Leave", "leave"} };
	private ChoiceProcessorInterface college0Processor;
	
	public MenuDataArchive college0Archive;
	
	MenuLibrary() {

		this.mainMenuProcessor = (String choiceIDPr) -> {
			System.out.println("choice ID = " + choiceIDPr);
			switch (choiceIDPr) {
			case "mainMenuBegin":
					Game.thisGame.gameMapSetup();
					Game.mainMenuOpen = false;
					Game.gameState = Game.STATES.Playing;
					Game.handler.removeObject(Game.mainMenu);
					Game.thisGame.removeKeyListener(Game.mainMenu);
					break;
				case "mainMenuInstructions":
					System.out.println("GAME INSTRUCTIONS DEBUG");
					break;
				case "mainMenuQuit":
					System.out.println("GAME QUIT DEBUG");
					System.exit(1);
					break;
			}
		};
		
		mainMenuArchive = new MenuDataArchive(mainMenuPrompt, mainMenuOptions, mainMenuProcessor);
		
		//=====================================================================================================================
		
		this.burgerProcessor = (String choiceIDPr) -> {
			switch (choiceIDPr) {
			case "buyBurgerFood" :
				///new menu here
				resultMenu = new ResultMenu(burger0Archive);
				makeResultMenu();
				closeOldMenu();
				HUD.hunger += (100 - HUD.hunger);
				HUD.money -= 2.50;

				break;
			case "applyBurgerJob" :
				resultMenu = new ResultMenu(burger1Archive);
				makeResultMenu();
				closeOldMenu();
				break;
			case "leaveBurger" :
				closeOldMenu();
				closeResultMenu();
				break;
			}
		};
		
		
		
		this.burger0Processor = (String choiceIDPr) -> {
			switch (choiceIDPr) {
			case "burger0Leave" :
				System.out.println("i should see a beter");
				closeResultMenu();
				break;
			}
		};
		
		this.burger1Processor = (String choiceIDPr) -> {
			switch (choiceIDPr) {
			case "endBurgerJob" :
				System.out.println("ending burger job");
				closeResultMenu();
				break;
			}
		};
		
		burger0Archive = new MenuDataArchive(burger0Prompt, burger0Options, burger0Processor);
		burger1Archive = new MenuDataArchive(burger1Prompt, burger1Options, burger1Processor);
		burgerTopArchive = new MenuDataArchive(burgerPrompt, burgerOptions, burgerProcessor);
		
		this.apartmentProcessor = (String choiceIDPr) -> {
			switch (choiceIDPr) {
			case "sleepApartment" :
				resultMenu = new ResultMenu(apartment0Archive);
				makeResultMenu();
				closeOldMenu();
				break;
			case "leave" :
				closeOldMenu();
				Game.gameState = Game.STATES.Playing;
				break;
			}
		};
	
		
		this.apartment0Processor = (String choiceIDPr) -> {
			switch (choiceIDPr) {
			case "leave" :
				closeResultMenu();
				break;
			}
		};
		
		apartmentArchive = new MenuDataArchive(apartmentPrompt, apartmentOptions, apartmentProcessor);
		apartment0Archive = new MenuDataArchive(apartment0Prompt, apartment0Options, apartment0Processor);
		
		this.collegeProcessor = (String choiceIDPr) -> {
			switch (choiceIDPr) {
			case "payTuition" :
				System.out.println("TUITION");
				resultMenu = new ResultMenu(college0Archive);
				closeOldMenu();
				makeResultMenu();
				break;
			case "leave":
				closeOldMenu();
				Game.gameState = Game.STATES.Playing;
				break;
			}
		};
		
		collegeArchive = new MenuDataArchive(collegePrompt, collegeOptions, collegeProcessor);
		
		this.college0Processor = (String choiceIDPr) -> {
			switch (choiceIDPr) {
			case "leave" :
				closeResultMenu();
				break;
			}
		};
		
		college0Archive = new MenuDataArchive(college0Prompt, college0Options, college0Processor);
		
	} // END OF BOX CONSTRUCTOR
		
	public void closeResultMenu() {
		System.out.println("Beter");
		Game.thisGame.removeKeyListener(resultMenu);
		Game.handler.removeObject(resultMenu);
		Game.gameState = Game.STATES.Playing;

	}
	public void closeOldMenu() {
		Game.handler.removeObject(Game.activeListener.getTopMenu());
		Game.thisGame.removeKeyListener(Game.activeListener.getTopMenu());
	}
	public void makeResultMenu() {
		Game.handler.addObject(resultMenu);
		Game.thisGame.addKeyListener(resultMenu);
	}
}
		

		
