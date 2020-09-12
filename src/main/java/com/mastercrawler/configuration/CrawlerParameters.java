package com.mastercrawler.configuration;


public class CrawlerParameters {
	
	
	private String seed;
	private int depth;
	private int milisecSheduleTaskDelay;
	
	public CrawlerParameters() {	
	}
	
	public CrawlerParameters(String seed, int depth, int milisecSheduleTaskDelay) {
		this.seed = seed;
		this.depth = depth;
		this.milisecSheduleTaskDelay = milisecSheduleTaskDelay;
	}
	
	public String getSeed() {
		return seed;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getMilisecSheduleTaskDelay() {
		return milisecSheduleTaskDelay;
	}
	public void setMilisecSheduleTaskDelay(int milisecSheduleTaskDelay) {
		this.milisecSheduleTaskDelay = milisecSheduleTaskDelay;
	}
	
}
