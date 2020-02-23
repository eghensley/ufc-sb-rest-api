package com.hensley.ufc.repository.mock;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Generated;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hensley.ufc.domain.CountryData;
import com.hensley.ufc.repository.CountryRepository;

/** Mock for { @link CountryRepository } */
@Generated(value = "org.junit-tools-1.1.0")
public class CountryRepositoryMock implements CountryRepository {

	private Optional<CountryData> findByCountryNameResult = null;
	private static final String TEST_COUNTRY_NAME = "TEST COUNTRY";
	
	public static CountryRepositoryMock create() {
		return new CountryRepositoryMock();
	}

	public void setFindByCountryNameResult(Optional<CountryData> findByCountryNameResult) {
		CountryData country = new CountryData();
		country.setCountryName(TEST_COUNTRY_NAME);
		
		this.findByCountryNameResult = Optional.of(country);
	}

	@Override
	public Optional<CountryData> findByCountryName(String countryName) {
		if (TEST_COUNTRY_NAME.equals(countryName)) {
			return this.findByCountryNameResult;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CountryData> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CountryData> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CountryData> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CountryData> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends CountryData> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<CountryData> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public CountryData getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CountryData> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CountryData> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CountryData> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CountryData> S save(S entity) {
		// TODO Auto-generated method stub
		entity.setVersion(0);
		entity.setCreateTs(new Date());
		entity.setOid("123");
		entity.setCreateUser("TEST USER");
		return null;
	}

	@Override
	public Optional<CountryData> findById(String id) {
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
	public void delete(CountryData entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends CountryData> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends CountryData> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CountryData> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CountryData> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends CountryData> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

}