package org.wbfd.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.wbfd.entity.Collocation;
import org.wbfd.entity.PhrasalVerb;
import org.wbfd.entity.WordFormation;
import org.wbfd.enums.EXERCISE_HEADER;
import org.wbfd.enums.LEVEL;
import org.wbfd.repository.CollocationRepository;
import org.wbfd.repository.PhrasalVerbRepository;
import org.wbfd.repository.WordFormationRepository;

public class ImportRawFormatServiceImpl extends ImportServiceImpl
{
    PhrasalVerbRepository phrasalVerbRepository;
    CollocationRepository collocationRepository;
    WordFormationRepository wordFormationRepository;

    public ImportRawFormatServiceImpl(PhrasalVerbRepository phrasalVerbRepository, CollocationRepository collocationRepository, WordFormationRepository wordFormationRepository)
    {
	this.phrasalVerbRepository = phrasalVerbRepository;
	this.collocationRepository = collocationRepository;
	this.wordFormationRepository = wordFormationRepository;
    }

    public void importDataBase(String dataBase) throws ClassNotFoundException, SQLException
    {

	EXERCISE_HEADER currentExercise = null;

	try
	{
	    BufferedReader reader = new BufferedReader(new FileReader(dataBase));
	    String line = "";
	    while ((line = reader.readLine()) != null)
	    {
		line = line.trim();
		if (line.length() == 0)
		    continue;
		if (line.contains("===") == true)
		    continue;

		if (line.equalsIgnoreCase(EXERCISE_HEADER.PV.getHeader()) == true)
		{
		    currentExercise = EXERCISE_HEADER.PV;
		    continue;
		}
		if (line.equalsIgnoreCase(EXERCISE_HEADER.WF.getHeader()) == true)
		{
		    currentExercise = EXERCISE_HEADER.WF;
		    continue;
		}
		if (line.equalsIgnoreCase(EXERCISE_HEADER.CO.getHeader()) == true)
		{
		    currentExercise = EXERCISE_HEADER.CO;
		    continue;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateExercise = sdf.parse(line);

		if (currentExercise == EXERCISE_HEADER.PV)
		{
		    importPhrasalVerb(dateExercise, reader);
		}
		else if (currentExercise == EXERCISE_HEADER.WF)
		{
		    importWordFormation(dateExercise, reader);
		}
		else if (currentExercise == EXERCISE_HEADER.CO)
		{
		    importCollocation(dateExercise, reader);
		}
	    }

	    reader.close();

	}
	catch (Exception exc)
	{
	    throw new RuntimeException(exc.toString());
	}
    }

    private void importPhrasalVerb(Date datePv, BufferedReader reader) throws ParseException, IOException, SQLException
    {
	PhrasalVerb pvCPE = new PhrasalVerb();
	pvCPE.setDate(datePv);

	pvCPE.setMeaning(getLine(reader));
	pvCPE.setExample(getLine(reader));
	pvCPE.setKey(getLine(reader));
	pvCPE.setLevel(LEVEL.CPE);

	phrasalVerbRepository.save(pvCPE);

	PhrasalVerb pvCAE = new PhrasalVerb();
	pvCAE.setDate(datePv);

	pvCAE.setMeaning(getLine(reader));
	pvCAE.setExample(getLine(reader));
	pvCAE.setKey(getLine(reader));
	pvCAE.setLevel(LEVEL.CAE);

	phrasalVerbRepository.save(pvCAE);
    }

    private void importWordFormation(Date dateWf, BufferedReader reader) throws ParseException, IOException, SQLException
    {
	WordFormation wfCPE = new WordFormation();

	wfCPE.setDate(dateWf);
	wfCPE.setLevel(LEVEL.CPE);
	wfCPE.setWordRoot(getLine(reader));
	wfCPE.setSentence1(getLine(reader));
	wfCPE.setSentence2(getLine(reader));
	wfCPE.setSentence3(getLine(reader));

	wfCPE.setSentenceKey1(getLine(reader));
	wfCPE.setSentenceKey2(getLine(reader));
	wfCPE.setSentenceKey3(getLine(reader));

	wordFormationRepository.save(wfCPE);

	WordFormation wfCAE = new WordFormation();
	wfCAE.setDate(dateWf);
	wfCAE.setLevel(LEVEL.CAE);

	wfCAE.setWordRoot(getLine(reader));
	wfCAE.setSentence1(getLine(reader));
	wfCAE.setSentence2(getLine(reader));
	wfCAE.setSentence3(getLine(reader));

	wfCAE.setSentenceKey1(getLine(reader));
	wfCAE.setSentenceKey2(getLine(reader));
	wfCAE.setSentenceKey3(getLine(reader));

	wordFormationRepository.save(wfCAE);
    }

    private void importCollocation(Date dateCo, BufferedReader reader) throws ParseException, IOException, SQLException
    {
	Collocation coCPE = new Collocation();

	coCPE.setDate(dateCo);
	coCPE.setLevel(LEVEL.CPE);

	coCPE.setSentence1(getLine(reader));
	coCPE.setSentence2(getLine(reader));
	coCPE.setSentence3(getLine(reader));
	coCPE.setKey(getLine(reader));

	collocationRepository.save(coCPE);

	Collocation coCAE = new Collocation();

	coCAE.setDate(dateCo);
	coCAE.setLevel(LEVEL.CAE);

	coCAE.setPhrase(getLine(reader));
	coCAE.setExample(getLine(reader));
	coCAE.setKey(getLine(reader));

	collocationRepository.save(coCAE);

    }

    private String getLine(BufferedReader reader) throws IOException
    {
	String line = null;
	while ((line = reader.readLine()) != null)
	{
	    line = line.trim();
	    if (line.length() == 0)
		continue;
	    break;
	}

	return line;
    }

    @Override
    protected String importLine(String line, Integer lineNumber)
    {
	return null;
    }

}
