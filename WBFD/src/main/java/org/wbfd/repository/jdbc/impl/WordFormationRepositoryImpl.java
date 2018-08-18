package org.wbfd.repository.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wbfd.entity.WordFormation;
import org.wbfd.enums.LEVEL;
import org.wbfd.repository.WordFormationRepository;

public class WordFormationRepositoryImpl implements WordFormationRepository
{

    private Connection connection;

    private String FIELD_WORDFORMATION_ID = "WORDFORMATION_ID";
    private String FIELD_DATE = "DATE";
    private String FIELD_LEVEL = "LEVEL";
    private String FIELD_WORDROOT = "WORDROOT";
    private String FIELD_SENTENCE1 = "SENTENCE1";
    private String FIELD_SENTENCE_KEY1 = "SENTENCE_KEY1";
    private String FIELD_SENTENCE2 = "SENTENCE2";
    private String FIELD_SENTENCE_KEY2 = "SENTENCE_KEY2";
    private String FIELD_SENTENCE3 = "SENTENCE3";
    private String FIELD_SENTENCE_KEY3 = "SENTENCE_KEY3";
    private String FIELD_HITS = "HITS";

    public WordFormationRepositoryImpl(Connection connection)
    {
	this.connection = connection;
    }

    private WordFormation convert2WordFormation(ResultSet rs) throws SQLException
    {
	WordFormation wf = new WordFormation();
	wf.setWordFormationId(rs.getInt(FIELD_WORDFORMATION_ID));
	wf.setDate(rs.getDate(FIELD_DATE));
	if (rs.getString(FIELD_LEVEL).equalsIgnoreCase(LEVEL.CAE.name()) == true)
	{
	    wf.setLevel(LEVEL.CAE);
	}
	else if (rs.getString(FIELD_LEVEL).equalsIgnoreCase(LEVEL.CPE.name()) == true)
	{
	    wf.setLevel(LEVEL.CPE);
	}

	wf.setWordRoot(rs.getString(FIELD_WORDROOT));
	wf.setSentence1(rs.getString(FIELD_SENTENCE1));
	wf.setSentenceKey1(rs.getString(FIELD_SENTENCE_KEY1));
	wf.setSentence2(rs.getString(FIELD_SENTENCE2));
	wf.setSentenceKey2(rs.getString(FIELD_SENTENCE_KEY2));
	wf.setSentence3(rs.getString(FIELD_SENTENCE3));
	wf.setSentenceKey3(rs.getString(FIELD_SENTENCE_KEY3));

	wf.setHits(rs.getInt(FIELD_HITS));

	return (wf);
    }

    private void loadValues(PreparedStatement pStmt, WordFormation wordFormation) throws SQLException
    {
	// DATE
	pStmt.setDate(1, new Date(wordFormation.getDate().getTime()));
	// LEVEL
	pStmt.setString(2, wordFormation.getLevel().name());
	// WORDROOT
	pStmt.setString(3, wordFormation.getWordRoot());
	// SENTENCE1
	pStmt.setString(4, wordFormation.getSentence1());
	// SENTENCE_KEY1
	pStmt.setString(5, wordFormation.getSentenceKey1());
	// SENTENCE2
	pStmt.setString(6, wordFormation.getSentence2());
	// SENTENCE_KEY2
	pStmt.setString(7, wordFormation.getSentenceKey2());
	// SENTENCE3
	pStmt.setString(8, wordFormation.getSentence3());
	// SENTENCE_KEY3
	pStmt.setString(9, wordFormation.getSentenceKey3());
	// HITS
	pStmt.setInt(10, wordFormation.getHits());

    }

    public WordFormation save(WordFormation wordFormation) throws SQLException
    {

	if (wordFormation.getWordFormationId() == -1)
	{
	    PreparedStatement pStmt = connection.prepareStatement("INSERT INTO WORD_FORMATION" + "(DATE, LEVEL, WORDROOT, SENTENCE1, SENTENCE_KEY1, SENTENCE2, SENTENCE_KEY2, " + "SENTENCE3, SENTENCE_KEY3, HITS )" + "VALUES(?,?,?,?,?,?,?,?,?,?)");

	    loadValues(pStmt, wordFormation);
	    pStmt.execute();

	    CallableStatement callableStatement = connection.prepareCall("CALL IDENTITY()");
	    ResultSet rs = callableStatement.executeQuery();
	    rs.next();
	    wordFormation.setWordFormationId(rs.getInt(1));
	    rs.close();
	    pStmt.close();
	}
	else
	{
	    PreparedStatement pStmt = connection.prepareStatement("UPDATE WORD_FORMATION SET " + "DATE=?, LEVEL=?, WORDROOT=?, SENTENCE1=?, SENTENCE_KEY1=?, SENTENCE2=?, SENTENCE_KEY2=?, " + "SENTENCE3=?, SENTENCE_KEY3=?, HITS=? " + "WHERE WORDFORMATION_ID=?");

	    loadValues(pStmt, wordFormation);
	    // PHRASALVERB_ID
	    pStmt.setInt(11, wordFormation.getWordFormationId());
	    pStmt.execute();
	}

	return (wordFormation);
    }

    private String buildSQL(WordFormation wordFormation, List<String> values)
    {
	StringBuilder sql = new StringBuilder("SELECT * FROM WORD_FORMATION WHERE 1=1");

	if (wordFormation.getWordFormationId() != -1)
	{
	    sql.append(" AND WORDFORMATION_ID=?");
	    values.add(wordFormation.getWordFormationId().toString());
	}
	if (wordFormation.getWordRoot() != null)
	{
	    sql.append(" AND WORDROOT=?");
	    values.add(wordFormation.getWordRoot());
	}
	if (wordFormation.getSentence1() != null)
	{
	    sql.append(" AND SENTENCE1=?");
	    values.add(wordFormation.getSentence1());
	}
	if (wordFormation.getSentenceKey1() != null)
	{
	    sql.append(" AND SENTENCE_KEY1=?");
	    values.add(wordFormation.getSentenceKey1());
	}
	if (wordFormation.getSentence2() != null)
	{
	    sql.append(" AND SENTENCE2=?");
	    values.add(wordFormation.getSentence2());
	}
	if (wordFormation.getSentenceKey2() != null)
	{
	    sql.append(" AND SENTENCE_KEY2=?");
	    values.add(wordFormation.getSentenceKey2());
	}
	if (wordFormation.getSentence3() != null)
	{
	    sql.append(" AND SENTENCE3=?");
	    values.add(wordFormation.getSentence3());
	}
	if (wordFormation.getSentenceKey3() != null)
	{
	    sql.append(" AND SENTENCE_KEY3=?");
	    values.add(wordFormation.getSentenceKey3());
	}

	return (sql.toString());
    }

    public WordFormation get(WordFormation wordFormation) throws SQLException
    {
	List<String> values = new ArrayList<String>(8);
	PreparedStatement pStmt = connection.prepareStatement(buildSQL(wordFormation, values));

	for (int i = 0; i < values.size(); i++)
	{
	    pStmt.setString(i + 1, values.get(i));
	}

	ResultSet rs = pStmt.executeQuery();
	WordFormation wf = null;
	if (rs.next() == true)
	{
	    wf = convert2WordFormation(rs);
	}

	return (wf);
    }

    public void delete(WordFormation wordFormation) throws SQLException
    {
	PreparedStatement pStmt = connection.prepareStatement("DELETE FROM WORD_FORMATION WHERE WORDFORMATION_ID=?");
	pStmt.setInt(1, wordFormation.getWordFormationId());
	pStmt.execute();
    }

    public List<WordFormation> getList(Integer maxResults, LEVEL level) throws SQLException
    {
	PreparedStatement pStmt;
	if (level == LEVEL.CPE_HELL_MODE)
	{
	    pStmt = connection.prepareStatement("SELECT * FROM WORD_FORMATION ORDER BY HITS ASC LIMIT " + maxResults);
	}
	else
	{
	    pStmt = connection.prepareStatement("SELECT * FROM WORD_FORMATION WHERE LEVEL=? ORDER BY HITS ASC LIMIT " + maxResults);
	    pStmt.setString(1, level.name());
	}
	List<WordFormation> wordFormationList = new ArrayList<WordFormation>(maxResults);

	ResultSet rs = pStmt.executeQuery();
	while (rs.next() == true)
	{
	    wordFormationList.add(convert2WordFormation(rs));
	}
	return (wordFormationList);
    }

    public void clearHits() throws SQLException
    {
	PreparedStatement pStmt = connection.prepareStatement("UPDATE WORD_FORMATION SET HITS=0");
	pStmt.execute();
    }

    public List<WordFormation> dumpDataBase(Connection connection) throws SQLException
    {
	List<WordFormation> dumpTable = new ArrayList<WordFormation>(1000);

	PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM WORD_FORMATION");
	ResultSet rs = pStmt.executeQuery();

	while (rs.next() == true)
	{
	    dumpTable.add(convert2WordFormation(rs));
	}

	return (dumpTable);
    }

}
