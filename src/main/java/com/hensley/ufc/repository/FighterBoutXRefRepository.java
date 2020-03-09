package com.hensley.ufc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.FighterBoutXRefData;

@Repository
public interface FighterBoutXRefRepository extends JpaRepository<FighterBoutXRefData, String> {

	@Query(value = "select * from ufc2.fighter_bout_xref fbx JOIN ufc2.FIGHTER f ON f.OID = fbx.FIGHTER_OID JOIN ufc2.BOUT b ON b.OID = fbx.BOUT_OID WHERE f.fighter_id=:fighterId AND b.BOUT_ID=:boutId", nativeQuery = true)
	Optional<FighterBoutXRefData> getFighterXRefByFighterIdAndBoutId(@Param("fighterId") String fighterId, @Param("boutId") String boutId);

	@Query(value = "select * from ufc2.fighter_bout_xref fbx JOIN ufc2.FIGHTER f ON f.OID = fbx.FIGHTER_OID JOIN ufc2.BOUT b ON b.OID = fbx.BOUT_OID WHERE b.BOUT_ID=:boutId", nativeQuery = true)
	Optional<List<FighterBoutXRefData>> getFighterXRefByBoutId(@Param("boutId") String boutId);

	@Query(value = "select fbx.* from ufc2.fighter_bout_xref fbx join ufc2.bout b on b.oid = fbx.bout_oid join ufc2.fight fi on fi.oid = b.fight_oid where fbx.fighter_oid =:fighterOid and fi.oid =:fightOid", nativeQuery = true)
	Optional<List<FighterBoutXRefData>> getFighterXRefByFighterOidFightOid(@Param("fighterOid") String fighterOid, @Param("fightOid") String fightOid);

	@Query(value = "select fbx.* from ufc2.fighter_bout_xref fbx join ufc2.bout b on b.oid = fbx.bout_oid join ufc2.fight fi on fi.oid = b.fight_oid where fi.oid =:fightOid", nativeQuery = true)
	Optional<List<FighterBoutXRefData>> getFighterXRefByFightOid(@Param("fightOid") String fightOid);

}
