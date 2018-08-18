package org.wbfd.service;

import java.sql.SQLException;

import org.wbfd.entity.WordFormation;
import org.wbfd.enums.LEVEL;

public interface WordFormationService
{
    public WordFormation getNextWordFormation(LEVEL level) throws SQLException;

    public WordFormation save(WordFormation wordFormation) throws SQLException;

    public void clearHits() throws SQLException;
}
