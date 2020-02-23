package com.hensley.ufc.repository.mock;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Generated;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hensley.ufc.domain.LocationData;
import com.hensley.ufc.repository.LocationRepository;

/** Mock for { @link LocationRepository } */
@Generated(value = "org.junit-tools-1.1.0")
public class LocationRepositoryMock implements LocationRepository {

	private Optional<LocationData> getLocationByCityNameAndCountryNameResult = null;
	private static final String TEST_CITY_NAME = "TEST CITY";
	private static final String TEST_COUNTRY_NAME = "TEST COUNTRY";
	
	public static LocationRepositoryMock create() {
		return new LocationRepositoryMock();
	}

	public void setGetLocationByCityNameAndCountryNameResult(
			Optional<LocationData> getLocationByCityNameAndCountryNameResult) {
		this.getLocationByCityNameAndCountryNameResult = getLocationByCityNameAndCountryNameResult;
	}

	@Override
	public
	Optional<LocationData> getLocationByCityNameAndCountryName(String cityName, String countryName) {
		if (TEST_CITY_NAME.equals(cityName) & TEST_COUNTRY_NAME.equals(countryName)) {
			return this.getLocationByCityNameAndCountryNameResult;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<LocationData> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocationData> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocationData> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends LocationData> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends LocationData> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<LocationData> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocationData getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends LocationData> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends LocationData> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<LocationData> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends LocationData> S save(S entity) {
		// TODO Auto-generated method stub
		entity.setVersion(0);
		entity.setCreateTs(new Date());
		entity.setOid("123");
		entity.setCreateUser("TEST USER");
		return null;
	}

	@Override
	public Optional<LocationData> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(LocationData entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends LocationData> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends LocationData> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends LocationData> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends LocationData> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends LocationData> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}
}