package org.wbfd.repository;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wbfd.di.BeanFactory;
import org.wbfd.entity.Collocation;
import org.wbfd.enums.LEVEL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CollocationRepositoryTest 
{
	
	private CollocationRepository collocationRepository;
	private Collocation collocationCPETest;
	private Collocation collocationCAETest;
	
	@Before
	public void init() throws SQLException, ClassNotFoundException
	{
		collocationRepository = new BeanFactory().getCollocationRepository();
		
		collocationCPETest = new Collocation();
		collocationCPETest.setDate(new Date());
		collocationCPETest.setLevel(LEVEL.CPE);
		collocationCPETest.setHits(1);
		collocationCPETest.setKey("key");
		collocationCPETest.setSentence1("s1");
		collocationCPETest.setSentence2("s2");
		collocationCPETest.setSentence3("s3");
		
		collocationCAETest = new Collocation();
		collocationCAETest.setDate(new Date());
		collocationCAETest.setLevel(LEVEL.CAE);
		collocationCAETest.setHits(1);
		collocationCAETest.setKey("key");
		collocationCAETest.setPhrase("phrase");
		collocationCAETest.setExample("example");
	}
	
	@Test
	public void CollocationRepositoryCRUDCPETest()
	{
		try
		{
			// save
			collocationCPETest = collocationRepository.save(collocationCPETest);
			
			// restore
			Collocation coNew = collocationRepository.get(collocationCPETest);
			assertEquals(collocationCPETest.getCollocationId(), coNew.getCollocationId());
			assertEquals(collocationCPETest.getLevel(), coNew.getLevel());
			assertEquals(collocationCPETest.getHits(), coNew.getHits());
			assertEquals(collocationCPETest.getKey(), coNew.getKey());
			assertEquals(collocationCPETest.getSentence1(), coNew.getSentence1());
			assertEquals(collocationCPETest.getSentence2(), coNew.getSentence2());
			assertEquals(collocationCPETest.getSentence3(), coNew.getSentence3());
			
			// update
			collocationCPETest.setLevel(LEVEL.CAE);
			collocationCPETest.setHits(2);
			collocationCPETest.setSentence1("sentence1_MOD");
			collocationCPETest.setSentence2("sentence2_MOD");
			collocationCPETest.setSentence3("sentence3_MOD");
			collocationCPETest.setKey("key_MOD");
			
			collocationRepository.save(collocationCPETest);
			coNew = collocationRepository.get(collocationCPETest);
			
			assertEquals(LEVEL.CAE, coNew.getLevel());
			assertEquals(2, coNew.getHits().intValue());
			assertEquals("sentence1_MOD", coNew.getSentence1());
			assertEquals("sentence2_MOD", coNew.getSentence2());
			assertEquals("sentence3_MOD", coNew.getSentence3());
			assertEquals("key_MOD", coNew.getKey());
			
			// delete
			collocationRepository.delete(collocationCPETest);
			collocationCPETest = collocationRepository.get(collocationCPETest);
			assertNull(collocationCPETest);
		}
		catch(Exception exc)
		{
			System.out.println(exc.toString());
			assertFalse(true);
		}
	}
	
	@Test
	public void CollocationRepositoryCRUDCAETest()
	{
		try
		{
			// save
			collocationCAETest = collocationRepository.save(collocationCAETest);
			
			// restore
			Collocation coNew = collocationRepository.get(collocationCAETest);
			assertEquals(collocationCAETest.getCollocationId(), coNew.getCollocationId());
			assertEquals(collocationCAETest.getLevel(), coNew.getLevel());
			assertEquals(collocationCAETest.getHits(), coNew.getHits());
			assertEquals(collocationCAETest.getKey(), coNew.getKey());
			assertEquals(collocationCAETest.getPhrase(), coNew.getPhrase());
			assertEquals(collocationCAETest.getExample(), coNew.getExample());
			
			// update
			collocationCAETest.setLevel(LEVEL.CPE);
			collocationCAETest.setHits(2);
			collocationCAETest.setPhrase("phrase_MOD");
			collocationCAETest.setExample("example_MOD");
			collocationCAETest.setKey("key_MOD");
			
			collocationRepository.save(collocationCAETest);
			coNew = collocationRepository.get(collocationCAETest);
			assertEquals(LEVEL.CPE, coNew.getLevel());
			assertEquals(2, coNew.getHits().intValue());
			assertEquals("phrase_MOD", coNew.getPhrase());
			assertEquals("example_MOD", coNew.getExample());
			assertEquals("key_MOD", coNew.getKey());
			
			// delete
			collocationRepository.delete(collocationCAETest);
			collocationCAETest = collocationRepository.get(collocationCAETest);
			assertNull(collocationCAETest);
		}
		catch(Exception exc)
		{
			System.out.println(exc.toString());
			assertFalse(true);
		}
	}
	
	@Test
	public void CollocationListTest()
	{
		List<Collocation> collocationList;
		try
		{
			collocationCPETest = collocationRepository.save(collocationCPETest);
			
			collocationList = collocationRepository.getList(10, LEVEL.CPE);
			assertNotNull(collocationList);
			assertTrue(collocationList.size()>0);
			
			collocationRepository.delete(collocationCPETest);
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
