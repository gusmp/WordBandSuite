package org.gusmp.wbfmserver.repository;

import java.util.Date;
import java.util.List;

import org.gusmp.wbfmserver.entity.PhrasalVerb;
import org.gusmp.wbfmserver.enumeration.Level;
import org.springframework.data.repository.Repository;

public interface PhrasalVerbRepository extends Repository<PhrasalVerb, Integer> {
	
	public PhrasalVerb save(PhrasalVerb phrasalVerb);
	public PhrasalVerb findById(Integer phrasalVerbId);
	public void delete(PhrasalVerb phrasalVerb);
	
	public List<PhrasalVerb> findByLevelAndMeaningAndExample(
			Level level,
			String meaning,
			String example);
	
	public List<PhrasalVerb> findAllByOrderByDateAsc();
	
	public List<PhrasalVerb> findByDateGreaterThan(Date date);
	
}
