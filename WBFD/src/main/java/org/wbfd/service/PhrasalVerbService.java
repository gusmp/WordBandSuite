package org.wbfd.service;

import java.sql.SQLException;

import org.wbfd.entity.PhrasalVerb;
import org.wbfd.enums.LEVEL;

public interface PhrasalVerbService
{
    public PhrasalVerb getNextPhrasalVerb(LEVEL level) throws SQLException;

    public PhrasalVerb save(PhrasalVerb phrasalVerb) throws SQLException;

    public void clearHits() throws SQLException;
}
