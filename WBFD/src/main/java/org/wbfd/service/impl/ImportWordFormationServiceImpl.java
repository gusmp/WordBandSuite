package org.wbfd.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.wbfd.entity.WordFormation;
import org.wbfd.repository.WordFormationRepository;

public class ImportWordFormationServiceImpl extends ImportServiceImpl
{
    WordFormationRepository wordFormationRepository;

    public ImportWordFormationServiceImpl(WordFormationRepository wordFormationRepository)
    {
	this.wordFormationRepository = wordFormationRepository;
    }

    /*
     * # Structure
     * #
     * # Field delimiter: ;
     * #
     * # Field 1: LEVEL (CAE / CPE)
     * # Field 2: Root word
     * # Field 3: First sentence
     * # Field 4: Answer first sentence
     * # Field 5: Second sencence
     * # Field 6: Answer second sentence
     * # Field 7: Third sencence
     * # Field 8: Answer third sentence
     * # (optional) Field 9: date with format dd/mm/yyyy
     * #
     */
    protected String importLine(String line, Integer lineNumber)
    {
	try
	{
	    if ((line.startsWith("#") == false) && (line.trim().length() > 0))
	    {
		String fields[] = line.split(FIELD_DELIMITER);

		WordFormation wf = new WordFormation();

		wf.setLevel(getLevel(fields[0]));
		wf.setWordRoot(getString(fields[1]));

		wf.setSentence1(getString(fields[2]));
		wf.setSentenceKey1(getString(fields[3]));

		wf.setSentence2(getString(fields[4]));
		wf.setSentenceKey2(getString(fields[5]));

		wf.setSentence3(getString(fields[6]));
		wf.setSentenceKey3(getString(fields[7]));

		if (fields.length > 8)
		{
		    wf.setDate(getDate(fields[8]));
		}
		else
		{
		    wf.setDate(new Date());
		}
		wf.setHits(0);

		if (wordFormationRepository.get(wf) == null)
		{
		    wordFormationRepository.save(wf);
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

	    List<WordFormation> dumpTable = wordFormationRepository.dumpDataBase(conn);

	    for (WordFormation entry : dumpTable)
	    {
		entry.setWordFormationId(-1);
		if (wordFormationRepository.get(entry) == null)
		{
		    wordFormationRepository.save(entry);
		}
	    }
	}
	finally
	{
	    closeConnection(conn);
	}
    }

}
