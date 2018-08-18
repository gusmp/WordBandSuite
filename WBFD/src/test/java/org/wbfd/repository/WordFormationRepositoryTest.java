package org.wbfd.repository;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wbfd.di.BeanFactory;
import org.wbfd.entity.WordFormation;
import org.wbfd.enums.LEVEL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WordFormationRepositoryTest 
{
	
	private WordFormationRepository wordFormationRepository;
	private WordFormation wordFormationTest;
	
	@Before
	public void init() throws SQLException, ClassNotFoundException
	{
		wordFormationRepository = new BeanFactory().getWordFormationRepository();
		wordFormationTest = new WordFormation();
		wordFormationTest.setDate(new Date());
		wordFormationTest.setLevel(LEVEL.CAE);
		wordFormationTest.setWordRoot("root");
		wordFormationTest.setSentence1("s1");
		wordFormationTest.setSentenceKey1("sk1");
		wordFormationTest.setSentence2("s2");
		wordFormationTest.setSentenceKey2("sk2");
		wordFormationTest.setSentence3("s3");
		wordFormationTest.setSentenceKey3("sk3");
		wordFormationTest.setHits(1);
	}
	
	@Test
	public void WordFormationRepositoryCRUDTest()
	{
		try
		{
			// save
			wordFormationTest = wordFormationRepository.save(wordFormationTest);
			
			// restore
			WordFormation wfNew = wordFormationRepository.get(wordFormationTest);
			assertEquals(wordFormationTest.getWordFormationId(), wfNew.getWordFormationId());
			assertEquals(wordFormationTest.getLevel(), wfNew.getLevel());
			assertEquals(wordFormationTest.getWordRoot(), wfNew.getWordRoot());
			assertEquals(wordFormationTest.getSentence1(), wfNew.getSentence1());
			assertEquals(wordFormationTest.getSentenceKey1(), wfNew.getSentenceKey1());
			assertEquals(wordFormationTest.getSentence2(), wfNew.getSentence2());
			assertEquals(wordFormationTest.getSentenceKey2(), wfNew.getSentenceKey2());
			assertEquals(wordFormationTest.getSentence3(), wfNew.getSentence3());
			assertEquals(wordFormationTest.getSentenceKey3(), wfNew.getSentenceKey3());
			assertEquals(wordFormationTest.getHits(), wfNew.getHits());
			
			// update
			wordFormationTest.setLevel(LEVEL.CPE);
			wordFormationTest.setWordRoot("root_MOD");
			wordFormationTest.setSentence1("s1_MOD");
			wordFormationTest.setSentenceKey1("sk1_MOD");
			wordFormationTest.setSentence2("s2_MOD");
			wordFormationTest.setSentenceKey2("sk2_MOD");
			wordFormationTest.setSentence3("s3_MOD");
			wordFormationTest.setSentenceKey3("sk3_MOD");
			wordFormationTest.setHits(2);
			
			wordFormationRepository.save(wordFormationTest);
			wfNew = wordFormationRepository.get(wordFormationTest);
			
			assertEquals(LEVEL.CPE, wfNew.getLevel());
			assertEquals("root_MOD", wfNew.getWordRoot());
			assertEquals("s1_MOD", wfNew.getSentence1());
			assertEquals("sk1_MOD", wfNew.getSentenceKey1());
			assertEquals("s2_MOD", wfNew.getSentence2());
			assertEquals("sk2_MOD", wfNew.getSentenceKey2());
			assertEquals("s3_MOD", wfNew.getSentence3());
			assertEquals("sk3_MOD", wfNew.getSentenceKey3());
			assertEquals(2, wfNew.getHits().intValue());
			
			// delete
			wordFormationRepository.delete(wordFormationTest);
			wordFormationTest = wordFormationRepository.get(wordFormationTest);
			assertNull(wordFormationTest);
		}
		catch(Exception exc)
		{
			System.out.println(exc.toString());
			assertFalse(true);
		}
	}
	
	@Test
	public void WordFormationListTest()
	{
		List<WordFormation> wordFormationList;
		try
		{
			wordFormationTest = wordFormationRepository.save(wordFormationTest);
			
			wordFormationList = wordFormationRepository.getList(10, LEVEL.CAE);
			assertNotNull(wordFormationList);
			assertTrue(wordFormationList.size()>0);
			
			wordFormationRepository.delete(wordFormationTest);
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
