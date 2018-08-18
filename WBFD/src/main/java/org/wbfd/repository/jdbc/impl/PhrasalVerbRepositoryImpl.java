package org.wbfd.repository.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wbfd.entity.PhrasalVerb;
import org.wbfd.enums.LEVEL;
import org.wbfd.repository.PhrasalVerbRepository;

public class PhrasalVerbRepositoryImpl implements PhrasalVerbRepository
{

    private Connection connection;

    private String FIELD_PHRASALVERB_ID = "PHRASALVERB_ID";
    private String FIELD_DATE = "DATE";
    private String FIELD_LEVEL = "LEVEL";
    private String FIELD_MEANING = "MEANING";
    private String FIELD_EXAMPLE = "EXAMPLE";
    private String FIELD_KEY = "KEY";
    private String FIELD_HITS = "HITS";

    public PhrasalVerbRepositoryImpl(Connection connection)
    {
	this.connection = connection;
    }

    private PhrasalVerb convert2PhrasalVerb(ResultSet rs) throws SQLException
    {
	PhrasalVerb pv = new PhrasalVerb();
	pv.setPhrasalVerbId(rs.getInt(FIELD_PHRASALVERB_ID));
	pv.setDate(rs.getDate(FIELD_DATE));
	if (rs.getString(FIELD_LEVEL).equalsIgnoreCase(LEVEL.CAE.name()) == true)
	{
	    pv.setLevel(LEVEL.CAE);
	}
	else if (rs.getString(FIELD_LEVEL).equalsIgnoreCase(LEVEL.CPE.name()) == true)
	{
	    pv.setLevel(LEVEL.CPE);
	}

	pv.setMeaning(rs.getString(FIELD_MEANING));
	pv.setExample(rs.getString(FIELD_EXAMPLE));
	pv.setKey(rs.getString(FIELD_KEY));
	pv.setHits(rs.getInt(FIELD_HITS));

	return (pv);
    }

    private void loadValues(PreparedStatement pStmt, PhrasalVerb phrasalVerb) throws SQLException
    {
	// DATE
	pStmt.setDate(1, new Date(phrasalVerb.getDate().getTime()));
	// LEVEL
	pStmt.setString(2, phrasalVerb.getLevel().name());
	// MEANING
	pStmt.setString(3, phrasalVerb.getMeaning());
	// EXAMPLE
	pStmt.setString(4, phrasalVerb.getExample());
	// KEY
	pStmt.setString(5, phrasalVerb.getKey());
	// HITS
	pStmt.setInt(6, phrasalVerb.getHits());
    }

    public PhrasalVerb save(PhrasalVerb phrasalVerb) throws SQLException
    {

	if (phrasalVerb.getPhrasalVerbId() == -1)
	{
	    PreparedStatement pStmt = connection.prepareStatement("INSERT INTO PHRASAL_VERBS" + "(DATE, LEVEL, MEANING, EXAMPLE, KEY, HITS )" + "VALUES(?,?,?,?,?,?)");

	    loadValues(pStmt, phrasalVerb);
	    pStmt.execute();

	    CallableStatement callableStatement = connection.prepareCall("CALL IDENTITY()");
	    ResultSet rs = callableStatement.executeQuery();
	    rs.next();
	    phrasalVerb.setPhrasalVerbId(rs.getInt(1));
	    rs.close();
	    pStmt.close();
	}
	else
	{
	    PreparedStatement pStmt = connection.prepareStatement("UPDATE PHRASAL_VERBS SET " + "DATE=?, LEVEL=?, MEANING=?, EXAMPLE=?, KEY=?, HITS=? WHERE PHRASALVERB_ID=?");

	    loadValues(pStmt, phrasalVerb);
	    // PHRASALVERB_ID
	    pStmt.setInt(7, phrasalVerb.getPhrasalVerbId());

	    pStmt.execute();
	}

	return (phrasalVerb);
    }

    private String buildSQL(PhrasalVerb phrasalVerb, List<String> values)
    {
	StringBuilder sql = new StringBuilder("SELECT * FROM PHRASAL_VERBS WHERE 1=1");
	if (phrasalVerb.getPhrasalVerbId() != -1)
	{
	    sql.append(" AND PHRASALVERB_ID=?");
	    values.add(phrasalVerb.getPhrasalVerbId().toString());
	}
	if (phrasalVerb.getMeaning() != null)
	{
	    sql.append(" AND MEANING=?");
	    values.add(phrasalVerb.getMeaning());
	}
	if (phrasalVerb.getExample() != null)
	{
	    sql.append(" AND EXAMPLE=?");
	    values.add(phrasalVerb.getExample());
	}
	if (phrasalVerb.getKey() != null)
	{
	    sql.append(" AND KEY=?");
	    values.add(phrasalVerb.getKey());
	}

	return (sql.toString());
    }

    public PhrasalVerb get(PhrasalVerb phrasalVerb) throws SQLException
    {
	List<String> values = new ArrayList<String>(4);
	PreparedStatement pStmt = connection.prepareStatement(buildSQL(phrasalVerb, values));

	for (int i = 0; i < values.size(); i++)
	{
	    pStmt.setString(i + 1, values.get(i));
	}

	ResultSet rs = pStmt.executeQuery();
	PhrasalVerb pv = null;
	if (rs.next() == true)
	{
	    pv = convert2PhrasalVerb(rs);
	}

	return (pv);
    }

    public void delete(PhrasalVerb phrasalVerb) throws SQLException
    {
	PreparedStatement pStmt = connection.prepareStatement("DELETE FROM PHRASAL_VERBS WHERE PHRASALVERB_ID=?");
	pStmt.setInt(1, phrasalVerb.getPhrasalVerbId());
	pStmt.execute();
    }

    public List<PhrasalVerb> getList(Integer maxResults, LEVEL level) throws SQLException
    {
	PreparedStatement pStmt;
	if (level == LEVEL.CPE_HELL_MODE)
	{
	    pStmt = connection.prepareStatement("SELECT * FROM PHRASAL_VERBS ORDER BY HITS ASC LIMIT " + maxResults);
	}
	else
	{
	    pStmt = connection.prepareStatement("SELECT * FROM PHRASAL_VERBS WHERE LEVEL=? ORDER BY HITS ASC LIMIT " + maxResults);
	    pStmt.setString(1, level.name());
	}

	List<PhrasalVerb> phrasalVerbList = new ArrayList<PhrasalVerb>(maxResults);

	ResultSet rs = pStmt.executeQuery();
	while (rs.next() == true)
	{
	    phrasalVerbList.add(convert2PhrasalVerb(rs));
	}
	return (phrasalVerbList);
    }

    public void clearHits() throws SQLException
    {
	PreparedStatement pStmt = connection.prepareStatement("UPDATE PHRASAL_VERBS SET HITS=0");
	pStmt.execute();
    }

    public List<PhrasalVerb> dumpDataBase(Connection connection) throws SQLException
    {
	List<PhrasalVerb> dumpTable = new ArrayList<PhrasalVerb>(1000);

	PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM PHRASAL_VERBS");
	ResultSet rs = pStmt.executeQuery();

	while (rs.next() == true)
	{
	    dumpTable.add(convert2PhrasalVerb(rs));
	}

	return (dumpTable);
    }

}
