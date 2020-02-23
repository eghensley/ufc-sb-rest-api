package com.hensley.ufc.enums;

import java.util.HashMap;
import java.util.Map;

public enum BoutOutcomeEnum {
	W("WIN"), L("LOSS"), P("PENDING"), D("DRAW"), NC("NC");
	
    private static final Map<String, BoutOutcomeEnum> BY_DESCRIPTION = new HashMap<>();

    static {
        for (BoutOutcomeEnum e : values()) {
        	BY_DESCRIPTION.put(e.description, e);
        }
    }
    
	public final String description;

	private BoutOutcomeEnum(String description) {
		this.description = description;
	}

    public static BoutOutcomeEnum valueOfDesc(String description) {
        return BY_DESCRIPTION.get(description);
    }
}
