package com.hensley.ufc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.FightData;

@Repository
public interface FightRepository extends JpaRepository<FightData, String> {
	Optional<FightData> findByFightId(String fightId);

	@Query(value = "select f.fight_id from ufc2.fight f", nativeQuery = true)
	List<String> findFightIds();
	
	@Query(value = "select f.oid from ufc2.fight f where f.mma_dec_fight_url is not null", nativeQuery = true)
	List<String> findFightIdsWithScore();

	@Query(value = "select * from ufc2.fight f where f.mma_dec_fight_url is null", nativeQuery = true)
	List<FightData> findFightIdsWithoutScore();
	
	@Query(value = "select * from ufc2.fight f where f.fight_name LIKE :fightName", nativeQuery = true)
	Optional<List<FightData>> findFightByFuzzyName(@Param("fightName") String fightName);
}
