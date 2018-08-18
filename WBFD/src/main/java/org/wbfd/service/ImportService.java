package org.wbfd.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface ImportService
{
    public void importFile(String file) throws FileNotFoundException, IOException;

    public void importDataBase(String dataBase) throws ClassNotFoundException, SQLException;

}
