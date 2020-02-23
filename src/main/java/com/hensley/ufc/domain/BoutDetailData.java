//package com.hensley.ufc.domain;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "BOUT_DETAIL")
//public class BoutDetailData extends BaseAuditEntity implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 6830024126207979369L;
//
//	@Column(name = "BOUT_DETAIL_ID", nullable = false)
//	private String boutDetailId;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "WINNER_FIGHTER_OID", referencedColumnName = "OID", nullable = false)
//	private FighterData winnerFighter;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "LOSER_FIGHTER_OID", referencedColumnName = "OID", nullable = false)
//	private FighterData loserFighter;
//	
//	
//	
//}
