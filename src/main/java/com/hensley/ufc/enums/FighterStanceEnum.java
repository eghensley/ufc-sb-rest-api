package com.hensley.ufc.enums;

import java.util.HashMap;
import java.util.Map;

public enum FighterStanceEnum {
	 SP("SOUTHPAW"),  OR("ORTHODOX"), SW("SWITCH");
	
    private static final Map<String, FighterStanceEnum> BY_DESCRIPTION = new HashMap<>();

    static {
        for (FighterStanceEnum e : values()) {
        	BY_DESCRIPTION.put(e.description, e);
        }
    }
    
	public final String description;

	private FighterStanceEnum(String description) {
		this.description = description;
	}

    public static FighterStanceEnum valueOfDesc(String description) {
        return BY_DESCRIPTION.get(description);
    }
	
}
