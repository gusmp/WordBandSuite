package org.gusmp.wbfmserver.service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gusmp.wbfmserver.entity.Collocation;
import org.gusmp.wbfmserver.repository.CollocationRepository;
import org.gusmp.wbfmserver.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollocationService {
	
	@Autowired
	private CollocationRepository collocationRepository;
	
	public Collocation save(Collocation collocation) {
		
		List<Collocation> coList = collocationRepository.findByLevelAndSentence1AndSentence2AndSentence3AndPhraseAndExample(
				collocation.getLevel(),
				collocation.getSentence1(),
				collocation.getSentence2(),
				collocation.getSentence3(),
				collocation.getPhrase(),
				collocation.getExample());
		
		if (coList.size() == 0) {
			collocation = collocationRepository.save(collocation);
		} else {
			log.info("Duplicate WF: " +  collocation.toString());
			log.info("Found: " + DateUtils.parseDate(coList.get(0).getDate()));
			collocation = coList.get(0);
		}
		
		
		return collocation;
	}
	
	public List<Collocation> findAll(Date date) {
		
		if (date == null) {
			return collocationRepository.findByDateGreaterThan(date);
		} else {
			return collocationRepository.findAllByOrderByDateAsc();
		}
	} 
}
