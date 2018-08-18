package org.wbfd.db;

import java.sql.Connection;
import java.sql.ResultSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wbfd.db.DataBaseFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DataBaseFactoryTest 
{
	DataBaseFactory dbf;
	
	@Before
	public void init()
	{
		dbf = new DataBaseFactory();
	}
	
	@Test
	public void testDB()
	{
		try
		{
			Connection connection = dbf.getConnection();
			ResultSet rs = connection.prepareStatement("SELECT 1 AS COL FROM INFORMATION_SCHEMA.SYSTEM_USERS").executeQuery();
			rs.next();
			assertEquals(1, rs.getInt("COL"));
		}
		catch(Exception exc)
		{
			System.out.println(exc.toString());
			assertFalse(true);
		}
	}
	
	//@Test
	public void testCreateSchema()
	{
		try
		{
			dbf.createSchema();
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
		dbf.closeConnection();
	}

}
