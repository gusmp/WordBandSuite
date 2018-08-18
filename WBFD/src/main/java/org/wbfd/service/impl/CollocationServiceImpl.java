package org.wbfd.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.wbfd.entity.Collocation;
import org.wbfd.enums.LEVEL;
import org.wbfd.enums.MAX_RESULTS;
import org.wbfd.repository.CollocationRepository;
import org.wbfd.service.CollocationService;

public class CollocationServiceImpl implements CollocationService
{
    private CollocationRepository collocationRepository;
    private Random rnd;
    private int previousIndex = -1;

    public CollocationServiceImpl(CollocationRepository collocationRepository)
    {
	this.collocationRepository = collocationRepository;
	this.rnd = new Random(System.currentTimeMillis());
    }

    public Collocation getNextCollocation(LEVEL level) throws SQLException
    {
	List<Collocation> collocationList = collocationRepository.getList(MAX_RESULTS.MAX_RESULTS_CO.getValue(), level);

	int index;
	do
	{
	    index = rnd.nextInt(collocationList.size());
	}
	while (index == previousIndex);
	previousIndex = index;

	return collocationList.get(index);
    }

    public Collocation save(Collocation collocation) throws SQLException
    {
	return (collocationRepository.save(collocation));
    }

    public void clearHits() throws SQLException
    {
	collocationRepository.clearHits();
    }
}
