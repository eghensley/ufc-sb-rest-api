package com.hensley.ufc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.StrikeData;

@Repository
public interface StrikeDataRepository extends JpaRepository<StrikeData, String> {
	@Query(value = "select sd.* from ufc2.strike_data sd join ufc2.fighter_bout_xref fbx on fbx.oid = sd.fighter_bout_oid\n" + 
			"join ufc2.bout b on b.oid = fbx.bout_oid\n " + 
			"where fbx.fighter_oid=:fighterOid and b.fight_oid=:fightOid\n" + 
			"and sd.round =:round", nativeQuery = true)
	Optional<List<StrikeData>> getStrikeDataByFighterAndFightAndRound(@Param("fighterOid") String fighterOid, @Param("fightOid") String fightOid, @Param("round") Integer round);

}
