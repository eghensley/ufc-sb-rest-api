package com.hensley.ufc.pojo.dto.fighter;

public class FighterOidAndNameDto {
	private String oid;
	private String fighterName;

	public FighterOidAndNameDto() {

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
	 * @return the fighterName
	 */
	public String getFighterName() {
		return fighterName;
	}

	/**
	 * @param fighterName the fighterName to set
	 */
	public void setFighterName(String fighterName) {
		this.fighterName = fighterName;
	}

}
