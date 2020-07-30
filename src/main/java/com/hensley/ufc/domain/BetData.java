//package com.hensley.ufc.domain;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "BET")
//public class BetData extends BaseAuditEntity implements Serializable {
//
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "FIGHTER_OID", referencedColumnName = "OID", nullable = false)
//	private FighterData fighter;
//	
//	@Column(name = "FIGHT_DATE", nullable = false)
//	private Date fightDate;
//	
//	
//	
//	public BetData() {
//		
//	}
//}
