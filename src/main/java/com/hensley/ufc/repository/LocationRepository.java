package com.hensley.ufc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.LocationData;

@Repository
public interface LocationRepository extends JpaRepository<LocationData, String> {

	@Query(value = "SELECT l.* from LOCATION l JOIN COUNTRY c ON c.OID = l.COUNTRY_OID WHERE l.CITY_NAME=:cityName AND c.COUNTRY_NAME=:countryName", nativeQuery = true)
	Optional<LocationData> getLocationByCityNameAndCountryName(@Param("cityName") String cityName, @Param("countryName") String countryName);
}
