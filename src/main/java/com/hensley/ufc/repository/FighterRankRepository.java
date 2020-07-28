package com.hensley.ufc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.FighterRankData;
import com.hensley.ufc.enums.WeightClassEnum;

@Repository
public interface FighterRankRepository extends JpaRepository<FighterRankData, String>  {

	List<FighterRankData> findByWeightClass(WeightClassEnum weightClass);
	
	Optional<FighterRankData> findByWeightClassAndFighterOid(WeightClassEnum weightClass, String fighterOid);
//	@Query(value = "select * from ufc2.fighter_rank fr where fr.weight_class =:weightClass and fr.fighter_oid =:fighter_idx", nativeQuery = true )
//	Optional<FighterRankData> findByFighterOidAndWeightClass(@Param("fighterOid") String fighterOid, @Param("weightClass") Integer weightClass);

	
}
