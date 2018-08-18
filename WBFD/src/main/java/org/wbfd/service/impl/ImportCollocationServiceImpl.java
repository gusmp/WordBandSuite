package org.wbfd.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.wbfd.entity.Collocation;
import org.wbfd.enums.LEVEL;
import org.wbfd.repository.CollocationRepository;

public class ImportCollocationServiceImpl extends ImportServiceImpl
{
    CollocationRepository collocationRepository;

    public ImportCollocationServiceImpl(CollocationRepository collocationRepository)
    {
	this.collocationRepository = collocationRepository;
    }

    /*
     * # Structure
     * #
     * # Field delimiter: ;
     * #
     * # Field 1: LEVEL (CAE / CPE)
     * #
     * # For CPE:
     * # Field 2: First sentence
     * # Field 3: Second sencence
     * # Field 4: Third sencence
     * # Field 5: Key
     * # (optional) Field 6: date with format dd/mm/yyyy
     * #
     * # For CAE:
     * # Field 2: Phrase
     * # Field 3: Example
     * # Field 4: Key
     * # (optional) Field 5: date with format dd/mm/yyyy
     * #
     */
    protected String importLine(String line, Integer lineNumber)
    {
	try
	{
	    if ((line.startsWith("#") == false) && (line.trim().length() > 0))
	    {
		String fields[] = line.split(FIELD_DELIMITER);

		Collocation co = new Collocation();

		co.setLevel(getLevel(fields[0]));

		if (co.getLevel() == LEVEL.CAE)
		{
		    importCaeLine(fields, co);
		}
		else
		{
		    importCpeLine(fields, co);
		}

		co.setHits(0);

		if (collocationRepository.get(co) == null)
		{
		    collocationRepository.save(co);
		}
	    }
	    return ("");
	}
	catch (Exception exc)
	{
	    return ("Error line number " + lineNumber + ": " + exc.toString() + "\n");
	}
    }

    /*
     * # Field 2: Phrase
     * # Field 3: Example
     * # Field 4: Key
     * # (optional) Field 5: date with format dd/mm/yyyy
     */
    private Collocation importCaeLine(String[] fields, Collocation co) throws ParseException
    {
	co.setPhrase(getString(fields[1]));
	co.setExample(getString(fields[2]));
	co.setKey(getString(fields[3]));
	if (fields.length > 3)
	{
	    co.setDate(getDate(fields[4]));
	}
	else
	{
	    co.setDate(new Date());
	}

	return (co);
    }

    /*
     * # Field 2: First sentence
     * # Field 3: Second sencence
     * # Field 4: Third sencence
     * # Field 5: Key
     * # (optional) Field 6: date with format dd/mm/yyyy
     */
    private Collocation importCpeLine(String[] fields, Collocation co) throws ParseException
    {
	co.setSentence1(getString(fields[1]));
	co.setSentence2(getString(fields[2]));
	co.setSentence3(getString(fields[3]));
	co.setKey(getString(fields[4]));
	if (fields.length > 4)
	{
	    co.setDate(getDate(fields[5]));
	}
	else
	{
	    co.setDate(new Date());
	}

	return (co);
    }

    public void importDataBase(String dataBase) throws ClassNotFoundException, SQLException
    {
	Connection conn = null;
	try
	{
	    conn = openConnection(dataBase);

	    List<Collocation> dumpTable = collocationRepository.dumpDataBase(conn);

	    for (Collocation entry : dumpTable)
	    {
		entry.setCollocationId(-1);
		if (collocationRepository.get(entry) == null)
		{
		    collocationRepository.save(entry);
		}
	    }
	}
	finally
	{
	    closeConnection(conn);
	}
    }
}
