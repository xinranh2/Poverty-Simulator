package com.tutorial.main;

public class MenuDataArchive {
	
	private String promptString;
	public String getPromptString() {
		return this.promptString;
	}
	
	private String[][] optionStrings;
	public String[][] getOptionStrings() {
		return this.optionStrings;
	}
	
	private ChoiceProcessorInterface archFunction;
	public ChoiceProcessorInterface getArchFunction() {
		return this.archFunction;
	}
	
	MenuDataArchive(String setArchPromptString, String[][] setOptionStrings, ChoiceProcessorInterface setArchFunction) {
		this.promptString = setArchPromptString;
		this.optionStrings = setOptionStrings;
		this.archFunction = setArchFunction;
	}
	
}
