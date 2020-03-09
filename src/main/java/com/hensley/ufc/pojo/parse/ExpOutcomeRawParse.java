package com.hensley.ufc.pojo.parse;

import java.util.List;

public class ExpOutcomeRawParse {
	private String name;
	private List<List<List<String>>> data;
	
	public ExpOutcomeRawParse() {
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the data
	 */
	public List<List<List<String>>> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<List<List<String>>> data) {
		this.data = data;
	}
	
	
}
