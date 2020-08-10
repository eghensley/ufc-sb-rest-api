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

	@Query(value = "select fbx.* from ufc2.fighter_bout_xref fbx join ufc2.bout b on fbx.bout_oid = b.oid join ufc2.fight f on b.fight_oid = f.oid where fbx.fighter_oid =:fighterIdx and f.fight_date < (select f2.fight_date from ufc2.fight f2 where f2.oid =:fightIdx) order by f.fight_date desc limit 1", nativeQuery = true)
	Optional<List<FighterBoutXRefData>> getPrevFBX(@Param("fighterIdx") String fighterIdx, @Param("fightIdx") String fightIdx);

	@Query(value = "select fbx.* from ufc2.fighter_bout_xref fbx join ufc2.bout b on fbx.bout_oid = b.oid join ufc2.fight f on b.fight_oid = f.oid where fbx.fighter_oid =:fighterIdx and f.oid =:fightIdx", nativeQuery = true)
	Optional<List<FighterBoutXRefData>> getFightFBX(@Param("fighterIdx") String fighterIdx, @Param("fightIdx") String fightIdx);

	@Query(value = "select count(fbx.oid) \n" + 
			"	from ufc2.fighter_bout_xref fbx \n" + 
			"		join ufc2.bout b \n" + 
			"			on fbx.bout_oid = b.oid \n" + 
			"		join ufc2.fight f \n" + 
			"			on b.fight_oid = f.oid \n" + 
			"		where fbx.fighter_oid =:fighterIdx \n" + 
			"		and f.fight_date < \n" + 
			"			(select f2.fight_date \n" + 
			"			 	from ufc2.fight f2 \n" + 
			"			 		where f2.oid =:fightIdx" + 
			"			) ", nativeQuery = true)
	Integer getCountPreviousFights(@Param("fighterIdx") String fighterIdx, @Param("fightIdx") String fightIdx);

	@Query(value = "select count(fbx.oid) \n" + 
			"	from ufc2.fighter_bout_xref fbx \n" + 
			"		join ufc2.bout b \n" + 
			"			on fbx.bout_oid = b.oid \n" + 
			"		join ufc2.fight f \n" + 
			"			on b.fight_oid = f.oid \n" + 
			"		where fbx.fighter_oid =:fighterIdx", nativeQuery = true)
	Integer getCountFights(@Param("fighterIdx") String fighterIdx);
	
	FighterBoutXRefData findByOid(String oid);
}
