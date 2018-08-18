package org.wbfd.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.wbfd.entity.WordFormation;
import org.wbfd.enums.LEVEL;

public interface WordFormationRepository
{
    public WordFormation save(WordFormation wordFormation) throws SQLException;

    public WordFormation get(WordFormation wordFormation) throws SQLException;

    public void delete(WordFormation wordFormation) throws SQLException;

    public List<WordFormation> getList(Integer maxResults, LEVEL level) throws SQLException;

    public void clearHits() throws SQLException;

    public List<WordFormation> dumpDataBase(Connection connection) throws SQLException;
}
