package com.tutorial.main;

public class Job{
	//should display a menu thats not attached to a building
	//maybe instead just have a boolean set for when shift starts and ends, or 
	//if player missed shift
	private String name;
	private boolean hasJob;
	private boolean shift;
	public int strikes;
	private double wage;
	
	public Job(String jobName, double pay) {
		name = jobName;
		wage = pay;
	}
	public boolean getHasJob() {
		return hasJob;
	}
	public boolean getShift() {
		return shift;
	}
	public double getWage() {
		return wage;
	}
	public void missedShift() {
		strikes++;
		if (strikes > 3) {
			hasJob = false;
			System.out.println("You lost your " + name + " job");
			//should somehow have a pop-up menu
		}
	}

}
