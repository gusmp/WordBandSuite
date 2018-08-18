package org.gusmp.wbfmserver.repository;

import java.util.Date;
import java.util.List;

import org.gusmp.wbfmserver.entity.Collocation;
import org.gusmp.wbfmserver.enumeration.Level;
import org.springframework.data.repository.Repository;

public interface CollocationRepository extends Repository<Collocation, Integer> {
	
	public Collocation save(Collocation collocation);
	public Collocation findById(Integer collocationId);
	public void delete(Collocation collocation);
	
	public List<Collocation> findByLevelAndSentence1AndSentence2AndSentence3AndPhraseAndExample(
			Level level,
			String sentence1,
			String sentence2,
			String sentence3,
			String phrase,
			String example);
	
	public List<Collocation> findAllByOrderByDateAsc();
	
	public List<Collocation> findByDateGreaterThan(Date date);
	
}
