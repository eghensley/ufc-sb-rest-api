package com.hensley.ufc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.CountryData;

@Repository
public interface CountryRepository extends JpaRepository<CountryData, String> {
	Optional<CountryData> findByCountryName(String countryName);

}
