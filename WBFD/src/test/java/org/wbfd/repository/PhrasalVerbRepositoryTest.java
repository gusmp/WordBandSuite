package org.wbfd.repository;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wbfd.di.BeanFactory;
import org.wbfd.entity.PhrasalVerb;
import org.wbfd.enums.LEVEL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PhrasalVerbRepositoryTest 
{
	
	private PhrasalVerbRepository phrasalVerbRepository;
	private PhrasalVerb phrasalVerbTest;
	
	@Before
	public void init() throws SQLException, ClassNotFoundException
	{
		phrasalVerbRepository = new BeanFactory().getPhrasalVerbRepository();
		phrasalVerbTest = new PhrasalVerb();
		phrasalVerbTest.setDate(new Date());
		phrasalVerbTest.setLevel(LEVEL.CAE);
		phrasalVerbTest.setMeaning("meaningTest");
		phrasalVerbTest.setExample("exampleTest");
		phrasalVerbTest.setKey("keyTest");
		phrasalVerbTest.setHits(1);
	}
	
	@Test
	public void PhrasalVerbCRUDTest()
	{
		try
		{
			// save
			phrasalVerbTest = phrasalVerbRepository.save(phrasalVerbTest);
			
			// restore
			PhrasalVerb pvNew = phrasalVerbRepository.get(phrasalVerbTest);
			assertEquals(phrasalVerbTest.getPhrasalVerbId(), pvNew.getPhrasalVerbId());
			assertEquals(phrasalVerbTest.getLevel(), pvNew.getLevel());
			assertEquals(phrasalVerbTest.getMeaning(), pvNew.getMeaning());
			assertEquals(phrasalVerbTest.getExample(), pvNew.getExample());
			assertEquals(phrasalVerbTest.getKey(), pvNew.getKey());
			assertEquals(phrasalVerbTest.getHits(), pvNew.getHits());
			
			// update
			phrasalVerbTest.setLevel(LEVEL.CPE);
			phrasalVerbTest.setMeaning("meaningTest_MOD");
			phrasalVerbTest.setExample("exampleTest_MOD");
			phrasalVerbTest.setKey("keyTest_MOD");
			phrasalVerbTest.setHits(2);
			
			phrasalVerbRepository.save(phrasalVerbTest);
			pvNew = phrasalVerbRepository.get(phrasalVerbTest);
			assertEquals(LEVEL.CPE, pvNew.getLevel());
			assertEquals("meaningTest_MOD", pvNew.getMeaning());
			assertEquals("exampleTest_MOD", pvNew.getExample());
			assertEquals("keyTest_MOD", pvNew.getKey());
			assertEquals(2, pvNew.getHits().intValue());
			
			// delete
			phrasalVerbRepository.delete(phrasalVerbTest);
			phrasalVerbTest = phrasalVerbRepository.get(phrasalVerbTest);
			assertNull(phrasalVerbTest);
		}
		catch(Exception exc)
		{
			System.out.println(exc.toString());
			assertFalse(true);
		}
	}
	
	@Test
	public void PhrasalVerbListTest()
	{
		List<PhrasalVerb> phrasalVerbList;
		try
		{
			phrasalVerbTest = phrasalVerbRepository.save(phrasalVerbTest);
			
			phrasalVerbList = phrasalVerbRepository.getList(10, LEVEL.CAE);
			assertNotNull(phrasalVerbList);
			assertTrue(phrasalVerbList.size()>0);
			
			phrasalVerbRepository.delete(phrasalVerbTest);
		}
		catch(Exception exc)
		{
			System.out.println(exc.toString());
			assertFalse(true);
		}
	}
	
	@After
	public void destroy()
	{
		
	}

}
