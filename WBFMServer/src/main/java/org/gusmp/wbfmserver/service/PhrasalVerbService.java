package org.gusmp.wbfmserver.service;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gusmp.wbfmserver.entity.PhrasalVerb;
import org.gusmp.wbfmserver.repository.PhrasalVerbRepository;
import org.gusmp.wbfmserver.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PhrasalVerbService {
	
	@Autowired
	private PhrasalVerbRepository phrasalVerbRepository;
	
	public PhrasalVerb save(PhrasalVerb phrasalVerb) {
		
		List<PhrasalVerb> pvList = phrasalVerbRepository.findByLevelAndMeaningAndExample(phrasalVerb.getLevel(), 
				phrasalVerb.getMeaning(),
				phrasalVerb.getExample());
		
		if (pvList.size() == 0) {
			phrasalVerb = phrasalVerbRepository.save(phrasalVerb);
		} else {
			log.info("Duplicate PV: " +  phrasalVerb.toString());
			log.info("Found: " + DateUtils.parseDate(pvList.get(0).getDate()));
			phrasalVerb = pvList.get(0);
		}
		
		
		return phrasalVerb;
	}
	
	public List<PhrasalVerb> findAll(Date date) {
		
		if (date == null) {
			return phrasalVerbRepository.findAllByOrderByDateAsc();
		} else {
			return phrasalVerbRepository.findByDateGreaterThan(date);
		}
	} 
}
