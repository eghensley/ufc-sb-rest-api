package com.hensley.ufc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hensley.ufc.domain.FightData;

@Repository
public interface AdminRepository extends JpaRepository<FightData, String> {

	@Query(value = "select count(oid) from ufc2.fighter where height is null", nativeQuery = true)
	Integer findCountFightersWithNullHeight();
	
	@Query(value = "select count(oid) from ufc2.fighter where reach is null", nativeQuery = true)
	Integer findCountFightersWithNullReach();
	
	@Query(value = "select count(oid) from ufc2.fighter where dob is null", nativeQuery = true)
	Integer findCountFightersWithNullDob();
	
	@Query(value = "select f1.fight_name, count(b1.oid) \n" + 
			"	from ufc2.bout b1\n" + 
			"		join ufc2.fight f1\n" + 
			"			on f1.oid = b1.fight_oid\n" + 
			"	where b1.oid in \n" + 
			"		(select fbx.bout_oid \n" + 
			"		 	from ufc2.fighter_bout_xref fbx \n" + 
			"		 	join ufc2.bout b\n" + 
			"		 		on b.oid = fbx.bout_oid\n" + 
			"		 	full join ufc2.strike_data sd \n" + 
			"		 		on fbx.oid = sd.fighter_bout_oid\n" + 
			"		 where \n" + 
			"		 	b.fight_oid != (select oid from ufc2.fight f order by f.fight_date desc limit 1)\n" + 
			"		 	and sd.oid is null)\n" + 
			"	group by f1.fight_name", nativeQuery = true)
	Object findFightsMissingBoutData();
}
