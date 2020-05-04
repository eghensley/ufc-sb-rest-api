package com.hensley.ufc.enums;

import java.util.HashMap;
import java.util.Map;

public enum WeightClassEnum {
	WSW("Women's Strawweight", GenderEnum.FEMALE, 106, 115),
	FW("Flyweight", GenderEnum.MALE, 116, 125),
	WFW("Women's Flyweight", GenderEnum.FEMALE, 116, 125),
	BW("Bantamweight", GenderEnum.MALE, 126, 135),
	WBW("Women's Bantamweight", GenderEnum.FEMALE, 126, 135),
	FFW("Featherweight", GenderEnum.MALE, 136, 145),
	WFFW("Women's Featherweight", GenderEnum.FEMALE, 136, 145),
	LW("Lightweight", GenderEnum.MALE, 146, 155),
	WW("Welterweight", GenderEnum.MALE, 156, 170),
	MW("Middleweight", GenderEnum.MALE, 171, 185),
	LHW("Light Heavyweight", GenderEnum.MALE, 186, 205),
	HW("Heavyweight", GenderEnum.MALE, 206, 265),
	CW("Catch Weight", GenderEnum.MALE, 0, 0);

    private static final Map<String, WeightClassEnum> BY_DESCRIPTION = new HashMap<>();

    static {
        for (WeightClassEnum e : values()) {
        	BY_DESCRIPTION.put(e.description, e);
        }
    }
     
	public final String description;
	public final GenderEnum gender;
	public final Integer weightFloor;
	public final Integer weightCeiling;
    
	private WeightClassEnum(String description, GenderEnum gender, int weightFloor, int weightCeiling) {
		this.description = description;
		this.gender = gender;
		this.weightFloor = weightFloor;
		this.weightCeiling = weightCeiling;
	}

    public static WeightClassEnum valueOfDesc(String description) {
        return BY_DESCRIPTION.get(description);
    }
    
    public Integer getWeight() {
    	return this.weightCeiling;
    }
	
}
