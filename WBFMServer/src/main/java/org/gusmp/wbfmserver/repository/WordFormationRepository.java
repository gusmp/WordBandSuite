package org.gusmp.wbfmserver.repository;

import java.util.Date;
import java.util.List;

import org.gusmp.wbfmserver.entity.WordFormation;
import org.gusmp.wbfmserver.enumeration.Level;
import org.springframework.data.repository.Repository;

public interface WordFormationRepository extends Repository<WordFormation, Integer> {
	
	public WordFormation save(WordFormation wordFormation);
	public WordFormation findById(Integer wordFormationId);
	public void delete(WordFormation wordFormation);

	
	public List<WordFormation> findByLevelAndWordRootAndSentence1AndSentence2AndSentence3(
			Level level,
			String wordRoot,
			String sentence1,
			String sentence2,
			String sentence3);
	
	public List<WordFormation> findAllByOrderByDateAsc();
	
	public List<WordFormation> findByDateGreaterThan(Date date);
	
}
