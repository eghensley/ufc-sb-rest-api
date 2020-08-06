package com.hensley.ufc.enums;

import java.util.HashMap;
import java.util.Map;

public enum WeightClassEnum {
	WSW("Women's Strawweight", GenderEnum.FEMALE, 106, 115, 0),
	FW("Flyweight", GenderEnum.MALE, 116, 125, 1),
	WFW("Women's Flyweight", GenderEnum.FEMALE, 116, 125, 2),
	BW("Bantamweight", GenderEnum.MALE, 126, 135, 3),
	WBW("Women's Bantamweight", GenderEnum.FEMALE, 126, 135, 4),
	FFW("Featherweight", GenderEnum.MALE, 136, 145, 5),
	WFFW("Women's Featherweight", GenderEnum.FEMALE, 136, 145, 6),
	LW("Lightweight", GenderEnum.MALE, 146, 155, 7),
	WW("Welterweight", GenderEnum.MALE, 156, 170, 8),
	MW("Middleweight", GenderEnum.MALE, 171, 185, 9),
	LHW("Light Heavyweight", GenderEnum.MALE, 186, 205, 10),
	HW("Heavyweight", GenderEnum.MALE, 206, 265, 11),
	CW("Catch Weight", GenderEnum.MALE, 0, 0, 12);

    private static final Map<String, WeightClassEnum> BY_DESCRIPTION = new HashMap<>();
    private static final Map<Integer, WeightClassEnum> BY_DB_KEY = new HashMap<>();

    static {
        for (WeightClassEnum e : values()) {
        	BY_DESCRIPTION.put(e.description, e);
        	BY_DB_KEY.put(e.dbKey, e);
        }
    }
     
	public final String description;
	public final GenderEnum gender;
	public final Integer weightFloor;
	public final Integer weightCeiling;
	public final Integer dbKey;
    
	private WeightClassEnum(String description, GenderEnum gender, int weightFloor, int weightCeiling, int dbKey) {
		this.description = description;
		this.gender = gender;
		this.weightFloor = weightFloor;
		this.weightCeiling = weightCeiling;
		this.dbKey = dbKey;
	}

    public static WeightClassEnum valueOfDesc(String description) {
        return BY_DESCRIPTION.get(description);
    }
    
    public static WeightClassEnum valueOfDbKey(Integer dbKey) {
        return BY_DB_KEY.get(dbKey);
    }
    
    public Integer getWeight() {
    	return this.weightCeiling;
    }
    
    public Integer getDbKey() {
    	return this.dbKey;
    }
	
}
