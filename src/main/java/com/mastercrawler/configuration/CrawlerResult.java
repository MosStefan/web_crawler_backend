package com.mastercrawler.configuration;

import java.util.List;


public class CrawlerResult {

	private String url;
    private String title;
    private String rating;
    private String countReviews;
	private String typeOfFoods;
	private String minOrderValue;
    private List<String> hyperlinks;
    private int depth;
    
	public CrawlerResult(String url, String title, String rating, String countReviews, String typeOfFoods,
			String minOrderValue, List<String> hyperlinks, int depth)
	{
		this.url = url;
		this.title = title;
		this.rating = rating;
		this.countReviews = countReviews;
		this.typeOfFoods = typeOfFoods;
		this.minOrderValue = minOrderValue;
		this.hyperlinks = hyperlinks;
		this.depth = depth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCountReviews() {
		return countReviews;
	}

	public void setCountReviews(String countReviews) {
		this.countReviews = countReviews;
	}

	public String getTypeOfFoods() {
		return typeOfFoods;
	}

	public void setTypeOfFoods(String typeOfFoods) {
		this.typeOfFoods = typeOfFoods;
	}

	public String getMinOrderValue() {
		return minOrderValue;
	}

	public void setMinOrderValue(String minOrderValue) {
		this.minOrderValue = minOrderValue;
	}

	public List<String> getHyperlinks() {
		return hyperlinks;
	}

	public void setHyperlinks(List<String> hyperlinks) {
		this.hyperlinks = hyperlinks;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	
    
    
	
}
