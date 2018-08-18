package org.gusmp.wbfmserver.service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gusmp.wbfmserver.entity.WordFormation;
import org.gusmp.wbfmserver.repository.WordFormationRepository;
import org.gusmp.wbfmserver.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WordFormationService {
	
	@Autowired
	private WordFormationRepository wordFormationRepository;
	
	public WordFormation save(WordFormation wordFormation) {
		
		List<WordFormation> wfList = wordFormationRepository.findByLevelAndWordRootAndSentence1AndSentence2AndSentence3(
				wordFormation.getLevel(),
				wordFormation.getWordRoot(),
				wordFormation.getSentence1(),
				wordFormation.getSentence2(),
				wordFormation.getSentence3());
				
		
		if (wfList.size() == 0) {
			wordFormation = wordFormationRepository.save(wordFormation);
		} else {
			log.info("Duplicate WF: " +  wordFormation.toString());
			log.info("Found: " + DateUtils.parseDate(wfList.get(0).getDate()));
			wordFormation = wfList.get(0);
		}
		
		
		return wordFormation;
	}
	
	public List<WordFormation> findAll(Date date) {
		
		if (date == null) {
			return wordFormationRepository.findAllByOrderByDateAsc();
		} else {
			return wordFormationRepository.findByDateGreaterThan(date);
		}
	} 
}
