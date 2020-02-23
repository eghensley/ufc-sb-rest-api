package com.hensley.ufc.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hensley.ufc.domain.CountryData;
import com.hensley.ufc.domain.LocationData;
import com.hensley.ufc.repository.CountryRepository;
import com.hensley.ufc.repository.LocationRepository;

@Service
public class LocationService {

	private final LocationRepository locationRepo;
	private final CountryRepository countryRepo;

	public LocationService(LocationRepository locationRepo, CountryRepository countryRepo) {
		this.locationRepo = locationRepo;
		this.countryRepo = countryRepo;
	}

	@Transactional
	public LocationData matchLocationToModel(String[] locArray) {
		String fightCountry;
		String fightState;
		String fightCity;

		if (locArray.length == 2) {
			fightCountry = locArray[1].trim();
			fightState = locArray[1].trim();
			fightCity = locArray[0].trim();
		} else {
			fightCountry = locArray[2].trim();
			fightState = locArray[1].trim();
			fightCity = locArray[0].trim();
		}

		Optional<LocationData> locationDao = locationRepo.getLocationByCityNameAndCountryName(fightCity, fightCountry);
		if (locationDao.isPresent()) {
			return locationDao.get();
		} else {
			return createMissingLocation(fightState, fightCity, fightCountry);
		}
	}

	public LocationData createMissingLocation(String stateName, String cityName, String countryName) {
		LocationData locationDao = new LocationData();
		locationDao.setCityName(cityName);
		locationDao.setStateName(stateName);

		CountryData countryDao = createCountryIfMissing(countryName);
		locationDao.setCountry(countryDao);
		locationRepo.save(locationDao);
		return locationDao;
	}

	public CountryData createCountryIfMissing(String countryName) {
		Optional<CountryData> countryDaoOpt = countryRepo.findByCountryName(countryName);
		if (!countryDaoOpt.isPresent()) {
			CountryData countryDao = new CountryData();
			countryDao.setCountryName(countryName);
			countryRepo.save(countryDao);
			return countryDao;
		} else {
			return countryDaoOpt.get();
		}
	}
}
