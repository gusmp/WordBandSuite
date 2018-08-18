package org.wbfd.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.wbfd.entity.PhrasalVerb;
import org.wbfd.enums.LEVEL;

public interface PhrasalVerbRepository
{
    public PhrasalVerb save(PhrasalVerb phrasalVerb) throws SQLException;

    public PhrasalVerb get(PhrasalVerb phrasalVerb) throws SQLException;

    public void delete(PhrasalVerb phrasalVerb) throws SQLException;

    public List<PhrasalVerb> getList(Integer maxResults, LEVEL level) throws SQLException;

    public void clearHits() throws SQLException;

    public List<PhrasalVerb> dumpDataBase(Connection connection) throws SQLException;
}
