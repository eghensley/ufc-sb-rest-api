package com.hensley.ufc.pojo.request;

public class AddMyBookieOddsRequest {
	private String oid;
	private Double mlOdds;

	public AddMyBookieOddsRequest() {
		
	}

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @return the mlOdds
	 */
	public Double getMlOdds() {
		return mlOdds;
	}

	/**
	 * @param mlOdds the mlOdds to set
	 */
	public void setMlOdds(Double mlOdds) {
		this.mlOdds = mlOdds;
	}
	
	
}
