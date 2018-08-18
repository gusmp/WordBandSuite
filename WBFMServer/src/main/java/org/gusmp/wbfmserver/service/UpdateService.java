package org.gusmp.wbfmserver.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.gusmp.wbfmserver.entity.Collocation;
import org.gusmp.wbfmserver.entity.PhrasalVerb;
import org.gusmp.wbfmserver.entity.WordFormation;
import org.gusmp.wbfmserver.enumeration.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UpdateService {
	
	@Autowired
	private PhrasalVerbService phrasalVerbService;
	
	@Autowired
	private WordFormationService wordFormationService;	
	
	@Autowired
	private CollocationService collocationService;
	
	@Value( "${updateFolder}" ) 
	private String updateFolder;
	
	private enum Mode { NONE, PHARSAL_VERB, WORD_FORMATION, COLLOCATION }
	
	private enum Step { NONE, 
		PV_CAE1, PV_CAE2, PV_CAE3, PV_CPE1, PV_CPE2, PV_CPE3,
		WF_CAE1, WF_CAE2, WF_CAE3, WF_CAE4, WF_CAE5, WF_CAE6, WF_CAE7, WF_CPE1, WF_CPE2, WF_CPE3, WF_CPE4, WF_CPE5, WF_CPE6, WF_CPE7,
		CO_CAE1, CO_CAE2, CO_CAE3, CO_CPE1, CO_CPE2, CO_CPE3, CO_CPE4
	}
	
	private static final String PHRASAL_VERB_HEADER = "PHRASAL VERBS";
	private static final String WORD_FORMATION_HEADER = "WORD FORMATION";
	private static final String COLLOCATION_HEADER = "COLLOCATION";
	
	public class StepHolder {
	    private Step value;
	    public StepHolder(Step initial) { value = initial; }
	    public void setValue(Step newValue) { value = newValue; }
	    public Step getValue() { return value; }
	}
	
	private FilenameFilter filter = new FilenameFilter() {
		
		public boolean accept(File dir, String name) {

			if (dir.isDirectory()) return true;
			
			if (name.endsWith("txt")) return true;
			else return false;
		}
	};
	
	@Transactional(readOnly=false)
	public String updateDabase() {

		try {
			processFolder(new File(updateFolder), filter);
		} catch (Exception exc) {
			throw new RuntimeException("Error updating: " + exc.toString());
		}
		
		return null;
	}
	
	private void processFolder(File currentFolder, FilenameFilter filter) {
		
		for(File f: currentFolder.listFiles(filter)) {
			
			if (f.isDirectory()) {
				processFolder(f, filter);
			} else {
				try {
					processFile(f);
				} catch(Exception exc) {
					log.error("Error processing file:" + f.getName());
					log.error("Details: " + exc.toString());
				}
			}
		}
	}
	
	private void processFile(File f) throws IOException {
		
		log.info("Processing file: " + f.getName());
		Mode mode = Mode.NONE;
		Step step = Step.NONE;
		
		PhrasalVerb currentPhrasalVerb = null;
		WordFormation currentWordFormation = null;
		Collocation currentCollocation = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
		    
		    String line = "";

		    while ((line = br.readLine()) != null) {
		    	
		    	line = line.trim();
		    	if (line.length() == 0 ) continue;
		    	if (line.startsWith("===")) continue;
		    	
		    	if (line.equalsIgnoreCase(UpdateService.PHRASAL_VERB_HEADER)) {
		    		mode = Mode.PHARSAL_VERB;
		    		continue;
		    	}
		    	
		    	if (line.equalsIgnoreCase(UpdateService.WORD_FORMATION_HEADER )) { 
		    		mode = Mode.WORD_FORMATION;
		    		continue;
		    	}
		    	
		    	if (line.equalsIgnoreCase(UpdateService.COLLOCATION_HEADER )) {
		    		mode = Mode.COLLOCATION;
		    		continue;
		    	}
		    	
		    	if (mode == Mode.PHARSAL_VERB) {
		    		
		    		StepHolder stepHolder = new StepHolder(step);
		    		currentPhrasalVerb = processPhrasalVerb(line, currentPhrasalVerb, step, stepHolder);
		    		step = stepHolder.getValue();
		    		continue;
		    	}
		    	else if (mode == Mode.WORD_FORMATION) {
		    		
		    		StepHolder stepHolder = new StepHolder(step);
		    		currentWordFormation = processWordFormation(line, currentWordFormation, step, stepHolder);
		    		step = stepHolder.getValue();
		    		continue;
		    	}
		    	else if (mode == Mode.COLLOCATION) {
		    		
		    		StepHolder stepHolder = new StepHolder(step);
		    		currentCollocation = processCollocation(line, currentCollocation, step, stepHolder);
		    		step = stepHolder.getValue();
		    		continue;
		    		
		    	}
		    }
		}
	}
	
	private Collocation processCollocation(String line, Collocation currentCollocation, Step step, StepHolder stepHolder) {
		
		Date d = containsDate(line);
		if (d != null) {
			currentCollocation = new Collocation();
			currentCollocation.setDate(d);
			currentCollocation.setLevel(Level.CPE);
			currentCollocation.setHits(0);
			step = Step.CO_CPE1;
			stepHolder.setValue(step);
			return currentCollocation;
		}
		
		line = sanitize(line);
		
		switch(step) {
			case CO_CPE1:
				currentCollocation.setSentence1(line);
				step = Step.CO_CPE2;
				stepHolder.setValue(step);
				return currentCollocation;
			case CO_CPE2:
				currentCollocation.setSentence2(line);
				step = Step.CO_CPE3;
				stepHolder.setValue(step);
				return currentCollocation;
			case CO_CPE3:
				currentCollocation.setSentence3(line);
				step = Step.CO_CPE4;
				stepHolder.setValue(step);
				return currentCollocation;
			case CO_CPE4:
				currentCollocation.setKey(line);
				collocationService.save(currentCollocation);
				step = Step.CO_CAE1;
				stepHolder.setValue(step);
				return currentCollocation;
			case CO_CAE1:
				currentCollocation.setCollocationId(null);
				currentCollocation.setSentence1(null);
				currentCollocation.setSentence2(null);
				currentCollocation.setSentence3(null);
				currentCollocation.setLevel(Level.CAE);
				currentCollocation.setPhrase(line);
				step = Step.CO_CAE2;
				stepHolder.setValue(step);
				return currentCollocation;
			case CO_CAE2:
				currentCollocation.setExample(line);
				step = Step.CO_CAE3;
				stepHolder.setValue(step);
				return currentCollocation;
			case CO_CAE3:
				currentCollocation.setKey(line);
				collocationService.save(currentCollocation);
				step = Step.NONE;
				stepHolder.setValue(step);
				return currentCollocation;
			default:
				return null;
		}
	}
	
	private WordFormation processWordFormation(String line, WordFormation currentWordFormation, Step step, StepHolder stepHolder) {
		
		Date d = containsDate(line);
		if (d != null) {
			currentWordFormation = new WordFormation();
			currentWordFormation.setDate(d);
			currentWordFormation.setLevel(Level.CPE);
			currentWordFormation.setHits(0);
			step = Step.WF_CPE1;
			stepHolder.setValue(step);
			return currentWordFormation;
		}
		
		line = sanitize(line);
		
		switch(step) {
			case WF_CPE1:
				currentWordFormation.setWordRoot(line);
				step = Step.WF_CPE2;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CPE2:
				currentWordFormation.setSentence1(line);
				step = Step.WF_CPE3;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CPE3:
				currentWordFormation.setSentence2(line);
				step = Step.WF_CPE4;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CPE4:
				currentWordFormation.setSentence3(line);
				step = Step.WF_CPE5;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CPE5:
				currentWordFormation.setSentenceKey1(line);
				step = Step.WF_CPE6;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CPE6:
				currentWordFormation.setSentenceKey2(line);
				step = Step.WF_CPE7;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CPE7:
				currentWordFormation.setSentenceKey3(line);
				wordFormationService.save(currentWordFormation);
				step = Step.WF_CAE1;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CAE1:
				currentWordFormation.setWordFormationId(null);
				currentWordFormation.setLevel(Level.CAE);
				currentWordFormation.setWordRoot(line);
				step = Step.WF_CAE2;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CAE2:
				currentWordFormation.setSentence1(line);
				step = Step.WF_CAE3;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CAE3:
				currentWordFormation.setSentence2(line);
				step = Step.WF_CAE4;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CAE4:
				currentWordFormation.setSentence3(line);
				step = Step.WF_CAE5;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CAE5:
				currentWordFormation.setSentenceKey1(line);
				step = Step.WF_CAE6;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CAE6:
				currentWordFormation.setSentenceKey2(line);
				step = Step.WF_CAE7;
				stepHolder.setValue(step);
				return currentWordFormation;
			case WF_CAE7:
				currentWordFormation.setSentenceKey3(line);
				wordFormationService.save(currentWordFormation);
				step = Step.NONE;
				stepHolder.setValue(step);
				return currentWordFormation;
			default:
				return null;
		}
	}
	
	private PhrasalVerb processPhrasalVerb(String line, PhrasalVerb currentPhrasalVerb, Step step, StepHolder stepHolder) {
		
		Date d = containsDate(line);
		if (d != null) {
			currentPhrasalVerb = new PhrasalVerb();
			currentPhrasalVerb.setDate(d);
			currentPhrasalVerb.setLevel(Level.CPE);
			currentPhrasalVerb.setHits(0);
			step = Step.PV_CPE1;
			stepHolder.setValue(step);
			return currentPhrasalVerb;
		}
		
		line = sanitize(line);
		
		switch(step) {
			case PV_CPE1:
				currentPhrasalVerb.setMeaning(line);
				step = Step.PV_CPE2;
				stepHolder.setValue(step);
				return currentPhrasalVerb;
			case PV_CPE2:
				currentPhrasalVerb.setExample(line);
				step = Step.PV_CPE3;
				stepHolder.setValue(step);
				return currentPhrasalVerb;
			case PV_CPE3:
				currentPhrasalVerb.setKey(line);
				step = Step.PV_CAE1;
				stepHolder.setValue(step);
				phrasalVerbService.save(currentPhrasalVerb);
				return currentPhrasalVerb;
	    	case PV_CAE1:
	    		currentPhrasalVerb.setPhrasalVerbId(null);
	    		currentPhrasalVerb.setLevel(Level.CAE);
				currentPhrasalVerb.setMeaning(line);
				step = Step.PV_CAE2;
				stepHolder.setValue(step);
				return currentPhrasalVerb;
	    	case PV_CAE2:
	    		currentPhrasalVerb.setExample(line);
				step = Step.PV_CAE3;
				stepHolder.setValue(step);
				return currentPhrasalVerb;
	    	case PV_CAE3:
	    		currentPhrasalVerb.setKey(line);
	    		phrasalVerbService.save(currentPhrasalVerb);
	    		step = Step.NONE;
	    		stepHolder.setValue(step);
				return currentPhrasalVerb;
			default:
				return null;
		}
	}
	
	private String sanitize(String line) {
		line = line.replaceAll("\"", "");
		line = line.replaceAll(";", "");
		
		
		return line;
		
	}
	
	private Date containsDate(String line) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			return sdf.parse(line);
		} catch(Exception exc) {
			return null;
		}
	}
}
