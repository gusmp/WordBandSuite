package org.wbfd.service;

import java.sql.SQLException;

import org.wbfd.entity.Collocation;
import org.wbfd.enums.LEVEL;

public interface CollocationService
{
    public Collocation getNextCollocation(LEVEL level) throws SQLException;

    public Collocation save(Collocation collocation) throws SQLException;

    public void clearHits() throws SQLException;
}
