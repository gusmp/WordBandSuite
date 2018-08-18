package org.wbfd.repository.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wbfd.entity.Collocation;
import org.wbfd.enums.LEVEL;
import org.wbfd.repository.CollocationRepository;

public class CollocationRepositoryImpl implements CollocationRepository
{

    private Connection connection;

    private String FIELD_COLLOCATION_ID = "COLLOCATION_ID";
    private String FIELD_DATE = "DATE";
    private String FIELD_LEVEL = "LEVEL";
    private String FIELD_HITS = "HITS";
    private String FIELD_KEY = "KEY";
    // CPE
    private String FIELD_SENTENCE1 = "SENTENCE1";
    private String FIELD_SENTENCE2 = "SENTENCE2";
    private String FIELD_SENTENCE3 = "SENTENCE3";
    // CAE
    private String FIELD_PHRASE = "PHRASE";
    private String FIELD_EXAMPLE = "EXAMPLE";

    public CollocationRepositoryImpl(Connection connection)
    {
	this.connection = connection;
    }

    private Collocation convert2Collocation(ResultSet rs) throws SQLException
    {
	Collocation co = new Collocation();
	co.setCollocationId(rs.getInt(FIELD_COLLOCATION_ID));
	co.setDate(rs.getDate(FIELD_DATE));
	if (rs.getString(FIELD_LEVEL).equalsIgnoreCase(LEVEL.CAE.name()) == true)
	{
	    co.setLevel(LEVEL.CAE);
	}
	else if (rs.getString(FIELD_LEVEL).equalsIgnoreCase(LEVEL.CPE.name()) == true)
	{
	    co.setLevel(LEVEL.CPE);
	}
	co.setHits(rs.getInt(FIELD_HITS));
	co.setKey(rs.getString(FIELD_KEY));

	co.setSentence1(rs.getString(FIELD_SENTENCE1));
	co.setSentence2(rs.getString(FIELD_SENTENCE2));
	co.setSentence3(rs.getString(FIELD_SENTENCE3));

	co.setPhrase(rs.getString(FIELD_PHRASE));
	co.setExample(rs.getString(FIELD_EXAMPLE));

	return (co);
    }

    private void loadValues(PreparedStatement pStmt, Collocation collocation) throws SQLException
    {
	// DATE
	pStmt.setDate(1, new Date(collocation.getDate().getTime()));
	// LEVEL
	pStmt.setString(2, collocation.getLevel().name());
	// SENTENCE1
	pStmt.setString(3, collocation.getSentence1());
	// SENTENCE2
	pStmt.setString(4, collocation.getSentence2());
	// SENTENCE3
	pStmt.setString(5, collocation.getSentence3());
	// PHRASE
	pStmt.setString(6, collocation.getPhrase());
	// EXAMPLE
	pStmt.setString(7, collocation.getExample());
	// KEY
	pStmt.setString(8, collocation.getKey());
	// HITS
	pStmt.setInt(9, collocation.getHits());
    }

    public Collocation save(Collocation collocation) throws SQLException
    {

	if (collocation.getCollocationId() == -1)
	{
	    PreparedStatement pStmt = connection.prepareStatement("INSERT INTO COLLOCATION" + "(DATE, LEVEL, SENTENCE1, SENTENCE2, " + "SENTENCE3, PHRASE, EXAMPLE, KEY, HITS )" + "VALUES(?,?,?,?,?,?,?,?,?)");

	    loadValues(pStmt, collocation);

	    pStmt.execute();

	    CallableStatement callableStatement = connection.prepareCall("CALL IDENTITY()");
	    ResultSet rs = callableStatement.executeQuery();
	    rs.next();
	    collocation.setCollocationId(rs.getInt(1));
	    rs.close();
	    pStmt.close();
	}
	else
	{
	    PreparedStatement pStmt = connection.prepareStatement("UPDATE COLLOCATION SET " + " DATE=?, LEVEL=?, SENTENCE1=?, SENTENCE2=?, " + "SENTENCE3=?, PHRASE=?, EXAMPLE=?, KEY=?, HITS=? " + "WHERE COLLOCATION_ID=?");

	    loadValues(pStmt, collocation);
	    // PHRASALVERB_ID
	    pStmt.setInt(10, collocation.getCollocationId());

	    pStmt.execute();
	}

	return (collocation);
    }

    private String buildSQL(Collocation collocation, List<String> value)
    {
	StringBuilder sql = new StringBuilder("SELECT * FROM COLLOCATION WHERE 1=1");

	if (collocation.getCollocationId() != -1)
	{
	    sql.append(" AND COLLOCATION_ID=?");
	    value.add(collocation.getCollocationId().toString());
	}
	if (collocation.getKey() != null)
	{
	    sql.append(" AND KEY=?");
	    value.add(collocation.getKey());
	}

	// specific fields
	if (collocation.getLevel() == LEVEL.CAE)
	{
	    if (collocation.getPhrase() != null)
	    {
		sql.append(" AND PHRASE=?");
		value.add(collocation.getPhrase());
	    }
	    if (collocation.getExample() != null)
	    {
		sql.append(" AND EXAMPLE=?");
		value.add(collocation.getExample());
	    }
	}
	else
	{
	    if (collocation.getSentence1() != null)
	    {
		sql.append(" AND SENTENCE1=?");
		value.add(collocation.getSentence1());
	    }
	    if (collocation.getSentence2() != null)
	    {
		sql.append(" AND SENTENCE2=?");
		value.add(collocation.getSentence2());
	    }
	    if (collocation.getSentence3() != null)
	    {
		sql.append(" AND SENTENCE3=?");
		value.add(collocation.getSentence3());
	    }
	}

	return (sql.toString());
    }

    public Collocation get(Collocation collocation) throws SQLException
    {
	List<String> values = new ArrayList<String>(5);
	PreparedStatement pStmt = connection.prepareStatement(buildSQL(collocation, values));

	for (int i = 0; i < values.size(); i++)
	{
	    pStmt.setString(i + 1, values.get(i));
	}

	ResultSet rs = pStmt.executeQuery();
	Collocation co = null;
	if (rs.next() == true)
	{
	    co = convert2Collocation(rs);
	}

	return (co);
    }

    public void delete(Collocation collocation) throws SQLException
    {
	PreparedStatement pStmt = connection.prepareStatement("DELETE FROM COLLOCATION WHERE COLLOCATION_ID=?");
	pStmt.setInt(1, collocation.getCollocationId());
	pStmt.execute();
    }

    public List<Collocation> getList(Integer maxResults, LEVEL level) throws SQLException
    {
	PreparedStatement pStmt;
	if (level == LEVEL.CPE_HELL_MODE)
	{
	    pStmt = connection.prepareStatement("SELECT * FROM COLLOCATION ORDER BY HITS ASC LIMIT " + maxResults);
	}
	else
	{
	    pStmt = connection.prepareStatement("SELECT * FROM COLLOCATION WHERE LEVEL=? ORDER BY HITS ASC LIMIT " + maxResults);
	    pStmt.setString(1, level.name());
	}
	List<Collocation> collocationList = new ArrayList<Collocation>(maxResults);

	ResultSet rs = pStmt.executeQuery();
	while (rs.next() == true)
	{
	    collocationList.add(convert2Collocation(rs));
	}
	return (collocationList);
    }

    public void clearHits() throws SQLException
    {
	PreparedStatement pStmt = connection.prepareStatement("UPDATE COLLOCATION SET HITS=0");
	pStmt.execute();
    }

    public List<Collocation> dumpDataBase(Connection connection) throws SQLException
    {
	List<Collocation> dumpTable = new ArrayList<Collocation>(1000);

	PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM COLLOCATION");
	ResultSet rs = pStmt.executeQuery();

	while (rs.next() == true)
	{
	    dumpTable.add(convert2Collocation(rs));
	}

	return (dumpTable);
    }

}
