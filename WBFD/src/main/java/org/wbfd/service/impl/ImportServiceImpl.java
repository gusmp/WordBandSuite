package org.wbfd.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.wbfd.enums.LEVEL;
import org.wbfd.service.ImportService;

public abstract class ImportServiceImpl implements ImportService
{
    protected String FIELD_DELIMITER = ";";

    protected abstract String importLine(String line, Integer lineNumber);

    public void importFile(String file) throws FileNotFoundException, IOException
    {
	String line;
	Integer lineNumber = 1;
	StringBuffer log = new StringBuffer();

	verifyFile(file);
	FileReader fin = new FileReader(file);
	BufferedReader bufferFileIn = new BufferedReader(fin);

	while ((line = bufferFileIn.readLine()) != null)
	{
	    log.append(importLine(line, lineNumber));
	    lineNumber++;
	}

	bufferFileIn.close();
	fin.close();
	if (log.length() > 0)
	{
	    throw new IOException(log.toString());
	}
    }

    protected Connection openConnection(String dataBase) throws ClassNotFoundException, SQLException
    {
	String dataBaseName = dataBase;
	if (dataBaseName.lastIndexOf(".") > 0)
	{
	    dataBaseName = dataBaseName.substring(0, dataBaseName.lastIndexOf("."));
	}
	Class.forName("org.hsqldb.jdbcDriver");
	Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + dataBaseName, "sa", "");
	return (connection);
    }

    protected void closeConnection(Connection connection)
    {
	try
	{
	    connection.createStatement().execute("SHUTDOWN");
	    connection.close();
	}
	catch (Exception exc)
	{
	}
    }

    protected void verifyFile(String file) throws IOException
    {
	if (file.endsWith(".csv") == false)
	{
	    throw new IOException("Wrong file format. File must end with the extension txt");
	}
    }

    protected LEVEL getLevel(String level) throws Exception
    {
	if (level.equalsIgnoreCase(LEVEL.CAE.name()) == true)
	{
	    return (LEVEL.CAE);
	}
	else if (level.equalsIgnoreCase(LEVEL.CPE.name()) == true)
	{
	    return (LEVEL.CPE);
	}
	else
	{
	    throw new Exception("First columns must be CAE or CPE");
	}
    }

    protected Date getDate(String date) throws ParseException
    {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	return (sdf.parse(date));
    }

    protected String getString(String data)
    {
	return (data.trim().toLowerCase());
    }

}
