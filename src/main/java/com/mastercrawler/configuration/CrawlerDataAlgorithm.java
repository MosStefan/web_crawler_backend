package com.mastercrawler.configuration;

public class CrawlerDataAlgorithm {

	private String seed;
	private int depth;
	
	public CrawlerDataAlgorithm() {
		
	}
	
	public CrawlerDataAlgorithm(String seed, int depth) {
		this.seed = seed;
		this.depth = depth;
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
	
}
