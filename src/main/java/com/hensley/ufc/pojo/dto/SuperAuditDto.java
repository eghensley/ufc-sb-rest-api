package com.hensley.ufc.pojo.dto;

import javax.persistence.Id;

public class SuperAuditDto {

	@Id
	private String oid;

	/**
	 * @return the id
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param id the id to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	
}
