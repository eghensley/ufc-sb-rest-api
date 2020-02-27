package com.hensley.ufc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.BoutData;

@Repository
public interface BoutRepository extends JpaRepository<BoutData, String> {
	Optional<BoutData> findByBoutId(String boutId);

	Optional<BoutData> findByOid(String boutOid);
	
	@Query(value = "select b.bout_id from ufc2.fight f join ufc2.bout b on b.fight_oid = f.oid where f.fight_id=:fightId", nativeQuery = true)
	List<String> findBoutIdByFightId(@Param("fightId") String fightId);
	
	@Query(value = "select b.* from ufc2.bout b join ufc2.fighter_bout_xref fbx on fbx.bout_oid = b.oid where b.fight_oid=:fightId and fbx.fighter_oid=:fighterId", nativeQuery = true)
	Optional<List<BoutData>> findBoutIdByFightIdAndFighterId(@Param("fightId") String fightId, @Param("fighterId") String fighterId);
	
	@Query(value = "select distinct(b.oid) from ufc2.bout b join ufc2.fighter_bout_xref fbx on fbx.bout_oid = b.oid join ufc2.strike_data s on s.fighter_bout_oid = fbx.oid where b.mma_dec_bout_url is not null and s.score is null", nativeQuery = true)
	List<String> findBoutsMissingScores();
}
