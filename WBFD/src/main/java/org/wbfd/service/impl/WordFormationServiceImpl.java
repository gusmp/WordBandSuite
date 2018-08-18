package org.wbfd.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.wbfd.entity.WordFormation;
import org.wbfd.enums.LEVEL;
import org.wbfd.enums.MAX_RESULTS;
import org.wbfd.repository.WordFormationRepository;
import org.wbfd.service.WordFormationService;

public class WordFormationServiceImpl implements WordFormationService
{
    private WordFormationRepository wordFormationRepository;
    private Random rnd;
    private int previousIndex = -1;

    public WordFormationServiceImpl(WordFormationRepository wordFormationRepository)
    {
	this.wordFormationRepository = wordFormationRepository;
	this.rnd = new Random(System.currentTimeMillis());
    }

    public WordFormation getNextWordFormation(LEVEL level) throws SQLException
    {
	List<WordFormation> wordFormationList = wordFormationRepository.getList(MAX_RESULTS.MAX_RESULTS_WF.getValue(), level);

	int index;
	do
	{
	    index = rnd.nextInt(wordFormationList.size());
	}
	while (index == previousIndex);
	previousIndex = index;

	return wordFormationList.get(index);
    }

    public WordFormation save(WordFormation wordFormation) throws SQLException
    {
	return (wordFormationRepository.save(wordFormation));
    }

    public void clearHits() throws SQLException
    {
	wordFormationRepository.clearHits();
    }
}
