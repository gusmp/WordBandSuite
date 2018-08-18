package org.wbfd.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.wbfd.entity.PhrasalVerb;
import org.wbfd.enums.LEVEL;
import org.wbfd.enums.MAX_RESULTS;
import org.wbfd.repository.PhrasalVerbRepository;
import org.wbfd.service.PhrasalVerbService;

public class PhrasalVerbServiceImpl implements PhrasalVerbService
{
    private PhrasalVerbRepository phrasalVerbRepository;
    private Random rnd;
    private int previousIndex = -1;

    public PhrasalVerbServiceImpl(PhrasalVerbRepository phrasalVerbRepository)
    {
	this.phrasalVerbRepository = phrasalVerbRepository;
	this.rnd = new Random(System.currentTimeMillis());
    }

    public PhrasalVerb getNextPhrasalVerb(LEVEL level) throws SQLException
    {
	List<PhrasalVerb> phrasalVerbList = phrasalVerbRepository.getList(MAX_RESULTS.MAX_RESULTS_PV.getValue(), level);

	int index;
	do
	{
	    index = rnd.nextInt(phrasalVerbList.size());
	}
	while (index == previousIndex);
	previousIndex = index;

	return phrasalVerbList.get(index);
    }

    public PhrasalVerb save(PhrasalVerb phrasalVerb) throws SQLException
    {
	return (phrasalVerbRepository.save(phrasalVerb));
    }

    public void clearHits() throws SQLException
    {
	phrasalVerbRepository.clearHits();
    }
}
