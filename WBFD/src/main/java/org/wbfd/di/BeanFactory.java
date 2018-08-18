package org.wbfd.di;

import java.sql.SQLException;
import org.wbfd.db.DataBaseFactory;
import org.wbfd.repository.CollocationRepository;
import org.wbfd.repository.PhrasalVerbRepository;
import org.wbfd.repository.WordFormationRepository;
import org.wbfd.repository.jdbc.impl.CollocationRepositoryImpl;
import org.wbfd.repository.jdbc.impl.PhrasalVerbRepositoryImpl;
import org.wbfd.repository.jdbc.impl.WordFormationRepositoryImpl;
import org.wbfd.service.CollocationService;
import org.wbfd.service.ImportService;
import org.wbfd.service.LocaleService;
import org.wbfd.service.PhrasalVerbService;
import org.wbfd.service.WordFormationService;
import org.wbfd.service.impl.CollocationServiceImpl;
import org.wbfd.service.impl.ImportCollocationServiceImpl;
import org.wbfd.service.impl.ImportPhrasalVerbServiceImpl;
import org.wbfd.service.impl.ImportRawFormatServiceImpl;
import org.wbfd.service.impl.ImportWordFormationServiceImpl;
import org.wbfd.service.impl.LocaleServiceImpl;
import org.wbfd.service.impl.PhrasalVerbServiceImpl;
import org.wbfd.service.impl.WordFormationServiceImpl;

public class BeanFactory
{

    private DataBaseFactory dbf;

    private PhrasalVerbRepository phrasalVerbRepository;
    private PhrasalVerbService phrasalVerbService;
    private ImportService importPhrasalVerbService;

    private WordFormationRepository wordFormationRepository;
    private WordFormationService wordFormationService;
    private ImportService importWordFormationService;

    private CollocationRepository collocationRepository;
    private CollocationService collocationService;
    private ImportService importCollocationService;
    
    private ImportService importRawFormatService;

    private LocaleService localeService;

    public BeanFactory() throws ClassNotFoundException, SQLException
    {
	dbf = new DataBaseFactory();

	// Phrasal Verbs
	phrasalVerbRepository = new PhrasalVerbRepositoryImpl(dbf.getConnection());
	phrasalVerbService = new PhrasalVerbServiceImpl(phrasalVerbRepository);
	importPhrasalVerbService = new ImportPhrasalVerbServiceImpl(phrasalVerbRepository);

	// Word Formation
	wordFormationRepository = new WordFormationRepositoryImpl(dbf.getConnection());
	wordFormationService = new WordFormationServiceImpl(wordFormationRepository);
	importWordFormationService = new ImportWordFormationServiceImpl(wordFormationRepository);

	// Collocations
	collocationRepository = new CollocationRepositoryImpl(dbf.getConnection());
	collocationService = new CollocationServiceImpl(collocationRepository);
	importCollocationService = new ImportCollocationServiceImpl(collocationRepository);
	
	// Raw format
	importRawFormatService = new ImportRawFormatServiceImpl(phrasalVerbRepository, collocationRepository, wordFormationRepository);

	// Locale
	localeService = new LocaleServiceImpl();

    }

    public DataBaseFactory getDbf()
    {
	return dbf;
    }

    public void setDbf(DataBaseFactory dbf)
    {
	this.dbf = dbf;
    }

    // Phrasal Verbs
    public PhrasalVerbRepository getPhrasalVerbRepository()
    {
	return phrasalVerbRepository;
    }

    public void setPhrasalVerbRepository(PhrasalVerbRepository phrasalVerbRepository)
    {
	this.phrasalVerbRepository = phrasalVerbRepository;
    }

    public PhrasalVerbService getPhrasalVerbService()
    {
	return phrasalVerbService;
    }

    public void setPhrasalVerbService(PhrasalVerbService phrasalVerbService)
    {
	this.phrasalVerbService = phrasalVerbService;
    }

    public ImportService getImportPhrasalVerbService()
    {
	return importPhrasalVerbService;
    }

    public void setImportPhrasalVerbService(ImportService importPhrasalVerbService)
    {
	this.importPhrasalVerbService = importPhrasalVerbService;
    }

    // Word Formation
    public WordFormationRepository getWordFormationRepository()
    {
	return wordFormationRepository;
    }

    public void setWordFormationRepository(WordFormationRepository wordFormationRepository)
    {
	this.wordFormationRepository = wordFormationRepository;
    }

    public WordFormationService getWordFormationService()
    {
	return wordFormationService;
    }

    public void setWordFormationService(WordFormationService wordFormationService)
    {
	this.wordFormationService = wordFormationService;
    }

    public ImportService getImportWordFormationService()
    {
	return importWordFormationService;
    }

    public void setImportWordFormationService(ImportService importWordFormationService)
    {
	this.importWordFormationService = importWordFormationService;
    }

    // Collocations
    public CollocationRepository getCollocationRepository()
    {
	return collocationRepository;
    }

    public void setCollocationRepository(CollocationRepository collocationRepository)
    {
	this.collocationRepository = collocationRepository;
    }

    public CollocationService getCollocationService()
    {
	return collocationService;
    }

    public void setCollocationService(CollocationService collocationService)
    {
	this.collocationService = collocationService;
    }

    public ImportService getImportCollocationService()
    {
	return importCollocationService;
    }

    public void setImportCollocationService(ImportService importCollocationService)
    {
	this.importCollocationService = importCollocationService;
    }
    
    // Import
    public ImportService getImportRawFormatService()
    {
	return importRawFormatService;
    }
    
    public void setImportRawFormatService(ImportService importRawFormatService)
    {
	this.importRawFormatService = importRawFormatService;
    }
    
    // Locale
    public LocaleService getLocaleService()
    {
	return localeService;
    }

    public void setLocaleService(LocaleService localeService)
    {
	this.localeService = localeService;
    }
}
