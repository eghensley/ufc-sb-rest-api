package com.hensley.ufc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.FighterData;

@Repository
public interface FighterRepository extends JpaRepository<FighterData, String> {

	Optional<FighterData> findByFighterId(String fighterId);
	
	Optional<FighterData> findByFighterName(String fighterName);
}
