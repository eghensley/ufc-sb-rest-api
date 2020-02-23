package com.hensley.ufc.enums;

import java.util.HashMap;
import java.util.Map;

public enum FightMethodEnum {
	KO_TKO("KO/TKO", 5, true), 
	DEC_SPLIT("Decision - Split", 1, false),
	DEC_UNAN("Decision - Unanimous", 3, false),
	SUB("Submission", 5, true),
	DEC_MAJ("Decision - Majority", 2, false),
	DOC_STOP("TKO - Doctor's Stoppage", 4, true),
	OVT("Overturned", 1, false),
	CNC("Could Not Continue",1,false),
	DQ("DQ", 1, false);
	
    private static final Map<String, FightMethodEnum> BY_DESCRIPTION = new HashMap<>();

    static {
        for (FightMethodEnum e : values()) {
        	BY_DESCRIPTION.put(e.description, e);
        }
    }
    
	public final String description;
	public final Integer winWeight;
	public final boolean finish;

	private FightMethodEnum(String description, Integer winWeight, boolean finish) {
		this.description = description;
		this.winWeight = winWeight;
		this.finish = finish;
	}
    
    public static FightMethodEnum valueOfDesc(String description) {
        return BY_DESCRIPTION.get(description);
    }

	/**
	 * @return the finish
	 */
	public boolean isFinish() {
		return finish;
	}
    
    
    
}
