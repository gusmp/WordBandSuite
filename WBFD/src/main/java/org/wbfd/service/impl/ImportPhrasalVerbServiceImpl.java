package org.wbfd.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.wbfd.entity.PhrasalVerb;
import org.wbfd.repository.PhrasalVerbRepository;

public class ImportPhrasalVerbServiceImpl extends ImportServiceImpl
{
    PhrasalVerbRepository phrasalVerbRepository;

    public ImportPhrasalVerbServiceImpl(PhrasalVerbRepository phrasalVerbRepository)
    {
	this.phrasalVerbRepository = phrasalVerbRepository;
    }

    /*
     * # Structure
     * #
     * # Field delimiter: ;
     * #
     * # First field LEVEL (CAE / CPE)
     * # Second field: Meaning
     * # Third field: example
     * # Fourth field: answer
     * # (optional) Fifth field: date with format dd/mm/yyyy
     * #
     */
    protected String importLine(String line, Integer lineNumber)
    {
	try
	{
	    if ((line.startsWith("#") == false) && (line.trim().length() > 0))
	    {
		String fields[] = line.split(FIELD_DELIMITER);

		PhrasalVerb pv = new PhrasalVerb();

		pv.setLevel(getLevel(fields[0]));
		pv.setMeaning(getString(fields[1]));
		pv.setExample(getString(fields[2]));
		pv.setKey(getString(fields[3]));
		if (fields.length > 4)
		{
		    pv.setDate(getDate(fields[4]));
		}
		else
		{
		    pv.setDate(new Date());
		}
		pv.setHits(0);

		if (phrasalVerbRepository.get(pv) == null)
		{
		    phrasalVerbRepository.save(pv);
		}
	    }
	    return ("");
	}
	catch (Exception exc)
	{
	    return ("Error line number " + lineNumber + ": " + exc.toString() + "\n");
	}
    }

    public void importDataBase(String dataBase) throws ClassNotFoundException, SQLException
    {
	Connection conn = null;
	try
	{
	    conn = openConnection(dataBase);

	    List<PhrasalVerb> dumpTable = phrasalVerbRepository.dumpDataBase(conn);

	    for (PhrasalVerb entry : dumpTable)
	    {
		entry.setPhrasalVerbId(-1);
		if (phrasalVerbRepository.get(entry) == null)
		{
		    phrasalVerbRepository.save(entry);
		}
	    }
	}
	finally
	{
	    closeConnection(conn);
	}
    }

}
