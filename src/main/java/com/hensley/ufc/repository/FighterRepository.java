package com.hensley.ufc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.FighterData;
import com.hensley.ufc.pojo.dto.fighter.FighterOidAndNameDto;

@Repository
public interface FighterRepository extends JpaRepository<FighterData, String> {

	Optional<FighterData> findByFighterId(String fighterId);

//	Optional<FighterData> findByFighterName(String fighterName);

	@Query(value = "select f.* from ufc2.fighter f where similarity(f.fighter_name, :fighterName) > .65 order by similarity(f.fighter_name, :fighterName) desc limit 1", nativeQuery = true)
	Optional<FighterData> findByFuzzyFighterName(@Param("fighterName") String fighterName);

	@Query(value = "select f.fighter_id from ufc2.fighter f where similarity(f.fighter_name, :fighterName) > .65 order by similarity(f.fighter_name, :fighterName) desc limit 1", nativeQuery = true)
	String findFighterIdByFuzzyFighterName(@Param("fighterName") String fighterName);

	@Query(value = "select f.* from ufc2.fighter f", nativeQuery = true)
	List<FighterData> getFighterList();
}
