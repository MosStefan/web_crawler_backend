package com.mastercrawler.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class ScraperResult {
	
	private String id;
	private String url;
	private String nameResult;
	private String rating;
	private String countReviews;
	private String typeOfFoods;
	private String minOrderValue;
	
	public ScraperResult() {
	
	}

	public ScraperResult(String id, String url, String nameResult, String rating, String countReviews, String typeOfFoods,
			String minOrderValue) {
		this.id = id;
		this.url = url;
		this.nameResult = nameResult;
		this.rating = rating;
		this.countReviews = countReviews;
		this.typeOfFoods = typeOfFoods;
		this.minOrderValue = minOrderValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getNameResult() {
		return nameResult;
	}

	public void setNameResult(String nameResult) {
		this.nameResult = nameResult;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countReviews == null) ? 0 : countReviews.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((minOrderValue == null) ? 0 : minOrderValue.hashCode());
		result = prime * result + ((nameResult == null) ? 0 : nameResult.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((typeOfFoods == null) ? 0 : typeOfFoods.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScraperResult other = (ScraperResult) obj;
		if (countReviews == null) {
			if (other.countReviews != null)
				return false;
		} else if (!countReviews.equals(other.countReviews))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (minOrderValue == null) {
			if (other.minOrderValue != null)
				return false;
		} else if (!minOrderValue.equals(other.minOrderValue))
			return false;
		if (nameResult == null) {
			if (other.nameResult != null)
				return false;
		} else if (!nameResult.equals(other.nameResult))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (typeOfFoods == null) {
			if (other.typeOfFoods != null)
				return false;
		} else if (!typeOfFoods.equals(other.typeOfFoods))
			return false;
		return true;
	}

	
	
	
	
	

}
